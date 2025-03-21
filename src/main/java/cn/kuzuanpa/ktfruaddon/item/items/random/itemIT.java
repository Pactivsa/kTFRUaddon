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

package cn.kuzuanpa.ktfruaddon.item.items.random;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import gregapi.data.LH;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemIT extends MultiItemRandom {
    public itemIT() {
        super(MOD_ID, "ktfru.item.it");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: IT", this, (short) 9701));

    }

    @Override
    public void addItems() {

        ItemList.SiliconBoulePure             .set(addItem(9701, "Pure Silicon Boule", "Ready to Cut into Plates"));
        ItemList.MoO2Boule                    .set(addItem(9702, "MoO2 Boule", "Ready to Cut into Plates"));
        ItemList.MoO2BoulePure                .set(addItem(9703, "Pure MoO2 Boule", "Ready to Cut into Plates"));

        ItemList.SiliconPlateT1.set(addItem(9800, "SiliconPlateT1", "Next Step:"+ LH.Chat._CYAN+"Coat"));
        ItemList.SiliconPlateT2.set(addItem(9801, "SiliconPlateT2", "Next Step:"+ LH.Chat._CYAN+"Clean"));
        ItemList.MoO2PlateT1.set(addItem(9802, "MoO2PlateT1", "Next Step:"+ LH.Chat._CYAN+"Clean"));
        ItemList.MoO2PlateT2.set(addItem(9803, "MoO2PlateT2", "Next Step:"+ LH.Chat._CYAN+"Clean"));

        ItemList.SiliconPlateCleanedT1.set(addItem(9810, "SiliconPlateCleanedT1", "Not used"));
        ItemList.SiliconPlateCleanedT2.set(addItem(9811, "SiliconPlateCleanedT2", "Next Step:"+ LH.Chat._CYAN+"Oxidize"));
        ItemList.MoO2PlateCleanedT1.set(addItem(9812, "MoO2PlateCleanedT1", "Next Step:"+ LH.Chat._CYAN+"Oxidize"));
        ItemList.MoO2PlateCleanedT2.set(addItem(9813, "MoO2PlateCleanedT2", "Next Step:"+ LH.Chat._CYAN+"Oxidize"));

        ItemList.SiliconPlateOxidizedT1.set(addItem(9820, "SiliconPlateOxidizedT1", "Not used"));
        ItemList.SiliconPlateOxidizedT2.set(addItem(9821, "SiliconPlateOxidizedT2", "Next Step:"+ LH.Chat._CYAN+"Coat"));
        ItemList.MoO2PlateOxidizedT1.set(addItem(9822, "MoO2PlateOxidizedT1", "Next Step:"+ LH.Chat._CYAN+"Coat"));
        ItemList.MoO2PlateOxidizedT2.set(addItem(9823, "MoO2PlateOxidizedT2", "Next Step:"+ LH.Chat._CYAN+"Coat"));

        ItemList.SiliconPlateCoatedT1.set(addItem(9830, "SiliconPlateCoatedT1", "Next Step:"+ LH.Chat._CYAN+"Photo Align"));
        ItemList.SiliconPlateCoatedT2.set(addItem(9831, "SiliconPlateCoatedT2", "Next Step:"+ LH.Chat._CYAN+"Soft Bake"));
        ItemList.MoO2PlateCoatedT1.set(addItem(9832, "MoO2PlateCoatedT1", "Next Step:"+ LH.Chat._CYAN+"Soft Bake"));
        ItemList.MoO2PlateCoatedT2.set(addItem(9833, "MoO2PlateCoatedT2", "Next Step:"+ LH.Chat._CYAN+"Soft Bake"));

        ItemList.SiliconPlateSoftBakedT1.set(addItem(9840, "SiliconPlateSoftBakedT1", "Not used"));
        ItemList.SiliconPlateSoftBakedT2.set(addItem(9841, "SiliconPlateSoftBakedT2", "Next Step:"+ LH.Chat._CYAN+"Photo Align"));
        ItemList.MoO2PlateSoftBakedT1.set(addItem(9842, "MoO2PlateSoftBakedT1", "Next Step:"+ LH.Chat._CYAN+"Photo Align"));
        ItemList.MoO2PlateSoftBakedT2.set(addItem(9843, "MoO2PlateSoftBakedT2", "Next Step:"+ LH.Chat._CYAN+"Photo Align"));

        ItemList.CPUPhotomask200um.set(addItem(9900, "CPU Photo Mask (200um)", "A plate with complex images, used to craft CPUs"));
        ItemList.CPUPhotomask72um.set(addItem(9901, "CPU Photo Mask (72um)", "A plate with complex images, used to craft CPUs"));
        ItemList.CPUPhotomask28um.set(addItem(9902, "CPU Photo Mask (28um)", "A plate with complex images, used to craft CPUs"));
        ItemList.CPUPhotomask8um.set(addItem(9903, "CPU Photo Mask (8um)", "A plate with complex images, used to craft CPUs"));
        ItemList.CPUPhotomask400nm.set(addItem(9904, "CPU Photo Mask (400nm)", "A plate with complex images, used to craft CPUs"));
        ItemList.CPUPhotomask80nm.set(addItem(9905, "CPU Photo Mask (80nm)", "A plate with complex images, used to craft CPUs"));
        ItemList.CPUPhotomask32nm.set(addItem(9906, "CPU Photo Mask (32nm)", "A plate with complex images, used to craft CPUs"));
        ItemList.CPUPhotomask14nm.set(addItem(9907, "CPU Photo Mask (14nm)", "A plate with complex images, used to craft CPUs"));


        ItemList.CPUWafer200um.set(addItem(9950, "CPU Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.CPUWafer72um .set(addItem(9951, "CPU Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.CPUWafer28um .set(addItem(9952, "CPU Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.CPUWafer8um  .set(addItem(9953, "CPU Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.CPUWafer400nm.set(addItem(9954, "CPU Wafer (400nm)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.CPUWafer80nm .set(addItem(9955, "CPU Wafer (80nm)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.CPUWafer32nm .set(addItem(9956, "CPU Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.CPUWafer14nm .set(addItem(9957, "CPU Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Develop"));

        ItemList.CPUWafer200umDeveloped.set(addItem(10000, "Developed CPU Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.CPUWafer72umDeveloped .set(addItem(10001, "Developed CPU Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.CPUWafer28umDeveloped .set(addItem(10002, "Developed CPU Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.CPUWafer8umDeveloped  .set(addItem(10003, "Developed CPU Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.CPUWafer400nmDeveloped.set(addItem(10004, "Developed CPU Wafer (400nm)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.CPUWafer80nmDeveloped .set(addItem(10005, "Developed CPU Wafer (80nm)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.CPUWafer32nmDeveloped .set(addItem(10006, "Developed CPU Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.CPUWafer14nmDeveloped .set(addItem(10007, "Developed CPU Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));

        ItemList.CPUWafer200umHardBaked.set(addItem(10050, "HardBaked CPU Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.CPUWafer72umHardBaked .set(addItem(10051, "HardBaked CPU Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.CPUWafer28umHardBaked .set(addItem(10052, "HardBaked CPU Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.CPUWafer8umHardBaked  .set(addItem(10053, "HardBaked CPU Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.CPUWafer400nmHardBaked.set(addItem(10054, "HardBaked CPU Wafer (400nm)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.CPUWafer80nmHardBaked .set(addItem(10055, "HardBaked CPU Wafer (80nm)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.CPUWafer32nmHardBaked .set(addItem(10056, "HardBaked CPU Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.CPUWafer14nmHardBaked .set(addItem(10057, "HardBaked CPU Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Dope"));

        ItemList.CPUWafer200umDoped.set(addItem(10100, "Doped CPU Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.CPUWafer72umDoped .set(addItem(10101, "Doped CPU Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.CPUWafer28umDoped .set(addItem(10102, "Doped CPU Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.CPUWafer8umDoped  .set(addItem(10103, "Doped CPU Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.CPUWafer400nmDoped.set(addItem(10104, "Doped CPU Wafer (400nm)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.CPUWafer80nmDoped .set(addItem(10105, "Doped CPU Wafer (80nm)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.CPUWafer32nmDoped .set(addItem(10106, "Doped CPU Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.CPUWafer14nmDoped .set(addItem(10107, "Doped CPU Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Check"));

        ItemList.CPUWafer200umChecked.set(addItem(10150, "Checked CPU Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.CPUWafer72umChecked .set(addItem(10151, "Checked CPU Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.CPUWafer28umChecked .set(addItem(10152, "Checked CPU Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.CPUWafer8umChecked  .set(addItem(10153, "Checked CPU Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.CPUWafer400nmChecked.set(addItem(10154, "Checked CPU Wafer (400nm)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.CPUWafer80nmChecked .set(addItem(10155, "Checked CPU Wafer (80nm)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.CPUWafer32nmChecked .set(addItem(10156, "Checked CPU Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.CPUWafer14nmChecked .set(addItem(10157, "Checked CPU Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));

        ItemList.CPUDieTF3386.set(addItem(10200, "CPUDieTF3386", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieTF3586.set(addItem(10201, "CPUDieTF3586", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieGT1000.set(addItem(10202, "CPUDieGT1000", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieGT2000.set(addItem(10203, "CPUDieGT2000", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieGT3660.set(addItem(10204, "CPUDieGT3660", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieGT3660v2.set(addItem(10205, "CPUDieGT3660v2", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieGT3660v3.set(addItem(10206, "CPUDieGT3660v3", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieGT3660v4.set(addItem(10207, "CPUDieGT3660v4", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieTF3386S.set(addItem(10208, "CPUDieTF3386S", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieTF3586S.set(addItem(10209, "CPUDieTF3586S", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieGT1090.set(addItem(10210, "CPUDieGT1090", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieGT2090.set(addItem(10211, "CPUDieGT2090", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieGT3680.set(addItem(10212, "CPUDieGT3680", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieGT3680v2.set(addItem(10213, "CPUDieGT3680v2", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieGT3680v3.set(addItem(10214, "CPUDieGT3680v3", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.CPUDieGT3680v4.set(addItem(10215, "CPUDieGT3680v4", "Next Step:"+ LH.Chat._CYAN+"Compose"));

        ItemList.CPUTF3386.set(addItem(10300, "CPUTF3386", "A basic CPU, resolve simple questions"));
        ItemList.CPUTF3586.set(addItem(10301, "CPUTF3586", "That CPU will change the world"));
        ItemList.CPUGT1000.set(addItem(10302, "CPUGT1000", "Brand new CPU! Up to 1MHz!"));
        ItemList.CPUGT2000.set(addItem(10303, "CPUGT2000", "Integrated Number of new technology"));
        ItemList.CPUGT3660.set(addItem(10304, "CPUGT3660", "CPUGT3660"));
        ItemList.CPUGT3660v2.set(addItem(10305, "CPUGT3660v2", "CPUGT3660v2"));
        ItemList.CPUGT3660v3.set(addItem(10306, "CPUGT3660v3", "CPUGT3660v3"));
        ItemList.CPUGT3660v4.set(addItem(10307, "CPUGT3660v4", "CPUGT3660v4"));
        ItemList.CPUTF3386S.set(addItem(10308, "CPUTF3386S", "A basic CPU with a bit improve, resolve simple questions"));
        ItemList.CPUTF3586S.set(addItem(10309, "CPUTF3586S", "That CPU will completely change the world"));
        ItemList.CPUGT1090.set(addItem(10310, "CPUGT1090", "Brand new CPU! Up to 1.3MHz!"));
        ItemList.CPUGT2090.set(addItem(10311, "CPUGT2090", "Integrated Hundred of new technology"));
        ItemList.CPUGT3680.set(addItem(10312, "CPUGT3680", "CPUGT3680"));
        ItemList.CPUGT3680v2.set(addItem(10313, "CPUGT3680v2", "CPUGT3680v2"));
        ItemList.CPUGT3680v3.set(addItem(10314, "CPUGT3680v3", "CPUGT3680v3"));
        ItemList.CPUGT3680v4.set(addItem(10315, "CPUGT3680v4", "CPUGT3680v4"));
        ItemList.CPUGT3699.set(addItem(10316, "CPUGT3699", "CPUGT3699"));
        ItemList.CPUGT3699v2.set(addItem(10317, "CPUGT3699v2", "CPUGT3699v2"));
        ItemList.CPUGT3699v3.set(addItem(10318, "CPUGT3699v3", "CPUGT3699v3"));
        ItemList.CPUGT3699v4.set(addItem(10319, "CPUGT3699v4", "CPUGT3699v4"));
        ItemList.CPUGT3680v3E.set(addItem(10320, "CPUGT3680v3E", "CPUGT3680v3E"));
        ItemList.CPUGT3680v4E.set(addItem(10321, "CPUGT3680v4E", "CPUGT3680v4E"));
        ItemList.CPUGT3699v3E.set(addItem(10322, "CPUGT3699v3E", "CPUGT3699v3E"));
        ItemList.CPUGT3699v4E.set(addItem(10323, "CPUGT3699v4E", "CPUGT3699v4E"));
        ItemList.CPUBoardT1.set(addItem(10400,"Basic CPU Board",""));
        ItemList.CPUBoardT2.set(addItem(10401,"Advanced CPU Board",""));
        ItemList.CPUBoardT3.set(addItem(10402,"Ultimate CPU Board",""));
        ItemList.RAMBoardT1.set(addItem(10410,"Basic RAM Board",""));
        ItemList.RAMBoardT2.set(addItem(10411,"Advanced RAM Board",""));
        ItemList.RAMBoardT3.set(addItem(10412,"Ultimate RAM Board",""));


//Circuits
        int i=0;
        ItemList.CircuitBoardEmptyT2.set(addItem(20000, "Plastic Circuit Board","Advanced circuit board"));
        ItemList.CircuitBoardEmptyT3.set(addItem(20001, "Epoxy Resin Circuit Board","Ultimate circuit board"));

        ItemList.ResistanceT1.set(addItem(20002, "Resistance (T1)",""));
        ItemList.DiodeT1     .set(addItem(20003, "Diode (T1)",""));
        ItemList.CapacitorT1 .set(addItem(20004, "Capacitor (T1)",""));
        ItemList.CoilT1      .set(addItem(20005, "Coil (T1)",""));

        ItemList.ResistanceT2.set(addItem(20006, "Resistance (T2)",""));
        ItemList.DiodeT2     .set(addItem(20007, "Diode (T2)",""));
        ItemList.CapacitorT2 .set(addItem(20008, "Capacitor (T2)",""));
        ItemList.CoilT2      .set(addItem(20009, "Coil (T2)",""));

        ItemList.ResistanceT3.set(addItem(20010, "Resistance (T3)",""));
        ItemList.DiodeT3     .set(addItem(20011, "Diode (T3)",""));
        ItemList.CapacitorT3 .set(addItem(20012, "Capacitor (T3)",""));
        ItemList.CoilT3      .set(addItem(20013, "Coil (T3)",""));

        ItemList.ResistanceT4.set(addItem(20014, "Resistance (T4)",""));
        ItemList.CoilT4      .set(addItem(20015, "Coil (T4)",""));

        ItemList.DiodeT2Part .set(addItem(20016, "Diode Part (T2)",""));
        ItemList.DiodeT3Part .set(addItem(20017, "Diode Part (T3)",""));

        ItemList.LEDSet     .set(addItem(20018, "LED Set",""));

        ItemList.CircuitBoardBasicUncompleted.set(addItem(20050, "Uncompleted Circuit Board (Basic)","Next Step:"+ LH.Chat._CYAN+"Install 4 Resistances"));
        ItemList.CircuitBoardGoodUncompleted1.set(addItem(20051, "Uncompleted Circuit Board (Good, Stage 1)","Next Step:"+ LH.Chat._CYAN+"Install 8 Capacitors"));
        ItemList.CircuitBoardGoodUncompleted2.set(addItem(20052, "Uncompleted Circuit Board (Good, Stage 2)","Next Step:"+ LH.Chat._CYAN+"Install 8 Resistances"));

        ItemList.CircuitPartPhotomaskT3.set(addItem(20100, "CircuitPart Photo Mask (200um)", "A plate with complex images"));
        ItemList.CircuitPartPhotomaskT4.set(addItem(20101, "CircuitPart Photo Mask (72um)", "A plate with complex images"));
        ItemList.CircuitPartPhotomaskT5.set(addItem(20102, "CircuitPart Photo Mask (28um)", "A plate with complex images"));
        ItemList.CircuitPartPhotomaskT6.set(addItem(20103, "CircuitPart Photo Mask (8um)", "A plate with complex images"));

        ItemList.CircuitPartWaferT3.set(addItem(20104, "CircuitPart Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.CircuitPartWaferT4.set(addItem(20105, "CircuitPart Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.CircuitPartWaferT5.set(addItem(20106, "CircuitPart Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.CircuitPartWaferT6.set(addItem(20107, "CircuitPart Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));

        ItemList.CircuitPartWaferT3Developed.set(addItem(20108, "Developed Circuit Part Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.CircuitPartWaferT4Developed.set(addItem(20109, "Developed Circuit Part Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.CircuitPartWaferT5Developed.set(addItem(20110, "Developed Circuit Part Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.CircuitPartWaferT6Developed.set(addItem(20111, "Developed Circuit Part Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));

        ItemList.CircuitPartWaferT3HardBaked.set(addItem(20112, "HardBaked Circuit Part Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.CircuitPartWaferT4HardBaked.set(addItem(20113, "HardBaked Circuit Part Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.CircuitPartWaferT5HardBaked.set(addItem(20114, "HardBaked Circuit Part Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.CircuitPartWaferT6HardBaked.set(addItem(20115, "HardBaked Circuit Part Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));

        ItemList.CircuitPartWaferT3Doped.set(addItem(20116, "Doped Circuit Part Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.CircuitPartWaferT4Doped.set(addItem(20117, "Doped Circuit Part Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.CircuitPartWaferT5Doped.set(addItem(20118, "Doped Circuit Part Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.CircuitPartWaferT6Doped.set(addItem(20119, "Doped Circuit Part Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Check"));

        ItemList.CircuitPartWaferT3Checked.set(addItem(20120, "Checked Circuit Part Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.CircuitPartWaferT4Checked.set(addItem(20121, "Checked Circuit Part Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.CircuitPartWaferT5Checked.set(addItem(20122, "Checked Circuit Part Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.CircuitPartWaferT6Checked.set(addItem(20123, "Checked Circuit Part Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));

//InterLayer

        ItemList.InterLayerPhotomask32nm.set(addItem(20500, "InterLayer Photo Mask (14nm)", "A plate with complex images, used to craft InterLayers"));
        ItemList.InterLayerPhotomask14nm.set(addItem(20501, "InterLayer Photo Mask (14nm)", "A plate with complex images, used to craft InterLayers"));

        ItemList.InterLayerWafer32nm.set(addItem(20510, "InterLayer Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.InterLayerWafer14nm.set(addItem(20511, "InterLayer Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Develop"));

        ItemList.InterLayerWafer32nmDeveloped.set(addItem(20520, "Developed InterLayer Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.InterLayerWafer14nmDeveloped.set(addItem(20521, "Developed InterLayer Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));

        ItemList.InterLayerWafer32nmHardBaked.set(addItem(20530, "HardBaked InterLayer Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.InterLayerWafer14nmHardBaked.set(addItem(20531, "HardBaked InterLayer Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Dope"));

        ItemList.InterLayerWafer32nmDoped.set(addItem(20540, "Doped InterLayer Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.InterLayerWafer14nmDoped.set(addItem(20541, "Doped InterLayer Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Check"));

        ItemList.InterLayerWafer32nmChecked.set(addItem(20550, "Checked InterLayer Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.InterLayerWafer14nmChecked.set(addItem(20551, "Checked InterLayer Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));

        ItemList.InterLayerT1.set(addItem(20560, "InterLayerT1", "Next Step:"+ LH.Chat._CYAN+"Compose"));
        ItemList.InterLayerT2.set(addItem(20561, "InterLayerT2", "Next Step:"+ LH.Chat._CYAN+"Compose"));


//Diode Part
        ItemList.DiodePhotomask200um.set(addItem(20600, "Diode Photo Mask (200um)", "A plate with complex images, used to craft Diodes"));
        ItemList.DiodePhotomask28um.set(addItem(20601, "Diode Photo Mask (28um)", "A plate with complex images, used to craft Diodes"));

        ItemList.DiodeWafer200um.set(addItem(20610, "Diode Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.DiodeWafer28um.set(addItem(20611, "Diode Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));

        ItemList.DiodeWafer200umDeveloped.set(addItem(20620, "Developed Diode Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.DiodeWafer28umDeveloped.set(addItem(20621, "Developed Diode Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));

        ItemList.DiodeWafer200umHardBaked.set(addItem(20630, "HardBaked Diode Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.DiodeWafer28umHardBaked.set(addItem(20631, "HardBaked Diode Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));

        ItemList.DiodeWafer200umDoped.set(addItem(20640, "Doped Diode Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.DiodeWafer28umDoped.set(addItem(20641, "Doped Diode Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Check"));

        ItemList.DiodeWafer200umChecked.set(addItem(20650, "Checked Diode Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.DiodeWafer28umChecked.set(addItem(20651, "Checked Diode Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));

//RAM
        ItemList.RAMPhotomask200um.set(addItem(30000, "RAM Photo Mask (200um)", "A plate with complex images, used to craft RAM"));
        ItemList.RAMPhotomask72um .set(addItem(30001, "RAM Photo Mask (72um)", "A plate with complex images, used to craft RAM"));
        ItemList.RAMPhotomask28um .set(addItem(30002, "RAM Photo Mask (28um)", "A plate with complex images, used to craft RAM"));
        ItemList.RAMPhotomask8um  .set(addItem(30003, "RAM Photo Mask (8um)", "A plate with complex images, used to craft RAM"));
        ItemList.RAMPhotomask400nm.set(addItem(30004, "RAM Photo Mask (400nm)", "A plate with complex images, used to craft RAM"));
        ItemList.RAMPhotomask80nm .set(addItem(30005, "RAM Photo Mask (80nm)", "A plate with complex images, used to craft RAM"));
        ItemList.RAMPhotomask32nm .set(addItem(30006, "RAM Photo Mask (32nm)", "A plate with complex images, used to craft RAM"));
        ItemList.RAMPhotomask14nm .set(addItem(30007, "RAM Photo Mask (14nm)", "A plate with complex images, used to craft RAM"));


        ItemList.RAMWafer200um.set(addItem(30050, "RAM Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.RAMWafer72um .set(addItem(30051, "RAM Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.RAMWafer28um .set(addItem(30052, "RAM Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.RAMWafer8um  .set(addItem(30053, "RAM Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.RAMWafer400nm.set(addItem(30054, "RAM Wafer (400nm)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.RAMWafer80nm .set(addItem(30055, "RAM Wafer (80nm)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.RAMWafer32nm .set(addItem(30056, "RAM Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Develop"));
        ItemList.RAMWafer14nm .set(addItem(30057, "RAM Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Develop"));

        ItemList.RAMWafer200umDeveloped.set(addItem(30100, "Developed RAM Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.RAMWafer72umDeveloped .set(addItem(30101, "Developed RAM Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.RAMWafer28umDeveloped .set(addItem(30102, "Developed RAM Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.RAMWafer8umDeveloped  .set(addItem(30103, "Developed RAM Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.RAMWafer400nmDeveloped.set(addItem(30104, "Developed RAM Wafer (400nm)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.RAMWafer80nmDeveloped .set(addItem(30105, "Developed RAM Wafer (80nm)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.RAMWafer32nmDeveloped .set(addItem(30106, "Developed RAM Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));
        ItemList.RAMWafer14nmDeveloped .set(addItem(30107, "Developed RAM Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Hard Bake"));

        ItemList.RAMWafer200umHardBaked.set(addItem(30150, "HardBaked RAM Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.RAMWafer72umHardBaked .set(addItem(30151, "HardBaked RAM Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.RAMWafer28umHardBaked .set(addItem(30152, "HardBaked RAM Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.RAMWafer8umHardBaked  .set(addItem(30153, "HardBaked RAM Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.RAMWafer400nmHardBaked.set(addItem(30154, "HardBaked RAM Wafer (400nm)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.RAMWafer80nmHardBaked .set(addItem(30155, "HardBaked RAM Wafer (80nm)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.RAMWafer32nmHardBaked .set(addItem(30156, "HardBaked RAM Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Dope"));
        ItemList.RAMWafer14nmHardBaked .set(addItem(30157, "HardBaked RAM Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Dope"));

        ItemList.RAMWafer200umDoped.set(addItem(30200, "Doped RAM Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.RAMWafer72umDoped .set(addItem(30201, "Doped RAM Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.RAMWafer28umDoped .set(addItem(30202, "Doped RAM Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.RAMWafer8umDoped  .set(addItem(30203, "Doped RAM Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.RAMWafer400nmDoped.set(addItem(30204, "Doped RAM Wafer (400nm)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.RAMWafer80nmDoped .set(addItem(30205, "Doped RAM Wafer (80nm)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.RAMWafer32nmDoped .set(addItem(30206, "Doped RAM Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Check"));
        ItemList.RAMWafer14nmDoped .set(addItem(30207, "Doped RAM Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Check"));

        ItemList.RAMWafer200umChecked.set(addItem(30250, "Checked RAM Wafer (200um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.RAMWafer72umChecked .set(addItem(30251, "Checked RAM Wafer (72um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.RAMWafer28umChecked .set(addItem(30252, "Checked RAM Wafer (28um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.RAMWafer8umChecked  .set(addItem(30253, "Checked RAM Wafer (8um)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.RAMWafer400nmChecked.set(addItem(30254, "Checked RAM Wafer (400nm)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.RAMWafer80nmChecked .set(addItem(30255, "Checked RAM Wafer (80nm)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.RAMWafer32nmChecked .set(addItem(30256, "Checked RAM Wafer (32nm)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));
        ItemList.RAMWafer14nmChecked .set(addItem(30257, "Checked RAM Wafer (14nm)", "Next Step:"+ LH.Chat._CYAN+"Laser Cut"));

        ItemList.RAMDie2K.set(addItem(30300, "RAMDie2K", "A RAM Die, can storage 2K data"));
        ItemList.RAMDie32K.set(addItem(30301, "RAMDie32K", "A RAM Die, can storage 32K data"));
        ItemList.RAMDie256K.set(addItem(30302, "RAMDie256K", "A RAM Die, can storage 256K data"));
        ItemList.RAMDie2M.set(addItem(30303, "RAMDie2M", "A RAM Die, can storage 2M data"));
        ItemList.RAMDie16M.set(addItem(30304, "RAMDie16M", "A RAM Die, can storage 16M data"));
        ItemList.RAMDie128M.set(addItem(30305, "RAMDie128M", "A RAM Die, can storage 128M data"));
        ItemList.RAMDie768M.set(addItem(30306, "RAMDie768M", "A RAM Die, can storage 768M data"));
        ItemList.RAMDie2G.set(addItem(30307, "RAMDie2G", "A RAM Die, can storage 2G data"));
        
        ItemList.RAMBar2K4.set(addItem(30350, "RAMBar8K", "Composed by 4* 2K RAM Die")).registerOre("ktfruRAM8K");
        ItemList.RAMBar32K4.set(addItem(30351, "RAMBar96K", "Composed by 4* 32K RAM Die")).registerOre("ktfruRAM128K");
        ItemList.RAMBar256K4.set(addItem(30352, "RAMBar1M", "Composed by 4* 256K RAM Die")).registerOre("ktfruRAM1M");
        ItemList.RAMBar2M4.set(addItem(30353, "RAMBar8M", "Composed by 4* 2M RAM Die")).registerOre("ktfruRAM8M");
        ItemList.RAMBar16M4.set(addItem(30354, "RAMBar64M", "Composed by 4* 16M RAM Die")).registerOre("ktfruRAM64M");
        ItemList.RAMBar128M4.set(addItem(30355, "RAMBar512M", "Composed by 4* 128M RAM Die")).registerOre("ktfruRAM512M");
        ItemList.RAMBar768M4.set(addItem(30356, "RAMBar3G", "Composed by 4* 768M RAM Die")).registerOre("ktfruRAM3G");
        ItemList.RAMBar2G4.set(addItem(30357, "RAMBar8G", "Composed by 4* 2G RAM Die")).registerOre("ktfruRAM8G");

        ItemList.RAMBar2K8.set(addItem(30400, "RAMBar16K", "Composed by 8* 2K RAM Die")).registerOre("ktfruRAM16K");
        ItemList.RAMBar32K8.set(addItem(30401, "RAMBar192K", "Composed by 8* 32K RAM Die")).registerOre("ktfruRAM256K");
        ItemList.RAMBar256K8.set(addItem(30402, "RAMBar2M", "Composed by 8* 256K RAM Die")).registerOre("ktfruRAM2M");
        ItemList.RAMBar2M8.set(addItem(30403, "RAMBar16M", "Composed by 8* 2M RAM Die")).registerOre("ktfruRAM16M");
        ItemList.RAMBar16M8.set(addItem(30404, "RAMBar128M", "Composed by 8* 16M RAM Die")).registerOre("ktfruRAM128M");
        ItemList.RAMBar128M8.set(addItem(30405, "RAMBar1G", "Composed by 8* 128M RAM Die")).registerOre("ktfruRAM1G");
        ItemList.RAMBar768M8.set(addItem(30406, "RAMBar6G", "Composed by 8* 768M RAM Die")).registerOre("ktfruRAM6G");
        ItemList.RAMBar2G8.set(addItem(30407, "RAMBar16G", "Composed by 8* 2G RAM Die")).registerOre("ktfruRAM16G");
        
        ItemList.RAMBar256K16.set(addItem(30452, "RAMBar4M", "Composed by 16* 256K RAM Die")).registerOre("ktfruRAM4M");
        ItemList.RAMBar2M16.set(addItem(30453, "RAMBar32M", "Composed by 16* 2M RAM Die")).registerOre("ktfruRAM32M");
        ItemList.RAMBar16M16.set(addItem(30454, "RAMBar256M", "Composed by 16* 16M RAM Die")).registerOre("ktfruRAM256M");
        ItemList.RAMBar128M16.set(addItem(30455, "RAMBar2G", "Composed by 16* 128M RAM Die")).registerOre("ktfruRAM2G");
        ItemList.RAMBar768M16.set(addItem(30456, "RAMBar12G", "Composed by 16* 768M RAM Die")).registerOre("ktfruRAM12G");
        ItemList.RAMBar2G16.set(addItem(30457, "RAMBar32G", "Composed by 16* 2G RAM Die")).registerOre("ktfruRAM32G");
        
        ItemList.RAMBar16M32.set(addItem(30504, "RAMBar512M", "Composed by 32* 16M RAM Die")).registerOre("ktfruRAM512M");
        ItemList.RAMBar128M32.set(addItem(30505, "RAMBar4G", "Composed by 32* 128M RAM Die")).registerOre("ktfruRAM4G");
        ItemList.RAMBar768M32.set(addItem(30506, "RAMBar24G", "Composed by 32* 768M RAM Die")).registerOre("ktfruRAM24G");
        ItemList.RAMBar2G32.set(addItem(30507, "RAMBar64G", "Composed by 32* 2G RAM Die")).registerOre("ktfruRAM64G");


    }
}