/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */

package cn.kuzuanpa.ktfruaddon.client.gui;

import cn.kuzuanpa.ktfruaddon.client.gui.button.CommonGuiButton;
import gregapi.code.ArrayListNoNulls;
import gregapi.gui.ContainerClient;
import gregapi.gui.ContainerCommon;
import net.minecraft.client.gui.GuiButton;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class ContainerClientbase extends ContainerClient {
    HashMap<Integer,requestedTooltip> requestedTooltipMap = new HashMap<>();
    protected GuiButton selectedButton;

    protected static class requestedTooltip {
        protected requestedTooltip(List<String> strings, Predicate<Vector2f> posPredicate, boolean additionalCondition){
            this.strings=strings;
            this.posPredicate=posPredicate;
            this.additionalCondition=additionalCondition;
        }
        protected List<String> strings;
        protected Predicate<Vector2f> posPredicate;
        public boolean isPosCheckPassed = false;
        protected boolean additionalCondition;
    }

    public void addTooltip(int id, String[] strings, Predicate<Vector2f> MousePositionPredicate, boolean additionalCondition){
        requestedTooltipMap.put(id, new requestedTooltip(Arrays.asList(strings),MousePositionPredicate,additionalCondition));
    }

    public void updateTooltip(int id, boolean additionalCondition){
        if(requestedTooltipMap.get(id)!=null)requestedTooltipMap.get(id).additionalCondition=additionalCondition;
    }

    public void addOrUpdateTooltip(int id, String[] strings,Predicate<Vector2f> MousePositionPredicate){
        if(requestedTooltipMap.get(id)!=null)requestedTooltipMap.get(id).strings= Arrays.asList(strings);
        else addTooltip(id,strings,MousePositionPredicate,true);
    }
    public void addOrUpdateTooltip(int id, String[] strings,Predicate<Vector2f> MousePositionPredicate,boolean additionalCondition){
        if(requestedTooltipMap.get(id)!=null){
            requestedTooltipMap.get(id).strings= Arrays.asList(strings);
            requestedTooltipMap.get(id).additionalCondition=additionalCondition;
        }
        else addTooltip(id,strings,MousePositionPredicate,additionalCondition);
    }

    public ContainerClientbase(ContainerCommon aContainer, String aBackgroundPath) {
        super(aContainer, aBackgroundPath);
    }
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        drawGuiContainerForegroundLayer2(par1,par2);
        requestedTooltipMap.values().forEach(tooltip -> {
            if (tooltip!=null&& tooltip.additionalCondition &&tooltip.isPosCheckPassed)drawHoveringText(tooltip.strings, par1-(width - xSize) / 2, par2-(height - ySize) / 2, fontRendererObj);
        });
    }
    protected abstract void drawGuiContainerForegroundLayer2(int par1, int par2);
    @Override
    protected void actionPerformed(GuiButton p_146284_1_) {super.actionPerformed(p_146284_1_);onButtonPressed(p_146284_1_);}

    @Override
    public void handleMouseInput(){
        super.handleMouseInput();
        int i = Mouse.getEventX() * this.width / this.mc.displayWidth;
        int j = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
        mouseMove(i,j);
    }

    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_)
    {
        super.mouseClicked(p_73864_1_, p_73864_2_, p_73864_3_);
        if (p_73864_3_ == 0)
        {
            boolean result=false;
            for (int l = 0; l < this.buttonList.size(); ++l)
            {
                GuiButton guibutton = (GuiButton)this.buttonList.get(l);

                if (guibutton.mousePressed(this.mc, p_73864_1_, p_73864_2_))
                {

                    GuiScreenEvent.ActionPerformedEvent.Pre event = new GuiScreenEvent.ActionPerformedEvent.Pre(this, guibutton, this.buttonList);
                    if (MinecraftForge.EVENT_BUS.post(event))
                        break;
                    this.selectedButton = event.button;
                    event.button.func_146113_a(this.mc.getSoundHandler());
                    result=onButtonPressed(event.button);
                    if (this.equals(this.mc.currentScreen))
                        MinecraftForge.EVENT_BUS.post(new GuiScreenEvent.ActionPerformedEvent.Post(this, event.button, this.buttonList));
                    if(result)break;
                }
            }
            if(!result)onNoButtonPressed();
        }
    }

    public void mouseMove(int x, int y){
        int ContainerX = (width - xSize) / 2;
        int ContainerY = (height - ySize) / 2;
        buttonList.forEach(button-> {if (button instanceof CommonGuiButton)((CommonGuiButton) button).isMouseHover= ((CommonGuiButton) button).isMouseOverButton(x, y);});
        requestedTooltipMap.values().forEach(tooltip-> tooltip.isPosCheckPassed=tooltip.posPredicate.test(new Vector2f(x-ContainerX,y-ContainerY)));
    };
    public abstract boolean onButtonPressed(GuiButton button);
    public abstract boolean onNoButtonPressed();
}
