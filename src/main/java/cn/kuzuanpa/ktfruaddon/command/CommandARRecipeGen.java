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

import gregapi.data.OP;
import gregapi.data.TD;
import gregapi.oredict.OreDictMaterial;
import gregapi.oredict.OreDictPrefix;
import gregapi.util.ST;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CommandARRecipeGen extends CommandBase {
    @Override
    public String getCommandName() {
        return "arRecipeGen";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "用法: <最高熔点> <最高工具属性耐久> <最高工具属性等级> <是否包含无工具属性的物品>";
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
        if (p_71515_2_.length< 3)throw new WrongUsageException("用法: <最高熔点> <最高工具属性耐久> <最高工具属性等级> <是否包含无工具属性的物品>");
        try {
            Path newDir = Paths.get("generatedARRecipes");
            try {
                Files.createDirectory(newDir);
            } catch (FileAlreadyExistsException e) {}

            //blacklist wood because it's too many
            Predicate<OreDictMaterial> predicate = material ->
                    material.mMeltingPoint <= Integer.parseInt(p_71515_2_[0]) && TD.Properties.WOOD.NOT.isTrue(material) && TD.Atomic.ANTIMATTER.NOT.isTrue(material) && TD.Properties.HAS_TOOL_STATS.isTrue(material) ? (
                            material.mToolDurability <= Integer.parseInt(p_71515_2_[1]) &&
                            material.mToolQuality <= Integer.parseInt(p_71515_2_[2]))
                    : Boolean.parseBoolean(p_71515_2_[3]);

            List<PrefixTransformContainer> prefixToPrefixMap = new ArrayList<>();

            PrintWriter cutMachine = new PrintWriter(Files.newOutputStream(new File(newDir.toString(), "CuttingMachine.xml").toPath()));
            //CuttingMachine block->plate, longStick->stick, boule/gem-> crystal plate, stick -> bolt
            prefixToPrefixMap.add(new PrefixTransformContainer(OP.block    , 1, OP.plate   ,8, OP.dust, 1));
            prefixToPrefixMap.add(new PrefixTransformContainer(OP.stickLong, 1, OP.stick   ,2));
            prefixToPrefixMap.add(new PrefixTransformContainer(OP.bouleGt  , 1, OP.plateGem,4));
            prefixToPrefixMap.add(new PrefixTransformContainer(OP.gem      , 1, OP.plateGem,1));
            prefixToPrefixMap.add(new PrefixTransformContainer(OP.stick    , 1, OP.bolt    ,4));
            prefixToPrefixMap.forEach(container -> {
                for (int i = 0; i < 32767; i++) {
                    String str = getRecipeXML(container, i, predicate);
                    if (str != null) cutMachine.write(str);
                }
            });

            cutMachine.flush();
            cutMachine.close();
            prefixToPrefixMap.clear();

            PrintWriter rollingMachine = new PrintWriter(Files.newOutputStream(new File(newDir.toString(), "RollingMachine.xml").toPath()));
            //RollingMachine ingot->plate, 1x,2x,3x,4x,5x,dense
            prefixToPrefixMap.add(new PrefixTransformContainer(OP.ingot         , 1, OP.plate         ,1));
            prefixToPrefixMap.add(new PrefixTransformContainer(OP.ingotDouble   , 1, OP.plateDouble   ,1));
            prefixToPrefixMap.add(new PrefixTransformContainer(OP.ingotTriple   , 1, OP.plateTriple   ,1));
            prefixToPrefixMap.add(new PrefixTransformContainer(OP.ingotQuadruple, 1, OP.plateQuadruple,1));
            prefixToPrefixMap.add(new PrefixTransformContainer(OP.ingotQuintuple, 1, OP.plateQuintuple,1));
            prefixToPrefixMap.add(new PrefixTransformContainer(OP.blockSolid    , 1, OP.plateDense    ,1));
            prefixToPrefixMap.forEach(container -> {
                for (int i = 0; i < 32767; i++) {
                    String str = getRecipeXML(container, i, predicate);
                    if (str != null) rollingMachine.write(str);
                }
            });

            rollingMachine.flush();
            rollingMachine.close();
            prefixToPrefixMap.clear();



            PrintWriter lathe = new PrintWriter(Files.newOutputStream(new File(newDir.toString(), "Lathe.xml").toPath()));
            //lathe ingot->stick
            prefixToPrefixMap.add(new PrefixTransformContainer(OP.ingot  , 1, OP.stick ,2));
            prefixToPrefixMap.forEach(container -> {
                for (int i = 0; i < 32767; i++) {
                    String str = getRecipeXML(container, i, predicate);
                    if (str != null) lathe.write(str);
                }
            });

            lathe.flush();
            lathe.close();
            prefixToPrefixMap.clear();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class PrefixTransformContainer{
        public OreDictPrefix prefixFrom;
        public int amountFrom;
        public OreDictPrefix prefixTo;
        public int amountTo;
        public OreDictPrefix prefixTo2 = null;
        public int amountTo2 = 0;
        public PrefixTransformContainer(OreDictPrefix prefixFrom, int amountFrom, OreDictPrefix prefixTo, int amountTo){
            this.prefixFrom = prefixFrom;
            this.amountFrom = amountFrom;
            this.prefixTo = prefixTo;
            this.amountTo = amountTo;
        }
        public PrefixTransformContainer(OreDictPrefix prefixFrom, int amountFrom, OreDictPrefix prefixTo, int amountTo, OreDictPrefix prefixTo2, int amountTo2) {
            this(prefixFrom, amountFrom, prefixTo, amountTo);
            this.prefixTo2 = prefixTo2;
            this.amountTo2 = amountTo2;
        }
    }

    public static String getRecipeXML(PrefixTransformContainer container, int i, Predicate<OreDictMaterial> predicate) {
        OreDictMaterial material = OreDictMaterial.get(i);
        if (material != null && ST.valid(container.prefixFrom.mat(material, 1)) && ST.valid(container.prefixTo.mat(material, 1)) && (container.prefixTo2==null || ST.valid(container.prefixTo2.mat(material, 1))) && predicate.test(material)) {
            return ("\t<Recipe timeRequired=\"" + getTimeRequired(material) + "\" power =\"" + getPower(material) + "\">\n" +
                    "\t\t<input>\n" +
                    "\t\t\t<itemStack>" + ST.regName(container.prefixFrom.mat(material, 1)) + ";" + container.amountFrom + ";" + ST.meta(container.prefixFrom.mat(material, 1)) + "</itemStack>\n" +
                    "\t\t</input>\n" +
                    "\t\t<output>\n" +
                    "\t\t\t<itemStack>" + ST.regName(container.prefixTo.mat(material, 1)) + ";" + container.amountTo + ";" + ST.meta(container.prefixTo.mat(material, 1)) + "</itemStack>\n" +
                    (container.prefixTo2 == null?"":"\t\t\t<itemStack>" + ST.regName(container.prefixTo2.mat(material, 1)) + ";" + container.amountTo2 + ";" + ST.meta(container.prefixTo2.mat(material, 1)) + "</itemStack>\n")+
                    "\t\t</output>\n" +
                    "\t</Recipe>" + "\n");
        }
        return null;
    }

    public static int getTimeRequired(OreDictMaterial material){
        return (int) (TD.Properties.HAS_TOOL_STATS.isTrue(material) ? material.mToolDurability + material.mMeltingPoint / 24 : material.mMeltingPoint / 10);
    }

    public static int getPower(OreDictMaterial material){
        return (int) (TD.Properties.HAS_TOOL_STATS.isTrue(material) ? material.mToolQuality * material.mToolDurability / 16 + material.mMeltingPoint / 256 : material.mMeltingPoint / 128);
    }

}
