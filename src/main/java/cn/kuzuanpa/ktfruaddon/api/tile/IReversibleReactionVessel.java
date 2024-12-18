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

package cn.kuzuanpa.ktfruaddon.api.tile;

import gregapi.fluid.FluidTankGT;
import gregapi.oredict.OreDictManager;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.recipes.Recipe;
import gregapi.tileentity.base.TileEntityBase05Inventories;

import static gregapi.data.CS.C;

public interface IReversibleReactionVessel {
    default long onHeat(long aSize, long aAmount, boolean aDoInject) {
        if (aDoInject) setTemp(getTemp() + (aSize * aAmount)*64F / getMass());
        return aAmount;
    }

    default void updateMass(TileEntityBase05Inventories tile, FluidTankGT[] inputTanks){
        long mMassLast = getMass();
        long mMassTotal = getMassSelf();
        for (int i = 0; i < tile.invsize(); i++) {
            if(!tile.slotHas(i) || OreDictManager.INSTANCE.getItemData(tile.slot(i)) == null)continue;
            mMassTotal += (long) OreDictManager.INSTANCE.getItemData(tile.slot(i)).getAllMaterialStacks().stream().mapToDouble(OreDictMaterialStack::weight).sum();
        }
        for (FluidTankGT tankGT : inputTanks) {
            if(tankGT==null || tankGT.isEmpty())continue;
            mMassTotal += tankGT.fluid().getDensity()* tankGT.amount()/1000 ;
        }
        if(mMassLast<mMassTotal) {
            float deltaTemp = getTemp() - C;
            cooldown(getTemp()-(deltaTemp*mMassLast/mMassTotal+C));
        }
        setMass(mMassTotal);
    }

    default void onRecipeFound(TileEntityBase05Inventories tile, FluidTankGT[] inputTanks, Recipe recipe) {
        updateMass(tile,inputTanks);
        setRecipeBestTemp(recipe.mSpecialValue);
        setRecipeFactor(20.1F-20*Math.min(1F, recipe.mEUt*1F/getStrictestEUt()));
    }

    default void cooldown(float degree){
        if(Math.abs(getTemp()-C)< degree){
            setTemp(C);
            return;
        }
        degree=Math.abs(degree);
        setTemp(getTemp() - getTemp()>C ? degree: -degree);
    }

    default float getTempFactor() {
        float delta = Math.abs(getTemp() - getRecipeBestTemp());
        if(delta == 0) delta=0.001F;
        float recipeFactor = getRecipeFactor();

        return Math.min(128, ((256* recipeFactor/ (float)Math.sqrt(delta)) - (float) Math.pow(delta, 1.5F) / recipeFactor )/recipeFactor);
    }

    void setTemp(float temp);
    float getTemp();
    float getRecipeBestTemp();
    void setRecipeBestTemp(float temp);
    float getRecipeFactor();
    void setRecipeFactor(float factor);
    long getMass();
    void setMass(long mass);
    long getMassSelf();
    long getStrictestEUt();

}
