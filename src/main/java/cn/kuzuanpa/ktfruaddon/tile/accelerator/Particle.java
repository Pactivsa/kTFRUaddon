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


package cn.kuzuanpa.ktfruaddon.tile.accelerator;

import gregapi.oredict.OreDictMaterial;

public class Particle {
    public long energy=0;
    public int charge=0,count=1;
    public OreDictMaterial material;
    public Particle(long energy,int charge,OreDictMaterial material){
        this.energy=energy;
        this.charge=charge;
        this.material=material;
    }
    public Particle(long energy,int charge,OreDictMaterial material,int count){
        this.energy=energy;
        this.charge=charge;
        this.material=material;
        this.count=count;
    }
}
