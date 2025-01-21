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

import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterUser;
import cn.kuzuanpa.ktfruaddon.tile.computerCluster.IWiredNetworkConnectable;
import cn.kuzuanpa.ktfruaddon.tile.computerCluster.NetworkCable;
import codechicken.lib.vec.BlockCoord;
import cpw.mods.fml.common.FMLLog;
import gregapi.code.HashSetNoNulls;
import net.minecraft.tileentity.TileEntity;
import org.apache.logging.log4j.Level;

import java.util.Objects;
import java.util.UUID;

import static gregapi.data.CS.*;

public class ControllerWired extends ControllerBase implements IWiredNetworkConnectable {
    @Override
    public boolean canReachController(IComputerClusterController controller) {
        for (byte tSide : ALL_SIDES) {
            TileEntity tDelegator = getTileEntityAtSideAndDistance(tSide, 1);
            if(Objects.equals(tDelegator, controller))return true;
        }
        for (byte tSide : ALL_SIDES) {
            TileEntity tDelegator = getTileEntityAtSideAndDistance(tSide, 1);
            if (tDelegator instanceof NetworkCable && ((NetworkCable) tDelegator).canReach(new BlockCoord(controller.getPos().x, controller.getPos().y,controller.getPos().z), new HashSetNoNulls<>())) return true;
        }
        return false;
    }

    @Override
    public boolean canReachUser(IComputerClusterUser user) {
        return super.canReachUser(user);
    }
    public String getTileEntityName() {
        return "ktfru.multitileentity.computecluster.controller.wired";
    }

    public int[] receivedData;
    @Override
    public void requestReachableCheck() {
        try {
            if(getCluster() == null)return;

            getCluster().updateAllControllerState();
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void takeChannel(UUID user) {

    }

    @Override
    public void updateReachable() {
        if(getCluster() == null || getCluster().controllerReachableUpdateCache.containsKey(myUUID)) return;
        getCluster().controllerReachableUpdateCache.put(myUUID, 0);
        for (byte tSide : ALL_SIDES_VALID) {
            TileEntity tDelegator = getTileEntityAtSideAndDistance(tSide, 1);
            if ((tDelegator instanceof IWiredNetworkConnectable)) ((IWiredNetworkConnectable) tDelegator).takeChannel(myUUID);
        }
    }

    @Override
    public void checkUpdatedReachable() {
        mState = Constants.STATE_ERROR;
        for (byte tSide : ALL_SIDES_VALID) {
            TileEntity tDelegator = getTileEntityAtSideAndDistance(tSide, 1);
            if ((tDelegator instanceof NetworkCable) && ((NetworkCable) tDelegator).connected(OPOS[tSide])){
                int connectedTargets = (((NetworkCable) tDelegator).controllers.size());
                if(connectedTargets  == getCluster().controllerReachableUpdateCache.size()) {
                    mState = Constants.STATE_NORMAL;
                    break;
                } else if (connectedTargets > 1) {
                    mState = Constants.STATE_WARNING;
                }
            }
        }
    }
}
