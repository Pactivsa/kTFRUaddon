/*
 * This class was created by <kuzuanpa>. It is distributed as
 * part of the kTFRUAddon Mod. Get the Source Code in github:
 * https://github.com/kuzuanpa/kTFRUAddon
 *
 * kTFRUAddon is Open Source and distributed under the
 * LGPLv3 License: https://www.gnu.org/licenses/lgpl-3.0.txt
 *
 */


package cn.kuzuanpa.ktfruaddon.tile.parts;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import cn.kuzuanpa.ktfruaddon.i18n.texts.kMessages;
import cn.kuzuanpa.ktfruaddon.tile.multiblock.SunBoiler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.data.LH;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;

import static gregapi.data.CS.*;

public class SunBoilerMirror extends TileEntityBase09FacingSingle implements IMultiTileEntity.IMTE_SyncDataByteArray {
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.sunboiler.mirror";
    }
    public ChunkCoordinates targetSunBoilerPos=null;
    public float rotateHorizontal,rotateVertical,rotateHorizontalToMove,rotateVerticalToMove;
    public boolean isValid=true;

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_TARGET)) {targetSunBoilerPos = new ChunkCoordinates(UT.Code.bindInt(aNBT.getLong(NBT_TARGET_X)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Y)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Z)));}
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        if (targetSunBoilerPos != null) {
            UT.NBT.setBoolean(aNBT, NBT_TARGET, T);
            UT.NBT.setNumber(aNBT, NBT_TARGET_X, targetSunBoilerPos.posX);
            UT.NBT.setNumber(aNBT, NBT_TARGET_Y, targetSunBoilerPos.posY);
            UT.NBT.setNumber(aNBT, NBT_TARGET_Z, targetSunBoilerPos.posZ);
        }
    }
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (isServerSide()) {
            ItemStack equippedItem = aPlayer.getCurrentEquippedItem();
            if (equippedItem.hasTagCompound() && equippedItem.getTagCompound().hasKey(NBT_USB_DATA)) {
                NBTTagCompound aNBT = equippedItem.getTagCompound().getCompoundTag(NBT_USB_DATA);
                targetSunBoilerPos = new ChunkCoordinates(UT.Code.bindInt(aNBT.getLong(NBT_TARGET_X)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Y)), UT.Code.bindInt(aNBT.getLong(NBT_TARGET_Z)));
                if (worldObj.getTileEntity(targetSunBoilerPos.posX, targetSunBoilerPos.posY, targetSunBoilerPos.posZ) instanceof SunBoiler) {
                    updateClientData();
                    aPlayer.addChatMessage(new ChatComponentText(LH.get(kMessages.SUN_BOILER_MIRROR) + targetSunBoilerPos.posX + "," + targetSunBoilerPos.posY + "," + targetSunBoilerPos.posZ));
                }else targetSunBoilerPos=null;
                return true;
            }
            return false;
        }else return true;
    }
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {return null;}
    public boolean[] getValidSides() {return SIDES_BACK;}
    @Override
    public void onTick2(long aTimer, boolean isServerside){
        if (!isServerside&&getTimer()%10==0) {
            if (this.getWorld().getWorldTime() % 24000 < 13800&&targetSunBoilerPos!=null) updateRotates();
            else rotateVerticalToMove=0;
        }
        if(!isServerside) {
            float f1 =rotateVertical - rotateVerticalToMove;
            float f2 =rotateHorizontal - rotateHorizontalToMove;

            if(f1>0.01)rotateVertical-=f1>10?1:(f1/10);
            if(f1<0.01)rotateVertical-=f1<-10?-1:(f1/10);
            if(f2>0.01)rotateHorizontal-=f2>10?1:(f2/10);
            if(f2<0.01)rotateHorizontal-=f2<-10?-1:(f2/10);
        }
    }
    @SideOnly(Side.CLIENT)
