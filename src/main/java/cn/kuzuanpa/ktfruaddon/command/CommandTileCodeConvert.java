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

package cn.kuzuanpa.ktfruaddon.command;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CommandTileCodeConvert extends CommandBase {
    @Override
    public String getCommandName() {
        return "convertStructureToCode";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "用法: <x1> <y1> <z1> <x2> <y2> <z2>, 使用时主方块应朝向-z轴方向";
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        return true;
    }

    @Override
    public void processCommand(ICommandSender p_71515_1_, String[] p_71515_2_) {
        if (p_71515_2_.length< 6)throw new WrongUsageException("Usage: <x1> <y1> <z1> <x2> <y2> <z2>");
        int x1=Integer.parseInt(p_71515_2_[0]),y1=Integer.parseInt(p_71515_2_[1]),z1=Integer.parseInt(p_71515_2_[2]),x2=Integer.parseInt(p_71515_2_[3]),y2=Integer.parseInt(p_71515_2_[4]),z2=Integer.parseInt(p_71515_2_[5]);
        if(x1>x2||y1>y2||z1>z2)throw new WrongUsageException("Start Pos must lower than end Pos!");
        int machineX=x2-x1+1,machineY=y2-y1+1,machineZ=z2-z1+1;
        String[][][] nameMap = new String[machineY][machineZ][machineX];
        String[][][] ignoreMap = new String[machineY][machineZ][machineX];
        for (int y = y1; y <= y2; y++) for (int x = x1; x <= x2; x++) for (int z = z1; z <= z2; z++) {
            Block block = p_71515_1_.getEntityWorld().getBlock(x,y,z);
            if(block == null || block.equals(Blocks.air)){
                nameMap[y-y1][z-z1][x-x1] = "空气";
                ignoreMap[y-y1][z-z1][x-x1] = "T";

            }else {
                nameMap[y-y1][z-z1][x-x1] = block.getLocalizedName()+":"+p_71515_1_.getEntityWorld().getBlockMetadata(x,y,z);
                ignoreMap[y-y1][z-z1][x-x1] = "F";
            }
        }
        p_71515_1_.addChatMessage(new ChatComponentText("已生成名称表, 请自行替换blockID和registryID:"));
        p_71515_1_.addChatMessage(new ChatComponentText(deepToString(nameMap)));
        p_71515_1_.addChatMessage(new ChatComponentText("\n\n\n\n已生成忽略表:"));
        p_71515_1_.addChatMessage(new ChatComponentText(deepToString(ignoreMap)));
    }
    public static String deepToString(Object[] a) {
        if (a == null)
            return "null";

        int bufLen = 20 * a.length;
        if (a.length != 0 && bufLen <= 0)
            bufLen = Integer.MAX_VALUE;
        StringBuilder buf = new StringBuilder(bufLen);
        deepToString(a, buf, new HashSet<Object[]>());
        return buf.toString();
    }
    private static void deepToString(Object[] a, StringBuilder buf,
                                     Set<Object[]> dejaVu) {
        if (a == null) {
            buf.append("null");
            return;
        }
        int iMax = a.length - 1;
        if (iMax == -1) {
            buf.append("[]");
            return;
        }

        dejaVu.add(a);
        buf.append('{');
        for (int i = 0; ; i++) {

            Object element = a[i];
            if (element == null) {
                buf.append("null");
            } else {
                Class<?> eClass = element.getClass();

                if (eClass.isArray()) {
                    if (eClass == byte[].class)
                        buf.append(Arrays.toString((byte[]) element));
                    else if (eClass == short[].class)
                        buf.append(Arrays.toString((short[]) element));
                    else if (eClass == int[].class)
                        buf.append(Arrays.toString((int[]) element));
                    else if (eClass == long[].class)
                        buf.append(Arrays.toString((long[]) element));
                    else if (eClass == char[].class)
                        buf.append(Arrays.toString((char[]) element));
                    else if (eClass == float[].class)
                        buf.append(Arrays.toString((float[]) element));
                    else if (eClass == double[].class)
                        buf.append(Arrays.toString((double[]) element));
                    else if (eClass == boolean[].class)
                        buf.append(Arrays.toString((boolean[]) element));
                    else { // element is an array of object references
                        if (dejaVu.contains(element))
                            buf.append("[...]");
                        else
                            deepToString((Object[])element, buf, dejaVu);
                    }
                } else {  // element is non-null and not an array
                    buf.append(element.toString());
                }
            }
            if (i == iMax)
                break;
            buf.append(", ");
        }
        buf.append('}');
        buf.append('\n');
        dejaVu.remove(a);
    }
}
