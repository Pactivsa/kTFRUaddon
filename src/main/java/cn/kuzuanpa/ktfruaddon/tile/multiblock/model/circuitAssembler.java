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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.model;
//This is an example machine used to learn structures, grammars etc. It's based on large bath vat in gregtech6
//这是一个示例机器，用于学习多方块机器的结构，语法等，这个机器是基于gregtech6中的大浸洗器创建的

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.tile.base.ModelRenderBaseMultiBlockMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.data.LH;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;

import static gregapi.data.CS.*;

public class circuitAssembler extends ModelRenderBaseMultiBlockMachine {

    //决定机器大小
    //this controls the size of machine.
    public final short machineX = 7, machineY = 3, machineZ = 3;
    //决定结构检测的起始位置，默认情况下是从主方块起始
    //This controls where is the start point to check structure,Default is the position of controller block
    public final short xMapOffset = -6, zMapOffset = 0;
    //映射表方向:
    //                 |
    //                 |
    //            ( tX ,tZ-1)
    //(tX-1, tZ );( tX , tZ );(tX+1, tZ )---->
    //            ( tX ,tZ+1)             x轴
    //                 | z轴
    //                 v
    //y轴为一张新的表格，代码中位于最上的表是最底下一层
    //默认情况(不改动偏移量)下主方块位于tX,tZ,如下所示
    //{main,part},
    //{part,part}
    //Map direction:
    //                 |
    //                 |
    //            ( tX ,tZ-1)
    //(tX-1, tZ );( tX , tZ );(tX+1, tZ )---->
    //            ( tX ,tZ+1)             axisX
    //                 | axisZ
    //                 v
    //In default (didn't modify offset),main block is on tX,tZ.For example:
    //{main,part},
    //{part,part}
    //这里决定每个参与构成本机器的方块的子id
    //Controls every block needed to build the machine
    //0=air,1=wall,2=main block,3=assemble head,4=table,5=传送带,6=heating table
    //7=Plate Input,8=Output
    public static int[][][] blockIDMap = {{
            {1, 0, 6, 0, 0, 2, 0},
            {1, 0, 6, 0, 0, 1, 0},
            {1, 1, 1, 4, 4, 1, 0},
    },{
            {5, 0, 0, 3, 3, 5, 0},
            {5, 0, 0, 3, 3, 5, 0},
            {1, 1, 1, 1, 1, 1, 0},
    },{
            {0, 7, 0, 0, 0, 0, 0},
            {0, 7, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0},
    },};
    //这是决定物品注册库（即来源mod）k是本mod,g是gregtech
    short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    //T是忽略此位置的方块 ,F是正常检测
    //T = ignore ,F = normally check
    public static boolean[][][] ignoreMap = {{
            {F, T, F, F, T, T, F},
            {F, T, F, F, T, T, F},
            {F, F, F, F, F, F, F},
    },{
            {F, T, T, T, F, F, F},
            {F, T, T, T, F, F, F},
            {F, F, F, F, F, F, F},
    },{
            {T, F, T, T, T, T, T},
            {T, F, T, T, T, T, T},
            {T, F, T, T, T, T, T},
    },};

    //change value there to set usage of every block.
    public int getUsage(int blockID ,short registryID){
        if (blockID == 31003&&registryID==k) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else if (blockID == 31003||blockID==18022&&registryID==g) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT;
        }else{return MultiTileEntityMultiBlockPart.NOTHING;}
    }
    public int getBlockID(int checkX, int checkY, int checkZ){
        return blockIDMap[checkY][checkZ][checkX];
    }

    public  boolean isIgnored(int checkX, int checkY, int checkZ){
        return ignoreMap[checkY][checkZ][checkX];
    }
    //Special Model Machines need to set every part to transparent, so we need to use parts in our own registry.
    public short getRegistryID(int x,int y,int z){return k;}

    @Override
    public boolean checkStructure3(boolean shouldPartsTransparent) {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (worldObj.blockExists(tX, tY, tZ)) {
            boolean tSuccess = T;
            tX= utils.getRealX(mFacing,tX,xMapOffset,zMapOffset);
            tZ=utils.getRealZ(mFacing,tZ,xMapOffset,zMapOffset);
            int cX, cY, cZ;
            for (cY  = 0; cY < machineY&&tSuccess; cY++) {
                for (cZ = 0; cZ < machineZ&&tSuccess; cZ++) {
                    for (cX = 0; cX < machineX&&tSuccess; cX++) {
                        if(!isIgnored(cX,cY,cZ))if (!utils.checkAndSetTarget(this, utils.getRealX(mFacing, tX, cX, cZ), tY + cY, utils.getRealZ(mFacing, tZ, cX, cZ),getBlockID(cX,cY,cZ), getRegistryID(cX,cY,cZ), shouldPartsTransparent?1:0, getUsage( getBlockID(cX,cY,cZ), getRegistryID(cX,cY,cZ)))) {
                            tSuccess = F;
                            //FMLLog.log(Level.FATAL, "failed");
                        }
                        //  FMLLog.log(Level.FATAL, "Checkpos" + mFacing + "/" + tX + "/" + tY + "/" + tZ + "/" + getCheckX(mFacing,tX,checkX,checkZ) + "/" + checkY + "/" +  getCheckZ(mFacing,tZ,checkX,checkZ)  + "/" + ignoreMap[checkY][checkZ][checkX]);
                    }
                }
            }
            return tSuccess;
        }
        return mStructureOkay;
    }
    @Override
    public void resetParts() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (worldObj.blockExists(tX, tY, tZ)) {
            tX= utils.getRealX(mFacing,tX,xMapOffset,zMapOffset);
            tZ=utils.getRealZ(mFacing,tZ,xMapOffset,zMapOffset);
            int cX, cY, cZ;
            for (cY  = 0; cY < machineY; cY++) {
                for (cZ = 0; cZ < machineZ; cZ++) {
                    for (cX = 0; cX < machineX; cX++) {
                        if(!isIgnored(cX,cY,cZ))utils.resetTarget(this, utils.getRealX(mFacing, tX, cX, cZ), tY + cY, utils.getRealZ(mFacing, tZ, cX, cZ), 0);
                    }
                }
            }
        }
    }


    //这是设置主方块的物品提示
    //controls tooltip of controller block
    static {
        LH.add("gt.tooltip.multiblock.example.complex.1", "5x5x2 of Stainless Steel Walls");
        LH.add("gt.tooltip.multiblock.example.complex.2", "Main Block centered on Side-Bottom and facing outwards");
        LH.add("gt.tooltip.multiblock.example.complex.3", "Input and Output at any Blocks");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.1"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.2"));
        aList.add(LH.Chat.WHITE + LH.get("gt.tooltip.multiblock.example.complex.3"));
        super.addToolTips(aList, aStack, aF3_H);
    }
    //这里是设置该机器的内部区域
    //controls areas inside the machine
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),machineX,machineZ),yCoord+machineY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),machineX,machineZ)).isXYZInBox(aX,aY,aZ);}
    //下面四个是设置输入输出的地方,return null是任意面
    //controls where to I/O, return null=any side
    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return getAdjacentTank(SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        return getAdjacentTileEntity(SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return null;
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
        return null;
    }
    //这里填写多方块结构的名称
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.circuit_assembler";
    }
}