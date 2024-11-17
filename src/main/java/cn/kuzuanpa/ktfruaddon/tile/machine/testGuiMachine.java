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


package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.client.gui.ContainerClientTest;
import cn.kuzuanpa.ktfruaddon.client.gui.ContainerCommonTest;
import gregapi.tileentity.machines.MultiTileEntityBasicMachineElectric;
import net.minecraft.entity.player.EntityPlayer;

public class testGuiMachine extends MultiTileEntityBasicMachineElectric {
    @Override public String getTileEntityName() {return "ktfru.multitileentity.machine.gui.electric";}
    @Override public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientTest(aPlayer.inventory, this, mRecipes, aGUIID, mGUITexture);
    }
    @Override public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonTest(aPlayer.inventory, this, aGUIID);
    }
}
