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

import java.io.*;
import java.util.*;

public class ComputerClusterClientData {
    /**ID: 0**/
    public static class ClientList{
        public List<ClientData> datas;
        public ClientList(List<ClientData> datas){
            if(datas==null)this.datas=new ArrayList<>();
            this.datas=datas;
        }
        public ClientList(Collection<ClientData> datas){
            this.datas=new ArrayList<>();
            this.datas.addAll(datas);
        }
        public static byte[] serialize(ClientList list) throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);

            dos.writeInt(list.datas.size());

            for (ClientData data : list.datas) {
                byte[] dataBytes = ClientData.serialize(data);
                dos.writeInt(dataBytes.length);
                dos.write(dataBytes);
            }

            dos.flush();
            return bos.toByteArray();
        }
        public static ClientList deserialize(byte[] bytes) throws IOException {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            DataInputStream dis = new DataInputStream(bis);

            int size = dis.readInt();
            List<ClientData> datas = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
                int length = dis.readInt();
                byte[] dataBytes = new byte[length];
                dis.readFully(dataBytes);
                ClientData data = ClientData.deserialize(dataBytes);
                datas.add(data);
            }

            return new ClientList(datas);
        }
    }
    /**ID: 1**/
    public static class ControllerList{
        public List<ControllerData> datas;
        public ControllerList(List<ControllerData> datas){
            this.datas=datas;
        }
        public ControllerList(Collection<ControllerData> datas){
            this.datas=new ArrayList<>();
            this.datas.addAll(datas);
        }
        public static byte[] serialize(ControllerList list) throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);

            dos.writeInt(list.datas.size());

            for (ControllerData data : list.datas) {
                byte[] dataBytes = ControllerData.serialize(data);
                dos.writeInt(dataBytes.length);
                dos.write(dataBytes);
            }

            dos.flush();
            return bos.toByteArray();
        }
        public static ControllerList deserialize(byte[] bytes) throws IOException {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            DataInputStream dis = new DataInputStream(bis);

            int size = dis.readInt();
            List<ControllerData> datas = new ArrayList<>(size);

            for (int i = 0; i < size; i++) {
                int length = dis.readInt();
                byte[] dataBytes = new byte[length];
                dis.readFully(dataBytes);
                ControllerData data = ControllerData.deserialize(dataBytes);
                datas.add(data);
            }

            return new ControllerList(datas);
        }
    }
    /**ID: 2**/
    public static class ClusterDetail {
        public byte clusterState;
        public int controllerCount;
        public int clientCount;
        public Map<ComputePower, Long> availPowers;
        public Map<ComputePower, Long> usedPowers;
        public byte[] events;

        public ClusterDetail(byte clusterState, int controllerCount, int clientCount, Map<ComputePower, Long> availPowers, Map<ComputePower, Long> usedPowers, byte[] events) {
            this.clusterState = clusterState;
            this.controllerCount = controllerCount;
            this.clientCount = clientCount;
            this.availPowers = availPowers;
            this.usedPowers = usedPowers;
            this.events = events;
        }
        public ClusterDetail(byte clusterState, int controllerCount, int clientCount, Map<ComputePower, Long> availPowers, Map<ComputePower, Long> usedPowers, Object[] events) {
            this.clusterState = clusterState;
            this.controllerCount = controllerCount;
            this.clientCount = clientCount;
            this.availPowers = availPowers;
            this.usedPowers = usedPowers;
            this.events = new byte[events.length];
            for (int i = 0; i < events.length; i++) {
                this.events[i] = (byte) events[i];
            }
        }

        public static byte[] serialize(ClusterDetail detail) throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);

            //clusterState
            dos.writeByte(detail.clusterState);
            //controllerCount
            dos.writeInt(detail.controllerCount);
            //clientCount
            dos.writeInt(detail.clientCount);

            //availPowers
            dos.writeInt(detail.availPowers.size());
            for (Map.Entry<ComputePower, Long> entry : detail.availPowers.entrySet()) {
                dos.writeByte(entry.getKey().ordinal());
                dos.writeLong(entry.getValue());
            }

            //usedPowers
            dos.writeInt(detail.usedPowers.size());
            for (Map.Entry<ComputePower, Long> entry : detail.usedPowers.entrySet()) {
                dos.writeByte(entry.getKey().ordinal());
                dos.writeLong(entry.getValue());
            }

            //events
            dos.writeInt(detail.events.length);
            dos.write(detail.events);

            dos.flush();
            return bos.toByteArray();
        }
        public static ClusterDetail deserialize(byte[] bytes) throws IOException {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            DataInputStream dis = new DataInputStream(bis);

            //clusterState
            byte clusterState = dis.readByte();
            //controllerCount
            int controllerCount = dis.readInt();
            //clientCount
            int clientCount = dis.readInt();

            //availPowers
            int availPowersSize = dis.readInt();
            Map<ComputePower, Long> availPowers = new HashMap<>();
            for (int i = 0; i < availPowersSize; i++) {
                byte key = dis.readByte();
                Long value = dis.readLong();
                availPowers.put(ComputePower.getType(key), value);
            }

            //usedPowers
            int usedPowersSize = dis.readInt();
            Map<ComputePower, Long> usedPowers = new HashMap<>();
            for (int i = 0; i < usedPowersSize; i++) {
                byte key = dis.readByte();
                Long value = dis.readLong();
                usedPowers.put(ComputePower.getType(key), value);
            }

            //events
            int eventsLength = dis.readInt();
            byte[] events = new byte[eventsLength];
            dis.readFully(events);

            return new ClusterDetail(clusterState, controllerCount, clientCount, availPowers, usedPowers, events);
        }
    }
    /**ID: 3**/
    public static class ControllerDetail {
        public byte controllerState;
        public byte computing;
        public long controllerProviding;
        public long clusterTotal;
        public byte[] events;

        public ControllerDetail(byte controllerState, byte computing, long controllerProviding, long clusterTotal, byte[] events) {
            this.controllerState = controllerState;
            this.computing = computing;
            this.controllerProviding = controllerProviding;
            this.clusterTotal = clusterTotal;
            this.events = events;
        }
        public ControllerDetail(byte controllerState, byte computing, long controllerProviding, long clusterTotal, Object[] events) {
            this.controllerState = controllerState;
            this.computing = computing;
            this.controllerProviding = controllerProviding;
            this.clusterTotal = clusterTotal;
            this.events = new byte[events.length];
            for (int i = 0; i < events.length; i++) {
                this.events[i] = (byte) events[i];
            }
        }

        public static byte[] serialize(ControllerDetail detail) throws IOException {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);

            dos.writeByte(detail.controllerState);
            dos.writeByte(detail.computing);
            dos.writeLong(detail.controllerProviding);
            dos.writeLong(detail.clusterTotal);

            //events
            dos.writeInt(detail.events.length);
            dos.write(detail.events);

            dos.flush();
            return bos.toByteArray();
        }

        public static ControllerDetail deserialize(byte[] bytes) throws IOException {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            DataInputStream dis = new DataInputStream(bis);

            byte controllerState = dis.readByte();
            byte computing = dis.readByte();
            long controllerProviding = dis.readLong();
            long clusterTotal = dis.readLong();

            //events
            int eventsLength = dis.readInt();
            byte[] events = new byte[eventsLength];
            dis.readFully(events);

            return new ControllerDetail(controllerState, computing, controllerProviding, clusterTotal, events);
        }
    }
}
