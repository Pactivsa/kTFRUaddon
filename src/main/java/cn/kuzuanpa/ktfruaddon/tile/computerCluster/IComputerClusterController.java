package cn.kuzuanpa.ktfruaddon.tile.computerCluster;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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


    /**For client container render**/
    @SideOnly(Side.CLIENT)
    void setClientData(ComputerClusterClientData.ControllerList data);
    /**For client container render**/
    @SideOnly(Side.CLIENT)
    void setClientData(ComputerClusterClientData.ClientList data);
    /**For client container render**/
    @SideOnly(Side.CLIENT)
    void setClientData(ComputerClusterClientData.ClusterDetail data);
    /**For client container render**/
    @SideOnly(Side.CLIENT)
    void setClientData(ComputerClusterClientData.ControllerDetail data);

    /**For client container render**/
    @SideOnly(Side.CLIENT)
    ComputerClusterClientData.ControllerList getClientDataControllerList();
    /**For client container render**/
    @SideOnly(Side.CLIENT)
    ComputerClusterClientData.ClientList getClientDataClientList();
    /**For client container render**/
    @SideOnly(Side.CLIENT)
    ComputerClusterClientData.ClusterDetail getClientDataClusterDetail();
    /**For client container render**/
    @SideOnly(Side.CLIENT)
    ComputerClusterClientData.ControllerDetail getClientDataControllerDetail();
}
