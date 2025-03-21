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


package cn.kuzuanpa.ktfruaddon.client.gui.button;

import net.minecraft.client.gui.GuiButton;

public class CommonGuiButton extends GuiButton {
    public boolean isMouseHover=false;
    public CommonGuiButton(int id, int xPos, int yPos, int width, int height, String displayText) {
        super(id, xPos, yPos,width,height,displayText);
    }
    public boolean isMouseOverButton(int mouseX, int mouseY) {
        return mouseX >= xPosition && mouseY >= yPosition && mouseX < xPosition + width && mouseY < yPosition + height;
    }

}
