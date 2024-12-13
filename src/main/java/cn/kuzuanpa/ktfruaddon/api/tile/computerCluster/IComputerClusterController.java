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

 * kTFRUAddon is Open Source and distributed under the
 * AGPLv3 License: https://www.gnu.org/licenses/agpl-3.0.txt
 *
 */
package cn.kuzuanpa.ktfruaddon.api.tile.computerCluster;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface IComputerClusterController {
    UUID getUUID();

    /**Designed for writeToNBT to save data, used for cluster recovery**/
    UUID getSavedClusterUUID();
    /**Designed for writeToNBT to save data, used for cluster recovery**/
    void setSavedClusterUUID(UUID uuid);
    /**Designed for writeToNBT to save data, used for cluster recovery, data is simplified that only contains worldID and pos**/
    void setSavedClusterControllers(List<ControllerData> data);

    void setUUID(UUID uuid);
    byte getState();
    Map.Entry<ComputerPower,Long> getComputePower();
    void notifyControllerEvent(short event);
    boolean setCluster(ComputerCluster cluster);
    ComputerCluster getCluster();

}
