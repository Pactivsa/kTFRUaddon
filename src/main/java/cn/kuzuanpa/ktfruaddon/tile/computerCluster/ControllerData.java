package cn.kuzuanpa.ktfruaddon.tile.computerCluster;

import cn.kuzuanpa.ktfruaddon.code.SingleEntry;
import codechicken.lib.vec.BlockCoord;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;

public class ControllerData{
    public World world;
    public BlockCoord pos;
    public byte state;
    public Map.Entry<ComputerPower, Long> power = new SingleEntry<>(ComputerPower.Normal, 0L);
    public boolean needToSendToClient = false;
    public Queue<Byte> events = new ArrayDeque<>();
    public ControllerData(World world, BlockCoord pos){this.world=world;this.pos=pos;}

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ControllerData)) return false;

        ControllerData that = (ControllerData) o;
        return state == that.state && world.equals(that.world) && pos.equals(that.pos) && Objects.equals(power, that.power);
    }

    @Override
    public int hashCode() {
        int result = world.hashCode();
        result = 31 * result + pos.hashCode();
        result = 31 * result + state;
        result = 31 * result + Objects.hashCode(power);
        return result;
    }

    public ControllerData copy() {
        ControllerData data = new ControllerData(this.world,this.pos);
        data.state=this.state;
        data.power=this.power;
        return data;
    }


    public static byte[] serialize(ControllerData data) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes;
        try (DataOutputStream dos = new DataOutputStream(baos)) {
            dos.writeInt(data.world.provider.dimensionId);
            dos.writeInt(data.pos.x);
            dos.writeShort((short) data.pos.y);
            dos.writeInt(data.pos.z);
            dos.writeByte(data.state);
            dos.writeByte(data.power.getKey().ordinal());
            dos.writeLong(data.power.getValue());
            dos.flush();
            bytes = baos.toByteArray();
        }
        baos.close();

        return bytes;
    }

    public static ControllerData deserialize(byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);

        int worldID = dis.readInt();
        int posX = dis.readInt();
        short posY = dis.readShort();
        int posZ = dis.readInt();
        byte state = dis.readByte();
        byte powerType = dis.readByte();
        long powerAmount = dis.readLong();

        dis.close();
        bais.close();
        ControllerData data = new ControllerData(DimensionManager.getWorld(worldID),new BlockCoord(posX,posY,posZ));
        data.state=state;
        data.power = new SingleEntry<>(ComputerPower.getType(powerType),powerAmount);
        return data;
    }
}
