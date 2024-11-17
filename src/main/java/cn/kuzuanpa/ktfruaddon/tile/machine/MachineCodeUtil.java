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



package cn.kuzuanpa.ktfruaddon.tile.machine;

import cn.kuzuanpa.ktfruaddon.code.CodeTranslate;
import cn.kuzuanpa.ktfruaddon.code.OreScanner;
import cpw.mods.fml.common.FMLLog;
import gregapi.old.Textures;
import gregapi.render.IIconContainer;
import gregapi.tileentity.machines.MultiTileEntityBasicMachine;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.Level;

import static gregapi.data.CS.PX_P;

public class MachineCodeUtil extends MultiTileEntityBasicMachine {
    public OreScanner oreVeinScanner;
    public void readFromNBT2(NBTTagCompound aNBT) {
        super.readFromNBT2(aNBT);
        oreVeinScanner = new OreScanner(0,xCoord,yCoord,zCoord, worldObj,true,true);
    }
@Override
public boolean onBlockActivated3(EntityPlayer aPlayer, byte aSide, float aHitX, float aHitY, float aHitZ) {
    if (isServerSide()) {
        openGUI(aPlayer, aSide);
        try {
            for (int i=0;i<this.ACCESSIBLE_SLOTS.length;i++) FMLLog.log(Level.FATAL,""+ CodeTranslate.itemToCode(slot(i)));
           // FMLLog.log(Level.FATAL,worldObj.getChunkFromChunkCoords(-28, 43).getBlock(5, 5,0).toString());
        }catch (Throwable ignored) {}
    }
    if(aPlayer.isSneaking()) oreVeinScanner.clearRendedOres();
    return false;
}
    public void onTick2(long aTimer, boolean isServerside){
        //if(isServerside)oreVeinScanner.startOrContinueUpdateGTOre((WorldServer)worldObj);
        //if(!isServerside&&worldObj!=null)oreVeinScanner.startOrContinueScanOres();
        //if(!isServerSide()&&aTimer%100==0)oreVeinScanner.rendOres(-10);

    }
    public boolean setBlockBounds2(Block aBlock, int aRenderPass, boolean[] aShouldSideBeRendered) {
        switch (aRenderPass) {
            case 0:
                return box(aBlock, PX_P[0] + 0.001, PX_P[0], PX_P[0] + 0.001, PX_P[16] - 0.001, PX_P[5], PX_P[16] - 0.001);
            case 1:
                return box(aBlock, PX_P[0] + 0.0001, PX_P[6], PX_P[0], PX_P[16], PX_P[16], PX_P[16]);
        }
        return true;
    }
    @Override
    public String getTileEntityName() {
        return "ktfru.multitileentity.machine.code";
    }

    @Override
    public int getRenderPasses2(Block aBlock, boolean[] aShouldSideBeRendered) {
        return 2;
    }

    public static IIconContainer
            sTextureCommon= new Textures.BlockIcons.CustomIcon("machines/cruciblemodel/common"),
            sTextureCommosn= new Textures.BlockIcons.CustomIcon("machines/cruciblemodel/commsson");
}
