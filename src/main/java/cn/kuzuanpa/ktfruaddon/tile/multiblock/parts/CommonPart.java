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


package cn.kuzuanpa.ktfruaddon.tile.multiblock.parts;

import gregapi.tileentity.multiblocks.MultiTileEntityMultiBlockPart;


public class CommonPart extends MultiTileEntityMultiBlockPart {

    @Override
    public int getLightOpacity(){
        return mDesign==1?255:0;
    }
    //When this part be hidden, This will make adjoining block's side rendering properly.
    @Override public boolean isSurfaceOpaque2       (byte aSide) {return mDesign!=1;}
    @Override
    public String getTileEntityName(){
        return "kfru.multitileentity.multiblock.commonpart";
    }
}
