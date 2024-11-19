/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.tile.energy.storage;

import cpw.mods.fml.common.FMLLog;
import gregapi.code.TagData;
import gregapi.data.CS;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.oredict.OreDictPrefix;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Level;

import java.util.List;

import static gregapi.data.CS.*;

public class FuelDeburner extends MultiTileEntityBasicMachine {
    public TagData mEnergyTypeHeated = TD.Energy.HU;
    public long mTempMax = Integer.MAX_VALUE, mMassTotal=1,mMassSelf=1,maxStrictEUt=1024,mMassLast=1;
    public float mTemp = C, recipeBestTemp= C, recipeFactor = 0.1F;
    public static final int HUPerMassTemp = 10;
    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (mStopped) return 0;
        boolean tPositive = (aSize > 0);
        aSize = Math.abs(aSize);
        if (aEnergyType == mEnergyTypeHeated) {
            if (aDoInject) mTemp += (aSize * aAmount)*1F* HUPerMassTemp /mMassTotal;
            this.receivedEnergy.add(new MeterData(aEnergyType,aSize, aAmount));
            return aAmount;
        }
        if (aEnergyType == mEnergyTypeAccepted) {
            if(aDoInject && aSize > getEnergySizeInputMax(aEnergyType, aSide))overcharge(aSide,aEnergyType);
            if (aDoInject) mStateNew = tPositive;
            long tInput = Math.min(mInputMax - mEnergy, aSize * aAmount), tConsumed = Math.min(aAmount, (tInput/aSize) + (tInput%aSize!=0?1:0));
            if (aDoInject) mEnergy += tConsumed * aSize;
            this.receivedEnergy.add(new MeterData(aEnergyType, aSize, tConsumed));
            return tConsumed;
        }
        return 0;
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        mSpecialIsStartEnergy=false;
        if (aNBT.hasKey(NBT_ENERGY_ACCEPTED_2)) mEnergyTypeHeated = TagData.createTagData(aNBT.getString(NBT_ENERGY_ACCEPTED_2));
        if (aNBT.hasKey("ktfru.nbt.massSelf")) mMassTotal = mMassSelf = aNBT.getLong("ktfru.nbt.massSelf");
        if (aNBT.hasKey(NBT_TEMPERATURE+".max")) mTempMax = aNBT.getLong(NBT_TEMPERATURE+".max");

        if (aNBT.hasKey(NBT_TEMPERATURE)) mTemp = aNBT.getFloat(NBT_TEMPERATURE);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        aNBT.setFloat(NBT_TEMPERATURE, mTemp);
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(aTool.equals(TOOL_thermometer)){
            if(aChatReturn!=null)aChatReturn.add("Temperature: " + mTemp + " / " + mTempMax);
            return 1;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if(mInventoryChanged){
            mMassLast = mMassTotal;
            mMassTotal = mMassSelf;
            for (int i = 0; i < invsize(); i++) {
                if(!slotHas(i) || OreDictManager.INSTANCE.getItemData(slot(i)) == null)continue;
                mMassTotal += (long) OreDictManager.INSTANCE.getItemData(slot(i)).getAllMaterialStacks().stream().mapToDouble(OreDictMaterialStack::weight).sum();
            }
            for (FluidTankGT tankGT:mTanksInput) {
                if(tankGT==null || tankGT.isEmpty())continue;
                mMassTotal += tankGT.fluid().getDensity()* tankGT.amount()/1000 ;
            }
            if(mMassLast<mMassTotal) {
                float deltaTemp = mTemp - C;
                cooldown(deltaTemp* (mMassLast-mMassTotal)/mMassTotal);
            }
        }
        super.onTick2(aTimer, aIsServerSide);
    }

    @Override
    public boolean doActive(long aTimer, long aEnergy) {
        if(mTemp > mTempMax)overcharge(mInputMax,TD.Energy.HU);
        //Natural Cooldown
        cooldown(Math.min(512F/mMassTotal, Math.abs(mTemp-C)));

        //Promote extra progress when Temp is Suitable, and if not suitable decreases the progress
        if (mMaxProgress > 0 && !(mSpecialIsStartEnergy && mChargeRequirement > 0) && mProgress <= mMaxProgress) {
            mProgress += (long) (aEnergy * getTempFactor());
        }
        return super.doActive(aTimer, aEnergy);
    }


    @Override
    public int checkRecipe(boolean aApplyRecipe, boolean aUseAutoIO) {
        int i = super.checkRecipe(aApplyRecipe, aUseAutoIO);

        if(mCurrentRecipe!=null) {
            recipeBestTemp = mCurrentRecipe.mSpecialValue;
            recipeFactor = 20.1F-20*Math.min(1F, mCurrentRecipe.mEUt*1F/maxStrictEUt);
        }
        return i;
    }

    public void cooldown(float value){
        if(Math.abs(mTemp-C)< value)mTemp=C;
        value=Math.abs(value);
        mTemp -= mTemp>C ? value: -value;
    }
    public float getTempFactor() {
        float delta = Math.abs(mTemp - recipeBestTemp);
        return Math.min(128, ((256*recipeFactor / (float)Math.sqrt(delta)) - (float) Math.pow(delta, 1.5F) / recipeFactor )/recipeFactor);
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.tileentity.fueldeburner";
    }
}
