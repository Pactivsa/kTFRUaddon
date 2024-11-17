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

import cn.kuzuanpa.ktfruaddon.item.util.ItemList;
import cn.kuzuanpa.ktfruaddon.recipe.recipeMaps;
import gregapi.data.FL;
import gregapi.data.MT;
import gregapi.util.ST;

import static gregapi.data.CS.*;

public class Fusion {
    public static void init(){
        recipeMaps.FusionTokamak .addRecipe1(F, 2048,400,new long[]{1000,100,1}, ST.tag(0), FL.array(MT.D .gas (U, T),MT.T.gas(U, T)), FL.array(MT.He.gas (23*U100, F), MT.n.gas (10*U100, F) ), ItemList.FusionTokamakData0.get(1),ItemList.FusionTokamakData1.get(1),ItemList.FusionTokamakData2.get(1) ).setSpecialNumber(1024L*1024L);
    }
}
