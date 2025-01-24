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
import net.minecraft.util.IIcon;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResearchItem {
    public final String name;
    public final String desc;
    public final IIcon icon;
    public int posX = 0;
    public int posY = 0;
    public int layer = 0;
    public final List<ResearchItem> prerequisites = new ArrayList<>();
    public final List<ResearchItem> nextResearch = new ArrayList<>();
    public final List<IResearchTask> tasks = new ArrayList<>();
    public boolean isUnlocked = false;
    public boolean isCompleted = false;
    public byte progress = 0;

    public ResearchItem(ResearchTree tree,String name, String desc) {
        this(tree, name, desc,null);
    }
    public ResearchItem(ResearchTree tree, String name, String desc, IIcon icon) {
        this.name = name;
        this.desc = desc;
        this.icon = icon;
        tree.addResearchItem(this);
    }
    public ResearchItem setPos(int x,int y){
        this.posX=x;
        this.posY=y;
        return this;
    }

    public String getName() {
        return name;
    }

    public void addPrerequisite(ResearchItem prerequisite) {
        if(prerequisite.name.equals("root"))layer=1;
        else layer = prerequisite.layer+1;
        prerequisites.add(prerequisite);
    }

    public boolean removePrerequisite(ResearchItem prerequisite) {
        return prerequisites.remove(prerequisite);
    }

    public boolean areDependenciesMet(Map<String, ResearchItem> completedResearch) {
        for (ResearchItem dependency : prerequisites) {
            if (!completedResearch.containsKey(dependency.getName())) {
                return false;
            }
        }
        return true;
    }
    public List<ResearchItem> getPrerequisites() {
        return prerequisites;
    }

    public void addCondition(IResearchTask condition) {
        tasks.add(condition);
    }

    public boolean removeCondition(IResearchTask condition) {
        return tasks.remove(condition);
    }

    public List<IResearchTask> getTasks() {
        return tasks;
    }

    public boolean areAllConditionsSatisfied() {
        for (IResearchTask task : tasks) {
            if (!task.isSatisfied()) {
                return false;
            }
        }
        return true;
    }
    public static class TestTask implements IResearchTask {
        public TestTask(IIcon icon){
            this.icon=icon;
        }
        IIcon icon;
        @Override
        public long getMaxProgress() {
            return 120;
        }

        @Override
        public long getProgress() {
            return 20;
        }

        @Override
        public IIcon getIcon() {
            return icon;
        }
    }
}