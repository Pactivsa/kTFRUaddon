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

public class Constants {
    public static final short EVENT_CLUSTER_DESTROY=0;
    public static final short EVENT_WRONG_UUID =1;
    public static final short EVENT_KICKING_FROM_CLUSTER =2;
    public static final short EVENT_A_CONTROLLER_LEFT =3;
    public static final byte STATE_OFFLINE=0;
    public static final byte STATE_NORMAL=1;
    public static final byte STATE_WARNING=2;
    public static final byte STATE_ERROR=3;
    public static final byte STATE_BELONG_ERR/*The controller owned by other cluster*/=4;

    public static String getControllerEventDesc(byte event){
        return "Hello World";
    }

    public static String getClusterEventDesc(byte event){
        return "Hello World";
    }
}
