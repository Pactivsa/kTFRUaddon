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

package cn.kuzuanpa.ktfruaddon.tile.computerCluster;

import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterUser;
import codechicken.lib.vec.BlockCoord;
import gregapi.code.HashSetNoNulls;
import gregapi.code.TagData;
import gregapi.data.OP;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.ITexture;
import gregapi.tileentity.connectors.TileEntityBase10ConnectorRendered;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.util.UT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.*;

import static gregapi.data.CS.*;

public class NetworkCable extends TileEntityBase10ConnectorRendered implements IWiredNetworkConnectable{
    public byte mRenderType = 0;
    public boolean checkedThisTick = false;
    public List<UUID> controllers = new ArrayList<>();

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        checkedThisTick = false;
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_PIPERENDER)) mRenderType = aNBT.getByte(NBT_PIPERENDER);
    }

    public boolean canReach(BlockCoord target, HashSetNoNulls<TileEntity> aAlreadyPassed) {
        for (byte tSide : ALL_SIDES) if (connected(tSide)) {
            TileEntity tDelegator = getTileEntityAtSideAndDistance(tSide, 1);
            if (aAlreadyPassed.add(tDelegator)) {
                if (tDelegator instanceof NetworkCable)if (((NetworkCable) tDelegator).canReach(target,aAlreadyPassed))return true;
                if ((tDelegator instanceof IComputerClusterUser || tDelegator instanceof IComputerClusterController) && target.equals(new BlockCoord(tDelegator.xCoord,tDelegator.yCoord,tDelegator.zCoord))) return true;
            }
        }
        return false;
    }

    @Override public float getBlockHardness() {
        return super.getBlockHardness();
    }


    @Override
    public boolean connect(byte aSide, boolean aNotify) {
        return super.connect(aSide, aNotify);
    }

    @Override public boolean canConnect(byte aSide, DelegatorTileEntity<TileEntity> aDelegator) {return aDelegator.mTileEntity instanceof IWiredNetworkConnectable;}

    @Override public boolean canDrop(int aInventorySlot) {return F;}

    @Override public ITexture getTextureSide                (byte aSide, byte aConnections, float aDiameter, int aRenderPass) {return mRenderType == 1 || mRenderType == 2 ? BlockTextureDefault.get(Textures.BlockIcons.INSULATION_FULL, isPainted()?mRGBa:UT.Code.getRGBInt(64, 64, 64)) : BlockTextureDefault.get(mMaterial, getIconIndexSide(aSide, aConnections, aDiameter, aRenderPass), F, mRGBa);}
    @Override public ITexture getTextureConnected           (byte aSide, byte aConnections, float aDiameter, int aRenderPass) {return mRenderType == 1 || mRenderType == 2 ? BlockTextureMulti.get(BlockTextureDefault.get(mMaterial, getIconIndexConnected(aSide, aConnections, aDiameter, aRenderPass), mIsGlowing), BlockTextureDefault.get(mRenderType==2?Textures.BlockIcons.INSULATION_BUNDLED:aDiameter<0.37F?Textures.BlockIcons.INSULATION_TINY:aDiameter<0.49F?Textures.BlockIcons.INSULATION_SMALL:aDiameter<0.74F?Textures.BlockIcons.INSULATION_MEDIUM:aDiameter<0.99F?Textures.BlockIcons.INSULATION_LARGE:Textures.BlockIcons.INSULATION_HUGE, isPainted()?mRGBa:UT.Code.getRGBInt(64, 64, 64))) : BlockTextureDefault.get(mMaterial, getIconIndexConnected(aSide, aConnections, aDiameter, aRenderPass), mIsGlowing, mRGBa);}

    @Override public int getIconIndexSide                   (byte aSide, byte aConnections, float aDiameter, int aRenderPass) {return OP.wire.mIconIndexBlock;}
    @Override public int getIconIndexConnected              (byte aSide, byte aConnections, float aDiameter, int aRenderPass) {return OP.wire.mIconIndexBlock;}

    @Override public Collection<TagData> getConnectorTypes  (byte aSide) {return IWiredNetworkConnectable.WIRE_NETWORK.AS_LIST;}

    @Override public String getFacingTool                   () {return TOOL_cutter;}

    @Override public String getTileEntityName               () {return "ktfru.multitileentity.computercluster.wire";}

    @Override
    public void onConnectionChange(byte aPreviousConnections) {
        for (byte tSide : ALL_SIDES_VALID) if (connected(tSide) || aPreviousConnections == tSide) {
            TileEntity t = getTileEntityAtSideAndDistance(tSide, 1);
            if(t instanceof NetworkCable)((NetworkCable) t).requestReachableCheck();
        }
    }

    public void requestReachableCheck(){
        if(checkedThisTick)return;
        checkedThisTick=true;
        int a= 1;
        Queue<NetworkCable> tileEntities = new ArrayDeque<>();
        List<NetworkCable> checkedCable = new ArrayList<>();
        List<IWiredNetworkConnectable> checkedConnectable = new ArrayList<>();
        for (byte tSide : ALL_SIDES_VALID) if (connected(tSide)) {
            TileEntity t = getTileEntityAtSideAndDistance(tSide, 1);
            if(t instanceof NetworkCable)tileEntities.add((NetworkCable) t);
            else if(t instanceof IWiredNetworkConnectable)checkedConnectable.add((IWiredNetworkConnectable) t);
        }
        while(!tileEntities.isEmpty()){
            NetworkCable tile = tileEntities.poll();
            checkedCable.add(tile);
            for (byte tSide : ALL_SIDES_VALID) if (tile.connected(tSide)) {
                TileEntity t = tile.getTileEntityAtSideAndDistance(tSide, 1);
                if(t instanceof NetworkCable) { if (!checkedCable.contains(t)) tileEntities.add((NetworkCable) t);}
                else if(t instanceof IWiredNetworkConnectable && !checkedConnectable.contains(t))checkedConnectable.add((IWiredNetworkConnectable) t);
            }
        }
        controllers = new ArrayList<>();
        if(checkedConnectable.isEmpty())return;
        checkedCable.forEach(c->c.controllers=this.controllers);
        checkedConnectable.forEach(IWiredNetworkConnectable::requestReachableCheck);
    }

    @Override
    public boolean breakBlock() {
        for (byte tSide : ALL_SIDES_VALID) {
            TileEntity t = getTileEntityAtSideAndDistance(tSide, 1);
            if(t instanceof IWiredNetworkConnectable)((IWiredNetworkConnectable) t).requestReachableCheck();
        }
        return super.breakBlock();
    }

    public void takeChannel(UUID user) {
        if(!controllers.contains(user))controllers.add(user);

    }

}
