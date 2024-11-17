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


package cn.kuzuanpa.ktfruaddon.tile.multiblock.base;

import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.IConditionParts;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import gregapi.util.WD;
import net.minecraft.util.ChunkCoordinates;

import java.util.ArrayList;

public abstract class TileEntityBaseControlledMachine extends TileEntityBase10MultiBlockMachine {
    public ArrayList<ChunkCoordinates> ConditionPartsPos = new ArrayList<>();

    public void addInputSubSource(ChunkCoordinates p){
        this.ConditionPartsPos.add(p);
    }

    public boolean isControllerAllValid(){
        return ConditionPartsPos.isEmpty()|| ConditionPartsPos.stream().allMatch(pos-> WD.te(getWorld(),pos,true) instanceof IConditionParts && ((IConditionParts) WD.te(getWorld(),pos,true)).canMachineRun());
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if(isServerSide())setStateOnOff(isControllerAllValid());
    }
}