public void updateRotates(){
        long Ti=getWorldObj().getWorldTime()+1800;
        double Xn=0,Yn=0,Zn=0,T=24000;
        Ti = Ti % 24000;

        Ti = Ti>15600?7800:Ti;

        int X2A = targetSunBoilerPos.posX - xCoord, Y2A = targetSunBoilerPos.posY - yCoord, Z2A = targetSunBoilerPos.posZ - zCoord;

        double L = Math.sqrt(X2A * X2A + Y2A * Y2A + Z2A * Z2A);
        double X2 = X2A / L, Y2 = Y2A / L, Z2 = Z2A / L;
        double alpha = Ti / 15600.0 * 3.14159, theta = 0, phi = 0;
        double X1 = Math.cos(alpha), Y1 = Math.sin(alpha);
        double[][] coefficient = {
                {Y1 * Z2, -X1 * Z2, (-Y1 * X2 + X1 * Y2)},
                {(X1 - X2), (Y1 - Y2), -Z2}
        };

        Matrix A = new Matrix(coefficient);
        Matrix mATA = A.transpose().times(A);
        EigenvalueDecomposition E = mATA.eig();

        //特征值
        Matrix mD = E.getD();
        //特征向量
        Matrix mV = E.getV();

        //检测哪一行特征值是0
        int index = 0;
        for (int i = 0; i < 3; i++) {
            double c = mD.get(i, i);
            if (c <= 1e-6 && c >= -1e-6) {
                index = i;
                break;
            }
        }

        Xn = mV.get(0, index);
        Yn = mV.get(1, index);
        Zn = mV.get(2, index);

        L = Math.sqrt((Xn * Xn + Yn * Yn + Zn * Zn));

        if (Yn < 0) L = -L;
        Xn = Xn / L;
        Yn = Yn / L;
        Zn = Zn / L;

        phi = Math.acos(Yn) * 180/3.14159 ;
        theta = Math.asin(Math.abs(Zn) / Math.sqrt(1 - Yn * Yn)) * 180/3.14159 ;

        if(Xn < 0) theta = 180 - theta;
        if(Zn < 0) theta = -theta;

        rotateVerticalToMove = (float) (phi);
        rotateHorizontalToMove = (float) (theta);
}
    @Override
    public boolean onTickCheck(long aTimer) {
        super.onTickCheck(aTimer);
        isValid=true;
        for (int i = yCoord+1; i < 256; i++) {
            if (!(worldObj.getBlock(xCoord,i,zCoord)== Blocks.air))isValid=false;
        }
        if (!(worldObj.getBlock(xCoord,yCoord+1,zCoord)== Blocks.air)
                ||!(worldObj.getBlock(xCoord+1,yCoord,zCoord+1)== Blocks.air)
                ||!(worldObj.getBlock(xCoord+1,yCoord,zCoord)== Blocks.air)
                ||!(worldObj.getBlock(xCoord+1,yCoord,zCoord-1)== Blocks.air)
                ||!(worldObj.getBlock(xCoord,yCoord,zCoord+1)== Blocks.air)
                ||!(worldObj.getBlock(xCoord,yCoord,zCoord-1)== Blocks.air)
                ||!(worldObj.getBlock(xCoord-1,yCoord,zCoord+1)== Blocks.air)
                ||!(worldObj.getBlock(xCoord-1,yCoord,zCoord)== Blocks.air)
                ||!(worldObj.getBlock(xCoord-1,yCoord,zCoord-1)== Blocks.air)) {
            isValid=false;
        }
        return isValid;
    }
    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        if(targetSunBoilerPos==null)return getClientDataPacketByteArray(aSendAll,(byte)UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa),getVisualData(),getDirectionData());
        return getClientDataPacketByteArray(aSendAll,(byte)UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa),getVisualData(),getDirectionData(),UT.Code.toByteI(targetSunBoilerPos.posX,0),UT.Code.toByteI(targetSunBoilerPos.posX,1),UT.Code.toByteI(targetSunBoilerPos.posY,0),UT.Code.toByteI(targetSunBoilerPos.posY,1),UT.Code.toByteI(targetSunBoilerPos.posZ,0),UT.Code.toByteI(targetSunBoilerPos.posZ,1));
    }
@Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler){
        mRGBa = UT.Code.getRGBInt(new short[] {UT.Code.unsignB(aData[0]), UT.Code.unsignB(aData[1]), UT.Code.unsignB(aData[2])});
        setVisualData(aData[3]);
        setDirectionData(aData[4]);
        if(aData.length>5)targetSunBoilerPos=new ChunkCoordinates(UT.Code.combine(aData[5],aData[6]),UT.Code.combine(aData[7],aData[8]),UT.Code.combine(aData[9],aData[10]));
        return true;
    }
    @Override
    public boolean canDrop(int aSlot) {return true;}
}
