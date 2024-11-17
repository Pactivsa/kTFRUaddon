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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

@SideOnly(Side.CLIENT)
public class ContainerClientMiner extends ContainerClientbase {
	@Override
	public void initGui() {
		super.initGui();
		mBackground = new ResourceLocation(MOD_ID,"textures/gui/miner.png");
	}
	public ContainerClientMiner(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID, String aGUITexture) {
		super(new ContainerCommonMiner(aInventoryPlayer, aTileEntity ,aGUIID), aGUITexture);
	}
	protected void drawGuiContainerForegroundLayer2(int par1, int par2) {
		ContainerCommonMiner container = (ContainerCommonMiner) mContainer;
	}
	@Override
	protected void drawGuiContainerBackgroundLayer2(float par1, int par2, int par3) {
		ContainerCommonMiner container = (ContainerCommonMiner) mContainer;

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x,y-5, 0, 0, xSize, ySize+5);

	}
	public void mouseMove(int x, int y){}

	public boolean onButtonPressed(GuiButton button) {return true;}
	@Override
	public boolean onNoButtonPressed() {return true;}
}
