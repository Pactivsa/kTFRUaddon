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
package cn.kuzuanpa.ktfruaddon;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import org.apache.logging.log4j.Level;

public class EnvironmentHelper {
    public static boolean isInTFRU = false, isGregtechTFRU = false, isAdvancedRocketryTFRU = false, isTFCTFRU = false, isBotaniaTFRU = false, isDraconicEvolutionTFRU = false, isForestryTFRU = false;

    public static void updateTFRUEnvironment(){
        try {if(                         gregtech.TFRUEnvHelper.isModTFRU)isGregtechTFRU          = true;}catch (Exception e){isGregtechTFRU          = false;}
        try {if(      zmaster587.advancedRocketry.TFRUEnvHelper.isModTFRU)isAdvancedRocketryTFRU  = true;}catch (Exception e){isAdvancedRocketryTFRU  = false;}
        try {if(                    com.bioxx.tfc.TFRUEnvHelper.isModTFRU)isTFCTFRU               = true;}catch (Exception e){isTFCTFRU               = false;}
        try {if(            vazkii.botania.common.TFRUEnvHelper.isModTFRU)isBotaniaTFRU           = true;}catch (Exception e){isBotaniaTFRU           = false;}
        try {if(com.brandon3055.draconicevolution.TFRUEnvHelper.isModTFRU)isDraconicEvolutionTFRU = true;}catch (Exception e){isDraconicEvolutionTFRU = false;}
        try {if(                         forestry.TFRUEnvHelper.isModTFRU)isForestryTFRU          = true;}catch (Exception e){isForestryTFRU          = false;}

        isInTFRU = isGregtechTFRU&&isAdvancedRocketryTFRU&&isTFCTFRU&&isBotaniaTFRU&&isDraconicEvolutionTFRU&&isForestryTFRU&&Loader.isModLoaded("thinker")&&Loader.isModLoaded("tfc-mixin");

        if(!isInTFRU)return;
        try {                        gregtech.TFRUEnvHelper.isInTFRU=true;
                  zmaster587.advancedRocketry.TFRUEnvHelper.isInTFRU=true;
                                com.bioxx.tfc.TFRUEnvHelper.isInTFRU=true;
                        vazkii.botania.common.TFRUEnvHelper.isInTFRU=true;
            com.brandon3055.draconicevolution.TFRUEnvHelper.isInTFRU=true;
                                     forestry.TFRUEnvHelper.isInTFRU=true;
        }catch (Exception e){
            FMLLog.log(Level.FATAL,e,"Error Occured when setting up TFRU pack environment! are you using right version of TFRU mods?");
            throw new IllegalArgumentException("Illegal TFRU Environment");
        }
    }
}
