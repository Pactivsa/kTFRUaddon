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

package cn.kuzuanpa.ktfruaddon.api.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import cpw.mods.fml.common.Loader;
import gregapi.network.INetworkHandler;
import gregapi.network.packets.PacketCoordinates;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;

import static gregapi.data.CS.ERR;

public class PacketSyncDataByteArrayLong extends PacketCoordinates {
    public byte[] mData;
    public static boolean isPacketLimitExtended = Loader.isModLoaded("biggerpacketsplz");

    public PacketSyncDataByteArrayLong(int aDecoderType) {
        super(aDecoderType);
    }

    /**
     * aData is limited to length = 536870900
     */
    public PacketSyncDataByteArrayLong(int aX, int aY, int aZ, byte... aData) {
        super(aX, aY, aZ);
        mData = aData;
    }

    public PacketSyncDataByteArrayLong(ChunkCoordinates aCoords, byte... aData) {
        super(aCoords);
        mData = aData;
    }

    @Override
    public byte getPacketIDOffset() {
        return 0;
    }

    @Override
    public ByteArrayDataOutput encode2(ByteArrayDataOutput aData) {
        if(!isPacketLimitExtended && mData.length> 2096000) {
            ERR.println("Too long byte array being synced!");
            return aData;
        }
        aData.writeInt(mData.length);
        aData.write(mData);
        return aData;
    }

    @Override
    public PacketCoordinates decode2(int aX, int aY, int aZ, ByteArrayDataInput aData) {
        byte[] tData = new byte[aData.readInt()];
        aData.readFully(tData);
        return new PacketSyncDataByteArrayLong(aX, aY, aZ, tData);
    }

    @Override
    public void process(IBlockAccess aWorld, INetworkHandler aNetworkHandler) {
        if (aWorld != null) {
            TileEntity tile = aWorld.getTileEntity(mX, mY, mZ);
            if (tile instanceof ITileSyncByteArrayLong)
                ((ITileSyncByteArrayLong) tile).receiveDataByteArrayLong(aWorld, mX, mY, mZ, mData, aNetworkHandler);
        }
    }
}