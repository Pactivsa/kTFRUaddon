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


package cn.kuzuanpa.ktfruaddon.loot;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

public class lootPostInit {
    private static final MultiTileEntityRegistry kRegistry = MultiTileEntityRegistry.getRegistry("ktfru.multitileentity");
    public static void init(FMLPostInitializationEvent aEvent){
    ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(kRegistry.getItem(32761,4),2,8,50));
    }
}
