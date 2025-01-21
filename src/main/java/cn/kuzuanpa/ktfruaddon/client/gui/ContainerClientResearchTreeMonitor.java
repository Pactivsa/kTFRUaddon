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

import cn.kuzuanpa.kGuiLib.client.anime.animeMoveLinear;
import cn.kuzuanpa.kGuiLib.client.anime.animeMoveSlowIn;
import cn.kuzuanpa.kGuiLib.client.anime.shortcut.animeTransparency;
import cn.kuzuanpa.kGuiLib.client.kGuiContainerBase;
import cn.kuzuanpa.kGuiLib.client.objects.gui.ThinkerButtonBase;
import cn.kuzuanpa.ktfruaddon.api.nei.IHiddenNei;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchItem;
import cn.kuzuanpa.ktfruaddon.api.research.ResearchTree;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.tileentity.ITileEntityInventoryGUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

@SideOnly(Side.CLIENT)
//TODO: 侧边栏显示研究项
//      可拖动的界面(考虑提取到kGuiLib)
public class ContainerClientResearchTreeMonitor extends kGuiContainerBase implements IHiddenNei {
	private ContainerCommonResearchTreeMonitor mContainer;
	public ResearchTree theTree = new ResearchTree();

	public ContainerClientResearchTreeMonitor(InventoryPlayer aInventoryPlayer, ITileEntityInventoryGUI aTileEntity, int aGUIID, String aGUITexture) {
		super(new ContainerCommonResearchTreeMonitor(aInventoryPlayer, aTileEntity,aGUIID));


		this.mContainer=(ContainerCommonResearchTreeMonitor)inventorySlots;
		this.xSize= 226;
		this.ySize= 235;
	}
	final ResourceLocation background = new ResourceLocation(MOD_ID,"textures/gui/research/background.png");
	final ResourceLocation main = new ResourceLocation(MOD_ID,"textures/gui/research/main.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_ALPHA_TEST);
		mc.getTextureManager().bindTexture(background);
		GL11.glColor4f(1,1,1,0.1f);

		this.drawTexturedModalRect(0,0, 0, 14, width, height);
	}

	public Map<String, Integer> researchIDToIntIDMap = new HashMap<>();
	@Override
	public void addButtons() {

		AtomicInteger i = new AtomicInteger();
		theTree.allResearch.forEach((s, researchItem) -> {
			researchIDToIntIDMap.put(s,i.get());
			int rate = 400;


			int layer = researchItem.layer;
			buttons.add(new researchButton(i.get(),researchItem).setJoinLeaveTime(layer*rate,Integer.MAX_VALUE).addAnime(new animeTransparency(layer*rate,layer*rate+1000,0,255)).addAnime(new animeMoveLinear(-1,0,30,-2)).addAnime(new animeMoveSlowIn(layer*rate, layer*rate+500, -30,2,3)));
			i.getAndIncrement();
		});
	}

	@Override
	public boolean onButtonPressed(GuiButton button, int mouseX, int mouseY) {

		return false;
	}


	@Override
	public void onKeyTyped(char key, int keyCode) {
		if(keyCode == Keyboard.KEY_ESCAPE)close();
	}

	public class researchButton extends ThinkerButtonBase {
		public researchButton(int id, ResearchItem researchItem) {
			super(id, researchItem.posX, researchItem.posY, 96, 48, "");
			this.researchItem=researchItem;
			setAnimatedInFBO(true);
		}
		final ResearchItem researchItem;
		@Override
		public void drawButton2(Minecraft mc, int mouseX, int mouseY) {
			if(!visible)return;
			float colorTimer = ((float) Math.sin(System.currentTimeMillis()%3141/1000f))/2f+0.5f;
			Tessellator tessellator = Tessellator.instance;

			GL11.glEnable(GL11.GL_ALPHA_TEST);

			drawBackground(tessellator,colorTimer);
			drawMainIcon();
			drawNameDesc();
			drawProgressBar(tessellator);
			drawConditionIcon();
			drawLockedMask(tessellator);
			GL11.glColor4f(1,1,1,1);
		}

