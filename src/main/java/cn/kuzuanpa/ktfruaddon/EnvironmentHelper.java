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
