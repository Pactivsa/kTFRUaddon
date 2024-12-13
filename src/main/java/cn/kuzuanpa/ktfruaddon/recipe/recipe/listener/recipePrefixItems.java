/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */



package cn.kuzuanpa.ktfruaddon.recipe.recipe.listener;

import cn.kuzuanpa.ktfruaddon.api.material.prefix.prefixList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.code.ICondition;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.RM;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.event.IOreDictListenerEvent;
import gregapi.util.ST;

import static gregapi.data.CS.U;
import static gregapi.data.CS.ZL_FS;

public class recipePrefixItems {

    public static class Parts_Turbine implements IOreDictListenerEvent {

        private final ICondition<OreDictMaterial> mCondition;
        public Parts_Turbine(ICondition<OreDictMaterial> aCondition) {mCondition = aCondition;}

        @Override
        public void onOreRegistration(OreDictRegistrationContainer aEvent) {
            if (mCondition.isTrue(aEvent.mMaterial)) {
                RM. CrystallisationCrucible.addRecipe1(false,16,3600,OP.dustDiv72.mat(aEvent.mMaterial,1),FL.array(aEvent.mMaterial.liquid(4*U,true), FL.Nitrogen.make(1000)),ZL_FS,OP.bouleGt.mat(aEvent.mMaterial,1));
                RM. CrystallisationCrucible.addRecipe1(false,16,3600,OP.dustDiv72.mat(aEvent.mMaterial,1),FL.array(aEvent.mMaterial.liquid(4*U,true), FL.Helium.make(200)),ZL_FS,OP.bouleGt.mat(aEvent.mMaterial,1));

                recipeMaps.CNC.addRecipe2(true,32,Math.max(aEvent.mMaterial.mToolQuality, 1)*aEvent.mMaterial.mToolDurability / 2, ST.tag(1), OP.bouleGt.mat(aEvent.mMaterial,1), FL.array(FL.DistW.make(2000)),ZL_FS, prefixList.largeTurbineBlade.mat(aEvent.mMaterial,1),OP.dust.mat(aEvent.mMaterial, 2));
                RM.        Welder    .addRecipe2(true,aEvent.mMaterial.mMeltingPoint/2, Math.max(aEvent.mMaterial.mToolQuality, 1)*800, prefixList.largeTurbineBlade.mat(aEvent.mMaterial,24), ST.tag(0), FL.array(MT.SolderingAlloy.liquid(12*U,true)),ZL_FS, prefixList.turbineLargeSteam.mat(aEvent.mMaterial,1));
                RM.        Welder    .addRecipe2(true,aEvent.mMaterial.mMeltingPoint/2, Math.max(aEvent.mMaterial.mToolQuality, 1)*600, prefixList.largeTurbineBlade.mat(aEvent.mMaterial,12), prefixList.turbineLargeSteam.mat(aEvent.mMaterial,1), FL.array(MT.SolderingAlloy.liquid(6*U,true)),ZL_FS, prefixList.turbineLargeGas.mat(aEvent.mMaterial,1));
                RM.        Welder    .addRecipe2(true,aEvent.mMaterial.mMeltingPoint/2, Math.max(aEvent.mMaterial.mToolQuality, 1)*600, prefixList.largeTurbineBlade.mat(aEvent.mMaterial,12), prefixList.turbineLargeSteamChecked.mat(aEvent.mMaterial,1), FL.array(MT.SolderingAlloy.liquid(6*U,true)),ZL_FS, prefixList.turbineLargeGas.mat(aEvent.mMaterial,1));

                recipeMaps.FlawDetector.addRecipe1(true,16,12000, new long[]{6000,4000}, prefixList.turbineLargeSteam.mat(aEvent.mMaterial,1), prefixList.turbineLargeSteamChecked.mat(aEvent.mMaterial,1), prefixList.turbineLargeSteamDamaged.mat(aEvent.mMaterial,1));
                recipeMaps.FlawDetector.addRecipe1(true,16,18000, new long[]{6000,4000}, prefixList.turbineLargeGas.mat(aEvent.mMaterial,1), prefixList.turbineLargeGasChecked.mat(aEvent.mMaterial,1), prefixList.turbineLargeGasDamaged.mat(aEvent.mMaterial,1));
            }
        }
    }
    public static class Parts_Flywheel implements IOreDictListenerEvent {

        private final ICondition<OreDictMaterial> mCondition;
        public Parts_Flywheel(ICondition<OreDictMaterial> aCondition) {mCondition = aCondition;}

        @Override
        public void onOreRegistration(OreDictRegistrationContainer aEvent) {
            if (mCondition.isTrue(aEvent.mMaterial)) {
                RM.Lathe.addRecipe1(false,Math.max(aEvent.mMaterial.mToolQuality,1)*20, aEvent.mMaterial.mToolDurability,OP.blockSolid.mat(aEvent.mMaterial,1),prefixList.flywheel.mat(aEvent.mMaterial,1),OP.dust.mat(aEvent.mMaterial,1));
            }
        }
    }

}