		public void fillColor(float colorTimer){
			if(!researchItem.isUnlocked)GL11.glColor4f(.5f,.5f,.5f,.4f);
			else if (!researchItem.isCompleted && researchItem.progress > 0) GL11.glColor4f(0f,colorTimer/2f+0.5f,(1-colorTimer)/4f+0.75f,.9f);
			else if (!researchItem.isCompleted) GL11.glColor4f(1f,1f-colorTimer/2.5f,0,.9f);
			else GL11.glColor4f(colorTimer/3f+.1f,.8f,.0f,.9f);
		}
		public void drawBackground(Tessellator tessellator, float colorTimer){
			fillColor(colorTimer);
			mc.getTextureManager().bindTexture(main);
			drawRect(tessellator, xPosition, yPosition, 0, 0, width, height);
			drawDependsLine(tessellator, colorTimer);
			GL11.glColor4f(1,1,1,1);
		}
		public void drawDependsLine(Tessellator tessellator, float colorTimer){
			for (ResearchItem item : researchItem.prerequisites){
				tessellator.startDrawing(GL11.GL_LINES);
				GL11.glLineWidth(1f);
				tessellator.addVertex(xPosition, yPosition + height/2f , this.zLevel);
				if(item.name.equals("root")) tessellator.addVertex(0, mc.currentScreen.height/2f, this.zLevel);
				else if(researchIDToIntIDMap.get(item.name) != null){
					ThinkerButtonBase b = buttons.get(researchIDToIntIDMap.get(item.name));
					if (b != null) tessellator.addVertex(b.xPosition + width, b.yPosition+height/2f, this.zLevel);
					tessellator.draw();
					continue;
				}
				tessellator.draw();
			}
		}
		public void drawProgressBar(Tessellator tessellator){
			mc.getTextureManager().bindTexture(background);
			GL11.glColor4f(1, 1, 1, 1f);
			drawRect(tessellator, xPosition+2, yPosition+height-12, 0, 14, width-4, 10);
		}
		public void drawConditionIcon(){
			AtomicInteger i = new AtomicInteger();
			researchItem.conditions.forEach(condition -> {
				mc.getTextureManager().bindTexture(mc.getTextureManager().getResourceLocation(Items.saddle.getSpriteNumber()));
				if(condition.getIcon() != null)drawTexturedModelRectFromIcon(xPosition + 2 + i.getAndIncrement() * 10,yPosition + 30, condition.getIcon(), 8,8);
				else drawTexturedModelRectFromIcon(xPosition + 2 + i.getAndIncrement() * 10,yPosition + 31, Items.book.getIconFromDamage(0), 8,8);
			});
		}
		public void drawMainIcon(){
			mc.getTextureManager().bindTexture(mc.getTextureManager().getResourceLocation(Items.book.getSpriteNumber()));
			if(researchItem.icon != null)drawTexturedModelRectFromIcon(xPosition + 3,yPosition + 3, researchItem.icon, 16,16);
			else drawTexturedModelRectFromIcon(xPosition + 3,yPosition + 3, Items.book.getIconFromDamage(0), 16,16);
		}
		public void drawLockedMask(Tessellator tessellator){
			if(researchItem.isUnlocked) return;
			mc.getTextureManager().bindTexture(background);
			GL11.glColor4f(1, 1, 1, 0.5f);
			drawRect(tessellator, xPosition, yPosition, 20, 40, width, height);
		}
		public void drawNameDesc(){
			String desc = mc.fontRenderer.trimStringToWidth(researchItem.desc+" Hello World",width-12);
			if(!desc.equals(researchItem.desc))desc += "...";
			mc.fontRenderer.drawStringWithShadow(desc, xPosition + 4,yPosition+20,0xffffffff);
			//Do not exchange these two line!
			mc.fontRenderer.drawStringWithShadow(researchItem.name, xPosition + 22,yPosition+6,0xffffffff);

			GL11.glColor4f(1,1,1,1);
		}
		public void drawRect(Tessellator tessellator,int x, int y,int u,int v, int width, int height){
			final float f = 0.00390625F;
			final float f1 = 0.00390625F;
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(x, y + height, this.zLevel, (u * f), (v + height) * f1);
			tessellator.addVertexWithUV(x + width, y + height, this.zLevel, (u + width) * f, (v + height) * f1);
			tessellator.addVertexWithUV(x + width, y, this.zLevel, (u + width) * f, v * f1);
			tessellator.addVertexWithUV(x, y, this.zLevel, (u * f), v * f1);
			tessellator.draw();
		}
	}
}
