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


package cn.kuzuanpa.ktfruaddon.tile.tank;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.tile.ICompressGasTank;
import gregapi.data.FL;
import gregapi.data.LH;
import gregtech.tileentity.tanks.MultiTileEntityBarrelMetal;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import java.util.List;

public class CompressedGasTank extends MultiTileEntityBarrelMetal implements ICompressGasTank {
    @Override
    public int fill(ItemStack aStack, FluidStack aFluid, boolean aDoFill) {
        return 0;
    }
    @Override
    public int funnelFill(byte aSide, FluidStack aFluid, boolean aDoFill) {
        return 0;
    }
    protected IFluidTank getFluidTankFillable2(byte aSide, FluidStack aFluidToFill) {return null;}

    @Override
    public boolean allowFluid(FluidStack aFluid) {
        return FL.temperature(aFluid) < mMaterial.mMeltingPoint && FL.gas(aFluid);
    }
    public int fillCompressedGas(FluidStack fluid){return mTank.fill(fluid);}

    @Override
    public void addToolTips(List<String> aList, ItemStack aStack, boolean aF3_H) {
        aList.add(LH.Chat.WHITE    + LH.get(I18nHandler.TANK_GAS_COMPRESSED_1));
        aList.add(LH.Chat.ORANGE    + LH.get(I18nHandler.TANK_GAS_COMPRESSED_2));
        aList.add(LH.Chat.ORANGE    + LH.get(I18nHandler.TANK_GAS_COMPRESSED_NEED_INPUTER));
        super.addToolTips(aList, aStack, aF3_H);
    }

    @Override public String getTileEntityName() {return "ktfru.multitileentity.tank.barrel.compressGas";}
}
