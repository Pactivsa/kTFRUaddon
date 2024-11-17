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
package cn.kuzuanpa.ktfruaddon.tile.multiblock.base.async;

import cn.kuzuanpa.ktfruaddon.tile.multiblock.parts.IMultiBlockPart;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

public interface IAsyncStructure {

    default void onAsyncCheckStructureCompleted() {};

    boolean asyncCheckStructure(AsyncStructureManager.WorldContainer worldContainer);

    static boolean checkAndSetTarget(AsyncStructureManager.WorldContainer worldContainer,ITileEntityMultiBlockController aController, ChunkCoordinates coord, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        TileEntity tTileEntity = worldContainer.getTileEntity(coord.posX,coord.posY,coord.posZ);
        if (tTileEntity == aController) {
            return true;
        } else if (tTileEntity instanceof MultiTileEntityMultiBlockPart && ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((MultiTileEntityMultiBlockPart) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) return false;
            else {
                ((MultiTileEntityMultiBlockPart) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else if (tTileEntity instanceof IMultiBlockPart && ((IMultiBlockPart) tTileEntity).getMultiTileEntityID() == aRegistryMeta && ((IMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID() == aRegistryID) {
            ITileEntityMultiBlockController tTarget = ((IMultiBlockPart) tTileEntity).getTarget(false);
            if (tTarget != aController && tTarget != null) return false;
            else {
                ((IMultiBlockPart) tTileEntity).setTarget(aController, aDesign, aMode);
                return true;
            }
        } else return false;
    }
}
