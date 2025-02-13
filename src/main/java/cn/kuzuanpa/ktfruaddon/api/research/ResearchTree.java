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

package cn.kuzuanpa.ktfruaddon.api.research;

import cn.kuzuanpa.ktfruaddon.api.research.task.IResearchTask;
import cpw.mods.fml.common.FMLLog;
import gregapi.util.UT;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResearchTree {

    public Map<String, ResearchItem> allResearch = new HashMap<>();

    public byte id;
    public void addResearchItem(ResearchItem item) {
        allResearch.put(item.getName(), item);
    }

    public ResearchTree(byte id){
        this.id = id;
        putTestValues();
    }
    public void putTestValues(){

        ResearchItem a = new ResearchItem(this, "A", "Description of AjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFjFj", null).setPos(60,130);
        ResearchItem b = new ResearchItem(this, "B", "Description of B", null).setPos(60,20);
        ResearchItem c = new ResearchItem(this, "C", "Description of C", null).setPos(180,10);
        ResearchItem d = new ResearchItem(this, "D", "Description of D", null).setPos(180,130);
        ResearchItem e = new ResearchItem(this, "E", "Description of E", null).setPos(320,130);
        ResearchItem f = new ResearchItem(this, "F", "Description of F", null).setPos(340,10);
        a.addPrerequisite(rootItem);
        b.addPrerequisite(rootItem);


        c.addPrerequisite(b);
        d.addPrerequisite(a);
        d.addPrerequisite(b);

        e.addPrerequisite(d);
        f.addPrerequisite(e);

        a.isUnlocked =true;
        b.isUnlocked =true;
        c.isUnlocked =true;

        a.tasks.add(new ResearchItem.TestTask(Items.clay_ball.getIconFromDamage(0)));
        a.tasks.add(new ResearchItem.TestTask(Items.book.getIconFromDamage(0)));
        a.tasks.add(new ResearchItem.TestTask(Items.lava_bucket.getIconFromDamage(0)));
        a.tasks.add(new ResearchItem.TestTask(Items.diamond_sword.getIconFromDamage(0)));
        a.tasks.add(new ResearchItem.TestTask(Items.bed.getIconFromDamage(0)));
        a.tasks.add(new ResearchItem.TestTask(Items.record_11.getIconFromDamage(0)));
        a.tasks.add(new ResearchItem.TestTask(Items.hopper_minecart.getIconFromDamage(0)));
        a.tasks.add(new ResearchItem.TestTask(Items.stone_shovel.getIconFromDamage(0)));
        b.tasks.add(new ResearchItem.TestTask(Items.lava_bucket.getIconFromDamage(0)));


    }
    public ResearchItem rootItem = new ResearchItem(this,"root","root of everything");

    private void removeChildRecursively(ResearchItem current, ResearchItem target) {
        List<ResearchItem> children = current.getPrerequisites();
        children.remove(target);
        for (ResearchItem child : new ArrayList<>(children)) {
            removeChildRecursively(child, target);
        }
    }
    public NBTTagCompound save(){
        NBTTagCompound tag = new NBTTagCompound();
        allResearch.forEach(((name, item) -> {
            //ONLY save task progress when research not completed
            if(item.getProgress() == 0)return;
            if(item.isCompleted){
                tag.setBoolean(name+".c", true);
                return;
            }
            NBTTagCompound list = new NBTTagCompound();
            item.tasks.forEach(task-> UT.NBT.setNumber(list, task.getIdentifier(), task.getProgress()));
            tag.setTag(name, list);
        }));
        return tag;
    }
    public void load(NBTTagCompound tag){
        allResearch.forEach(((name, item) -> {
            if(tag.hasKey(name+".c"))item.isCompleted = true;
            else if(tag.hasKey(name)){
                NBTTagCompound list = tag.getCompoundTag(name);
                item.tasks.forEach(task-> task.setProgress(list.getLong(task.getIdentifier())));
            }
        }));
    }
    public byte[] saveToArray() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeByte(id);
            for (Map.Entry<String, ResearchItem> entry : allResearch.entrySet()) {
                String name = entry.getKey();
                ResearchItem item = entry.getValue();
                //ONLY save task progress when research not completed
                if (!item.isUnlocked || item.getProgress() == 0) continue;
                dos.writeUTF(name);
                dos.writeShort(item.isCompleted ? -1 : item.tasks.size());
                if (item.isCompleted) continue;

                for (IResearchTask task : item.tasks) {
                    dos.writeUTF(task.getIdentifier());
                    long progress = task.getProgress();
                    if (progress > Integer.MAX_VALUE || progress < Integer.MIN_VALUE) {
                        dos.writeByte(3);
                        dos.writeLong(progress);
                    } else if (progress > Short.MAX_VALUE || progress < Short.MIN_VALUE) {
                        dos.writeByte(2);
                        dos.writeInt((int) progress);
                    } else {
                        dos.writeByte(1);
                        dos.writeShort((short) progress);
                    }
                }
            }
            dos.flush();
            return bos.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
            return new byte[0];
        }
    }
    public void loadFromArray(byte[] bytes) {
        try {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bis);
        id = dis.readByte();
        while (dis.available() > 0) {
            String name = dis.readUTF();
            ResearchItem item = allResearch.get(name);
            if(item == null)item = new ResearchItem(null, "","");
            short taskCount = dis.readShort();
            if (taskCount == -1) {
                item.isCompleted = true;
                continue;
            }
            for (int i = 0; i < taskCount; i++) {
                String taskName = dis.readUTF();
                IResearchTask task = item.tasks.stream().filter(t -> t.getIdentifier().equals(taskName)).findFirst().orElse(skippedDummyTask);
                byte progressType = dis.readByte();
                switch (progressType) {
                    case 1:
                        task.setProgress(dis.readShort());
                        break;
                    case 2:
                        task.setProgress(dis.readInt());
                        break;
                    case 3:
                        task.setProgress(dis.readLong());
                        break;
                    default:
                        FMLLog.log(Level.ERROR, "Unknown progress type: " + progressType+", packet may corrupted");
                        break;
                }
            }
        }
    }catch (IOException e){
        e.printStackTrace();
    }
    }
    public static DummyTask skippedDummyTask = new DummyTask();
    public static class DummyTask implements IResearchTask{
        public DummyTask(){}
        @Override public long getMaxProgress() {return 0;}
        @Override public long getProgress() {return 0;}
        @Override public void setProgress(long progress) {}
        @Override public IIcon getIcon() {return null;}
        @Override public String getIdentifier() {return "";}
    }
}
