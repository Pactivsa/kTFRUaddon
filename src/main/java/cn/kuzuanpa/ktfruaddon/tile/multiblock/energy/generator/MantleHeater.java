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

package cn.kuzuanpa.ktfruaddon.tile.multiblock.energy.generator;

import cn.kuzuanpa.ktfruaddon.tile.multiblock.IMappedStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.TagData;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.multiblocks.IMultiBlockFluidHandler;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import gregapi.util.WD;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

public class MantleHeater extends HeaterBase implements IMultiBlockFluidHandler, ITileEntityEnergy, IMappedStructure {
    public ChunkCoordinates lastFailedPos;


    //决定机器大小
    //this controls the size of machine.
    public static final short machineX = 5;
    public static final short machineY = 4;
    public static final short machineZ = 5;
    public TagData mEnergyTypeEmitted=TD.Energy.HU;
    public long mRate=32, mCapacity =5000000, mRateMax =256;
    public boolean clickDoubleCheck=false;
    //决定结构检测的起始位置，默认情况下是从主方块起始
    //This controls where is the start point to check structure,Default is the position of controller block
    public final short xMapOffset = -2, zMapOffset = 0;
    public FluidTankGT[] mTanks = {new FluidTankGT(80000),new FluidTankGT(80000)};
    public static int[][][] blockIDMap = {{
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
    },{
            {18002, 18002,   0  , 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
    },{
            {31002, 31002, 31002, 31002, 31002},
            {31002, 31003, 31003, 31003, 31002},
            {31002, 31003, 18002, 31003, 31002},
            {31002, 31003, 31003, 31003, 31002},
            {31002, 31002, 31002, 31002, 31002},
    },{
            {31004, 31004, 31004, 31004, 31004},
            {31004, 31004, 31004, 31004, 31004},
            {31004, 31004, 31004, 31004, 31004},
            {31004, 31004, 31004, 31004, 31004},
            {31004, 31004, 31004, 31004, 31004},
    }};
    short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);

    public int getUsage(int mapX, int mapY, int mapZ, int registryID, int blockID){
        if(blockID==18002&&registryID==g)return MultiTileEntityMultiBlockPart.ONLY_FLUID_IN;
        if(blockID==31004&&registryID==k)return MultiTileEntityMultiBlockPart.ONLY_FLUID_OUT;
        return MultiTileEntityMultiBlockPart.NOTHING;
    }

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_OUTPUT)) mRate = aNBT.getLong(NBT_OUTPUT);
        if (aNBT.hasKey(NBT_OUTPUT_MAX)) mRateMax = aNBT.getLong(NBT_OUTPUT_MAX);
    }

    @Override
    public int getDesign(int mapX, int mapY, int mapZ, int blockId, int registryID) {return 0;}

    @Override
    public int getBlockID(int mapX, int mapY, int mapZ) {
        return blockIDMap[mapY][mapZ][mapX];
    }

    @Override
    public boolean isIgnored(int mapX, int mapY, int mapZ) {
        return false;
    }

    @Override
    public short getRegistryID(int mapX, int mapY, int mapZ) {
        return getBlockID(mapX,mapY,mapZ) == 18002 ? g:k;
    }

    @Override
    public List<ChunkCoordinates> getComputeNodesCoordList() { return null; }

    static {
        LH.add("ktfru.tooltip.multiblock.sunheater.1", "5x2x5 of Stainless Steel Walls");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.sunheater.1"));
        aList.add(LH.Chat.DRED + LH.get(LH.HAZARD_MELTDOWN));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_TOGGLE_SCREWDRIVER));
        aList.add(LH.Chat.DGRAY + LH.get(LH.TOOL_TO_MEASURE_THERMOMETER));
        super.addToolTips(aList, aStack, aF3_H);
    }

    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer,aIsServerSide);
        if (aTimer%60==0)clickDoubleCheck=false;
    }

    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = checkMappedStructure(lastFailedPos, machineX, machineY, machineZ, xMapOffset,0,0);
        return lastFailedPos==null;
    }

    @Override
    public void doOutputEnergy() {
        //emitEnergy
        if (mEnergyStored <= mRate) return;
        mRate = getEmitRate();
        TileEntity tileToEmit = worldObj.getTileEntity(utils.getRealX(mFacing, xCoord, 0, 2), yCoord + 1+ machineY, utils.getRealZ(mFacing, zCoord, 0, 2));
        if (tileToEmit instanceof ITileEntityEnergy) mEnergyStored -= mRate * ITileEntityEnergy.Util.insertEnergyInto(TD.Energy.HU, SIDE_BOTTOM, mRate, 1, this, tileToEmit);
    }

    @Override
    public void doOutputFluid() {
        FL.move(mTanks[1],WD.te(worldObj,utils.getRealX(mFacing, xCoord, 0, 2), yCoord + 1 + machineY, utils.getRealZ(mFacing, zCoord, 0, 2),SIDE_BOTTOM,false));
    }

    @Override
    public long getHotLiquidRecipeRate() {
        return mRateMax * 4;
    }
    public void overheat() {
        for (int x = -2; x < machineX-2; x++)for(int z=0;z<machineZ;z++)for(int y=0;y<machineY+1;y++)worldObj.setBlock(utils.getRealX(mFacing,xCoord,x,z),yCoord+y,utils.getRealZ(mFacing,zCoord,x,z), Blocks.flowing_lava);
    }

    @Override
    public long getCapacity() {
        return mCapacity;
    }

    public long getEmitRate(){
        return mEnergyStored > getCapacity() * 0.2 ? mRateMax : (int) ((mEnergyStored / (getCapacity() * 0.2f)) * mRateMax);
    }

    public float getTemperature(){return ((float)mEnergyStored/(float)getCapacity() *961)+DEFAULT_ENVIRONMENT_TEMPERATURE;}
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {return true;}

    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return aSide == SIDE_TOP && super.isEnergyEmittingTo(aEnergyType, aSide, aTheoretical);}
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {
        return Math.min(mRate, mEnergyStored);
    }
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {return mRateMax;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeEmitted.AS_LIST;}

    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.mantleheater";
    }
}
