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

package cn.kuzuanpa.ktfruaddon.tile.multiblock;

import cn.kuzuanpa.ktfruaddon.api.client.fx.FxRenderBlockOutline;
import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.tile.IMappedStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.IMeterDetectable;
import cn.kuzuanpa.ktfruaddon.api.tile.crucible.IDummyCrucibleMaterialProvider;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.code.ArrayListNoNulls;
import gregapi.code.TagData;
import gregapi.data.LH;
import gregapi.data.TD;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictMaterialStack;
import gregapi.tileentity.multiblocks.IMultiBlockEnergy;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockBase;
import gregapi.util.ST;
import gregapi.util.UT;
import gregtech.tileentity.multiblocks.MultiTileEntityCrucible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static gregapi.data.CS.*;

public class DummyCrucible extends TileEntityBase10MultiBlockBase implements IMappedStructure, IMultiBlockEnergy, IDummyCrucibleMaterialProvider {
    public boolean mStopped = false;
    public long mEnergy = 0, mInputMax = 1024, mEnergyBaseConsume = 100, mMassTotal = 0, mMassSelf = 3200;
    public float mTemp = 0.0F, mTempMax = 32768.0F;
    public List<IMeterDetectable.MeterData> receivedEnergy = new ArrayList<>(), receivedEnergyLast = new ArrayList<>();
    public TagData mEnergyType = TD.Energy.EU;
    protected List<OreDictMaterialStack> mContent = new ArrayListNoNulls<>();

    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if(aNBT.hasKey(NBT_ENERGY))mEnergy = aNBT.getLong(NBT_ENERGY);
        if (aNBT.hasKey("ktfru.nbt.massSelf")) mMassTotal = mMassSelf = aNBT.getLong("ktfru.nbt.massSelf");
        if (aNBT.hasKey(NBT_TEMPERATURE+".max")) mTempMax = aNBT.getLong(NBT_TEMPERATURE+".max");
        if(aNBT.hasKey(NBT_INPUT_MAX))mInputMax = aNBT.getLong(NBT_INPUT_MAX);
        if(aNBT.hasKey(NBT_TEMPERATURE))mTemp = aNBT.getFloat(NBT_TEMPERATURE);
        mContent = OreDictMaterialStack.loadList(NBT_MATERIALS, aNBT);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);
        aNBT.setFloat(NBT_TEMPERATURE, mTemp);
        OreDictMaterialStack.saveList(NBT_MATERIALS, aNBT, mContent);
    }

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

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        mTemp += (mEnergy - mEnergyBaseConsume) * MultiTileEntityCrucible.KG_PER_ENERGY * 2F / mMassTotal;
        mEnergy = 0;

        receivedEnergyLast = receivedEnergy;
        receivedEnergy = new ArrayList<>();
    }

    @Override
    public long doInject(TagData aEnergyType, byte aSide, long aSize, long aAmount, boolean aDoInject) {
        if (mStopped) return 0;
        aSize = Math.abs(aSize);
        if (aEnergyType == mEnergyType) {
            if(aSize > mInputMax && aDoInject){
                explode(false);
                return aAmount;
            }
            if (aDoInject) mEnergy += aSize * aAmount;
            this.receivedEnergy.add(new IMeterDetectable.MeterData(aEnergyType,aSize, aAmount));
            return aAmount;
        }
        return 0;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.dummy_crucible";
    }

    //Structure

    public final short sizeX = 5, sizeY = 1, sizeZ = 4;
    public final short xMapOffset = -2, zMapOffset = 0;

    public static int[][][] blockIDMap = {{
            {18002, 18006, 0, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
            {18002, 18002, 18002, 18002, 18002},
    }};
    short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);
    short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public short[][][] registryIDMap = {{
            {g, g, k, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
            {g, g, g, g, g},
    }};
    public static boolean[][][] ignoreMap = {{
            {T, T, T, T, T},
            {F, F, F, F, F},
            {F, F, F, F, F},
            {F, F, F, F, F},
    }};
    @Override
    public TileDesc[] getTileDescs(int mapX, int mapY, int mapZ) {
        return new TileDesc[]{ new TileDesc(getRegistryID(mapX, mapY, mapZ), getBlockID(mapX, mapY, mapZ),getUsage(mapX, mapY, mapZ))};
    }

    public int getUsage(int mapX, int mapY, int mapZ) {
        int registryID = getRegistryID(mapX,mapY,mapZ), blockID = getBlockID(mapX, mapY, mapZ);
        if (blockID == 18002&&registryID==k) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
        } else if (blockID == 18002||blockID==18022&&registryID==g) {
            return  MultiTileEntityMultiBlockPart.ONLY_ENERGY_OUT;
        }else{return MultiTileEntityMultiBlockPart.NOTHING;}
    }

    public int getBlockID(int checkX, int checkY, int checkZ){
        return blockIDMap[checkY][checkZ][checkX];
    }

    public boolean isIgnored(int checkX, int checkY, int checkZ){
        return ignoreMap[checkY][checkZ][checkX];
    }
    public short getRegistryID(int checkX, int checkY, int checkZ){return registryIDMap[checkY][checkZ][checkX];}

    ChunkCoordinates lastFailedPos=null;
    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = checkMappedStructure(null, sizeX, sizeY, sizeZ,xMapOffset,0,zMapOffset);
        if(lastFailedPos!=null) FxRenderBlockOutline.addBlockOutlineToRender(lastFailedPos,0xff0000,2,System.currentTimeMillis()+30000);
        return lastFailedPos==null;
    }
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,zMapOffset), sizeX, sizeZ),yCoord+ sizeY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,zMapOffset), sizeX, sizeZ)).isXYZInBox(aX,aY,aZ);
    }

    @Override
    public @Nullable CrucibleOreDictMaterialStack extractMaterial(long amount, @Nullable OreDictMaterial selectedMaterial) {
        OreDictMaterialStack rawMatStack = findValidMaterial(selectedMaterial);
        if(rawMatStack == null)return null;
        long requiredAmount = UT.Code.units(amount, U, rawMatStack.mMaterial.mTargetSolidifying.mAmount, T);
        if(rawMatStack.mAmount <= requiredAmount){
            mContent.remove(rawMatStack);
            return new CrucibleOreDictMaterialStack(rawMatStack, rawMatStack.mAmount == requiredAmount);
        }
        rawMatStack.mAmount -= requiredAmount;
        return new CrucibleOreDictMaterialStack(rawMatStack.copy(requiredAmount), true);
    }

    private OreDictMaterialStack findValidMaterial(@Nullable OreDictMaterial selectedMaterial){
        if(selectedMaterial != null) return mContent.stream().filter(stack -> stack.mMaterial.equals(selectedMaterial) && selectedMaterial.mMeltingPoint < mTemp).findFirst().orElse(null);
        return mContent.stream().filter(stack -> stack.mMaterial.mMeltingPoint < mTemp).findFirst().orElse(null);
    }


}
