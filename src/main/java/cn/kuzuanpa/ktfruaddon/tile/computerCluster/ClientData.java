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

import codechicken.lib.vec.BlockCoord;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClientData {
    public World world;
    public BlockCoord pos;
    public byte state;
    public Map<ComputerPower, Long> consumingPower = new HashMap<>();
    public boolean needToSendToClient = false;
    public ClientData(World world, BlockCoord pos){this.world=world;this.pos=pos;}

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientData)) return false;

        ClientData that = (ClientData) o;
        return state == that.state && world.equals(that.world) && pos.equals(that.pos) && Objects.equals(consumingPower, that.consumingPower);
    }

    @Override
    public int hashCode() {
        int result = world.hashCode();
        result = 31 * result + pos.hashCode();
        result = 31 * result + state;
        result = 31 * result + Objects.hashCode(consumingPower);
        return result;
    }

    public ClientData copy() {
        ClientData data = new ClientData(this.world,this.pos);
        data.state=this.state;
        data.consumingPower =this.consumingPower;
        return data;
    }

    public static byte[] serialize(ClientData data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes;
        try (DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeInt(data.world.provider.dimensionId);
            dos.writeInt(data.pos.x);
            dos.writeShort((short) data.pos.y);
            dos.writeInt(data.pos.z);
            dos.writeByte(data.state);
            dos.writeByte(data.consumingPower.size());
            for (Map.Entry<ComputerPower, Long> entry : data.consumingPower.entrySet()) {
                ComputerPower key = entry.getKey();
                Long amount = entry.getValue();
                dos.writeByte(key.ordinal());
                dos.writeLong(amount);
            }
            dos.flush();
            bytes = baos.toByteArray();
        }
        baos.close();

        return bytes;
    }

    public static ClientData deserialize(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);

        int worldID = dis.readInt();
        int posX = dis.readInt();
        short posY = dis.readShort();
        int posZ = dis.readInt();
        byte state = dis.readByte();
        Map<ComputerPower, Long> consumingPower = new HashMap<>();
        for (int i = 0; i < dis.readByte(); i++) {
            consumingPower.put(ComputerPower.getType(dis.readByte()), dis.readLong());
        }

        dis.close();
        bais.close();
        ClientData data = new ClientData(DimensionManager.getWorld(worldID),new BlockCoord(posX,posY,posZ));
        data.state=state;
        data.consumingPower = consumingPower;
        return data;
    }
}
