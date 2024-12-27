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

import cn.kuzuanpa.ktfruaddon.api.code.BoundingBox;
import cn.kuzuanpa.ktfruaddon.api.tile.IMappedStructure;
import cn.kuzuanpa.ktfruaddon.api.tile.base.TileEntityBaseControlledMachine;
import cn.kuzuanpa.ktfruaddon.api.tile.part.IConditionParts;
import cn.kuzuanpa.ktfruaddon.api.tile.util.utils;
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
public class maskAlignerUV extends TileEntityBaseControlledMachine implements IMappedStructure {
    public final short sizeX = 3, sizeY = 2, sizeZ = 2;
    public final short xMapOffset = -1;

    public static final int[][][] blockIDMap = {{
                {31000,   0  , 31000},
                {31000, 31500, 31000},
        },{
                {31000, 31010, 31000},
                {31000, 31020, 31000},
        }};

    public short g = ST.id(MultiTileEntityRegistry.getRegistry("gt.multitileentity").mBlock);
    public short k = ST.id(MultiTileEntityRegistry.getRegistry("ktfru.multitileentity").mBlock);

    //change value there to set usage of every block.

    @Override
    public int getUsage(int mapX,int mapY,int mapZ, int registryID, int blockID){
        if (registryID==k) switch (blockID){
            case 31110: return MultiTileEntityMultiBlockPart.ONLY_ENERGY_IN;
            case 31120: return MultiTileEntityMultiBlockPart.ONLY_ITEM_FLUID;
        }
        return MultiTileEntityMultiBlockPart.ONLY_IN;
    }

    @Override
    public int getDesign(int mapX, int mapY, int mapZ, int blockId, int registryID) {
        return 1;
    }

    public int getBlockID(int checkX, int checkY, int checkZ){return blockIDMap[checkY][checkZ][checkX];}
    public boolean isIgnored(int checkX, int checkY, int checkZ){
            return false;
        }

    public short getRegistryID(int x,int y,int z){return k;}

