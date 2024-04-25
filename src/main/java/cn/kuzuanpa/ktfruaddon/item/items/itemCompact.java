/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.item.items;

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import gregapi.data.MT;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;
import gregapi.oredict.OreDictItemData;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;
import static gregapi.data.CS.U;
import static gregapi.data.CS.U2;

public class itemCompact extends MultiItemRandom {
    public itemCompact() {
        super(MOD_ID, "ktfru.item.compact");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Compact", this, (short) 100));
    }


    @Override
    public void addItems() {
        ItemList.CrucibleModelInnerLayer.set(addItem(0, "Crucible Model Inner Layer", ""));
        ItemList.IntelligentCore .set(addItem(100, "Intelligent Core", ""));

        ItemList.ArmorAirSealant.set(addItem(1000, "Armor Sealant", ""));
        ItemList.SpaceSuitCloth .set(addItem(1001, "Space Suit Inner Cloth", ""));

        //To Translators: those tooltip can be translated to whatever you like.
        ItemList.EngineCrankShaftManual1.set(addItem(2000, "Rough Engine Crank Shaft (Bronze)"        , "You wonder if that could even work"),new OreDictItemData(MT.Bronze       ,2*U));
        ItemList.EngineCrankShaftManual2.set(addItem(2001, "Rough Engine Crank Shaft (Arsenic Copper)", "You wonder if that could even work"),new OreDictItemData(MT.ArsenicCopper,2*U));
        ItemList.EngineCrankShaftManual3.set(addItem(2002, "Rough Engine Crank Shaft (Arsenic Bronze)", "You wonder if that could even work"),new OreDictItemData(MT.ArsenicBronze,2*U));
        ItemList.EngineCrankShaftManual4.set(addItem(2003, "Rough Engine Crank Shaft (Steel)"         , "You wonder if that could even work"),new OreDictItemData(MT.Steel        ,2*U));
        ItemList.EngineCrankShaftManual5.set(addItem(2004, "Rough Engine Crank Shaft (Invar)"         , "You wonder if that could even work"),new OreDictItemData(MT.Invar        ,2*U));
        ItemList.EngineCrankShaftManual6.set(addItem(2005, "Rough Engine Crank Shaft (Titanium)"      , "You wonder if that could even work"),new OreDictItemData(MT.Ti           ,2*U));
        ItemList.EngineCrankShaftManual7.set(addItem(2006, "Rough Engine Crank Shaft (Tungsten Steel)", "You wonder if that could even work"),new OreDictItemData(MT.TungstenSteel,2*U));
        ItemList.EngineCrankShaftManual8.set(addItem(2007, "Rough Engine Crank Shaft (Iridium)"       , "You wonder if that could even work"),new OreDictItemData(MT.Ir           ,2*U));

        ItemList.EngineCylinderManual1.set(addItem(2100, "Rough Engine Cylinder (Bronze)"        , "It doesn't seal enough..."),new OreDictItemData(MT.Bronze       ,2*U+U2));
        ItemList.EngineCylinderManual2.set(addItem(2101, "Rough Engine Cylinder (Arsenic Copper)", "It doesn't seal enough..."),new OreDictItemData(MT.ArsenicCopper,2*U+U2));
        ItemList.EngineCylinderManual3.set(addItem(2102, "Rough Engine Cylinder (Arsenic Bronze)", "It doesn't seal enough..."),new OreDictItemData(MT.ArsenicBronze,2*U+U2));
        ItemList.EngineCylinderManual4.set(addItem(2103, "Rough Engine Cylinder (Steel)"         , "It doesn't seal enough..."),new OreDictItemData(MT.Steel        ,2*U+U2));
        ItemList.EngineCylinderManual5.set(addItem(2104, "Rough Engine Cylinder (Invar)"         , "It doesn't seal enough..."),new OreDictItemData(MT.Invar        ,2*U+U2));
        ItemList.EngineCylinderManual6.set(addItem(2105, "Rough Engine Cylinder (Titanium)"      , "It doesn't seal enough..."),new OreDictItemData(MT.Ti           ,2*U+U2));
        ItemList.EngineCylinderManual7.set(addItem(2106, "Rough Engine Cylinder (Tungsten Steel)", "It doesn't seal enough..."),new OreDictItemData(MT.TungstenSteel,2*U+U2));
        ItemList.EngineCylinderManual8.set(addItem(2107, "Rough Engine Cylinder (Iridium)"       , "It doesn't seal enough..."),new OreDictItemData(MT.Ir           ,2*U+U2));

        ItemList.EngineCrankShaft1.set(addItem(2300, "Engine Crank Shaft (Bronze)"        , ""),new OreDictItemData(MT.Bronze       ,2*U));
        ItemList.EngineCrankShaft2.set(addItem(2301, "Engine Crank Shaft (Arsenic Copper)", ""),new OreDictItemData(MT.ArsenicCopper,2*U));
        ItemList.EngineCrankShaft3.set(addItem(2302, "Engine Crank Shaft (Arsenic Bronze)", ""),new OreDictItemData(MT.ArsenicBronze,2*U));
        ItemList.EngineCrankShaft4.set(addItem(2303, "Engine Crank Shaft (Steel)"         , ""),new OreDictItemData(MT.Steel        ,2*U));
        ItemList.EngineCrankShaft5.set(addItem(2304, "Engine Crank Shaft (Invar)"         , ""),new OreDictItemData(MT.Invar        ,2*U));
        ItemList.EngineCrankShaft6.set(addItem(2305, "Engine Crank Shaft (Titanium)"      , ""),new OreDictItemData(MT.Ti           ,2*U));
        ItemList.EngineCrankShaft7.set(addItem(2306, "Engine Crank Shaft (Tungsten Steel)", ""),new OreDictItemData(MT.TungstenSteel,2*U));
        ItemList.EngineCrankShaft8.set(addItem(2307, "Engine Crank Shaft (Iridium)"       , ""),new OreDictItemData(MT.Ir           ,2*U));

        ItemList.EngineCylinder1.set(addItem(2400, "Engine Cylinder (Bronze)"        , ""),new OreDictItemData(MT.Bronze       ,2*U+U2));
        ItemList.EngineCylinder2.set(addItem(2401, "Engine Cylinder (Arsenic Copper)", ""),new OreDictItemData(MT.ArsenicCopper,2*U+U2));
        ItemList.EngineCylinder3.set(addItem(2402, "Engine Cylinder (Arsenic Bronze)", ""),new OreDictItemData(MT.ArsenicBronze,2*U+U2));
        ItemList.EngineCylinder4.set(addItem(2403, "Engine Cylinder (Steel)"         , ""),new OreDictItemData(MT.Steel        ,2*U+U2));
        ItemList.EngineCylinder5.set(addItem(2404, "Engine Cylinder (Invar)"         , ""),new OreDictItemData(MT.Invar        ,2*U+U2));
        ItemList.EngineCylinder6.set(addItem(2405, "Engine Cylinder (Titanium)"      , ""),new OreDictItemData(MT.Ti           ,2*U+U2));
        ItemList.EngineCylinder7.set(addItem(2406, "Engine Cylinder (Tungsten Steel)", ""),new OreDictItemData(MT.TungstenSteel,2*U+U2));
        ItemList.EngineCylinder8.set(addItem(2407, "Engine Cylinder (Iridium)"       , ""),new OreDictItemData(MT.Ir           ,2*U+U2));

    }
}
