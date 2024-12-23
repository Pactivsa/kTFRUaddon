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

package cn.kuzuanpa.ktfruaddon.api.tile;

import cn.kuzuanpa.ktfruaddon.api.i18n.texts.I18nHandler;
import cn.kuzuanpa.ktfruaddon.api.item.IComputerItem;
import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import gregapi.data.LH;
import gregapi.util.UT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ICircuitChangeableTileEntity {

    List<ItemStack> getComputers();

    Map<ComputePower, Long> getComputePowerRequired();

    void setComputers(List<ItemStack> computers);

    static void saveCircuitInfo(NBTTagCompound nbt, List<ItemStack> computers){
        NBTTagList tagList = new NBTTagList();
        for (ItemStack stack : computers) {
            tagList.appendTag(IComputerItem.save(stack));
        }
        nbt.setTag("ktfru.circuits", tagList);
    }

    static List<ItemStack> loadCircuitInfo(NBTTagCompound nbt){
        NBTTagList list = nbt.getTagList("ktfru.circuits", 10);
        List<ItemStack> stackList = new ArrayList<>();
        for (int i = 0; i < list.tagCount(); i++) {
            NBTTagCompound data = list.getCompoundTagAt(i);
            stackList.add(IComputerItem.load(data));
        }
        return stackList;
    }

    default boolean tryStart(){
        Map<ComputePower, Long> computePowers = new HashMap<>();
        getComputers().forEach(computer -> IComputerItem.getComputePower(computer).forEach((k,v)-> computePowers.merge(k, v, Long::sum)));
        if(getComputePowerRequired().keySet().stream().allMatch(key -> computePowers.get(key) >= getComputePowerRequired().get(key))){
            return getComputers().stream().allMatch(IComputerItem::tryStart);
        }
        return false;
    }

    default boolean tryStop(boolean force){
        return force || getComputers().stream().allMatch(IComputerItem::tryStop);
    }
    default void addCircuitTooltip(List<String> aList, ItemStack aStack, boolean aF3_H){
        if(getComputers().isEmpty())aList.add(I18nHandler.COMPUTE_TILE_EMPTY);
        else if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            aList.add(LH.get(I18nHandler.COMPUTE_TILE_COMPUTERS));
            getComputers().forEach(computer -> aList.add(computer.getDisplayName() + " " + IComputerItem.getDescOneLine(computer)));
        }
        else aList.add(LH.get(I18nHandler.COMPUTE_TILE_SHIFT_SHOW_COMPUTERS));
    }
    static NBTBase addCircuit(int ... idsAndAmounts){
        if(idsAndAmounts.length % 2 == 1)throw new IllegalArgumentException("idsAndAmounts length must be even");
        NBTTagList tagList = new NBTTagList();
        for (int i = 0; i < idsAndAmounts.length; i+=2) {
            NBTTagCompound rNBT = UT.NBT.make();
            UT.NBT.setNumber(rNBT, "type", idsAndAmounts[i]);
            UT.NBT.setNumber(rNBT, "Count", idsAndAmounts[i+1]);
            tagList.appendTag(rNBT);
        }
        return tagList;
    }
}
