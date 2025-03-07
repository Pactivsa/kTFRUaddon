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


package cn.kuzuanpa.ktfruaddon.api.tile;

import cn.kuzuanpa.ktfruaddon.api.client.fx.FxRenderBlockOutline;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface IMappedStructure extends ITileEntityMultiBlockController {
    /**@return null = structure complete**/
    default @Nullable ChunkCoordinates checkMappedStructure(ChunkCoordinates lastFailedPos, int machineX, int machineY, int machineZ, int xMapOffset, int yMapOffset, int zMapOffset){
        if (lastFailedPos != null) FxRenderBlockOutline.removeBlockOutlineToRender(lastFailedPos);
        int tX = getX(), tY = getY(), tZ = getZ();
        if (!getWorld().blockExists(tX, tY, tZ)) return null;
        List<TileEntity> specialBlockList = new ArrayList<>();
        tX = utils.getRealX(getFacing(), tX, xMapOffset, -zMapOffset);
        tY += yMapOffset;
        tZ = utils.getRealZ(getFacing(), tZ, xMapOffset, -zMapOffset);
        int mapX, mapY, mapZ;
        for (mapY = 0; mapY < machineY; mapY++) for (mapZ = 0; mapZ < machineZ ; mapZ++) for (mapX = 0; mapX < machineX; mapX++) {
            int realX=utils.getRealX(getFacing(), tX, mapX, mapZ),realY=tY + mapY,realZ=utils.getRealZ(getFacing(), tZ, mapX, mapZ);
            if (isIgnored(mapX,mapY,mapZ)) continue;
            ChunkCoordinates realPos = new ChunkCoordinates(realX,realY,realZ);

            boolean partValid = false;

            if(this instanceof ICustomPartValidator){
                if(((ICustomPartValidator) this).isPartValid(realPos, new ChunkCoordinates(mapX,mapY,mapZ))) partValid = true;
            }
            else if (utils.checkAndSetTarget(this, realPos, getTileDescs(mapX,mapY,mapZ))) partValid = true;

            if(partValid){
                TileEntity tile = this.getTileEntity(realPos);
                if(isPartSpecial(tile)) specialBlockList.add(tile);
            }else if(!onCheckFailed(mapX,mapY,mapZ))return realPos;
        }
        if(!specialBlockList.isEmpty())receiveSpecialBlockList(specialBlockList);
        return null;
    }

    /**@return will we ignore this error and continue check**/
    default boolean onCheckFailed(int mapX,int mapY,int mapZ){return false;}

    TileDesc[] getTileDescs(int mapX, int mapY, int mapZ);

    boolean isIgnored(int mapX,int mapY,int mapZ);
    short getFacing();
    int getX();
    int getY();
    int getZ();
    World getWorld();

    default boolean isPartSpecial(TileEntity tile){return false;}
    default void receiveSpecialBlockList(List<TileEntity> list){};
}
