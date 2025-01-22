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
package cn.kuzuanpa.ktfruaddon.tile.energy.generator;


import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.MT;
import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.util.ST;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

import static gregapi.data.CS.*;
import static net.minecraftforge.common.util.ForgeDirection.VALID_DIRECTIONS;

public class WaterMill extends TileEntityBase09FacingSingle implements ITileEntityEnergy{
    public String getTileEntityName() {
        return "ktfru.multitileentity.energy.generator.watermill";
    }


    public float rotateTorque = 0;

    public short rottenTimer = 0;
    public boolean isRotten = false;
    public long transferredAmpere = 0, transferrAmpereMax = 8, transferredAmpereLast = 0;
    @Override
    public void onFacingChange(byte aPreviousFacing) {
        if(mFacing == SIDE_WEST){ mFacing = SIDE_EAST ;}
        if(mFacing == SIDE_NORTH){ mFacing = SIDE_SOUTH ;}
        if(mFacing == SIDE_DOWN){ mFacing = SIDE_UP ;}
    }

    public boolean[] getValidSides() {
        return new boolean[] {T ,T ,T ,T ,T ,T, F};
    }

    @Override
    public void onTick2(long aTimer, boolean isServerside) {
        if(isServerside && aTimer % 20 == 0) rottenTimer += (short) rng(2);
        if((isServerside && rottenTimer > 4800)||isRotten) {
            rotateTorque = 0;
            return;
        }
        if(aTimer % 10 == 0) rotateTorque = getRotateTorque();

        if ( transferredAmpere > transferrAmpereMax - (Math.abs(rotateTorque) < 0.1f? 0: 1)/*Self 1A*/ )overcharge(8, TD.Energy.RU);
        transferredAmpereLast=transferredAmpere;
        transferredAmpere = 0;

        if(Math.abs(rotateTorque) < 0.1f || !isServerside)return;

        int emittingAmpere = rotateTorque>2?2:1;
        DelegatorTileEntity<TileEntity> tile = getAdjacentTileEntity(mFacing);
        if (ITileEntityEnergy.Util.insertEnergyInto(TD.Energy.RU, OPOS[mFacing], rotateTorque>0?-2:2, emittingAmpere, this, tile.mTileEntity) != 0) return;
        tile = getAdjacentTileEntity(OPOS[mFacing]);
        ITileEntityEnergy.Util.insertEnergyInto(TD.Energy.RU, mFacing, rotateTorque>0?2:-2, emittingAmpere, this, tile.mTileEntity);
    }

    @Override
    public boolean isEnergyAcceptingFrom(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return aEnergyType.equals(TD.Energy.RU) && (aSide == mFacing || aSide == OPOS[mFacing]);
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if(!aEnergyType.equals(TD.Energy.RU))return 0;
        if(Math.abs(aSize) > 16)overcharge(8, aEnergyType);
        if(aSide == mFacing && (aSize > 0 && rotateTorque < 0 || aSize < 0 && rotateTorque > 0))overcharge(2, aEnergyType);
        if(aSide != mFacing && (aSize > 0 && rotateTorque > 0 || aSize < 0 && rotateTorque < 0))overcharge(2, aEnergyType);

        DelegatorTileEntity<TileEntity> tile = getAdjacentTileEntity(OPOS[aSide]);
        long amount = ITileEntityEnergy.Util.insertEnergyInto(TD.Energy.RU, aSide, aSize, aAmount, this, tile.mTileEntity);
        transferredAmpere += amount;
        return amount;
    }

