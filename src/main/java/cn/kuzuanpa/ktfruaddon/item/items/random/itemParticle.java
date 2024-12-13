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

package cn.kuzuanpa.ktfruaddon.item.items.random;

import cn.kuzuanpa.ktfruaddon.api.item.ItemList;
import gregapi.item.CreativeTab;
import gregapi.item.multiitem.MultiItemRandom;

import static cn.kuzuanpa.ktfruaddon.ktfruaddon.MOD_ID;

public class itemParticle extends MultiItemRandom {
    public itemParticle() {
        super(MOD_ID, "ktfru.item.particle");
        setCreativeTab(new CreativeTab(getUnlocalizedName(), "kTFRUAddon: Particles", this, (short) 1008));
    }

    @Override
    public void addItems() {
        ItemList.FusionTokamakData0.set(addItem(0, "Tokamak Experiment Data (Electric)", "A common data, do some help in improving tokamak reactor."));
        ItemList.FusionTokamakData1.set(addItem(1, "Tokamak Experiment Data (Heat)", "A rare data, do massive help in improving tokamak reactor."));
        ItemList.FusionTokamakData2.set(addItem(2, "Tokamak Experiment Data (Structure)", "A legendary data, can be used to create a fusion reactor."));

        //Copy From GT6U
        ItemList.Proton.set(addItem(1000, "Proton", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Anti_Proton.set(addItem(1001, "Anti Proton", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Electron.set(addItem(1002, "Electron", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Positron.set(addItem(1003, "Positron (Anti Electron)", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Neutron.set(addItem(1004, "Neutron", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Neutrino.set(addItem(1005, "Neutrino", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Anti_Neutrino.set(addItem(1006, "Anti Neutrino", "A subatomic particle. Can be produced in particle collider."));
        ItemList.Alpha_Particle.set(addItem(1007, "Alpha Particle", "The nucleus of helium. "));
        ItemList.Higgs_Boson.set(addItem(1008, "Higgs-Boson", "A Standard Model particle. Origin of mass. "));
        ItemList.Kerr_Blackhole.set(addItem(1009, "Kerr Blackhole", "An extremely rare tiny blackhole that can be manually produced in particle collider "));

    }
}
