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

package cn.kuzuanpa.ktfruaddon.api.tile.util;

import cn.kuzuanpa.ktfruaddon.api.tile.part.IMultiBlockPart;
import gregapi.tileentity.base.TileEntityBase04MultiTileEntities;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;

public class utils {
    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        return checkAndSetTarget(aController,new ChunkCoordinates(aX,aY,aZ),aRegistryMeta,aRegistryID,aDesign,aMode);
    }
    public static boolean resetTarget(ITileEntityMultiBlockController aController,int aX, int aY, int aZ, int aDesign) {
        TileEntity tTileEntity = aController.getTileEntity(aX, aY, aZ);
        if(tTileEntity == null) return false;
        else if (tTileEntity == aController) {
            return true;
        }
        try {
            if(tTileEntity instanceof MultiTileEntityMultiBlockPart&&((MultiTileEntityMultiBlockPart) tTileEntity).getTarget(false).equals(aController))((MultiTileEntityMultiBlockPart) tTileEntity).setTarget(null, aDesign, 0);
            if(tTileEntity instanceof IMultiBlockPart&& ((IMultiBlockPart) tTileEntity).getTarget(false).equals(aController))((IMultiBlockPart) tTileEntity).setTarget(null, aDesign, 0);
        } catch (Throwable ignored){}
        return true;
    }
    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, ChunkCoordinates coord, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        return checkAndSetTarget(aController,coord, new TileDesc[] {new TileDesc(aRegistryMeta, aRegistryID, aMode, aDesign)});
    }
    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, TileDesc[] availTiles) {
        return checkAndSetTarget(aController,new ChunkCoordinates(aX,aY,aZ), availTiles);
    }
    public static boolean checkAndSetTarget(ITileEntityMultiBlockController aController, ChunkCoordinates coord, TileDesc[] availTiles) {
        TileEntity tTileEntity = aController.getTileEntity(coord);
        if (tTileEntity == aController) return true;

        if (tTileEntity instanceof MultiTileEntityMultiBlockPart) {
            for (TileDesc tTile : availTiles) {
                if (tTile.aRegistryMeta != ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityID() || tTile.aRegistryID != ((MultiTileEntityMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID()) continue;
                return setTarget(aController, tTileEntity, tTile.aDesign, tTile.aUsage);
            }
        } else if (tTileEntity instanceof IMultiBlockPart) {
            for (TileDesc tTile : availTiles) {
                if (tTile.aRegistryMeta != ((IMultiBlockPart) tTileEntity).getMultiTileEntityID() || tTile.aRegistryID != ((IMultiBlockPart) tTileEntity).getMultiTileEntityRegistryID()) continue;
                return setTarget(aController, tTileEntity, tTile.aDesign, tTile.aUsage);
            }
        }
        return false;
    }

    public static boolean setTarget(ITileEntityMultiBlockController aController, TileEntity tile, int aDesign, int aMode) {
        if(tile instanceof MultiTileEntityMultiBlockPart) {
            MultiTileEntityMultiBlockPart part = (MultiTileEntityMultiBlockPart)tile;
            ITileEntityMultiBlockController tTarget = part.getTarget(false);
            if (tTarget != aController && tTarget != null) return false;

            part.setTarget(aController, aDesign, aMode);
            return true;
        }else if (tile instanceof IMultiBlockPart) {
            IMultiBlockPart part = (IMultiBlockPart)tile;
            ITileEntityMultiBlockController tTarget = part.getTarget(false);
            if (tTarget != aController && tTarget != null) return false;

            part.setTarget(aController, aDesign, aMode);
            return true;
        }
        return false;
    }
    public static boolean resetTarget(ITileEntityMultiBlockController aController,ChunkCoordinates coord, int aDesign, int aMode) {
        return resetTarget(aController,coord.posX,coord.posY, coord.posZ, aDesign);
    }
    @Deprecated
    public static boolean checkAndSetTargetEnergyConsumerPermitted(ITileEntityMultiBlockController aController, int aX, int aY, int aZ, int aRegistryMeta, int aRegistryID, int aDesign, int aMode) {
        return checkAndSetTarget(aController, aX, aY, aZ, aRegistryMeta, aRegistryID, aDesign, aMode);
    }

    public static String getTargetTileEntityName(TileEntity tile) {
        if (tile==null||tile.isInvalid())return "null";
        try {
            return ((TileEntityBase04MultiTileEntities)tile).getTileEntityName();
        }catch (ClassCastException e){
            if (tile.getClass().getName().contains("net.minecraft.tileentity")) return tile.getClass().getName().replace("net.minecraft.tileentity","minecraft");
            return tile.getClass().getName();
        }
    }

    /**
     * @author kuzuanpa
     * <pre></pre>
     * <pre>ABC about Real Reflection</pre>
     * <pre>Using this could keep every block exractly same whether Facing.</pre>
     * <pre>使用这个可以保持所有方块完全相同，无论主方块的朝向</pre>
     * <pre></pre>
     * <pre>Currently not be fully done.</pre>
     * <pre>  1: cannot be used in machines that main block facing up and down.</pre>
     * <pre>  2: Could anyone tell me is this have any negatives such as proformance loss or something?</pre>
     * <pre></pre>
     * <pre> getRealCoord(...) needs to input a coord that didn't depend on Facing. When crotrollerBlock facing -x, then every coord is exractly what you need.</pre>
     * <pre>It will return a Real WorldCoord that can directly be used in checkAndSetTarget(...) or sth.</pre>
     * <pre>If you know Vanilla Commands, This method is very like ^ ^ ^, getRealCoord(...addX,addY,addZ) will return same Coord as ^addX ^addY ^-addZ</pre>
     * <pre></pre>
     * <pre>zh_CN:</pre>
     * <pre>getRealCoord(...) 需要输入一个不取决于主方块朝向的坐标，如果主方块正面向-x方向，目标方块的相对坐标即是你应填入该方法的坐标</pre>
     * <pre>它将返回一个真实的世界坐标，可以直接用于checkAndSetTarget(...)等方法的那种</pre>
     * <pre>如果你熟悉原版命令，这个方法很类似于^ ^ ^坐标表示法，getRealCoord(...addX,addY,addZ) 将返回与 ^addX ^addY ^-addZ相同的坐标</pre>
     * <pre></pre>
     * <pre>You can also use Dummy Structure Map,please turn to exampleMachine class to get a look into that.</pre>
     * <pre></pre>
    **/
    private static void dontSpamMyIDE(){};

    /**
     * Detailed help can be found in utils.java
     * @param Facing controllerBlock's facing,usually this.mFacing
     * @param oX X Coord of the Start point
     * @param addX X offset in Dummy Coord
     * @return an int[] contains X,Y,Z in real world.
     */
    public static ChunkCoordinates getRealCoord(byte Facing, int oX, int oY, int oZ, int addX, int addY, int addZ) {
        int[] resultX = {0, 0, oX - addX, oX + addX, oX + addZ, oX - addZ, 0, 0};
        int[] resultZ = {0, 0, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0, 0};
        return new ChunkCoordinates(resultX[Facing],oY +addY,resultZ[Facing]);
    }
    public static int getRealX(short Facing, int oX, int addX, int addZ){
        int[] resultX = {0, 0, oX - addX, oX + addX, oX + addZ, oX - addZ, 0, 0};
        return resultX[Facing];
    }
    public static double getRealX(short Facing, double oX, double addX, double addZ){
        double[] resultX = {0, 0, oX - addX, oX + addX, oX + addZ, oX - addZ, 0, 0};
        return resultX[Facing];
    }
    public static int getRealZ(short Facing, int oZ, int addX, int addZ){
        int[] resultZ = {0, 0, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0, 0};
        return resultZ[Facing];
    }
    public static double getRealZ(short Facing, double oZ, double addX, double addZ){
        double[] resultZ = {0, 0, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0, 0};
        return resultZ[Facing];
    }


    public static Vec3 getRealCoord(byte Facing, double oX, double oY, double oZ, double addX, double addY, double addZ) {
        double[] resultX = {0, 0, oX - addX, oX + addX, oX + addZ, oX - addZ, 0, 0};
        double[] resultZ = {0, 0, oZ + addZ, oZ - addZ, oZ + addX, oZ - addX, 0, 0};
        return Vec3.createVectorHelper(resultX[Facing],oY +addY,resultZ[Facing]);
    }
    public static int getXOffset(byte Facing,int offsetX,int offsetZ){
        int[] resultX = {0, 0,  - offsetX,  + offsetX,  + offsetZ,  - offsetZ, 0, 0};
        return resultX[Facing];
    }
    public static double getXOffset(byte Facing,double offsetX,double offsetZ){
        double[] resultX = {0, 0,  - offsetX,  + offsetX,  + offsetZ,  - offsetZ, 0, 0};
        return resultX[Facing];
    }
    public static int getZOffset(byte Facing,int offsetX,int offsetZ){
        int[] resultZ = {0, 0, + offsetZ, - offsetZ, + offsetX, - offsetX, 0, 0};
        return resultZ[Facing];
    }
    public static double getZOffset(byte Facing,double offsetX,double offsetZ){
        double[] resultZ = {0, 0, + offsetZ, - offsetZ, + offsetX, - offsetX, 0, 0};
        return resultZ[Facing];
    }

}