    ChunkCoordinates lastFailedPos=null;
    @Override
    public boolean checkStructure2() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (!worldObj.blockExists(tX, tY, tZ)) return mStructureOkay;
        lastFailedPos = checkMappedStructure(null, sizeX, sizeY, sizeZ,xMapOffset,0,0);
        if(lastFailedPos!=null)resetParts();
        return lastFailedPos==null;
    }

    @Override
    public boolean isPartSpecial(TileEntity tile) {
        return tile instanceof IConditionParts;
    }

    @Override
    public void receiveSpecialBlockList(List<TileEntity> list) {
        ConditionPartsPos = list.stream().map(tile -> new ChunkCoordinates(tile.xCoord,tile.yCoord,tile.zCoord)).collect(Collectors.toList());
    }

    public void resetParts() {
        int tX = xCoord, tY = yCoord, tZ = zCoord;
        if (worldObj.blockExists(tX, tY, tZ)) {
            tX= utils.getRealX(mFacing,tX,xMapOffset,0);
            tZ=utils.getRealZ(mFacing,tZ,xMapOffset,0);
            int cX, cY, cZ;
            for (cY  = 0; cY < sizeY; cY++) {
                for (cZ = 0; cZ < sizeZ; cZ++) {
                    for (cX = 0; cX < sizeX; cX++) {
                        if(!isIgnored(cX,cY,cZ))utils.resetTarget(this, utils.getRealX(mFacing, tX, cX, cZ), tY + cY, utils.getRealZ(mFacing, tZ, cX, cZ), 0, getUsage( cX,cY,cZ,getBlockID(cX,cY,cZ), getRegistryID(cX,cY,cZ)));
                    }
                }
            }
        }
    }
        //这是设置主方块的物品提示
        //controls tooltip of controller block
        static {
            LH.add("ktfru.tooltip.multiblock.maskaligner.0.1", "2 set of 2x2x1 Al Wall. 1 Block gap between them.");
            LH.add("ktfru.tooltip.multiblock.maskaligner.0.2", "Main Block facing outwards, in side-bottom of the gap");
            LH.add("ktfru.tooltip.multiblock.maskaligner.0.3", "Light Module is in top of Main Block, Energy Module is behind the Main Block.");
            LH.add("ktfru.tooltip.multiblock.maskaligner.0.4", "The left 1 block space is IO Manager.");
            LH.add("ktfru.tooltip.multiblock.maskaligner.0.5", "Input LU from upside of Light Module, Input EU from anyside of Energy Module.");
            LH.add("ktfru.tooltip.multiblock.maskaligner.0.6", "Fluid inputs from anyblock in upside, Item input from upside of IO manager, output from backside.");
        }

        @Override
        public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
            aList.add(LH.Chat.CYAN + LH.get(LH.STRUCTURE) + ":");
            aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.0.1"));
            aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.0.2"));
            aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.0.3"));
            aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.0.4"));
            aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.0.5"));
            aList.add(LH.Chat.WHITE + LH.get("ktfru.tooltip.multiblock.maskaligner.0.6"));
            super.addToolTips(aList, aStack, aF3_H);
        }
        //这里设置该机器的内部区域
        //controls areas inside the machine
        @Override
        public boolean isInsideStructure(int aX, int aY, int aZ) {
            return new BoundingBox(utils.getRealX(mFacing,xCoord,xMapOffset,0),yCoord,utils.getRealZ(mFacing,zCoord,xMapOffset,0),utils.getRealX(mFacing,utils.getRealX(mFacing,xCoord,xMapOffset,0), sizeX, sizeZ),yCoord+ sizeY,utils.getRealZ(mFacing,utils.getRealZ(mFacing,zCoord,xMapOffset,0), sizeX, sizeZ)).isXYZInBox(aX,aY,aZ);
        }
        //下面四个是设置输入输出的地方,return null是任意面
        //controls where to I/O, return null=any side
        @Override
        public DelegatorTileEntity<IFluidHandler> getFluidOutputTarget(byte aSide, Fluid aOutput) {
            return null;
        }

        @Override
        public DelegatorTileEntity<TileEntity> getItemOutputTarget(byte aSide) {
            DelegatorTileEntity<TileEntity> te = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 2), this.yCoord+1 , this.getOffsetZN(this.mFacing, 2), this.mFacing, false);
            if(te == null || te.mTileEntity == null) return this.delegator(SIDE_BOTTOM);
            return new DelegatorTileEntity<>(te.mTileEntity,SIDE_BOTTOM);
        }

        @Override
        public DelegatorTileEntity<IInventory> getItemInputTarget(byte aSide) {
            TileEntity te = WD.te(this.worldObj, this.getOffsetXN(this.mFacing, 1), this.yCoord+2 , this.getOffsetZN(this.mFacing, 1),false);
            if(!(te instanceof IInventory)) return new DelegatorTileEntity<>(this, SIDE_BOTTOM);
            return new DelegatorTileEntity<>((IInventory) te,SIDE_BOTTOM);
        }

        @Override
        public DelegatorTileEntity<IFluidHandler> getFluidInputTarget(byte aSide) {
            return null;
        }


    @Override
    public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {
        return 5;
    }

    @Override
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        if (mStructureOkay) {
            BoundingBox box;
            switch (aRenderPass) {
                case 0:
                    box = new BoundingBox(utils.getRealCoord(mFacing, 0.5, 0.5, 0.5, -1.502, -0.4999, -0.502), utils.getRealCoord(mFacing, 0.5, 0.5, 0.5, 1.501, 2.5, 1.501));
                    return box(aBlock, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
                case 1:
                    box = new BoundingBox(utils.getRealCoord(mFacing, 0.5, 0.5, 0.5, -0.5, -0.502, -0.5), utils.getRealCoord(mFacing, 0.5, 0.5, 0.5, 0.5, 1.502, 0.5));
                    return box(aBlock, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
                case 2:
                    //box for Top&Bottom overlay
                    box = new BoundingBox(utils.getRealCoord(mFacing, 0.5, 0.5, 0.5, -0.5, -0.502, 0.5), utils.getRealCoord(mFacing, 0.5, 0.5, 0.5, 0.5, 1.502, 1.5));
                    return box(aBlock, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
                case 3:
                    //box for Top&Bottom texture
                    box = new BoundingBox(utils.getRealCoord(mFacing, 0.5, 0.5, 0.5, -1.5, -0.501, -0.5), utils.getRealCoord(mFacing, 0.5, 0.5, 0.5, 1.5, 1.501, 2.5));
                    return box(aBlock, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
                case 4:
                    box = new BoundingBox(utils.getRealCoord(mFacing, 0.5, 0.5, 0.5, -1.501, -0.5, -0.5), utils.getRealCoord(mFacing, 0.5, 0.5, 0.5, 1.501, 1.5, 1.5));
                    return box(aBlock, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ);
            }
        }
        return T;
    }


    public static IIconContainer
            sTextureSidesA      = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/normal/sidesa"),
            sTextureSidesB      = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/normal/sidesb"),
            sTextureSingle      = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/single/sides"),
            sOverlaySingleFront = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/single/front"),
            sOverlayFront       = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/overlay/front"),
            sOverlayFrontActive = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/overlay/front_active"),
            sOverlayFrontRunningGlow = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/overlay/front_running_glow"),
            sOverlayFrontActiveGlow = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/overlay/front_active_glow"),
            sOverlayBack        = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/overlay/back"),
            sOverlayTopA        = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/overlay/topa"),
            sOverlayTopB        = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/overlay/topb"),
            sOverlayBottomB        = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/overlay/bottom"),
    sTextureTopA       = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/normal/topa"),
    sTextureTopB       = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/normal/topb"),
    sTextureTopC       = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/normal/topc"),
    sTextureTopD       = new Textures.BlockIcons.CustomIcon("machines/maskaligner/0/normal/topd")
            ;
    @Override
    public boolean breakBlock() {
        setStateOnOff(T);
        CS.GarbageGT.trash(mTanksInput);
        CS.GarbageGT.trash(mTanksOutput);
        CS.GarbageGT.trash(mOutputItems);
        CS.GarbageGT.trash(mOutputFluids);
        resetParts();
        return super.breakBlock();
    }
    @Override
    public boolean allowCover(byte aSide, ICover aCover) {return false;}
    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (mStructureOkay) {
            switch (aRenderPass) {
                case 0:
                    if (mActive) {
                        return aSide == mFacing ? BlockTextureMulti.get(BlockTextureDefault.get(sTextureSidesA, mRGBa), BlockTextureDefault.get(sOverlayFrontActive), BlockTextureDefault.get(sOverlayFrontActiveGlow, true)) : SIDE_TOP == aSide||SIDE_BOTTOM == aSide ?null: aSide == OPOS[mFacing] ? BlockTextureMulti.get(BlockTextureDefault.get(sTextureSidesA, mRGBa),BlockTextureDefault.get(sOverlayBack)) : null;
                    } else if (mRunning) {
                        return aSide == mFacing ? BlockTextureMulti.get(BlockTextureDefault.get(sTextureSidesA, mRGBa), BlockTextureDefault.get(sOverlayFront), BlockTextureDefault.get(sOverlayFrontRunningGlow, true)) : SIDE_TOP == aSide||SIDE_BOTTOM == aSide ? null: aSide == OPOS[mFacing] ? BlockTextureMulti.get(BlockTextureDefault.get(sTextureSidesA, mRGBa),BlockTextureDefault.get(sOverlayBack)) : null;
                    }
                    return aSide == mFacing ? BlockTextureMulti.get(BlockTextureDefault.get(sTextureSidesA, mRGBa), BlockTextureDefault.get(sOverlayFront)) :  SIDE_TOP == aSide||SIDE_BOTTOM == aSide ? null :  aSide == OPOS[mFacing] ? BlockTextureMulti.get(BlockTextureDefault.get(sTextureSidesA, mRGBa),BlockTextureDefault.get(sOverlayBack)) : null;
                case 1:
                    return aSide == SIDE_TOP?BlockTextureMulti.get(BlockTextureDefault.get(sOverlayTopA)):null;
                case 2:
                    return aSide == SIDE_BOTTOM?BlockTextureMulti.get(BlockTextureDefault.get(sOverlayBottomB)):aSide == SIDE_TOP?BlockTextureMulti.get(BlockTextureDefault.get(sOverlayTopB)):null;
                case 3:
                    if(mFacing==2) return aSide == SIDE_BOTTOM||aSide == SIDE_TOP?BlockTextureMulti.get(BlockTextureDefault.get(sTextureTopC,mRGBa)):null;
                    if(mFacing==3) return aSide == SIDE_BOTTOM||aSide == SIDE_TOP?BlockTextureMulti.get(BlockTextureDefault.get(sTextureTopA,mRGBa)):null;
                    if(mFacing==4) return aSide == SIDE_BOTTOM||aSide == SIDE_TOP?BlockTextureMulti.get(BlockTextureDefault.get(sTextureTopB,mRGBa)):null;
                    if(mFacing==5) return aSide == SIDE_BOTTOM||aSide == SIDE_TOP?BlockTextureMulti.get(BlockTextureDefault.get(sTextureTopD,mRGBa)):null;

                case 4:
                    return aSide != mFacing &&aSide != OPOS[mFacing] &&aSide != SIDE_BOTTOM &&aSide != SIDE_TOP ? BlockTextureMulti.get(BlockTextureDefault.get(sTextureSidesB, mRGBa)):null;

            }
        }
        return aShouldSideBeRendered[aSide] ?aSide == mFacing?BlockTextureMulti.get(BlockTextureDefault.get(sTextureSingle, mRGBa),BlockTextureDefault.get(sOverlaySingleFront)):BlockTextureMulti.get(BlockTextureDefault.get(sTextureSingle, mRGBa)) : null;
    }
    @Override
        public String getTileEntityName() {
            return "ktfru.multitileentity.multiblock.maskaligner.0";
        }
    }
