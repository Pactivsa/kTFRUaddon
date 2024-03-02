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
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

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


    }
}