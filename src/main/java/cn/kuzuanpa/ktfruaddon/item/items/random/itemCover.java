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

import cn.kuzuanpa.ktfruaddon.cover.CoverFilterProperties;
import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import gregapi.data.TC;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemCover extends MultiItemRandom {
    public itemCover() {
        super(MOD_ID, "ktfru.item.cover");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Covers", this, (short) 0));
    }


    @Override
    public void addItems() {
        ItemList.PropertiesFilter.set(addItem(0, "Item Filter (Properties)", "Filters for an Item", new CoverFilterProperties(), TC.stack(TC.MACHINA, 1), TC.stack(TC.ORDO, 1), TC.stack(TC.ITER, 1)));
    }
}
