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
import gregapi.block.IBlockSyncData;
import gregapi.network.INetworkHandler;
import gregapi.network.packets.PacketCoordinates;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;

import static gregapi.data.CS.ERR;

public class PacketSyncDataByteArrayLongAndIDs extends PacketCoordinates {
    public byte[] mData;
    public short mID1 = 0, mID2 = 0;
    public static boolean isPacketLimitExtended = Loader.isModLoaded("biggerpacketsplz");

    public PacketSyncDataByteArrayLongAndIDs(int aDecoderType) {
        super(aDecoderType);
    }

    /**
     * aData is limited to length = 536870900
     */
    public PacketSyncDataByteArrayLongAndIDs(int aX, int aY, int aZ, short aID1, short aID2, byte... aData) {
        super(aX, aY, aZ);
        mData = aData;
        mID1 = aID1;
        mID2 = aID2;
    }

    public PacketSyncDataByteArrayLongAndIDs(ChunkCoordinates aCoords, short aID1, short aID2, byte... aData) {
        super(aCoords);
        mData = aData;
        mID1 = aID1;
        mID2 = aID2;
    }

    @Override
    public byte getPacketIDOffset() {
        return 8;
    }

    @Override
    public ByteArrayDataOutput encode2(ByteArrayDataOutput aData) {
        aData.writeShort(mID1);
        aData.writeShort(mID2);
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
        short tID1 = aData.readShort(), tID2 = aData.readShort();
        byte[] tData = new byte[aData.readInt()];
        aData.readFully(tData);
        return new PacketSyncDataByteArrayLongAndIDs(aX, aY, aZ,tID1, tID2, tData);
    }

    @Override
    public void process(IBlockAccess aWorld, INetworkHandler aNetworkHandler) {
        if (aWorld != null) {
            Block tBlock = aWorld.getBlock(mX, mY, mZ);
            if (tBlock instanceof IBlockSyncData.IBlockSyncDataAndIDs) ((IBlockSyncData.IBlockSyncDataAndIDs)tBlock).receiveData(aWorld, mX, mY, mZ, aNetworkHandler, mID1, mID2);

            TileEntity tile = aWorld.getTileEntity(mX, mY, mZ);
            if (tile instanceof ITileSyncByteArrayLong) ((ITileSyncByteArrayLong) tile).receiveDataByteArrayLong(aWorld, mX, mY, mZ, mData, aNetworkHandler);
        }
    }
}