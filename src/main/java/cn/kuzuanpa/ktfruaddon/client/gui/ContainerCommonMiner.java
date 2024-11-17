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


package cn.kuzuanpa.ktfruaddon.client.gui;

import gregapi.gui.ContainerCommon;
import gregapi.gui.Slot_Normal;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerCommonMiner extends ContainerCommon {

	public ContainerCommonMiner(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID) {
		super(aInventoryPlayer, aTileEntity, aGUIID);
	}
	
	@Override
	public int addSlots(InventoryPlayer aPlayerInventory) {
		int tIndex =0;
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 80, 4));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 8, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 26, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 44, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 62, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 80, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 98, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 116, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 134, 40));
		addSlotToContainer(new Slot_Normal(mTileEntity, tIndex++, 152, 40));
		return super.addSlots(aPlayerInventory);
	}

	@Override public int getStartIndex() {return 0;}
	@Override public int getSlotCount() {return 10;}
	@Override public int getShiftClickStartIndex() {return 0;}
	@Override public int getShiftClickSlotCount() {return 10;}
}
