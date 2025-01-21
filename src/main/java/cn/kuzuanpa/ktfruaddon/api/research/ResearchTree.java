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

import net.minecraft.init.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResearchTree {

    public Map<String, ResearchItem> allResearch = new HashMap<>();
    public Map<String, ResearchItem> completedResearch = new HashMap<>();

    public void addResearchItem(ResearchItem item) {
        allResearch.put(item.getName(), item);
    }

    public void completeResearch(String researchName) {
        ResearchItem item = allResearch.get(researchName);
        if (item != null && item.areDependenciesMet(completedResearch)) {
            completedResearch.put(researchName, item);
            System.out.println("Completed research: " + researchName);
        } else {
            System.out.println("Cannot complete research: " + researchName + " due to unsatisfied dependencies.");
        }
    }

    public ResearchTree(){
        putTestValues();
    }
    public void putTestValues(){

        ResearchItem a = new ResearchItem("A", "Descr你好世界你好世界你好世界你好世界你好世界你好世界你好世界你好世界你好世界你好世界你好世界on of A", null).setPos(60,130);
        ResearchItem b = new ResearchItem("B", "Description of B", null).setPos(60,20);
        ResearchItem c = new ResearchItem("C", "Description of C", null).setPos(180,10);
        ResearchItem d = new ResearchItem("D", "Description of D", null).setPos(180,130);
        a.addPrerequisite(rootItem);
        b.addPrerequisite(rootItem);


        c.addPrerequisite(b);
        d.addPrerequisite(a);
        d.addPrerequisite(b);


        addResearchItem(a);
        addResearchItem(b);
        addResearchItem(c);
        addResearchItem(d);

        a.isUnlocked =true;
        b.isUnlocked =true;
        c.isUnlocked =true;
        a.isCompleted = true;
        b.progress = 64;

        a.conditions.add(new ResearchItem.TestCondition(Items.clay_ball.getIconFromDamage(0)));
        a.conditions.add(new ResearchItem.TestCondition(Items.book.getIconFromDamage(0)));
        a.conditions.add(new ResearchItem.TestCondition(Items.lava_bucket.getIconFromDamage(0)));
        b.conditions.add(new ResearchItem.TestCondition(Items.lava_bucket.getIconFromDamage(0)));


    }
    public ResearchItem rootItem = new ResearchItem("root","root of everything");


    public boolean removeChildItem(ResearchItem item) {

        removeChildRecursively(rootItem, item);

        return !item.getPrerequisites().isEmpty();
    }

    private void removeChildRecursively(ResearchItem current, ResearchItem target) {
        List<ResearchItem> children = current.getPrerequisites();
        children.remove(target);
        for (ResearchItem child : new ArrayList<>(children)) {
            removeChildRecursively(child, target);
        }
    }

}
