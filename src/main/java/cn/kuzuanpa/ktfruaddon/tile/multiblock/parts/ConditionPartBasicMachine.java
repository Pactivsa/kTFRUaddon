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


package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import gregapi.data.LH;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ConditionPartBasicMachine extends CommonMachinePart implements IConditionParts {

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.part.electric";
    }
    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        LH.addEnergyToolTips(this, aList, mEnergyTypeAccepted, null, null, null);
    }
    @Override
    public boolean allowRightclick(Entity aEntity) {
        return false;
    }

    @Override
    public boolean canMachineRun() {
        return mActive;
    }
}
