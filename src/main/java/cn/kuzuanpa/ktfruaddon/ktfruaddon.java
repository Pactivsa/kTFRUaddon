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

package cn.kuzuanpa.ktfruaddon;

import cn.kuzuanpa.ktfruaddon.command.CommandARRecipeGen;
import cn.kuzuanpa.ktfruaddon.command.CommandTileCodeConvert;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import gregapi.api.Abstract_Mod;
import gregapi.api.Abstract_Proxy;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.ModData;
import gregapi.data.LH;
import gregapi.network.INetworkHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

@Mod(modid = cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID, name = ktfruaddon.MOD_NAME, version = ktfruaddon.VERSION,dependencies= ktfruaddon.DEPENDS , acceptedMinecraftVersions = "1.7.10")
public final class ktfruaddon extends Abstract_Mod {
    public static final String MOD_ID = "ktfruaddon";
    public static final String MOD_NAME = "kTFRUAddon";
    public static final String VERSION = "test-MC1710";
    public static final String DEPENDS = "required-after:gregtech";
    public static ModData MOD_DATA = new ModData("ktfruaddon", "kTFRUAddon");
    @SidedProxy(clientSide = "cn.kuzuanpa.ktfruaddon.clientProxy",
            serverSide = "cn.kuzuanpa.ktfruaddon.commonProxy")
    public static commonProxy PROXY;
    public static MultiTileEntityRegistry kTileRegistry0 = null;
    public static INetworkHandler kNetworkHandler;
    public static INetworkHandler kNetworkHandler2;
    public ktfruaddon() {
    }
    @Mod.EventHandler
    public void registerCommands(FMLServerStartingEvent e){
        e.registerServerCommand(new CommandTileCodeConvert());
        e.registerServerCommand(new CommandARRecipeGen());
    }
    public String getModID() {
        return "ktfruaddon";
    }

    public String getModName() {
        return "kTFRUAddon";
    }

    public String getModNameForLog() {
        return "kTFRUAddon";
    }

    public Abstract_Proxy getProxy() {
        return PROXY;
    }

    // Do not change these 7 Functions. Just keep them this way.
    @cpw.mods.fml.common.Mod.EventHandler
    public final void onPreLoad(cpw.mods.fml.common.event.FMLPreInitializationEvent aEvent) {
        onModPreInit(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onLoad(cpw.mods.fml.common.event.FMLInitializationEvent aEvent) {
        onModInit(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onPostLoad(cpw.mods.fml.common.event.FMLPostInitializationEvent aEvent) {
        onModPostInit(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onServerStarting(cpw.mods.fml.common.event.FMLServerStartingEvent aEvent) {
        onModServerStarting(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onServerStarted(cpw.mods.fml.common.event.FMLServerStartedEvent aEvent) {
        onModServerStarted(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onServerStopping(cpw.mods.fml.common.event.FMLServerStoppingEvent aEvent) {
        onModServerStopping(aEvent);
    }

    @cpw.mods.fml.common.Mod.EventHandler
    public final void onServerStopped(cpw.mods.fml.common.event.FMLServerStoppedEvent aEvent) {
        onModServerStopped(aEvent);
    }

    @Override
    public void onModPreInit2(FMLPreInitializationEvent aEvent) {
        PROXY.preInit(aEvent);
    }

    @Override
    public void onModInit2(cpw.mods.fml.common.event.FMLInitializationEvent aEvent) {
        PROXY.init(aEvent);
    }

    @Override
    public void onModPostInit2(cpw.mods.fml.common.event.FMLPostInitializationEvent aEvent) {
PROXY.postInit(aEvent);
    }

    @Override
    public void onModServerStarting2(cpw.mods.fml.common.event.FMLServerStartingEvent aEvent) {
        // Insert your ServerStarting Code here and not above
    }

    @Override
    public void onModServerStarted2(cpw.mods.fml.common.event.FMLServerStartedEvent aEvent) {
        // Insert your ServerStarted Code here and not above
        if(!EnvironmentHelper.TFRUVer.equalsIgnoreCase(EnvironmentHelper.checkedTFRUVer) && MinecraftServer.getServer() != null){
            MinecraftServer.getServer().addChatMessage(new ChatComponentText(LH.get("ktfru.msg.outdated")));
        }
    }

    @Override
    public void onModServerStopping2(cpw.mods.fml.common.event.FMLServerStoppingEvent aEvent) {
        // Insert your ServerStopping Code here and not above
    }

    @Override
    public void onModServerStopped2(cpw.mods.fml.common.event.FMLServerStoppedEvent aEvent) {
        // Insert your ServerStopped Code here and not above
    }

}

