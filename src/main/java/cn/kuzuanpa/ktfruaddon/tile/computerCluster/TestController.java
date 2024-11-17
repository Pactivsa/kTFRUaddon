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
package cn.kuzuanpa.ktfruaddon.tile.computerCluster;

import cn.kuzuanpa.ktfruaddon.client.gui.computerCluster.ContainerClientClusterController;
import cn.kuzuanpa.ktfruaddon.client.gui.computerCluster.ContainerCommonClusterController;
import cn.kuzuanpa.ktfruaddon.code.SingleEntry;
import codechicken.lib.vec.BlockCoord;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase07Paintable;
import gregapi.util.UT;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

import static cn.kuzuanpa.ktfruaddon.tile.computerCluster.Constants.STATE_NORMAL;
import static cn.kuzuanpa.ktfruaddon.tile.computerCluster.Constants.STATE_OFFLINE;
import static gregapi.data.CS.*;

public class TestController extends TileEntityBase07Paintable implements IComputerClusterController {
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.testC";
    }
    UUID myUUID = null,clusterUUID = null;
    ComputerCluster cluster = null;
    List<ControllerData> clusterControllers = new ArrayList<>();
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        ComputerCluster.readClusterFromNBT(aNBT,this);

    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        ComputerCluster.writeClusterToNBT(aNBT,this);
    }

    @Override
    public boolean canDrop(int aSlot) {
        return false;
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(!isServerSide())return 0;
        if(aTool.equals(TOOL_plunger))cluster = new ComputerCluster(null);
        if(aTool.equals(TOOL_screwdriver))cluster.join(worldObj,new BlockCoord(xCoord,yCoord,zCoord),this);
        if(aTool.equals(TOOL_chisel)) cluster.join(worldObj,new BlockCoord(xCoord,yCoord+1,zCoord), (IComputerClusterController) WD.te(worldObj,xCoord,yCoord+1,zCoord,false));

        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if(!aIsServerSide)return;
        if(clusterControllers != null && aTimer%100 == 1){
            ComputerCluster.recoverOrJoin(clusterControllers, clusterUUID);
            if(cluster != null)clusterControllers=null;
        }
        if(cluster!=null && aTimer%10 == 0)cluster.update();
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        if(aTimer%10 == 0)return true;
        return false;
    }

    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(isServerSide()) {
            openGUI(aPlayer, aSide);
            return true;
        }
        return false;
    }

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return null;
    }

    @Override
    public UUID getUUID() {
        return myUUID;
    }

    @Override
    public void setUUID(UUID uuid) {
        myUUID=uuid;
    }

    @Override
    public UUID getSavedClusterUUID() {
        return clusterUUID;
    }
    @Override
    public void setSavedClusterUUID(UUID uuid) {
        clusterUUID = uuid;
    }

    @Override
    public void setSavedClusterControllers(List<ControllerData> data) {
        clusterControllers = data;
    }

    public boolean mActive = true;
    @Override
    public byte getState() {
        return mActive?STATE_NORMAL:STATE_OFFLINE;
    }

    @Override
    public Map.Entry<ComputerPower, Long> getComputePower() {
        return new SingleEntry<>(ComputerPower.Normal, 1000L);
    }

    @Override
    public void notifyControllerEvent(short event) {
        switch (event){
            case Constants.EVENT_WRONG_UUID: myUUID =UUID.randomUUID();
            case Constants.EVENT_CLUSTER_DESTROY: cluster=null;
        }
    }

    @Override
    public boolean setCluster(ComputerCluster cluster) {
        if(getState() == STATE_OFFLINE|| this.cluster!=null)return false;
        this.cluster=cluster;
        return true;
    }
    
    //inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    @Override
    public ComputerCluster getCluster() {
        return cluster;
    }

    ComputerClusterClientData.ControllerList   dataControllerList ;
    ComputerClusterClientData.ClientList       dataClientList ;
    ComputerClusterClientData.ClusterDetail    dataClusterDetail ;
    ComputerClusterClientData.ControllerDetail dataControllerDetail ;
    @Override
    public void setClientData(ComputerClusterClientData.ControllerList data) {
        dataControllerList = data;
    }

    @Override
    public void setClientData(ComputerClusterClientData.ClientList data) {
        dataClientList = data;
    }

    @Override
    public void setClientData(ComputerClusterClientData.ClusterDetail data) {
        dataClusterDetail = data;
    }

    @Override
    public void setClientData(ComputerClusterClientData.ControllerDetail data) {
        dataControllerDetail = data;
    }

    @Override
    public ComputerClusterClientData.ControllerList getClientDataControllerList() {
        return dataControllerList;
    }

    @Override
    public ComputerClusterClientData.ClientList getClientDataClientList() {
        return dataClientList;
    }

    @Override
    public ComputerClusterClientData.ClusterDetail getClientDataClusterDetail() {
        return dataClusterDetail;
    }

    @Override
    public ComputerClusterClientData.ControllerDetail getClientDataControllerDetail() {
        return dataControllerDetail;
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        super.receiveDataByteArray(Arrays.copyOf(aData,4), aNetworkHandler);
        if(aData.length<5)return true;
        try {
            switch (aData[4]) {
                case 0:
                    setClientData(ComputerClusterClientData.ClientList.deserialize(Arrays.copyOfRange(aData, 5, aData.length)));
                    return true;
                case 1:
                    setClientData(ComputerClusterClientData.ControllerList.deserialize(Arrays.copyOfRange(aData, 5, aData.length)));
                    return true;
                case 2:
                    setClientData(ComputerClusterClientData.ClusterDetail.deserialize(Arrays.copyOfRange(aData, 5, aData.length)));
                    return true;
                case 3:
                    setClientData(ComputerClusterClientData.ControllerDetail.deserialize(Arrays.copyOfRange(aData, 5, aData.length)));
                    return true;
            }
        }catch (IOException e){}
        return true;
    }

    public byte sendType = 0;
    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        byte[] bytes;
        if(cluster == null) return super.getClientDataPacket(aSendAll);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try (DataOutputStream dos = new DataOutputStream(baos)) {
                dos.writeByte((byte) UT.Code.getR(mRGBa));
                dos.writeByte((byte) UT.Code.getG(mRGBa));
                dos.writeByte((byte) UT.Code.getB(mRGBa));
                dos.writeByte(getVisualData());
                dos.writeByte(sendType);
                switch (sendType){
                    case 0: dos.write(ComputerClusterClientData.ClientList.serialize(cluster.fetchClientDataClientList())); break;
                    case 1: dos.write(ComputerClusterClientData.ControllerList.serialize(cluster.fetchClientDataControllerList())); break;
                    case 2: dos.write(ComputerClusterClientData.ClusterDetail.serialize(cluster.fetchClientDataClusterDetail())); break;
                    case 3: dos.write(ComputerClusterClientData.ControllerDetail.serialize(cluster.fetchClientDataControllerDetail(myUUID))); break;
                }
                if(sendType<4)sendType++;
                else sendType=0;
                dos.flush();
                bytes = baos.toByteArray();
            }
            baos.close();
            return getClientDataPacketByteArray(aSendAll, bytes);
        }catch (IOException e){
            return super.getClientDataPacket(aSendAll);
        }
    }

    @Override
    public Object getGUIClient2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerClientClusterController(aPlayer.inventory, this, aGUIID,"");
    }
    @Override
    public Object getGUIServer2(int aGUIID, EntityPlayer aPlayer) {
        return new ContainerCommonClusterController(aPlayer.inventory, this, aGUIID);
    }
}
