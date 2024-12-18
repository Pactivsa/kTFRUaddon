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

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.IReversibleReactionVessel;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.tileentity.data.ITileEntityTemperature;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

import static gregapi.data.CS.*;

public class FuelDeburner extends MultiTileEntityBasicMachine implements IReversibleReactionVessel, ITileEntityTemperature {

    public TagData mEnergyTypeHeated = TD.Energy.HU;
    public long mTempMax = Integer.MAX_VALUE, mMass =1,mMassSelf=1,maxStrictEUt=1024;
    public float mTemp = C, recipeBestTemp= C, recipeFactor = 0.1F;
    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (mStopped) return 0;
        boolean tPositive = (aSize > 0);
        aSize = Math.abs(aSize);
        if (aEnergyType == mEnergyTypeHeated) {
            this.receivedEnergy.add(new MeterData(aEnergyType, aSize, onHeat(aSize, aAmount, aDoInject)));
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
        if (aNBT.hasKey("ktfru.nbt.massSelf")) mMass = mMassSelf = aNBT.getLong("ktfru.nbt.massSelf");
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
            if(aChatReturn!=null)aChatReturn.add(LH.get(I18nHandler.TEMPERATURE) +": "+ mTemp + " / " + mTempMax);
            return 1;
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if(mInventoryChanged){
            updateMass(this, mTanksInput);
        }
        //Natural Cooldown
        cooldown(Math.min(10F*Math.abs(mTemp-C)/ mMass, Math.abs(mTemp-C)));
        super.onTick2(aTimer, aIsServerSide);
    }
    @Override
    public boolean doActive(long aTimer, long aEnergy) {
        if(mTemp > mTempMax)overcharge(mInputMax,mEnergyTypeHeated);

        //Promote extra progress when Temp is Suitable, and if not suitable decreases the progress
        if (mMaxProgress > 0 && !(mSpecialIsStartEnergy && mChargeRequirement > 0) && mProgress <= mMaxProgress) {
            mProgress += (long) (aEnergy * getTempFactor());
        }
        if(mProgress<0)mProgress=0;
        return super.doActive(aTimer, aEnergy);
    }


    @Override
    public int checkRecipe(boolean aApplyRecipe, boolean aUseAutoIO) {
        int i = super.checkRecipe(aApplyRecipe, aUseAutoIO);
        if(mCurrentRecipe!=null) {
            onRecipeFound(this,mTanksInput,mCurrentRecipe);
        }
        return i;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.energy.fueldeburner";
    }

    @Override public long getTemperatureValue(byte aSide) {return (long) mTemp;}
    @Override public long getTemperatureMax(byte aSide) {return mTempMax;}
    @Override public void setTemp(float temp) {mTemp = temp;}
    @Override public float getTemp() {return mTemp;}
    @Override public float getRecipeBestTemp() {return recipeBestTemp;}
    @Override public void setRecipeBestTemp(float temp) {recipeBestTemp = temp;}
    @Override public float getRecipeFactor() {return recipeFactor;}
    @Override public void setRecipeFactor(float factor) {recipeFactor=factor;}
    @Override public long getMass() {return mMass;}
    @Override public void setMass(long mass) {mMass=mass;}
    @Override public long getMassSelf() {return mMassSelf;}
    @Override public long getStrictestEUt() {return maxStrictEUt;}
}
