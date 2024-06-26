/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.i18n;

import cn.kuzuanpa.ktfruaddon.i18n.texts.kMessages;
import cn.kuzuanpa.ktfruaddon.i18n.texts.kTooltips;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import gregapi.data.LH;

public class i18nInit {
    public i18nInit(FMLPostInitializationEvent aEvent){
        LH.add(kTooltips.SIDE_BACK,"Back");
        LH.add(kTooltips.SIDE_FRONT,"Front");
        LH.add(kTooltips.SIDE_TOP,"top");
        LH.add(kTooltips.SIDE_BOTTOM,"bottom");
        LH.add(kTooltips.SIDE_LEFT,"left");
        LH.add(kTooltips.SIDE_RIGHT,"right");
        LH.add(kTooltips.AUTO,"(auto)");
        LH.add(kTooltips.USE_MONKEY_WRENCH_CHANGE_STRUCTURE,"Use Monkey Wrench to Change Contents.");

        LH.add(kMessages.DONE_CHANGING_STRUCTURE,"Changing Contents");
        LH.add(kMessages.CHANGING_STRUCTURE,"Done Changing Contents");
        LH.add(kMessages.INPUT,"Input");
        LH.add(kMessages.OUTPUT,"Output");
        LH.add(kMessages.INVENTORY,"Inventory");
        LH.add(kMessages.SLOT,"Slot");
        LH.add(kMessages.TANK,"Tank");
        LH.add(kMessages.NULL,"Null");
        LH.add(kMessages.EMPTY,"Empty");
        LH.add(kMessages.NORMAL,"Normal");
        LH.add(kMessages.ERROR,"error");
        LH.add(kMessages.CRUCIBLE_MODEL_0,"Still need ");
        LH.add(kMessages.CRUCIBLE_MODEL_1," clay ball.");
        LH.add(kMessages.SUN_BOILER_MIRROR,"Successfully binding Sun Boiler target:");
        LH.add(kMessages.SUN_BOILER_MIRROR_ERR,"Some block above or around the mirror blocked the sunlight");
        LH.add(kMessages.SUN_BOILER_0,"The Position of this block wrote to USB stick.");
        LH.add(kMessages.SUN_BOILER_1,"There already have some data in USB stick, click again to overwrite it.");
        LH.add(kMessages.SUN_BOILER_ERR,"There are some errors in structure.");
        LH.add(kMessages.COMPUTE_CLUSTER_0,"No computer inserted.");
        LH.add(kMessages.COMPUTE_CLUSTER_1,"Compute Cluster State: ");
        LH.add(kMessages.COMPUTE_CLUSTER_2,"Total Compute Power: ");
        LH.add(kMessages.COMPUTE_CLUSTER_3,"Power off");
    }
}
