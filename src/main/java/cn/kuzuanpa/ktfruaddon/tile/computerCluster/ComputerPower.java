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
package cn.kuzuanpa.ktfruaddon.tile.computerCluster;

public enum ComputerPower {
    Normal(0x33cce5), Biology(0x99e533), Quantum(0xb233cc), Spacetime(0x334ce5);

    public final int color;
    ComputerPower(int color){
        this.color = color;
    }
    public static ComputerPower getType(int ordinal){
        switch (ordinal){
            case 0:return Normal;
            case 1:return Biology;
            case 2:return Quantum;
            case 3:return Spacetime;
        }
        return Normal;
    }
}
