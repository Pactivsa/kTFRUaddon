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
package cn.kuzuanpa.ktfruaddon.tile.energy.generator;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.recipe.recipeMaps;
import cn.kuzuanpa.ktfruaddon.api.tile.util.kTileNBT;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.code.TagData;
import gregapi.data.FL;
import gregapi.data.LH;
import gregapi.data.LH.Chat;
import gregapi.data.MT;
import gregapi.data.TD;
import gregapi.fluid.FluidTankGT;
import gregapi.network.INetworkHandler;
import gregapi.network.IPacket;
import gregapi.old.Textures;
import gregapi.oredict.OreDictMaterial;
import gregapi.recipes.Recipe;
import gregapi.recipes.Recipe.RecipeMap;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.ITileEntityFunnelAccessible;
import gregapi.tileentity.ITileEntityTapAccessible;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.behavior.TE_Behavior_Active_Trinary;
import gregapi.tileentity.energy.ITileEntityEnergy;
import gregapi.tileentity.machines.ITileEntityAdjacentOnOff;
import gregapi.tileentity.machines.ITileEntityRunningActively;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;

import java.util.Collection;
import java.util.List;

import static gregapi.data.CS.*;

/**
 * @author kuzuanpa
 */
public class FuelBattery extends TileEntityBase09FacingSingle implements IFluidHandler, ITileEntityTapAccessible, ITileEntityFunnelAccessible, ITileEntityEnergy, ITileEntityRunningActively, ITileEntityAdjacentOnOff, IMultiTileEntity.IMTE_SyncDataByteArray {
    public boolean mStopped = F;
    public short mEfficiency = 10000;
    public int mElectrolyteRequired = 1000;
    public long mEnergy = 0, mRate = 32;
    public TagData mEnergyTypeEmitted = TD.Energy.EU;
    public RecipeMap mRecipes = recipeMaps.FuelBattery;
    public int mDesign = 0;
    public Recipe mLastRecipe = null;
    public FluidTankGT[] mTanks = {new FluidTankGT(8000),new FluidTankGT(8000), new FluidTankGT(8000), new FluidTankGT(8000), new FluidTankGT(mElectrolyteRequired)}

    , mTanksInput = {mTanks[0],mTanks[1]}, mTanksOutput ={ mTanks[2],mTanks[3]}, mTanksRecipe={mTanks[0],mTanks[1],mTanks[4]};

    public FluidTankGT mTankStatic =mTanks[4];
    public TE_Behavior_Active_Trinary mActivity = null;

