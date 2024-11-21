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

package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.code.IOreScanner;
import cn.kuzuanpa.ktfruaddon.code.OreScanner;
import cpw.mods.fml.common.FMLLog;
import gregapi.block.multitileentity.IMultiTileEntity;
import gregapi.old.Textures;
import gregapi.render.BlockTextureDefault;
import gregapi.render.BlockTextureMulti;
import gregapi.render.IIconContainer;
import gregapi.render.ITexture;
import gregapi.tileentity.base.TileEntityBase09FacingSingle;
import gregapi.tileentity.energy.ITileEntityEnergy;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import java.util.List;

import static gregapi.data.CS.SIDE_TOP;
import static gregapi.data.CS.TOOL_softhammer;

public class TileOreScanner extends TileEntityBase09FacingSingle implements ITileEntityEnergy, IMultiTileEntity.IMTE_SyncDataByteArray, IOreScanner {
    public static IIconContainer mTextureMaterial, mTextureFront, mTextureFrontActive, mTextureFrontFinished, mTextureAutoInput;
    public static final byte STATE_IDLE = 0, STATE_NOT_INIT = 1, STATE_FINISHED = 2;
    public byte mState = STATE_IDLE;
    public OreScanner mScanner = null;
    static {
        mTextureMaterial      = new Textures.BlockIcons.CustomIcon("machines/miner/colored");
        mTextureFront         = new Textures.BlockIcons.CustomIcon("machines/miner/front");
        mTextureFrontActive   = new Textures.BlockIcons.CustomIcon("machines/miner/front_active");
        mTextureFrontFinished = new Textures.BlockIcons.CustomIcon("machines/miner/front_finished");
        mTextureAutoInput     = new Textures.BlockIcons.CustomIcon("machines/miner/side_in");
    }

    public TileOreScanner(){
        super();
        int range = 2;
        mScanner = new OreScanner(range,xCoord,yCoord,zCoord,worldObj,true,true).setScanner(this);
    }
    @Override public ITexture getTexture2(Block aBlock, int aRenderPass, byte aSide, boolean[] aShouldSideBeRendered) {
        return aShouldSideBeRendered[aSide] ? BlockTextureMulti.get(BlockTextureDefault.get(mTextureMaterial, mRGBa), aSide==mFacing? BlockTextureDefault.get(mState==STATE_IDLE || mState == STATE_NOT_INIT? mTextureFront: mState==STATE_FINISHED? mTextureFrontFinished : mTextureFrontActive): aSide==SIDE_TOP ? BlockTextureDefault.get(mTextureAutoInput) : null) : null;
    }

    @Override
    public void onTick2(long aTimer, boolean aIsServerSide) {
        super.onTick2(aTimer, aIsServerSide);
        if(!aIsServerSide)return;
        if(mScanner!=null&& !mScanner.finished)mScanner.startOrContinueScanOres();
    }

    @Override
    public long onToolClick2(String aTool, long aRemainingDurability, long aQuality, Entity aPlayer, List<String> aChatReturn, IInventory aPlayerInventory, boolean aSneaking, ItemStack aStack, byte aSide, float aHitX, float aHitY, float aHitZ) {
        if(aTool.equals(TOOL_softhammer)){
            mScanner.resetScanOres();
        }
        return super.onToolClick2(aTool, aRemainingDurability, aQuality, aPlayer, aChatReturn, aPlayerInventory, aSneaking, aStack, aSide, aHitX, aHitY, aHitZ);
    }

    @Override
    public boolean canDrop(int aSlot) {
        return true;
    }

    @Override
    public String getTileEntityName() {
        return "ktfru.test.oreScanner";
    }

    @Override
    public void onOreFind(int x, int y, int z, short materialID, int type) {
        FMLLog.log(Level.FATAL,"find: "+x+" "+y+" "+z+" "+materialID+" "+type);
    }

    @Override
    public void onFinished() {

    }
}
