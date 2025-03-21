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
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemComputer extends MultiItemRandom {
    public itemComputer() {
    super(MOD_ID, "ktfru.item.it.computer");
    setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Computers", this, (short) 17));
}
    public static long getComputePowerFromID(int id){
        if (id > ComputePower.length||id<0) return 0;
        return ComputePower[id];
    }
    //Index:                                   0 ,1 ,2  ,3   ,4   ,5   ,6    ,7    ,8     ,9     ,10    ,11    ,12    ,13    ,14    ,15    ,16     ,17    ,18    ,19     ,20    ,21     ,22     ,23     ,24,25  ,26  ,27   ,28    ,29    ,30  ,31   ,32   ,33    ,34    ,35
    private static final long[] ComputePower ={40,60,840,1020,6530,8160,34220,40370,102890,125540,181320,256330,305680,561300,453110,533120,1100230,806320,911310,1830770,939090,1566570,1025630,2285670,35,1020,2160,11820,42390,126230,2000,10000,30000,150000,500000,1500000};
    @Override
    public void addItems() {
        ItemList.ComputerTF3386          .set(addItem(0 ,"TF3386 Computer"   ,"Computing Power: 40 MFLOPS"))                .registerOre("ktfruBasicComputer");
        ItemList.ComputerTF3386S         .set(addItem(1 ,"TF3386S Computer"  ,"Computing Power: 70 MFLOPS"))                .registerOre("ktfruBasicComputer");
        ItemList.ComputerTF3586          .set(addItem(2 ,"TF3586 Computer"   ,"Computing Power: 840 MFLOPS"))               .registerOre("ktfruBasicComputer");
        ItemList.ComputerTF3586S         .set(addItem(3 ,"TF3586S Computer"  ,"Computing Power: 1020 MFLOPS"))              .registerOre("ktfruBasicComputer");
        ItemList.ComputerGT1000          .set(addItem(4 ,"GT1000 Computer"   ,"Computing Power: 6530 MFLOPS"))              .registerOre(    "ktfruNoviceComputer");
        ItemList.ComputerGT1090          .set(addItem(5 ,"GT1090 Computer"   ,"Computing Power: 8160 MFLOPS"))              .registerOre(    "ktfruNoviceComputer");
        ItemList.ComputerGT2000          .set(addItem(6 ,"GT2000 Computer"   ,"Computing Power: 34220 MFLOPS"))             .registerOre(        "ktfruModerateComputer");
        ItemList.ComputerGT2090          .set(addItem(7 ,"GT2090 Computer"   ,"Computing Power: 40370 MFLOPS"))             .registerOre(        "ktfruModerateComputer");
        ItemList.ComputerGT3660          .set(addItem(8 ,"GT3660 Computer"   ,"Computing Power: 102890 MFLOPS"))            .registerOre(        "ktfruModerateComputer");
        ItemList.ComputerGT3680          .set(addItem(9 ,"GT3680 Computer"   ,"Computing Power: 125540 MFLOPS"))            .registerOre(        "ktfruModerateComputer");
        ItemList.ComputerGT3699          .set(addItem(10,"GT3699 Computer"   ,"Computing Power: 181320 MFLOPS"))            .registerOre(            "ktfruAdvancedComputer");
        ItemList.ComputerGT3660v2        .set(addItem(11,"GT3660v2 Computer" ,"Computing Power: 256330 MFLOPS"))            .registerOre(            "ktfruAdvancedComputer");
        ItemList.ComputerGT3680v2        .set(addItem(12,"GT3680v2 Computer" ,"Computing Power: 305680 MFLOPS"))            .registerOre(            "ktfruAdvancedComputer");
        ItemList.ComputerGT3699v2        .set(addItem(13,"GT3699v2 Computer" ,"Computing Power: 561300 MFLOPS"))            .registerOre(                "ktfruEliteComputer");
        ItemList.ComputerGT3660v3        .set(addItem(14,"GT3660v3 Computer" ,"Computing Power: 453110 MFLOPS"))            .registerOre(            "ktfruAdvancedComputer");
        ItemList.ComputerGT3680v3        .set(addItem(15,"GT3680v3 Computer" ,"Computing Power: 533120 MFLOPS"))            .registerOre(                "ktfruEliteComputer");
        ItemList.ComputerGT3699v3        .set(addItem(16,"GT3699v3 Computer" ,"Computing Power: 1100230 MFLOPS"))           .registerOre(                    "ktfruMasterComputer");
        ItemList.ComputerGT3660v4        .set(addItem(17,"GT3660v4 Computer" ,"Computing Power: 806320 MFLOPS"))            .registerOre(                    "ktfruMasterComputer");
        ItemList.ComputerGT3680v4        .set(addItem(18,"GT3680v4 Computer" ,"Computing Power: 911310 MFLOPS"))            .registerOre(                    "ktfruMasterComputer");
        ItemList.ComputerGT3699v4        .set(addItem(19,"GT3699v4 Computer" ,"Computing Power: 1830770 MFLOPS"))           .registerOre(                        "ktfruUltimateComputer");

        ItemList.ComputerGT3680v3e       .set(addItem(20,"GT3680v3e Computer","Computing Power: 939090 MFLOPS"))            .registerOre(                    "ktfruMasterComputer");
        ItemList.ComputerGT3699v3e       .set(addItem(21,"GT3699v3e Computer","Computing Power: 1566570 MFLOPS"))           .registerOre(                        "ktfruUltimateComputer");
        ItemList.ComputerGT3680v4e       .set(addItem(22,"GT3680v4e Computer","Computing Power: 1025630 MFLOPS"))           .registerOre(                        "ktfruUltimateComputer");
        ItemList.ComputerGT3699v4e       .set(addItem(23,"GT3699v4e Computer","Computing Power: 2285670 MFLOPS"))           .registerOre(                        "ktfruUltimateComputer");

        ItemList.ComputerBasicCircuits   .set(addItem(24,"Basic Circuits Computer","Computing Power: 35 MFLOPS"));
        ItemList.ComputerGoodCircuits    .set(addItem(25,"Good Circuits Computer","Computing Power: 1020 MFLOPS"))          .registerOre("ktfruBasicComputer");
        ItemList.ComputerAdvancedCircuits.set(addItem(26,"Advanced Circuits Computer","Computing Power: 2160 MFLOPS"))      .registerOre(    "ktfruNoviceComputer");
        ItemList.ComputerEliteCircuits   .set(addItem(27,"Elite Circuits Computer","Computing Power: 11820 MFLOPS"))        .registerOre(    "ktfruNoviceComputer");
        ItemList.ComputerMasterCircuits  .set(addItem(28,"Master Circuits Computer","Computing Power: 42390 MFLOPS"))       .registerOre(        "ktfruModerateComputer");
        ItemList.ComputerUltimateCircuits.set(addItem(29,"Ultimate Circuits Computer","Computing Power: 126230 MFLOPS"))    .registerOre(            "ktfruAdvancedComputer");

        ItemList.UnderClockedNoviceComputer  .set(addItem(30,"Under Clocked Novice Computer","Computing Power: 2000 MFLOPS"))     .registerOre("ktfruBasicComputer");
        ItemList.UnderClockedModerateComputer.set(addItem(31,"Under Clocked Moderate Computer","Computing Power: 12000 MFLOPS"))  .registerOre(    "ktfruNoviceComputer");
        ItemList.UnderClockedAdvancedComputer.set(addItem(32,"Under Clocked Advanced Computer","Computing Power: 50000 MFLOPS"))  .registerOre(        "ktfruModerateComputer");
        ItemList.UnderClockedEliteComputer   .set(addItem(33,"Under Clocked Elite Computer","Computing Power: 150000 MFLOPS"))    .registerOre(            "ktfruAdvancedComputer");
        ItemList.UnderClockedMasterComputer  .set(addItem(34,"Under Clocked Master Computer","Computing Power: 500000 MFLOPS"))   .registerOre(                "ktfruEliteComputer");
        ItemList.UnderClockedUltimateComputer.set(addItem(35,"Under Clocked Ultimate Computer","Computing Power: 1500000 MFLOPS")).registerOre(                    "ktfruMasterComputer");

    }
}