    public boolean changingStaticTank =false;
    //stores materialId of positive and negative electrode.
    public short[] mDisplayMat = {0,0};
    public FuelBattery(){}
    //NBT
    @Override
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        if (aNBT.hasKey(NBT_ENERGY)) mEnergy = aNBT.getLong(NBT_ENERGY);
        mActivity = new TE_Behavior_Active_Trinary(this, aNBT);
        if (aNBT.hasKey(NBT_STOPPED)) mStopped = aNBT.getBoolean(NBT_STOPPED);
        if (aNBT.hasKey(NBT_OUTPUT)) mRate = aNBT.getLong(NBT_OUTPUT);
        if (aNBT.hasKey(kTileNBT.LIQUID_CATALYST_REQUIRED)) mElectrolyteRequired = aNBT.getInteger(kTileNBT.LIQUID_CATALYST_REQUIRED);
        if (aNBT.hasKey(NBT_EFFICIENCY)) mEfficiency = (short)UT.Code.bind_(0, 10000, aNBT.getShort(NBT_EFFICIENCY));
        if (aNBT.hasKey(NBT_ENERGY_EMITTED)) mEnergyTypeEmitted = TagData.createTagData(aNBT.getString(NBT_ENERGY_EMITTED));
        if (aNBT.hasKey(NBT_FUELMAP)) mRecipes = RecipeMap.RECIPE_MAPS.get(aNBT.getString(NBT_FUELMAP));
        if (aNBT.hasKey(NBT_FACING)) mFacing = aNBT.getByte(NBT_FACING);
        if (aNBT.hasKey(NBT_DESIGN)) mDesign = aNBT.getInteger(NBT_DESIGN);
        mTanks[0].readFromNBT(aNBT, NBT_TANK+".0").setCapacity(8000);
        mTanks[1].readFromNBT(aNBT, NBT_TANK+".1").setCapacity(8000);
        mTanks[2].readFromNBT(aNBT, NBT_TANK+".2").setCapacity(8000);
        mTanks[3].readFromNBT(aNBT, NBT_TANK+".3").setCapacity(8000);
        mTanks[4].readFromNBT(aNBT, NBT_TANK+".4").setCapacity(mElectrolyteRequired);
    }

    @Override
    public void writeToNBT2(NBTTagCompound aNBT) {
        super.writeToNBT2(aNBT);
        UT.NBT.setNumber(aNBT, NBT_ENERGY, mEnergy);
        UT.NBT.setBoolean(aNBT, NBT_STOPPED, mStopped);
        aNBT.setByte(NBT_FACING, mFacing);
        mActivity.save(aNBT);
        mTanks[0].writeToNBT(aNBT, NBT_TANK+".0");
        mTanks[1].writeToNBT(aNBT, NBT_TANK+".1");
        mTanks[2].writeToNBT(aNBT, NBT_TANK+".2");
        mTanks[3].writeToNBT(aNBT, NBT_TANK+".3");
        mTanks[4].writeToNBT(aNBT, NBT_TANK+".4");
    }

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(Chat.CYAN     + LH.get(LH.RECIPES) + ": " + Chat.WHITE + LH.get(mRecipes.mNameInternal));
        aList.add(Chat.GREEN    + LH.get(LH.FLUID_INPUT)+ ": " + Chat.WHITE + LH.get(I18nHandler.SIDE_RIGHT)+", "+LH.get(I18nHandler.SIDE_LEFT));
        aList.add(Chat.RED      + LH.get(LH.FLUID_OUTPUT)+ ": " + Chat.WHITE + LH.get(I18nHandler.SIDE_FRONT)+" "+LH.get(I18nHandler.AUTO)+", "+LH.get(I18nHandler.SIDE_BACK)+" "+LH.get(I18nHandler.AUTO));
        LH.addEnergyToolTips(this, aList, null, mEnergyTypeEmitted, null, LH.get(LH.FACE_TOP));
        aList.add(Chat.WHITE    + String.format(LH.get(I18nHandler.FUEL_BATTERY_0), mElectrolyteRequired));
        aList.add(Chat.ORANGE   + LH.get(LH.NO_GUI_FUNNEL_TAP_TO_TANK));
        aList.add(Chat.DGRAY    + LH.get(LH.TOOL_TO_DETAIL_MAGNIFYINGGLASS));
        aList.add(Chat.DGRAY    + LH.get(I18nHandler.USE_MONKEY_WRENCH_CHANGE_STRUCTURE));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override
    public int funnelFill(byte aSide, FluidStack aFluid, boolean aDoFill) {
        if (!mRecipes.containsInput(aFluid, this, NI)) return 0;
        updateInventory();
        return getAvailInputTank(aFluid.getFluid()).fill(aFluid, aDoFill);
    }

    @Override
    public FluidStack tapDrain(byte aSide, int aMaxDrain, boolean aDoDrain) {
        updateInventory();
        return getFluidTankDrainable2(aSide,null).drain(aMaxDrain, aDoDrain);
    }
    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        if (aIsServerSide) {
            //emitEnergy
            if (mEnergy >= mRate) {
                ITileEntityEnergy.Util.emitEnergyToNetwork(mEnergyTypeEmitted, mRate, 1, this);
                mEnergy -= mRate;
            }
            //doRecipe
            if (!changingStaticTank&&mEnergy < mRate * 2 && !mStopped &&slot(0)!=null&&slot(1)!=null && mTankStatic.amount() >= mElectrolyteRequired) {
                mActivity.mActive = F;
                Recipe tRecipe = mRecipes.findRecipe(this, mLastRecipe, T, Long.MAX_VALUE, NI, mTanksRecipe, slot(0),slot(1));
                if (tRecipe != null) {
                    if (tRecipe.mFluidOutputs.length < 1 || mTanksOutput[0].canFillAll(tRecipe.mFluidOutputs[0])&&(tRecipe.mFluidOutputs.length<2||mTanksOutput[1].canFillAll(tRecipe.mFluidOutputs[1]))) {
                    if (tRecipe.mFluidInputs.length >1&&mTanksInput[0].fluid()!=tRecipe.mFluidInputs[0].getFluid()){
                        //swap Tanks when Input Fluids in wrong order
                        FluidStack tmp = mTanksInput[0].get();
                        mTanksInput[0].setEmpty();
                        mTanksInput[0].fillAll(mTanksInput[1].get());
                        mTanksInput[1].setEmpty();
                        mTanksInput[1].fillAll(tmp);
                    }
                        if (tRecipe.isRecipeInputEqual(T, F, mTanksRecipe,slot(0),slot(1))) {
                            mActivity.mActive = T;
                            mLastRecipe = tRecipe;
                            mEnergy += UT.Code.units(Math.abs(tRecipe.mEUt * tRecipe.mDuration), 10000, mEfficiency, F);
                            if (tRecipe.mFluidOutputs.length > 1) mTanksOutput[1].fill(tRecipe.mFluidOutputs[1]);
                            if (tRecipe.mFluidOutputs.length > 0) mTanksOutput[0].fill(tRecipe.mFluidOutputs[0]);
                            while (mEnergy < mRate * 2 && (tRecipe.mFluidOutputs.length < 1 || tRecipe.mFluidOutputs.length == 1&&mTanksOutput[0].canFillAll(tRecipe.mFluidOutputs[0])||tRecipe.mFluidOutputs.length == 2&&mTanksOutput[0].canFillAll(tRecipe.mFluidOutputs[0])&&mTanksOutput[1].canFillAll(tRecipe.mFluidOutputs[1])) && tRecipe.isRecipeInputEqual(T, F, mTanksInput, ZL_IS)) {
                                mEnergy += UT.Code.units(Math.abs(tRecipe.mEUt * tRecipe.mDuration), 10000, mEfficiency, F);
                                if (tRecipe.mFluidOutputs.length > 1) mTanksOutput[1].fill(tRecipe.mFluidOutputs[1]);
                                if (tRecipe.mFluidOutputs.length > 0) mTanksOutput[0].fill(tRecipe.mFluidOutputs[0]);
                                if (mTanksInput[0].isEmpty()||(mTanksInput[1].isEmpty()&&tRecipe.mFluidInputs[1]!=null)) break;
                            }
                        }
                    }
                }
            }
            if (mEnergy < 0) mEnergy = 0;
            //AutoOutput
            if (mTanks[2]!=null) FL.move(mTanks[2], getAdjacentTank(mFacing));
            if (mTanks[3]!=null) FL.move(mTanks[3], getAdjacentTank(OPOS[mFacing]));
        }
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        long rReturn = super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
        if (rReturn > 0) return rReturn;

        if (isClientSide()) return 0;

        if (aTool.equals(TOOL_magnifyingglass)) {
            if (aChatReturn != null) {
                aChatReturn.add("Compositions: "+mTankStatic.content()+" "+(slot(0)==null?LH.get(I18nHandler.EMPTY):slot(0).getDisplayName())+" "+(slot(1)==null?LH.get(I18nHandler.EMPTY):slot(1).getDisplayName()));
                aChatReturn.add(LH.get(I18nHandler.INPUT ) + ": "  + mTanks[0].content()+" / "+ mTanks[1].content());
                aChatReturn.add(LH.get(I18nHandler.OUTPUT) + ": " + mTanks[2].content()+" / "+ mTanks[3].content());
            }
            return 1;
        }
        if(aTool.equals(TOOL_monkeywrench)){
            if (changingStaticTank) aChatReturn.add(LH.get(I18nHandler.DONE_CHANGING_STRUCTURE));
            else aChatReturn.add(LH.get(I18nHandler.CHANGING_STRUCTURE));
            changingStaticTank = !changingStaticTank;
            updateClientData();
        }
        if (getFacingTool() != null && aTool.equals(getFacingTool())) {byte aTargetSide = UT.Code.getSideWrenching(aSide, aHitX, aHitY, aHitZ); if (getValidSides()[aTargetSide]) {byte oFacing = mFacing; mFacing = aTargetSide; updateClientData(); causeBlockUpdate(); onFacingChange(oFacing); return 10000;}}
        return 0;
    }

    @Override
    public boolean onTickCheck(long aTimer) {
        return mActivity.check(mStopped) || super.onTickCheck(aTimer);
    }
     @Override public byte getDefaultSide() {return SIDE_FRONT;}
    @Override public boolean[] getValidSides() {return SIDES_HORIZONTAL;}

    @Override
    protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {
        return getAvailInputTank(aFluidToFill.getFluid());
    }

    @Override
    protected IFluidTank getFluidTankDrainable2(byte aSide, FluidStack aFluidToDrain) {
        if (changingStaticTank && !mTankStatic.isEmpty()) return mTankStatic;
        else for (FluidTankGT tank:mTanks) if (!tank.isEmpty()) return tank;
        return null;
    }

    @Override
    protected IFluidTank[] getFluidTanks2(byte aSide) {
        return mTanks;
    }

    // Icons
    public final static IIconContainer
            sTexture            = new Textures.BlockIcons.CustomIcon("machines/generators/fuel_battery/color"),
            sOverlaySides       = new Textures.BlockIcons.CustomIcon("machines/generators/fuel_battery/overlay/sides"),
            sOverlaySidesInner  = new Textures.BlockIcons.CustomIcon("machines/generators/fuel_battery/overlay/sides_inner"),
            sOverlayTop         = new Textures.BlockIcons.CustomIcon("machines/generators/fuel_battery/overlay/top"),
            sOverlayFront       = new Textures.BlockIcons.CustomIcon("machines/generators/fuel_battery/overlay/front"),
            sOverlaySidesActive = new Textures.BlockIcons.CustomIcon("machines/generators/fuel_battery/overlay/sides_active"),
            sOverlayFrontActive = new Textures.BlockIcons.CustomIcon("machines/generators/fuel_battery/overlay/front_active"),
            sOverlayFrontPole   = new Textures.BlockIcons.CustomIcon("machines/generators/fuel_battery/overlay/front_pole"),

            sOverlaySidesPoleA1   = new Textures.BlockIcons.CustomIcon("machines/generators/fuel_battery/overlay/sides_pole_a_1"),
            sOverlaySidesPoleA2   = new Textures.BlockIcons.CustomIcon("machines/generators/fuel_battery/overlay/sides_pole_a_2"),
            sOverlaySidesPoleB1   = new Textures.BlockIcons.CustomIcon("machines/generators/fuel_battery/overlay/sides_pole_b_1"),
            sOverlaySidesPoleB2   = new Textures.BlockIcons.CustomIcon("machines/generators/fuel_battery/overlay/sides_pole_b_2");

    @Override
    public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        if (!aShouldSideBeRendered[aSide]) return null;
        if (aSide == mFacing||aSide == OPOS[mFacing]){
            if (mActivity.mState>0)       return BlockTextureMulti.get(BlockTextureDefault.get(sTexture, mRGBa),BlockTextureDefault.get(sOverlayFrontPole, aSide==mFacing? getColorForTexture(mDisplayMat[0]) : getColorForTexture(mDisplayMat[1])),BlockTextureDefault.get(sOverlayFront),BlockTextureDefault.get(sOverlayFrontActive));
            return                               BlockTextureMulti.get(BlockTextureDefault.get(sTexture, mRGBa),BlockTextureDefault.get(sOverlayFrontPole, aSide==mFacing? getColorForTexture(mDisplayMat[0]) : getColorForTexture(mDisplayMat[1])),BlockTextureDefault.get(sOverlayFront));
        }
        if (aSide == SIDE_TOP)            return BlockTextureMulti.get(BlockTextureDefault.get(sTexture, mRGBa), BlockTextureDefault.get(sOverlayTop));
        if (aSide == SIDE_BOTTOM)         return BlockTextureMulti.get(BlockTextureDefault.get(sTexture, mRGBa));

        IIconContainer sOverlaySidesPoleA = mDesign == 1? sOverlaySidesPoleA1 : sOverlaySidesPoleA2;
        IIconContainer sOverlaySidesPoleB = mDesign == 1? sOverlaySidesPoleB1 : sOverlaySidesPoleB2;
        //Left Right
        if (mActivity.mState>0) return    BlockTextureMulti.get(BlockTextureDefault.get(sTexture, mRGBa), BlockTextureDefault.get(sOverlaySidesInner), mDisplayMat[0]==MT.Teflon.mID?null:BlockTextureDefault.get(sOverlaySidesPoleA,getColorForTexture(mDisplayMat[0])),mDisplayMat[1]==MT.Teflon.mID?null:BlockTextureDefault.get(sOverlaySidesPoleB,getColorForTexture(mDisplayMat[1])),BlockTextureDefault.get(sOverlaySides),BlockTextureDefault.get(sOverlaySidesActive));
        return                            BlockTextureMulti.get(BlockTextureDefault.get(sTexture, mRGBa), BlockTextureDefault.get(sOverlaySidesInner), mDisplayMat[0]==MT.Teflon.mID?null:BlockTextureDefault.get(sOverlaySidesPoleA,getColorForTexture(mDisplayMat[0])),mDisplayMat[1]==MT.Teflon.mID?null:BlockTextureDefault.get(sOverlaySidesPoleB,getColorForTexture(mDisplayMat[1])),BlockTextureDefault.get(sOverlaySides));

    }

    public int getColorForTexture(int materialID){
        OreDictMaterial mat=OreDictMaterial.get(materialID);
        short newR = (short) (mat.mRGBaSolid[0]>235?255:(mat.mRGBaSolid[0]+20));
        short newG = (short) (mat.mRGBaSolid[1]>235?255:(mat.mRGBaSolid[1]+20));
        short newB = (short) (mat.mRGBaSolid[2]>235?255:(mat.mRGBaSolid[2]+20));
        return UT.Code.getRGBInt(newR,newG,newB);
    }
    @Override public boolean isEnergyType(TagData aEnergyType, byte aSide, boolean aEmitting) {
        return aEmitting && aEnergyType == mEnergyTypeEmitted;}
    @Override public boolean isEnergyEmittingTo(TagData aEnergyType, byte aSide, boolean aTheoretical) {
        return aSide == SIDE_TOP && super.isEnergyEmittingTo(aEnergyType, aSide, aTheoretical);}
    @Override public long getEnergyOffered(TagData aEnergyType, byte aSide, long aSize) {
        return Math.min(mRate, mEnergy);}
    @Override public long getEnergySizeOutputRecommended(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public long getEnergySizeOutputMin(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public long getEnergySizeOutputMax(TagData aEnergyType, byte aSide) {return mRate;}
    @Override public Collection<TagData> getEnergyTypes(byte aSide) {return mEnergyTypeEmitted.AS_LIST;}

    @Override public boolean getStateRunningPassively() {return mActivity.mActive;}
    @Override public boolean getStateRunningPossible() {return mActivity.mActive;}
    @Override public boolean getStateRunningActively() {return mActivity.mActive;}
    @Override public boolean setAdjacentOnOff(boolean aOnOff) {mStopped = !aOnOff; return !mStopped;}
    @Override public boolean setStateOnOff(boolean aOnOff) {mStopped = !aOnOff; return !mStopped;}
    @Override public boolean getStateOnOff() {return !mStopped;}

    public FluidTankGT getAvailInputTank(Fluid input) {
        if (changingStaticTank&&!mTankStatic.isFull()) return mTankStatic;
        else {
            for (FluidTankGT tank : mTanksInput) if (tank.fluid() != null && tank.fluid() == input) {
                if (!tank.isFull()) return tank;
                else return null;
            }
            for (FluidTankGT tank : mTanksInput) if (tank.isEmpty()) return tank;
        }
        return null;
    }

    //Visuals

    public short getSlotItemMaterial(int slot){
        if(slot(slot)!=null&&slot(slot).getUnlocalizedName().contains("ktfru.item.battery.pole")&&slot(slot).getItemDamage()<32768)return (short) slot(slot).getItemDamage();
        return MT.Teflon.mID;
    }

    @Override
    public IPacket getClientDataPacket(boolean aSendAll) {
        return getClientDataPacketByteArray(aSendAll, (byte)UT.Code.getR(mRGBa), (byte)UT.Code.getG(mRGBa), (byte)UT.Code.getB(mRGBa),getDirectionData(),mActivity.mState,UT.Code.toByteS(getSlotItemMaterial(0),0),UT.Code.toByteS(getSlotItemMaterial(0),1),UT.Code.toByteS(getSlotItemMaterial(1),0),UT.Code.toByteS(getSlotItemMaterial(1),1));
    }

    @Override
    public boolean receiveDataByteArray(byte[] aData, INetworkHandler aNetworkHandler) {
        mRGBa = UT.Code.getRGBInt(new short[] {UT.Code.unsignB(aData[0]), UT.Code.unsignB(aData[1]), UT.Code.unsignB(aData[2])});
        setDirectionData(aData[3]);
        mActivity.mState=aData[4];
        mDisplayMat[0]=UT.Code.combine(aData[5],aData[6]);
        mDisplayMat[1]=UT.Code.combine(aData[7],aData[8]);
        return T;
    }
    @Override public String getTileEntityName() {return "ktfru.multitileentity.generator.gasbattery";}
    //Inventory
    @Override public ItemStack[] getDefaultInventory(NBTTagCompound aNBT) {return new ItemStack[2];}
    @Override public boolean canDrop(int aInventorySlot) {return T;}

    private static final int[] ACCESSIBLE_SLOTS = new int[] {0, 1};

    @Override public int[] getAccessibleSlotsFromSide2(byte aSide) {return ACCESSIBLE_SLOTS;}

    @Override
    public boolean canInsertItem2(int aSlot, ItemStack aStack, byte aSide) {
        if (aSlot >= 2) return F;
        for (int i = 0; i < 2; i++) if (slot(i)== null&&aStack.getItem().getUnlocalizedName().contains("ktfru.item.battery.pole")&&i==aSlot) return T;
        return F;
    }
    @Override public boolean canExtractItem2(int aSlot, ItemStack aStack, byte aSide) {
        return !mActivity.mActive;
    }
    @Override
    public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if (/*isServerSide()&&*/changingStaticTank&&!mActivity.mActive) {
          //  byte tSlot = (byte)((mFacing==2||mFacing==4)?aHitX<0.5?0:1:(mFacing==3||mFacing==5)&&aHitZ<0.5?0:1);
            byte tSlot = 0;
            ItemStack aStack = aPlayer.getCurrentEquippedItem();
            if (aStack!=null) tSlot=(byte)(slot(0)==null?0:1);
            else tSlot=(byte)(slot(1)!=null?1:0);
            if (ST.valid(aStack) && aStack.getUnlocalizedName().contains("ktfru.item.battery.pole")&&slot(tSlot)==null)
                if (ST.move(aPlayer.inventory, this, aPlayer.inventory.currentItem, tSlot,1) > 0) {
                    playClick();
                    updateClientData();
                    return T;
                }
            if (slotHas(tSlot) && aStack == null && UT.Inventories.addStackToPlayerInventoryOrDrop(aPlayer, slot(tSlot), T, worldObj, xCoord + 0.5, yCoord + 1.2, zCoord + 0.5)) {
                slotKill(tSlot);
                updateInventory();
                updateClientData();
                return T;
            }
        }

        return T;
    }

}



