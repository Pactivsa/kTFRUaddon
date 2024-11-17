/**
 * Copyright (c) 2019 Gregorius Techneticies
 *
 * This file is part of GregTech.
 *
 * GregTech is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GregTech is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GregTech. If not, see <http://www.gnu.org/licenses/>.
 */

package cn.kuzuanpa.ktfruaddon.client.gui.computerCluster;

import cn.kuzuanpa.kGuiLib.client.IkGuiContainerLayer;
import cn.kuzuanpa.kGuiLib.client.anime.animeMoveLinear;
import cn.kuzuanpa.kGuiLib.client.anime.animeMoveSlowIn;
import cn.kuzuanpa.kGuiLib.client.anime.shortcut.animeFadeIn;
import cn.kuzuanpa.kGuiLib.client.kGuiContainerBase;
import cn.kuzuanpa.kGuiLib.client.objects.IAnimatableButton;
import cn.kuzuanpa.kGuiLib.client.objects.gui.ThinkerButtonBase;
import cn.kuzuanpa.ktfruaddon.nei.IHiddenNei;
import cn.kuzuanpa.ktfruaddon.tile.computerCluster.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

@SideOnly(Side.CLIENT)
public class ContainerClientClusterController extends kGuiContainerBase implements IHiddenNei {
	private ContainerCommonClusterController mContainer;

	ComputerClusterClientData.ClientList       dataClientList      ;
	ComputerClusterClientData.ClusterDetail    dataClusterDetail   ;
	ComputerClusterClientData.ControllerDetail dataControllerDetail;
	ComputerClusterClientData.ControllerList   dataControllerList  ;

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glEnable(GL11.GL_BLEND);
		mc.getTextureManager().bindTexture(commonBackground);
		GL11.glColor4f(1,1,1,1);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x,y+14, 0, 14, xSize, ySize-14);
	}

	public ContainerClientClusterController(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID, String aGUITexture) {
		super(new ContainerCommonClusterController(aInventoryPlayer, aTileEntity,aGUIID));

		this.mContainer=(ContainerCommonClusterController)inventorySlots;
		this.xSize= 226;
		this.ySize= 235;
	}
	final ResourceLocation commonBackground = new ResourceLocation(MOD_ID,"textures/gui/computerCluster/base.png");

	@Override
	public void addButtons() {
		int ContainerX = (width - xSize) / 2;
		int ContainerY = (height - ySize) / 2;
		buttons.add(new switchButton(0,ContainerX+ 2,ContainerY, 2,"Overview",        commonBackground).addAnime(new animeMoveLinear(-1,0,50,0)).addAnime(new animeMoveSlowIn(0, 600,-50,0,2)).addAnime(new animeFadeIn(200)));
		buttons.add(new switchButton(1,ContainerX+22,ContainerY,22,"Cluster Overview",commonBackground).addAnime(new animeMoveLinear(-1,0,50,0)).addAnime(new animeMoveSlowIn(0, 800,-50,0,2)).addAnime(new animeFadeIn(400)));
		buttons.add(new switchButton(2,ContainerX+42,ContainerY,42,"Controller List", commonBackground).addAnime(new animeMoveLinear(-1,0,50,0)).addAnime(new animeMoveSlowIn(0,1000,-50,0,2)).addAnime(new animeFadeIn(600)));
		buttons.add(new switchButton(3,ContainerX+62,ContainerY,62,"Client List",     commonBackground).addAnime(new animeMoveLinear(-1,0,50,0)).addAnime(new animeMoveSlowIn(0,1200,-50,0,2)).addAnime(new animeFadeIn(800)));
	}

	@Override
	public boolean onButtonPressed(GuiButton button, int mouseX, int mouseY) {
		int ContainerX = (width - xSize) / 2;
		int ContainerY = (height - ySize) / 2;
		switch (button.id){
			case 0: childGui = new ScreenControllerDetail().setup(this.width,this.height,ContainerX,ContainerY).setParentGui(this).setMC(mc).setFontRenderer(fontRendererObj); break;
			case 1: childGui = new ScreenClusterDetail()   .setup(this.width,this.height,ContainerX,ContainerY).setParentGui(this).setMC(mc).setFontRenderer(fontRendererObj); break;
			case 2: childGui = new ScreenControllerList()  .setup(this.width,this.height,ContainerX,ContainerY).setParentGui(this).setMC(mc).setFontRenderer(fontRendererObj); break;
			case 3: childGui = new ScreenClientList()      .setup(this.width,this.height,ContainerX,ContainerY).setParentGui(this).setMC(mc).setFontRenderer(fontRendererObj); break;
		}
		if(childGui!=null){
			childGui.initGui();
			syncValuesToChildGui();
			return true;
		}
		return false;
	}

	public void syncValuesToChildGui(){
		if(childGui instanceof ScreenControllerDetail)((ScreenControllerDetail) childGui).updateFromData(((IComputerClusterController)mContainer.mTileEntity).getClientDataControllerDetail());
		if(childGui instanceof ScreenClusterDetail   )((ScreenClusterDetail   ) childGui).updateFromData(((IComputerClusterController)mContainer.mTileEntity).getClientDataClusterDetail());
		if(childGui instanceof ScreenControllerList  )((ScreenControllerList  ) childGui).updateFromData(((IComputerClusterController)mContainer.mTileEntity).getClientDataControllerList());
		if(childGui instanceof ScreenClientList      )((ScreenClientList      ) childGui).updateFromData(((IComputerClusterController)mContainer.mTileEntity).getClientDataClientList());
	}

	@Override
	public void onKeyTyped(char key, int keyCode) {
		if(keyCode == Keyboard.KEY_ESCAPE)close();
	}

	@Override
	public void setWorldAndResolution(Minecraft p_146280_1_, int p_146280_2_, int p_146280_3_) {
		super.setWorldAndResolution(p_146280_1_, p_146280_2_, p_146280_3_);
		int ContainerX = (width - xSize) / 2;
		int ContainerY = (height - ySize) / 2;
		if(childGui instanceof IkGuiContainerLayer) ((IkGuiContainerLayer) childGui).setup(this.width,this.height,ContainerX,ContainerY);
	}

	public class switchButton extends ThinkerButtonBase {

		public switchButton(int id, int xPos, int yPos, int u, String displayText,ResourceLocation resourceLocation) {
			super(id, xPos, yPos, 20, 14, displayText);
			this.resourceLocation=resourceLocation;
			this.u=u;
		}
		final int u;
		final ResourceLocation resourceLocation;
		@Override
		public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
			if(!visible)return;
			IAnimatableButton.drawPre(this, this.timer);

			mc.getTextureManager().bindTexture(resourceLocation);

			GL11.glColor3f(1,1,1);
            float f = 0.00390625F;
			float f1 = 0.00390625F;
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(xPosition         , yPosition + height , this.zLevel, (u * f), height * f1);
			tessellator.addVertexWithUV(xPosition + width , yPosition + height , this.zLevel, (u + width) * f, height * f1);
			tessellator.addVertexWithUV(xPosition + width , yPosition          , this.zLevel, (u + width) * f, 0);
			tessellator.addVertexWithUV(xPosition         , yPosition          , this.zLevel, (u * f), 0);
			GL11.glTranslatef(xPosition, yPosition ,0);
			GuiAnimeList.forEach(anime -> anime.animeDraw(timer));
			GL11.glTranslatef(-(xPosition ), -(yPosition ),0);
			tessellator.draw();
			IAnimatableButton.drawAfter(this, this.timer);
		}
	}
}
