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

import codechicken.lib.vec.BlockCoord;
import gregapi.util.WD;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.*;
import net.minecraftforge.common.DimensionManager;

import java.util.*;
import java.util.stream.Collectors;


public class ComputerCluster {
    public Map<UUID, ControllerData> controllerList = new HashMap<>();
    public Map<UUID, ClientData> clientList = new HashMap<>();
    public Map<ComputerPower, Long> totalComputePower = new HashMap<>();
    public Map<ComputerPower, Long> usedComputePower = new HashMap<>();
    public Queue<Byte> events = new ArrayDeque<>();

    public long lastUpdateTime = -1;
    public UUID clusterUUID;
    public byte state = Constants.STATE_OFFLINE;

    public ComputerCluster(UUID uuid) {
        if(uuid != null)this.clusterUUID = uuid;
        else this.clusterUUID = UUID.randomUUID();
    }

    public static ComputerCluster create(World initialControllerWorld, BlockCoord initialControllerPos) {
        ComputerCluster cluster = new ComputerCluster(null);
        if (cluster.join(initialControllerWorld,initialControllerPos) != null)return null;
        cluster.update();
        return cluster;
    }

    public void update(){
        if(MinecraftServer.getServer().getTickCounter() <= lastUpdateTime)return;
        lastUpdateTime= MinecraftServer.getServer().getTickCounter();
        Map<ComputerPower, Long> totalComputePowerMap = new HashMap<>();
        controllerList.forEach((uuid,data) -> {
            ControllerData oldData = data.copy();
            updateControllerData(uuid,data,totalComputePowerMap);
            if(!oldData.equals(data)) data.needToSendToClient=true;
        });
        totalComputePower = totalComputePowerMap;
    }

    public void updateControllerData(UUID uuid, ControllerData data, Map<ComputerPower, Long> totalComputePowerMap){
        IComputerClusterController controller = getControllerFromData(data);
        if(controller == null){
            data.state = Constants.STATE_OFFLINE;
            return;
        }

        if(controller.getCluster() == null){
            if(Objects.equals(uuid, controller.getUUID()))controller.setCluster(this);
        }
        else if(controller.getCluster() != this) {
            data.state = Constants.STATE_BELONG_ERR;
            return;
        }

        if(!Objects.equals(controller.getUUID(), uuid)){
            data.state = Constants.STATE_ERROR;
            controller.notifyControllerEvent(Constants.EVENT_WRONG_UUID);
            return;
        }
        data.state = controller.getState();
        if(data.state == Constants.STATE_NORMAL) {
            data.power = controller.getComputePower();
            totalComputePowerMap.merge(data.power.getKey(), data.power.getValue(), Long::sum);
        }
    }

    public List<ControllerData> getOnlineControllers(){
        return controllerList.values().stream().filter(data->data.state== Constants.STATE_NORMAL).collect(Collectors.toList());
    }

    public static void recoverOrJoin(List<ControllerData> controllerList, UUID clusterUUID){
        ComputerCluster cluster = null;
        for (ControllerData data : controllerList) {
            IComputerClusterController controller = getControllerFromData(data);
            if(controller==null)continue;

            if(cluster != null && cluster.join(data.world,data.pos,controller) == null)continue;//Try join existing Cluster

            cluster = controller.getCluster();
            if(cluster == null && Objects.equals(controller.getSavedClusterUUID(), clusterUUID)) {//Create new Cluster
                cluster = new ComputerCluster(clusterUUID);
                cluster.join(data.world,data.pos,controller);
            }
        }
    }
    public void mergeTo(ComputerCluster to){

    }

    public String join(World world, BlockCoord pos){
        TileEntity tile = world.getTileEntity(pos.x,pos.y,pos.z);
        if(!(tile instanceof IComputerClusterController))return "ktfru.compute_cluster.msg.join.not_controller";
        return join(world,pos, (IComputerClusterController) tile);
    }

    /**@return ERROR message, null if successful**/
    public String join(World world, BlockCoord pos, IComputerClusterController controller){
        if(controller.getUUID()!=null && controllerList.containsKey(controller.getUUID())){
            UUID duplicatedUUID = controller.getUUID();
            if(world.equals(controllerList.get(duplicatedUUID).world) && pos.equals(controllerList.get(duplicatedUUID).pos)) return "ktfru.compute_cluster.msg.join.already_exist";
            controller.notifyControllerEvent(Constants.EVENT_WRONG_UUID);
            return "ktfru.compute_cluster.msg.join.duplicate_uuid";
        }
        if((controller.getCluster() != null && controller.getCluster() != this)|| (controller.getSavedClusterUUID() != null && !Objects.equals(controller.getSavedClusterUUID(), this.clusterUUID)))return "ktfru.compute_cluster.msg.join.belong_other";
        else if(controller.getCluster() == null)controller.setCluster(this);
        controllerList.put(controller.getUUID(),new ControllerData(world,pos));
        return null;
    }

