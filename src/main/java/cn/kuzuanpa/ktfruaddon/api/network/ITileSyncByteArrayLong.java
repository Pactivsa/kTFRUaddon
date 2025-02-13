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

import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.IBlockAccess;

public interface ITileSyncByteArrayLong {
    void receiveDataByteArrayLong(IBlockAccess aWorld, int aX, int aY, int aZ, byte[] aData, INetworkHandler aNetworkHandler);
    default IPacket getClientDataPacketByteArrayLong(boolean aSendAll, byte... aByteArray)   {return aSendAll ? new PacketSyncDataByteArrayLongAndIDs(getCoords(), getMultiTileEntityRegistryID(), getMultiTileEntityID(), aByteArray    ) : new PacketSyncDataByteArrayLong(getCoords(), aByteArray);}
    public ChunkCoordinates getCoords();
    public short getMultiTileEntityID();
    public short getMultiTileEntityRegistryID();
}