    public float getRotateTorque(){
        ForgeDirection front = VALID_DIRECTIONS[mFacing];

        Vec3 frontVec = Vec3.createVectorHelper(front.offsetX, front.offsetY, front.offsetZ);

        final byte[] checkBlockX = {1 ,0 ,0 ,-1,0 ,0 };
        final byte[] checkBlockY = {0 ,1 ,0 ,0 ,-1,0 };
        final byte[] checkBlockZ = {0 ,0 ,1 ,0 ,0 ,-1};
        float torque = 0;
        for (int i = 0; i < 6; i++) if(!(Math.abs(checkBlockX[i]) == Math.abs(front.offsetX) && Math.abs(checkBlockY[i]) == Math.abs(front.offsetY) && Math.abs(checkBlockZ[i]) == Math.abs(front.offsetZ))) {
            Block b = getWorld().getBlock(xCoord + checkBlockX[i], yCoord + checkBlockY[i], zCoord + checkBlockZ[i]);
            if(!(b instanceof BlockLiquid)) {
                if(b.isOpaqueCube()) return 0;
                continue;
            }

            BlockLiquid block = (BlockLiquid)b;

            Vec3 origin = Vec3.createVectorHelper(checkBlockX[i], checkBlockY[i], checkBlockZ[i]);
            Vec3 flowVector = block.getFlowVector(getWorld(), xCoord + checkBlockX[i], yCoord + checkBlockY[i], zCoord + checkBlockZ[i]);
            Vec3 flowVector2D = Vec3.createVectorHelper(flowVector.xCoord * (frontVec.xCoord == 0? 1: 0), flowVector.yCoord * (frontVec.yCoord==0? 1: 0), flowVector.zCoord * (frontVec.zCoord==0? 1: 0));

            if(!flowVector2D.crossProduct(frontVec).equals(Vec3.createVectorHelper(0,0,0))){
                torque += (float) origin.crossProduct(flowVector2D).dotProduct(Vec3.createVectorHelper(1,1,1));
            }
        }
        return torque;
    }


    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(isServerSide() && aPlayer.getCurrentEquippedItem() == null){
            if(rottenTimer < 4800)aPlayer.addChatComponentMessage(new ChatComponentText(LH.get(I18nHandler.TFC_WATERMILL_0) + (int)(rotateTorque*10)));
            else aPlayer.addChatComponentMessage(new ChatComponentText(LH.get(I18nHandler.TFC_WATERMILL_1)));
            return true;
        }else if(isServerSide() && ST.equal(aPlayer.getCurrentEquippedItem(), OP.ring.mat(MT.Bronze,1))){
            if(rottenTimer < 3000){
                aPlayer.addChatComponentMessage(new ChatComponentText(LH.get(I18nHandler.TFC_WATERMILL_2)));
                return true;
            }
            aPlayer.addChatComponentMessage(new ChatComponentText(LH.get(I18nHandler.TFC_WATERMILL_3)));
            rottenTimer = 0;
            updateClientData();
            aPlayer.getCurrentEquippedItem().stackSize --;
        }
        return super.onBlockActivated3(aPlayer, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        return rottenTimer >= 4800 && rottenTimer < 4810;
    }

    @Override
    public byte getVisualData() {
        return (byte) (rottenTimer>4800?1:0);
    }

    @Override
    public void setVisualData(byte aData) {
        isRotten = aData==1;
    }

    @Override
    public boolean isSurfaceOpaque2(byte aSide) {
        return false;
    }

    @Override
    public boolean canDrop(int aSlot) {
        return true;
    }

    @Override
    public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {
        return 2;
    }

    @Override
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        return box(aBlock, PX_P[2]-0.01, PX_P[2]-0.01, PX_P[2]-0.01, PX_P[14]+0.01, PX_P[14]+0.01, PX_P[14]+0.01);
    }

    @Override
    public boolean addDefaultCollisionBoxToList() {
        return true;
    }

    @Override
    public int getLightOpacity(){
        return 1;
    }


    @Override
    public boolean renderItem(Block aBlock, RenderBlocks aRenderer) {
        TileEntityRendererDispatcher.instance.renderTileEntityAt(this, 0, 0, 0, 0);
        return F;
    }
    public static IIconContainer
            sTextureFace      = new Textures.BlockIcons.CustomIcon("machines/generators/watermill/face"),
            sTextureSide      = new Textures.BlockIcons.CustomIcon("machines/generators/watermill/side");
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return aSide==mFacing || aSide == OPOS[mFacing] ? BlockTextureDefault.get(sTextureFace) : BlockTextureDefault.get(sTextureSide);
    }
}
