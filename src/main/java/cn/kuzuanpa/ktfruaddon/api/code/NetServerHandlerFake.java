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


package cn.kuzuanpa.ktfruaddon.api.code;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IChatComponent;

import javax.crypto.SecretKey;
import java.net.InetAddress;
import java.net.SocketAddress;

public class NetServerHandlerFake extends NetHandlerPlayServer {

    public static class NetworkManagerFake extends NetworkManager {

        public NetworkManagerFake() {

            super(false);
        }

        @Override
        public void channelActive(ChannelHandlerContext p_channelActive_1_) throws Exception {

        }

        @Override
        public void setConnectionState(EnumConnectionState p_150723_1_) {

        }

        @Override
        public void channelInactive(ChannelHandlerContext p_channelInactive_1_) {

        }

        @Override
        public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) {

        }

        @Override
        public void setNetHandler(INetHandler p_150719_1_) {

        }

        @Override
        public void scheduleOutboundPacket(Packet p_150725_1_, GenericFutureListener... p_150725_2_) {

        }

        @Override
        public void processReceivedPackets() {

        }

        @Override
        public SocketAddress getSocketAddress() {

            return null;
        }

        @Override
        public void closeChannel(IChatComponent p_150718_1_) {

        }

        @Override
        public boolean isLocalChannel() {

            return false;
        }

        @SideOnly(Side.CLIENT)
        public static NetworkManager provideLanClient(InetAddress p_150726_0_, int p_150726_1_) {

            return null;
        }

        @SideOnly(Side.CLIENT)
        public static NetworkManager provideLocalClient(SocketAddress p_150722_0_) {

            return null;
        }

        @Override
        public void enableEncryption(SecretKey p_150727_1_) {

        }

        @Override
        public boolean isChannelOpen() {

            return false;
        }

        @Override
        public INetHandler getNetHandler() {

            return null;
        }

        @Override
        public IChatComponent getExitMessage() {

            return null;
        }

        @Override
        public void disableAutoRead() {

        }

        @Override
        public Channel channel() {

            return null;
        }

    }

    public NetServerHandlerFake(MinecraftServer par1MinecraftServer, EntityPlayerMP par3EntityPlayerMP) {

        super(par1MinecraftServer, new cofh.core.entity.NetServerHandlerFake.NetworkManagerFake(), par3EntityPlayerMP);
    }

    @Override
    public void onNetworkTick() {

    }

    @Override
    public void kickPlayerFromServer(String p_147360_1_) {

    }

    @Override
    public void processInput(C0CPacketInput p_147358_1_) {

    }

    @Override
    public void processPlayer(C03PacketPlayer p_147347_1_) {

    }

    @Override
    public void setPlayerLocation(double p_147364_1_, double p_147364_3_, double p_147364_5_, float p_147364_7_, float p_147364_8_) {

    }

    @Override
    public void processPlayerDigging(C07PacketPlayerDigging p_147345_1_) {

    }

    @Override
    public void processPlayerBlockPlacement(C08PacketPlayerBlockPlacement p_147346_1_) {

    }

    @Override
    public void onDisconnect(IChatComponent p_147231_1_) {

    }

    @Override
    public void sendPacket(final Packet p_147359_1_) {

    }

    @Override
    public void processHeldItemChange(C09PacketHeldItemChange p_147355_1_) {

    }

    @Override
    public void processChatMessage(C01PacketChatMessage p_147354_1_) {

    }

    @Override
    public void processAnimation(C0APacketAnimation p_147350_1_) {

    }

    @Override
    public void processEntityAction(C0BPacketEntityAction p_147357_1_) {

    }

    @Override
    public void processUseEntity(C02PacketUseEntity p_147340_1_) {

    }

    @Override
    public void processClientStatus(C16PacketClientStatus p_147342_1_) {

    }

    @Override
    public void processCloseWindow(C0DPacketCloseWindow p_147356_1_) {

    }

    @Override
    public void processClickWindow(C0EPacketClickWindow p_147351_1_) {

    }

    @Override
    public void processEnchantItem(C11PacketEnchantItem p_147338_1_) {

    }

    @Override
    public void processCreativeInventoryAction(C10PacketCreativeInventoryAction p_147344_1_) {

    }

    @Override
    public void processConfirmTransaction(C0FPacketConfirmTransaction p_147339_1_) {

    }

    @Override
    public void processUpdateSign(C12PacketUpdateSign p_147343_1_) {

    }

    @Override
    public void processKeepAlive(C00PacketKeepAlive p_147353_1_) {

    }

    @Override
    public void processPlayerAbilities(C13PacketPlayerAbilities p_147348_1_) {

    }

    @Override
    public void processTabComplete(C14PacketTabComplete p_147341_1_) {

    }

    @Override
    public void processClientSettings(C15PacketClientSettings p_147352_1_) {

    }

    @Override
    public void processVanilla250Packet(C17PacketCustomPayload p_147349_1_) {

    }

    @Override
    public void onConnectionStateTransition(EnumConnectionState p_147232_1_, EnumConnectionState p_147232_2_) {

    }
}
