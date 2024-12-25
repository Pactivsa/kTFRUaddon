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

import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterController;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.IComputerClusterUser;
import gregapi.data.LH;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase07Paintable;
import gregapi.util.OM;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_NORMAL;
import static cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.Constants.STATE_OFFLINE;
import static gregapi.data.CS.*;

public class TestUser extends TileEntityBase07Paintable implements IComputerClusterUser {
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.testU";
    }
    UUID myUUID = null;
    IComputerClusterController controller = null;
    List<IComputerClusterController> backupController = new ArrayList<>();
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        IComputerClusterUser.readFromNBT(aNBT,this);

    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        IComputerClusterUser.writeToNBT(aNBT,this);
    }

    @Override
    public boolean canDrop(int aSlot) {
        return false;
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        if(aTimer%10 == 0)return true;
        return false;
    }
    public boolean clickDoubleCheck=false;
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(isServerSide()) {
            addControllerFromUSB(aPlayer);
            mActive = true;
            if(aPlayer.isSneaking())tryStop();
            else tryStart();
            return true;
        }
        return false;
    }
    public boolean addControllerFromUSB(EntityPlayer aPlayer){
        ItemStack equippedItem=aPlayer.getCurrentEquippedItem();
        if (!(OM.is(OD_USB_STICKS[0],equippedItem))) return false;

        if (equippedItem.hasTagCompound() && equippedItem.getTagCompound().hasKey(NBT_USB_DATA)) {
            NBTTagCompound aNBT = equippedItem.getTagCompound().getCompoundTag(NBT_USB_DATA);

            if(!aNBT.hasKey("worldID") || !aNBT.hasKey(NBT_TARGET_X) || !aNBT.hasKey(NBT_TARGET_Y) || !aNBT.hasKey(NBT_TARGET_Z)){
                aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get("Join Failed: USB data not valid")));
                return false;
            }

            ChunkCoordinates coord = new ChunkCoordinates();
            World world = DimensionManager.getWorld(aNBT.getInteger("worldID"));

            if(world == null){
                aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get("Join Failed: World ID"+aNBT.getInteger("worldID")+" not exists")));
                return false;
            }

            coord.posX = aNBT.getInteger(NBT_TARGET_X);
            coord.posY = aNBT.getInteger(NBT_TARGET_Y);
            coord.posZ = aNBT.getInteger(NBT_TARGET_Z);
            TileEntity tile = WD.te(world,coord,false);

            if(!(tile instanceof IComputerClusterController) || ((IComputerClusterController) tile).getCluster() == null){
                aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get("Join Failed: target not exist or not loaded")));
                return false;
            }
            setController((IComputerClusterController) tile);
            aPlayer.addChatMessage(new ChatComponentText(LH.Chat.CYAN+LH.get("Join Success")));
        }

        return true;
    }
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return null;
    }

    public boolean mActive = true;

    @Override
    public IComputerClusterController getController() {
        return controller;
    }

    @Override
    public void setController(IComputerClusterController controller) {
        this.controller=controller;
    }

    @Override
    public List<IComputerClusterController> getBackupControllers() {
        return backupController;
    }

    @Override
    public void setBackupControllers(List<IComputerClusterController> list) {
        backupController=list;
    }

    @Override
    public byte getState() {
        return mActive?STATE_NORMAL:STATE_OFFLINE;
    }

    @Override
    public void onComputerPowerReleased() {
        mActive=false;
    }

    @Override
    public Map<ComputePower, Long> getComputePowerNeeded() {
        return ComputePower.Normal.asMap(133);
    }

    @Override
    public UUID getUUID() {
        return myUUID;
    }

    @Override
    public void setUUID(UUID uuid) {
        myUUID=uuid;
    }

    //inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[1];}

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

}
