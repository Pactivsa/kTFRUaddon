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
import gregapi.util.ST;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ResearchItem {
    public final String name;
    public final String desc;
    public final Item iconItem;
    public final int iconItemMeta;
    public int posX = 0;
    public int posY = 0;
    public int layer = 0;
    public final List<ResearchItem> prerequisites = new ArrayList<>();
    public final List<IResearchTask> tasks = new ArrayList<>();
    public boolean isUnlocked = false;
    public boolean isCompleted = false;

    public ResearchItem(ResearchTree tree,String name, String desc) {
        this(tree, name, desc,null,0);
    }
    public ResearchItem(ResearchTree tree, String name, String desc, Item icon, int iconMeta) {
        this.name = name;
        this.desc = desc;
        this.iconItem = icon;
        this.iconItemMeta = iconMeta;
        if(tree != null)tree.addResearchItem(this);
    }
    public ResearchItem setPos(int x,int y){
        this.posX=x;
        this.posY=y;
        return this;
    }

    @Nullable
    public IIcon getIcon(){
        return iconItem != null ? iconItem.getIconFromDamage(iconItemMeta) : null;
    }
    public String getName() {
        return name;
    }

    public ResearchItem addPrerequisite(ResearchItem... prerequisites) {
        for (ResearchItem prerequisite : prerequisites) {
            if(prerequisite.name.equals("root"))this.layer=Math.max(1,this.layer);
            else this.layer = Math.max(this.layer, prerequisite.layer+1);
            this.prerequisites.add(prerequisite);
        }
        return this;
    }

    public boolean removePrerequisite(ResearchItem prerequisite) {
        return prerequisites.remove(prerequisite);
    }
    /**Range: 0~100, note the progress is ceiled**/
    public byte getProgress(){
        return (byte)(100 * Math.ceil(tasks.stream().mapToLong(IResearchTask::getProgress).sum()*1.0f/(tasks.stream().mapToLong(IResearchTask::getMaxProgress).sum())));
    }
    public float getProgressF(){
        return (100 * (tasks.stream().mapToLong(IResearchTask::getProgress).sum()*1.0f/(tasks.stream().mapToLong(IResearchTask::getMaxProgress).sum())));
    }
    public List<ResearchItem> getPrerequisites() {
        return prerequisites;
    }

    public void addTask(IResearchTask condition) {
        tasks.add(condition);
    }

    public boolean removeTask(IResearchTask condition) {
        return tasks.remove(condition);
    }

    public List<IResearchTask> getTasks() {
        return tasks;
    }

    public boolean areAllTasksCompleted() {
        for (IResearchTask task : tasks) {
            if (!task.isCompleted()) {
                return false;
            }
        }
        return true;
    }
    public static class TestTask implements IResearchTask {

        public TestTask(Item item){
            this.item=item;
        }

        Item item;

        long progress = 20;

        @Override
        public long getMaxProgress() {
            return 120;
        }

        @Override
        public long getProgress() {
            return progress;
        }

        @Override
        public void setProgress(long progress) {
            this.progress=progress;
        }

        @Override
        public String getIdentifier() {
            return String.valueOf(ST.id(item));
        }

        @Override
        public IIcon getIcon() {
            return item.getIconFromDamage(0);
        }
    }
}