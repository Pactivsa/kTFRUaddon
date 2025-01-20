/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 */

package cn.kuzuanpa.ktfruaddon.tile.computerCluster.controller;

import cn.kuzuanpa.ktfruaddon.api.code.codeUtil;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterUser;
import codechicken.lib.vec.BlockCoord;
import gregapi.tileentity.connectors.MultiTileEntityWireElectric;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.util.WD;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

import static gregapi.data.CS.ALL_SIDES;

public class ControllerElectric extends ControllerBase implements ITileEntityEnergy {
    @Override
    public boolean canReachController(IComputerClusterController controller) {
        if(this.equals(controller))return true;
        for (byte tSide : ALL_SIDES) {
            TileEntity t = getTileEntityAtSideAndDistance(tSide, 1);
            if(Objects.equals(controller, t))return true;
            if(!(t instanceof MultiTileEntityWireElectric))continue;
            if(walkElectricWire(new BlockCoord(t.xCoord,t.yCoord,t.zCoord), controller.getPos()))return true;
        }
        return false;
    }

    public boolean walkElectricWire(BlockCoord startPos, BlockCoord targetPos){
        Queue<MultiTileEntityWireElectric> queue = new ArrayDeque<>();
        List<BlockCoord> visitedPos = new ArrayList<>();
        visitedPos.add(startPos);
        TileEntity start = WD.te(getWorld(), codeUtil.CCCoord2MCCoord(startPos),false);
        if(start instanceof MultiTileEntityWireElectric)queue.add(((MultiTileEntityWireElectric) start));
        else return false;
        while(!queue.isEmpty()){
            MultiTileEntityWireElectric tile = queue.poll();
            for (byte tSide : ALL_SIDES) {
                TileEntity t = tile.getTileEntityAtSideAndDistance(tSide, 1);
                if(t == null)continue;
                if(Objects.equals(new BlockCoord(t.xCoord,t.yCoord,t.zCoord),targetPos))return true;
                if(t instanceof MultiTileEntityWireElectric && !visitedPos.contains(new BlockCoord(t.xCoord,t.yCoord,t.zCoord))){
                    queue.add((MultiTileEntityWireElectric) t);
                    visitedPos.add(new BlockCoord(t.xCoord,t.yCoord,t.zCoord));
                }
            }
        }
        return false;
    }

    @Override
    public boolean canReachUser(IComputerClusterUser user) {
        return super.canReachUser(user);
    }
    public String getTileEntityName() {
        return "ktfru.multitileentity.computecluster.controller.electric";
    }

}
