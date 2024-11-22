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



package cn.kuzuanpa.ktfruaddon.i18n;

import cn.kuzuanpa.ktfruaddon.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.i18n.texts.kUserInterface;
import cn.kuzuanpa.ktfruaddon.item.itemPreInit;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import gregapi.data.LH;

public class i18nPostInit {
    public i18nPostInit(FMLPostInitializationEvent aEvent){
        LH.add(I18nHandler.SIDE_BACK,"back");
        LH.add(I18nHandler.SIDE_FRONT,"front");
        LH.add(I18nHandler.SIDE_TOP,"top");
        LH.add(I18nHandler.SIDE_BOTTOM,"bottom");
        LH.add(I18nHandler.SIDE_LEFT,"left");
        LH.add(I18nHandler.SIDE_RIGHT,"right");
        LH.add(I18nHandler.AUTO,"(auto)");
        LH.add(I18nHandler.HAS_PROJECTOR_STRUCTURE,"See Structure in Projector.");
        LH.add(I18nHandler.USE_MONKEY_WRENCH_CHANGE_STRUCTURE,"Use Monkey Wrench to Change Contents.");
        LH.add(I18nHandler.TANK_GAS_COMPRESSED_INPUTER,"Works with Compressed Gas Tank, Speed: (Input KU)*10 L/t.");
        LH.add(I18nHandler.TURBINE_UNCHECKED,"Unchecked, may break when spinning amd cause explode");
        LH.add(I18nHandler.TURBINE_DAMAGED,"Already Damaged, you can try to recycle it.");
        LH.add(I18nHandler.TURBINE_DURABILITY,"Turbine Durability: ");
        LH.add(I18nHandler.TURBINE_POWERRATE,"Turbine Power Rate: ");
        LH.add(I18nHandler.FLYWHEEL_STORAGE,"Can Store:");
        LH.add(I18nHandler.FLYWHEEL_MaxRPM,"Max RPM:");
        LH.add(I18nHandler.ORE_SCANNER_REQUIRE_PIPES,"Require Pipe");

        LH.add(I18nHandler.REQUIRE_MANA_BURST,"Require Mana Burst To Work!");
        LH.add(I18nHandler.DONE_CHANGING_STRUCTURE,"Changing Contents");
        LH.add(I18nHandler.CHANGING_STRUCTURE,"Done Changing Contents");
        LH.add(I18nHandler.INPUT,"Input");
        LH.add(I18nHandler.OUTPUT,"Output");
        LH.add(I18nHandler.INVENTORY,"Inventory");
        LH.add(I18nHandler.SLOT,"Slot");
        LH.add(I18nHandler.TANK,"Tank");
        LH.add(I18nHandler.NULL,"Null");
        LH.add(I18nHandler.EMPTY,"Empty");
        LH.add(I18nHandler.NORMAL,"Normal");
        LH.add(I18nHandler.ERROR,"error");
        LH.add(I18nHandler.STORED_ENERGY,"Stored Energy");
        LH.add(I18nHandler.CAPACITY,"Capacity");
        LH.add(I18nHandler.OUTPUTTING,"Outputting");
        LH.add(I18nHandler.CRUCIBLE_MODEL_0,"Still need ");
        LH.add(I18nHandler.CRUCIBLE_MODEL_1," clay ball.");
        LH.add(I18nHandler.SUN_BOILER_MIRROR,"Successfully binding Sun Boiler target:");
        LH.add(I18nHandler.SUN_BOILER_MIRROR_ERR,"Some block above or around the mirror blocked the sunlight");
        LH.add(I18nHandler.SUN_BOILER_0,"The Position of this block wrote to USB stick.");
        LH.add(I18nHandler.SUN_BOILER_1,"There already have some data in USB stick, click again to overwrite it.");
        LH.add(I18nHandler.SUN_BOILER_ERR,"There are some errors in structure.");
        LH.add(I18nHandler.COMPUTE_CLUSTER_0,"No computer inserted.");
        LH.add(I18nHandler.COMPUTE_CLUSTER_1,"Compute Cluster State: ");
        LH.add(I18nHandler.COMPUTE_CLUSTER_2,"Total Compute Power: ");
        LH.add(I18nHandler.COMPUTE_CLUSTER_3,"Power off");
        LH.add(I18nHandler.FILTERING_PROPERTIES,"Filtering Property:");
        LH.add(I18nHandler.FILTER_PROPERTIES_ALL,"All Properties: ");
        LH.add(I18nHandler.OVERCLOCKING,"Overclocking:");

        LH.add(kUserInterface.FUSION_TOKAMAK_STATE_STOPPED,"Stopped");
        LH.add(kUserInterface.FUSION_TOKAMAK_STATE_CHARGING,"Charging");
        LH.add(kUserInterface.FUSION_TOKAMAK_STATE_RUNNING,"Running");
        LH.add(kUserInterface.FUSION_TOKAMAK_STATE_ERROR,"ERROR");
        LH.add(kUserInterface.FUSION_TOKAMAK_STATE_VOIDCHARGE,"Void Charging");



        LH.add(kUserInterface.TYPE ,"Type");
        LH.add(kUserInterface.COMPUTECLUSTER_CLIENT              ,"Client");
        LH.add(kUserInterface.COMPUTECLUSTER_CLUSTER             ,"Cluster");
        LH.add(kUserInterface.COMPUTECLUSTER_CONTROLLER          ,"Controller");
        LH.add(kUserInterface.COMPUTECLUSTER_CLUSTER_STATE_OFFLINE           ,"Offline");
        LH.add(kUserInterface.COMPUTECLUSTER_CLUSTER_STATE_NORMAL            ,"§2Normal" );
        LH.add(kUserInterface.COMPUTECLUSTER_CLUSTER_STATE_WARNING           ,"§eWarning");
        LH.add(kUserInterface.COMPUTECLUSTER_CLUSTER_STATE_ERROR             ,"§4ERROR"  );
        LH.add(kUserInterface.COMPUTECLUSTER_CLUSTER_OVERVIEW                ,"Cluster View");
        LH.add(kUserInterface.COMPUTECLUSTER_CONTROLLER_STATE_OFFLINE        ,"Offline");
        LH.add(kUserInterface.COMPUTECLUSTER_CONTROLLER_STATE_NORMAL         ,"§2Normal" );
        LH.add(kUserInterface.COMPUTECLUSTER_CONTROLLER_STATE_WARNING        ,"§eWarning");
        LH.add(kUserInterface.COMPUTECLUSTER_CONTROLLER_STATE_ERROR          ,"§4ERROR"  );
        LH.add(kUserInterface.COMPUTECLUSTER_CONTROLLER_STATE_ERR_BELONG     ,"§cBelong Error");
        LH.add(kUserInterface.COMPUTECLUSTER_CONTROLLER_INFO                 ,"Info");
        LH.add(kUserInterface.COMPUTECLUSTER_CONTROLLER_PROVIDING            ,"Providing");
        LH.add(kUserInterface.COMPUTECLUSTER_CONTROLLER_LIST                 ,"Controllers");
        LH.add(kUserInterface.COMPUTECLUSTER_CLIENT_LIST                     ,"Clients");
        LH.add(kUserInterface.COMPUTECLUSTER_RECENT_EVENTS                   ,"Events");


        LH.add(kUserInterface.COMPUTE_POWER                   ,"Computer Power");
        LH.add(kUserInterface.COMPUTE_POWER_LOGIC             ,"Logic");
        LH.add(kUserInterface.COMPUTE_POWER_BIOLOGY           ,"Biology");
        LH.add(kUserInterface.COMPUTE_POWER_QUANTUM           ,"Quantum");
        LH.add(kUserInterface.COMPUTE_POWER_SPACETIME         ,"Spacetime");

        itemPreInit.turbineLargeGas         .addTooltips(LH.Chat.RED+LH.get(I18nHandler.TURBINE_UNCHECKED));
        itemPreInit.turbineLargeGasChecked  .addTooltips(LH.Chat.RED+LH.get(I18nHandler.TURBINE_UNCHECKED));
        itemPreInit.turbineLargeSteamChecked.addTooltips(LH.Chat.WHITE+LH.get(I18nHandler.TURBINE_DAMAGED));
        itemPreInit.turbineLargeSteamDamaged.addTooltips(LH.Chat.WHITE+LH.get(I18nHandler.TURBINE_DAMAGED));
    }
}
