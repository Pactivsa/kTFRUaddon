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

package cn.kuzuanpa.ktfruaddon.api.item;

import cn.kuzuanpa.ktfruaddon.api.tile.computerCluster.ComputePower;
import gregapi.data.IL;
import gregapi.data.MD;
import gregapi.util.ST;
import gregapi.util.UT;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public interface IComputerItem {
    @NotNull Map<String, Map<ComputePower, Long>> plainItemRegistry = new HashMap<>();
    @NotNull Map<Integer, ItemStack> typeRegistry = new HashMap<>();

    @NotNull Map<ComputePower, Long> getComputePower(int amount, int meta);

    /**@return is this computer able to start, false will interrupt start**/
    default boolean onStart(int meta) { return true; }
    /**@return is this computer able to stop, false may interrupt stop (may force stop)**/
    default boolean onStop(int meta)  { return true; }

    static boolean tryStart(ItemStack stack) {
        return !(stack.getItem() instanceof IComputerItem) || ((IComputerItem) stack.getItem()).onStart(stack.getItemDamage());
    }

    static boolean tryStop(ItemStack stack) {
        return !(stack.getItem() instanceof IComputerItem) || ((IComputerItem) stack.getItem()).onStop(stack.getItemDamage());
    }

    static @NotNull Map<ComputePower, Long> getComputePower(ItemStack stack) {
        if(stack.getItem() instanceof IComputerItem) return ((IComputerItem) stack.getItem()).getComputePower(stack.stackSize, stack.getItemDamage());
        else return getComputePowerForPlainItem(stack);
    }
    static @NotNull Map<ComputePower, Long> getComputePowerForPlainItem(ItemStack stack){
        Map<ComputePower, Long> map = plainItemRegistry.get(ST.regMeta(stack));
        return map == null? new HashMap<>() : map;
    }
    static void setComputePowerForPlainItem(ItemStack stack, Map<ComputePower, Long> computePower){
        plainItemRegistry.put(ST.regMeta(stack), computePower);
    }
    static void putDefaultPlainItemValues(){
        setComputePowerForPlainItem(ST.make(MD.GC_ADV_ROCKETRY,"circuitIC",1, 2), ComputePower.Normal.asMap(2));
        setComputePowerForPlainItem(IL.Circuit_Basic.get(1)                                       , ComputePower.Normal.asMap(8));
        setComputePowerForPlainItem(IL.Circuit_Good.get(1)                                        , ComputePower.Normal.asMap(150));
        setComputePowerForPlainItem(IL.Circuit_Advanced.get(1)                                    , ComputePower.Normal.asMap(620));
        setComputePowerForPlainItem(IL.Circuit_Elite.get(1)                                       , ComputePower.Normal.asMap(4600));
        setComputePowerForPlainItem(IL.Circuit_Master.get(1)                                      , ComputePower.Normal.asMap(15674));
        setComputePowerForPlainItem(IL.Circuit_Ultimate.get(1)                                    , ComputePower.Normal.asMap(45261));
    }

    static void putDefaultTypes(){
        typeRegistry.put(  0, ST.make(MD.GC_ADV_ROCKETRY,"circuitIC",1, 2));
        typeRegistry.put(  1, IL.Circuit_Basic.get(1));
        typeRegistry.put(  2, IL.Circuit_Good.get(1));
        typeRegistry.put(  3, IL.Circuit_Advanced.get(1));
        typeRegistry.put(  4, IL.Circuit_Elite.get(1));
        typeRegistry.put(  5, IL.Circuit_Master.get(1));
        typeRegistry.put(  6, IL.Circuit_Ultimate.get(1));
    }
    static NBTTagCompound save(ItemStack aStack) {
        if (aStack == null || aStack.stackSize < 0) return null;
        NBTTagCompound rNBT = UT.NBT.make();
        Map.Entry<Integer, ItemStack> findEntry = typeRegistry.entrySet().stream().filter(entry-> entry.getValue().getItem() == aStack.getItem() && entry.getValue().getItemDamage() == aStack.getItemDamage()).findFirst().orElse(null);
        if(findEntry == null)return null;
        UT.NBT.setNumber(rNBT, "type", findEntry.getKey());
        UT.NBT.setNumber(rNBT, "Count", aStack.stackSize);
        if (aStack.hasTagCompound()) rNBT.setTag("tag", aStack.getTagCompound());
        return rNBT;
    }

    static ItemStack load(NBTTagCompound aNBT) {
        if (aNBT == null) return null;
        ItemStack rStack = typeRegistry.get(aNBT.getInteger("type")).copy();
        rStack.stackSize = aNBT.getInteger("Count");
        if (aNBT.hasKey("tag")) rStack.setTagCompound(aNBT.getCompoundTag("tag"));
        return rStack;
    }

    static String getDescOneLine(ItemStack stack){
        return ComputePower.getDescOneLine(IComputerItem.getComputePower(stack));
    }
}