    public String kick(World world, BlockCoord pos){
        UUID uuid = null;
        for (Map.Entry<UUID, ControllerData> entry : controllerList.entrySet()) {
            UUID id = entry.getKey();
            ControllerData data = entry.getValue();
            if (data.world.equals(world) && data.pos.equals(pos)) {
                uuid = id;
                break;
            }
        }
        return kick(uuid);
    }

    public String kick(UUID uuid) {
        if (uuid == null || controllerList.get(uuid) == null) return "ktfru.compute_cluster.msg.kick.not_found";
        ControllerData data = controllerList.get(uuid);
        IComputerClusterController controller = getControllerFromData(data);
        if(controller == null) return "ktfru.compute_cluster.msg.kick.not_loaded";
        if(controller.getCluster() != this)return "ktfru.compute_cluster.msg.kick.not_belong_me";
        controller.notifyControllerEvent(Constants.EVENT_KICKING_FROM_CLUSTER);
        remove0(uuid);
        return null;
    }

    public String quit(UUID uuid, IComputerClusterController controller){
        remove0(uuid);
        return null;
    }

    protected void remove0(UUID uuid){
        controllerList.remove(uuid);
        postEventToAllControllers(Constants.EVENT_A_CONTROLLER_LEFT);
    }

    public void postEventToAllControllers(short event){
        controllerList.forEach(((uuid, data) ->  {
            IComputerClusterController controller = getControllerFromData(data);
            if(controller!=null)controller.notifyControllerEvent(event);
        }));
    }
    public static IComputerClusterController getControllerFromData(ControllerData data){
        if (data.world == null||data.pos == null)return null;
        TileEntity te = WD.te(data.world,data.pos.x, data.pos.y, data.pos.z,false);
        if(te instanceof IComputerClusterController)return (IComputerClusterController) te;
        return null;
    }

    public void destroy(){
        postEventToAllControllers(Constants.EVENT_CLUSTER_DESTROY);
    }

    public static void writeClusterToNBT(NBTTagCompound nbt, IComputerClusterController controller){
        if(controller.getUUID() !=null){
            nbt.setLong("myUUIDHigh", controller.getUUID().getMostSignificantBits());
            nbt.setLong("myUUIDLow", controller.getUUID().getLeastSignificantBits());
        }
        if(controller.getCluster() !=null){
            nbt.setLong("clusterUUIDHigh", controller.getCluster().clusterUUID.getMostSignificantBits());
            nbt.setLong("clusterUUIDLow", controller.getCluster().clusterUUID.getLeastSignificantBits());
            NBTTagList controllers = new NBTTagList();
            for (Map.Entry<UUID, ControllerData> entry : controller.getCluster().controllerList.entrySet()) {
                ControllerData data = entry.getValue();
                controllers.appendTag(new NBTTagIntArray(new int[] {data.world.provider.dimensionId, data.pos.x, data.pos.y, data.pos.z}));
            }
            nbt.setTag("clusterControllers", controllers);
        }
    }
    public static void readClusterFromNBT(NBTTagCompound nbt, IComputerClusterController controller){
        if(nbt.hasKey("myUUIDHigh") && nbt.hasKey("myUUIDLow")) controller.setUUID(new UUID(nbt.getLong("myUUIDHigh"), nbt.getLong("myUUIDLow")));
        else controller.setUUID(UUID.randomUUID());
        if(nbt.hasKey("clusterUUIDHigh")&& nbt.hasKey("clusterUUIDLow")){
            controller.setSavedClusterUUID(new UUID(nbt.getLong("clusterUUIDHigh"), nbt.getLong("clusterUUIDHigh")));
            List<ControllerData> datas = new ArrayList<>();
            NBTTagList list = nbt.getTagList("clusterControllers", 11);
            for (int i = 0; i < list.tagCount(); i++) {
                int[] data = list.func_150306_c(i);
                datas.add(new ControllerData(DimensionManager.getWorld(data[0]), new BlockCoord(data[1],data[2],data[3])));
            }
            controller.setSavedClusterControllers(datas);
        }
    }
    public ComputerClusterClientData.ControllerList fetchClientDataControllerList() {
        return new ComputerClusterClientData.ControllerList(controllerList.values());
    }

    public ComputerClusterClientData.ClientList fetchClientDataClientList() {
        return new ComputerClusterClientData.ClientList(clientList.values());
    }

    public ComputerClusterClientData.ClusterDetail fetchClientDataClusterDetail() {
        return new ComputerClusterClientData.ClusterDetail(state,controllerList.size(),clientList.size(),totalComputePower,usedComputePower, events.toArray());
    }

    public ComputerClusterClientData.ControllerDetail fetchClientDataControllerDetail(UUID controllerID) {
        ControllerData controllerData = controllerList.get(controllerID);
        if(controllerData == null) return null;
        return new ComputerClusterClientData.ControllerDetail(controllerData.state,(byte)controllerData.power.getKey().ordinal(),controllerData.power.getValue(),totalComputePower.get(controllerData.power.getKey()),controllerData.events.toArray());
    }
}
