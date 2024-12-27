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


package cn.kuzuanpa.ktfruaddon.api.tile.async;

import cn.kuzuanpa.ktfruaddon.api.client.fx.FxRenderBlockOutline;
import cn.kuzuanpa.ktfruaddon.api.tile.IMappedStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import net.minecraft.util.ChunkCoordinates;
import org.jetbrains.annotations.Nullable;

public interface IAsyncMappedStructure extends IMappedStructure {
    /**@return null = structure complete**/
    default @Nullable ChunkCoordinates checkMappedStructure(AsyncStructureManager.WorldContainer worldContainer, ChunkCoordinates lastFailedPos, int machineX, int machineY, int machineZ, int xMapOffset, int yMapOffset, int zMapOffset, boolean loadChunk){
        if (lastFailedPos != null) FxRenderBlockOutline.removeBlockOutlineToRender(lastFailedPos);
        int tX = getX(), tY = getY(), tZ = getZ();
        if (!worldContainer.isBlockExist(tX, tY, tZ) && !loadChunk) return null;
        tX = utils.getRealX(getFacing(), tX, xMapOffset, -zMapOffset);
        tY += yMapOffset;
        tZ = utils.getRealZ(getFacing(), tZ, xMapOffset, -zMapOffset);
        int mapX, mapY, mapZ;
        for (mapY = 0; mapY < machineY; mapY++) for (mapZ = 0; mapZ < machineZ ; mapZ++) for (mapX = 0; mapX < machineX; mapX++) {
            int realX=utils.getRealX(getFacing(), tX, mapX, mapZ),realY=tY + mapY,realZ=utils.getRealZ(getFacing(), tZ, mapX, mapZ);
            if (isIgnored(mapX,mapY,mapZ)) continue;
            if (IAsyncStructure.checkAndSetTarget(worldContainer,this, new ChunkCoordinates(realX, realY, realZ), getBlockID(mapX, mapY, mapZ), getRegistryID(mapX,mapY,mapZ), getDesign(mapX,mapY,mapZ, getBlockID(mapX, mapY, mapZ), getRegistryID(mapX,mapY,mapZ) ), getUsage(mapX,mapY,mapZ, getBlockID(mapX, mapY, mapZ), getRegistryID(mapX,mapY,mapZ) ))) {

            } else if(!onCheckFailed(mapX,mapY,mapZ))return new ChunkCoordinates(realX,realY,realZ);
        }
        return null;
    }

}
