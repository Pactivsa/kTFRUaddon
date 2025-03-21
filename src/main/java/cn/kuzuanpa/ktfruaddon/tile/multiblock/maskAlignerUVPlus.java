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

package cn.kuzuanpa.ktfruaddon.tile.multiblock;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.IMappedStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.base.TileEntityBaseControlledMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.part.IConditionParts;
import cn.kuzuanpa.ktfruaddon.api.tile.util.TileDesc;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.cover.ICover;
import gregapi.data.CS;
import gregapi.data.LH;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.delegate.DelegatorTileEntity;
import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;
import gregapi.util.ST;
import gregapi.util.WD;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.List;
import java.util.stream.Collectors;

import static gregapi.data.CS.*;

public class maskAlignerUVPlus extends TileEntityBaseControlledMachine implements IMappedStructure {
    public final short sizeX = 3, sizeY = 3, sizeZ = 3;
    public final short xMapOffset = -1;
    public IIconContainer[] mTexturesMaterial = null, mTexturesInactive = null, mTexturesActive = null, mTexturesRunning = null;

    public static final int[][][] blockIDMap = {{
            {18002,   0  , 18002},
            {18002, 31021, 18002},
            {18002, 31501, 18002},
    },{
            {18002, 31005, 18002},
            {31005, 31006, 31005},
            {18002, 31005, 18002},
    },{
            {31011, 31011, 31011},
            {31011, 31011, 31011},
            {31011, 31011, 31011},
    }};
    public static short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public static short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);

    public static final short[][][] registryIDMap = {{
            {g, k, g},
            {g, k, g},
            {g, k, g},
    },{
            {g, k, g},
            {k, k, k},
            {g, k, g},
    },{
            {k, k, k},
            {k, k, k},
            {k, k, k},
    }};
    public int getUsage(int mapX, int mapY, int mapZ){
        int registryID = getRegistryID(mapX,mapY,mapZ), blockID = getBlockID(mapX, mapY, mapZ);
        if (registryID==k) switch (blockID){
            case 31011: return MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
            case 31021: return MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID;
        }
        return MultiTileEntityMultiBlockPart.ONLY_IN;
    }

    @Override
    public TileDesc[] getTileDescs(int mapX, int mapY, int mapZ) {
        return new TileDesc[]{ new TileDesc(getRegistryID(mapX, mapY, mapZ), getBlockID(mapX, mapY, mapZ),getUsage(mapX, mapY, mapZ))};
    }

    public int getBlockID(int checkX, int checkY, int checkZ){
        return blockIDMap[checkY][checkZ][checkX];
    }
    @Override
    public  boolean isIgnored(int checkX, int checkY, int checkZ){
        return false;
    }
    public short getRegistryID(int x,int y,int z){
        return registryIDMap[y][z][x];
    }

    @Override
    public boolean isPartSpecial(TileEntity tile) {
        return tile instanceof IConditionParts;
    }

    @Override
    public void receiveSpecialBlockList(List<TileEntity> list) {
        ConditionPartsPos = list.stream().map(tile -> new ChunkCoordinates(tile.xCoord,tile.yCoord,tile.zCoord)).collect(Collectors.toList());
    }
    ChunkCoordinates lastFailedPos=null;
    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = checkMappedStructure(null, sizeX, sizeY, sizeZ,xMapOffset,0,0);
        return lastFailedPos==null;
    }
    //这是设置主方块的物品提示
    //controls tooltip of controller block
    static {
        LH.add("ktfru.tooltip.multiblock.maskaligner.1.0", "this machine has 3 layer, layer 1 at bottom and 3 at top.");
        LH.add("ktfru.tooltip.multiblock.maskaligner.1.1", "L1: 3x1x3 stainless steel wall, main block in middle of any side, facing outwards.");
        LH.add("ktfru.tooltip.multiblock.maskaligner.1.2", "    IO manager behind the main block, Energy module behind IO manager.");
        LH.add("ktfru.tooltip.multiblock.maskaligner.1.3", "L2: Wafer platform at center, 4 platform motor next to it, empty space are walls");
        LH.add("ktfru.tooltip.multiblock.maskaligner.1.4", "L3: 3x1x3 light module in top.");
        LH.add("ktfru.tooltip.multiblock.maskaligner.1.5", "Input LU from upside of Light Module, Input EU from anyside of Energy Module.");
        LH.add("ktfru.tooltip.multiblock.maskaligner.1.6", "Item and Fluid inputs from anyblock around, Item output from bottom.");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.CYAN + LH.get(I18nHandler.HAS_PROJECTOR_STRUCTURE));
        aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.1.0"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.1.1"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.1.2"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.1.3"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.1.4"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.1.5"));
        aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.1.6"));
        super.addToolTips(aList, aStack, aF3_H);
    }
    //这里设置该机器的内部区域
    //controls areas inside the machine
    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        //FMLLog.log(Level.FATAL,"a"+(xCoord-(SIDE_X_NEG == mFacing ? 0 : SIDE_X_POS == mFacing ? 3 : machineX)));
        return aX >= xCoord - (SIDE_X_NEG == mFacing ? 0 : SIDE_X_POS == mFacing ? 3 : sizeX) &&
                aY >= yCoord - (SIDE_Y_NEG == mFacing ? 0 : SIDE_Y_POS == mFacing ? 3 : sizeY) &&
                aZ >= zCoord - (SIDE_Z_NEG == mFacing ? 0 : SIDE_Z_POS == mFacing ? 3 : sizeZ) &&
                aX <= xCoord + (SIDE_X_POS == mFacing ? 0 : SIDE_X_NEG == mFacing ? 3 : sizeX) &&
                aY <= yCoord + (SIDE_Y_POS == mFacing ? 0 : SIDE_Y_NEG == mFacing ? 3 : sizeX) &&
                aZ <= zCoord + (SIDE_Z_POS == mFacing ? 0 : SIDE_Z_NEG == mFacing ? 3 : sizeZ);
    }
    //下面四个是设置输入输出的地方,return null是任意面
    //controls where to I/O, return null=any side
    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
        return null;
    }

    @Override
    public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
        DelegatorTileEntity<TileEntity> te = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 1), this.yCoord -1 , this.getOffsetZN(this.mFacing, 1), this.mFacing, false);
        if(te == null || te.mTileEntity == null) return this.delegator(SIDE_BOTTOM);
        return new DelegatorTileEntity<>(te.mTileEntity,SIDE_BOTTOM);
    }

    @Override
    public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
        return new DelegatorTileEntity<>(this, SIDE_FRONT);
    }

    @Override
    public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
        return null;
    }
    @Override
    public boolean breakBlock() {
        setStateOnOff(T);
        CS.GarbageGT.trash(mTanksInput);
        CS.GarbageGT.trash(mTanksOutput);
        CS.GarbageGT.trash(mOutputItems);
        CS.GarbageGT.trash(mOutputFluids);
        return super.breakBlock();
    }
    @Override
    public boolean allowCover(byte aSide, ICover aCover) {return false;}
    public static IIconContainer
            sTextureSides      = new Textures.BlockIcons.CustomIcon("machines/maskaligner/1/common"),
            sOverlayFront       = new Textures.BlockIcons.CustomIcon("machines/maskaligner/1/overlay/front"),
            sOverlayFrontRunning = new Textures.BlockIcons.CustomIcon("machines/maskaligner/1/overlay/front_running"),
            sOverlayFrontActive = new Textures.BlockIcons.CustomIcon("machines/maskaligner/1/overlay/front_active");

    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if(aSide == mFacing) {
            if (mActive) return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa), BlockTextureDefault.get(sOverlayFrontActive));
            else if (mRunning) return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa), BlockTextureDefault.get(sOverlayFrontRunning));
            else return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa), BlockTextureDefault.get(sOverlayFront));
        }
        else return BlockTextureMulti.get(BlockTextureDefault.get(sTextureSides, mRGBa));
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.multiblock.maskaligner.1";
    }
}