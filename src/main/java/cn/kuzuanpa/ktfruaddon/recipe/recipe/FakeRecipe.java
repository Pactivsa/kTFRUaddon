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


package cn.kuzuanpa.ktfruaddon.recipe.recipe;

import cn.kuzuanpa.ktfruaddon.api.fluid.flList;
import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.FL;
import gregapi.util.ST;
import net.minecraft.init.Items;

import static gregapi.data.CS.ZL_IS;

public class FakeRecipe {
    public static void init() {
        MultiTileEntityRegistry gRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
        MultiTileEntityRegistry kRegistry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");

        recipeMaps.OilMiner.addRecipe1(false,false,true,false,true, 1, 1, kRegistry.getItem(30013), FL.Water.make(1), flList.AqueousOilExtraHeavy.make(1),ZL_IS);
        recipeMaps.OilMiner.addRecipe1(false,false,true,false,true, 1, 1, kRegistry.getItem(30013), FL.Water.make(1), flList.AqueousOilHeavy.make(1),ZL_IS);
        recipeMaps.OilMiner.addRecipe1(false,false,true,false,true, 1, 1, kRegistry.getItem(30013), FL.Water.make(1), flList.AqueousOilMedium.make(1),ZL_IS);
        recipeMaps.OilMiner.addRecipe1(false,false,true,false,true, 1, 1, kRegistry.getItem(30013), FL.Water.make(1), flList.AqueousOilNormal.make(1),ZL_IS);
        recipeMaps.OilMiner.addRecipe1(false,false,true,false,true, 1, 1, kRegistry.getItem(30013), FL.Water.make(1), flList.AqueousOilLight.make(1),ZL_IS);
        recipeMaps.OilMiner.addRecipe1(false,false,true,false,true, 1, 1, kRegistry.getItem(30013), FL.Air.make(1), FL.Gas_Natural.make(1),ZL_IS);

        recipeMaps.CrucibleModel.addRecipe2(false, false,true,false,true,16,20, ItemList.CrucibleModelInnerLayer.get(0), ST.make(Items.clay_ball,7,0), gRegistry.getItem(1005));
    }
}
