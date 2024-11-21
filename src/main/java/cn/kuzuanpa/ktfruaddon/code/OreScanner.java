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


package cn.kuzuanpa.ktfruaddon.code;

import cn.kuzuanpa.ktfruaddon.client.render.FxRenderBlockOutline;
import cn.kuzuanpa.ktfruaddon.tile.machine.TileOreScanner;
import codechicken.lib.vec.BlockCoord;
import com.bioxx.tfc.Blocks.Terrain.BlockOre;
import com.bioxx.tfc.TileEntities.TEOre;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.TFCBlocks;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameData;
import gregapi.block.prefixblock.PrefixBlockTileEntity;
import gregapi.data.MD;
import gregapi.data.MT;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.UT;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OreScanner {
    public static final byte ORE_TYPE_GT_NORMAL=0,ORE_TYPE_GT_SMALL=1,ORE_TYPE_GT_BEDROCK=2,ORE_TYPE_GT_BEDROCK_SMALL=3,ORE_TYPE_TFC=4, ORE_TYPE_VANILLA=5;
    public int range, xPos, yPos, zPos, timerA,timerB, xChunkPos = 0, zChunkPos = 0;
    public final int xChunkStart, zChunkStart;
    public World world;
    public boolean includeSmallOre = false, finished = false, includeBedRockOre = true;
    public List<discoveredOres> discoveredOres = new ArrayList<>();
    protected IOreScanner theScanner = null;
    public OreScanner setScanner(IOreScanner theScanner) {
        this.theScanner = theScanner;
        return this;
    }
    public OreScanner(int range, int xPos, int yPos, int zPos, World world, boolean includeSmallOre) {
        this.range = range;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.world = world;
        this.timerA = 0;
        this.timerB=0;
        this.includeSmallOre = includeSmallOre;
        xChunkPos = xChunkStart = world.getChunkFromBlockCoords(xPos, zPos).xPosition - range;
        zChunkPos = zChunkStart = world.getChunkFromBlockCoords(xPos, zPos).zPosition - range;
        includeBedRockOre = true;
    }

    public OreScanner(int range, int xPos, int yPos, int zPos, World world, boolean includeSmallOre, boolean includeBedRockOre) {
        this.range = range;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.world = world;
        this.timerA = 0;
        this.timerB=0;
        this.includeSmallOre = includeSmallOre;
        xChunkPos = xChunkStart = world.getChunkFromBlockCoords(xPos, zPos).xPosition - range;
        zChunkPos = zChunkStart = world.getChunkFromBlockCoords(xPos, zPos).zPosition - range;
        this.includeBedRockOre = includeBedRockOre;
    }

    /**
     * @return is scanning finished.
     */
    public boolean startOrContinueScanOres() {
        if (finished) return true;
        if (timerA != 0 && timerA % yPos == 0) {
            xChunkPos++;
            if (xChunkPos > xChunkStart + range * 2) {
                xChunkPos = xChunkStart;
                zChunkPos++;
                if (zChunkPos > zChunkStart + range * 2) {
                    if(theScanner !=null) theScanner.onFinished();
                    finished = true;
                }
            }
        }
        scanOres();
        timerA++;
        return false;
    }

    public void resetScanOres() {
        clear();
        timerA = 0;
        timerB = 0;
    }

    protected boolean scanOres() {
        if (world == null) return false;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int xPos=xChunkPos * 16 + x;
                int yPos=timerA % this.yPos;
                int zPos=zChunkPos * 16 + z;
                Block block = world.getBlock(xPos,yPos,zPos);
                String name = GameData.getBlockRegistry().getNameForObject(block);
                if (name.startsWith("minecraft")) {
                    if(getVanillaOreMaterialID(name) > 0)addDiscoveredOres(getVanillaOreMaterialID(name), xPos,yPos,zPos, ORE_TYPE_VANILLA);
                    continue;
                }
                if (MD.TFC.mLoaded && isTFCOre(block,xPos,yPos,zPos)) {
                    int matID = getMaterialIDForTFCOre(world, block,xPos,yPos,zPos);
                    if(matID > 0)addDiscoveredOres(matID, xPos,yPos,zPos, ORE_TYPE_TFC);
                    continue;
                }
                if(!(name.startsWith("gregtech:")))continue;
                name = name.replaceFirst("gregtech:","");
                if (includeBedRockOre && name.startsWith("gt.meta.ore.normal.bedrock")) {
                    addDiscoveredOres(block.getDamageValue(world, xPos,yPos,zPos), xPos,yPos,zPos, ORE_TYPE_GT_BEDROCK);
                    continue;
                }
                if (includeBedRockOre &&name.startsWith("gt.meta.ore.small.bedrock")) {
                    addDiscoveredOres(block.getDamageValue(world, xPos,yPos,zPos), xPos,yPos,zPos, ORE_TYPE_GT_BEDROCK_SMALL);
                    continue;
                }
                if (name.startsWith("gt.meta.ore.normal")) {
                    addDiscoveredOres(block.getDamageValue(world, xPos,yPos,zPos), xPos,yPos,zPos, ORE_TYPE_GT_NORMAL);
                    continue;
                }
                if (includeSmallOre &&name.startsWith("gt.meta.ore.small")) {
                    addDiscoveredOres(block.getDamageValue(world, xPos,yPos,zPos), xPos,yPos,zPos, ORE_TYPE_GT_SMALL);
                    continue;
                }
            }
        }
        return true;
    }

    public boolean addDiscoveredOres(int materialID, int posX, int posY, int posZ, byte oreType) {
        if(materialID > 32767) FMLLog.log(Level.ERROR,"Scanned Ore with Materal ID > 32767 at"+posX+","+posY+","+posZ);
        return addDiscoveredOres((short) materialID, posX, posY, posZ, oreType);
    }
    public boolean addDiscoveredOres(short materialID, int posX, int posY, int posZ, byte oreType) {
        if(theScanner !=null) theScanner.onOreFind(posX,posY,posZ,materialID,oreType);
        discoveredOres.add(new discoveredOres(materialID, posX, posY, posZ,  oreType));
        return true;
        //FxRenderBlockOutline.addBlockOutlineToRender(new ChunkCoordinates(posX,posY,posZ), UT.Code.getRGBInt(OreDictMaterial.get(materialID).mRGBaSolid),1);
    }

    public List<discoveredOres> getDiscoveredOres() {
        return discoveredOres;
    }

    public void clear() {
        discoveredOres.clear();
    }

    public void rendOres(int thickness) {
        discoveredOres.forEach(ore -> FxRenderBlockOutline.addBlockOutlineToRender(new ChunkCoordinates(ore.posX, ore.posY, ore.posZ), UT.Code.getRGBInt(OreDictMaterial.get(ore.materialID).mRGBaSolid), thickness));
    }

    public void clearRendedOres(){
        discoveredOres.forEach(ore -> FxRenderBlockOutline.removeBlockOutlineToRender(new ChunkCoordinates(ore.posX, ore.posY, ore.posZ)));
    }
    public static byte[] serialize(OreScanner scanner) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeInt(scanner.range);
            dos.writeInt(scanner.xPos);
            dos.writeInt(scanner.yPos);
            dos.writeInt(scanner.zPos);
            dos.writeInt(scanner.timerA);
            dos.writeInt(scanner.timerB);
            dos.writeInt(scanner.xChunkPos);
            dos.writeInt(scanner.zChunkPos);
            dos.writeInt(scanner.world.provider.dimensionId);
            dos.writeBoolean(scanner.includeSmallOre);
            dos.writeBoolean(scanner.includeBedRockOre);
            dos.flush();
            return bos.toByteArray();
        }catch (Exception ignored){}
        return null;
    }
    public static OreScanner deserialize(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bis);
        Map<BlockCoord, TileOreScanner.OreData> list = new HashMap<>();
        try {
            int range = dis.readInt();
            int xPos  = dis.readInt();
            int yPos  = dis.readInt();
            int zPos  = dis.readInt();
            int timerA= dis.readInt();
            int timerB= dis.readInt();
            int world = dis.readInt();
            int xChunkPos = dis.readInt();
            int zChunkPos = dis.readInt();
            boolean includeSmallOre = dis.readBoolean();
            boolean includeBedRockOre = dis.readBoolean();
            OreScanner scanner = new OreScanner(range,xPos,yPos,zPos, DimensionManager.getWorld(world), includeSmallOre,includeBedRockOre);
            scanner.timerA=timerA;
            scanner.timerB=timerB;
            scanner.xChunkPos=xChunkPos;
            scanner.zChunkPos=zChunkPos;
            return scanner;
        } catch (IOException e) {
            return null;
        }
    }
    /**oreType: 0=normal,1=small,2=bedrock normal,3=bedrock small,4=TFCOre**/
    public static class discoveredOres {
    public discoveredOres(short materialID, int posX, int posY, int posZ,byte oreType){
        this.materialID=materialID;
        this.posX=posX;
        this.posY=posY;
        this.posZ=posZ;
        this.oreType=oreType;
    }
    public short materialID;
    public int posX;
    public int posY;
    public int posZ;
    public byte oreType;
    }
    public static short[] getOreColor(World world, int x,int y,int z){
        Block block = world.getBlock(x,y,z);
        String name = GameData.getBlockRegistry().getNameForObject(block);
        if(name == null) return new short[0];
        int matID = -1;
        if(name.startsWith("minecraft"))matID = getVanillaOreMaterialID(name);
        if(name.startsWith("terrafirmacraft")) matID = getMaterialIDForTFCOre(world,block,x,y,z);
        if(name.startsWith("gregtech")&&world.getTileEntity(x, y, z) instanceof PrefixBlockTileEntity)matID = OreDictMaterial.get(((PrefixBlockTileEntity) world.getTileEntity(x, y, z)).mMetaData).mID;
        OreDictMaterial oreDictMaterial = OreDictMaterial.get(matID);
        if(matID>0 && oreDictMaterial!=null)return oreDictMaterial.mRGBaSolid;
        return new short[0];
    }
    public static short getVanillaOreMaterialID(String name){
        switch (name){
            case "minecraft:iron_ore": return MT.OREMATS.BrownLimonite.mID;
            case "minecraft:gold_ore": return MT.Au.mID;
            case "minecraft:coal_ore": return MT.Coal.mID;
            case "minecraft:diamond_ore": return MT.Diamond.mID;
            case "minecraft:redstone_ore": return MT.Redstone.mID;
            case "minecraft:lapis_ore": return MT.Lapis.mID;
            case "minecraft:emerald_ore": return MT.Emerald.mID;
            case "minecraft:quartz_ore": return MT.NetherQuartz.mID;
        }
        return -1;
    }
    public boolean isTFCOre(Block block, int x, int y, int z) {
        return block!=null&&(block == TFCBlocks.ore || block == TFCBlocks.ore2 || block == TFCBlocks.ore3) && world.getTileEntity(x, y, z) instanceof TEOre;
    }
    public static short getMaterialIDForTFCOre(World world,Block block, int x, int y, int z) {
        TileEntity tile =  world.getTileEntity(x, y, z);
        if(!(tile instanceof TEOre))return -1;

        int meta=world.getBlockMetadata(x, y, z);
        if (block == TFCBlocks.ore)  meta = ((BlockOre) block).getOreGrade((TEOre)tile, meta);
        if (block == TFCBlocks.ore2) meta = meta + Global.ORE_METAL.length;
        if (block == TFCBlocks.ore3) meta = meta + Global.ORE_METAL.length + Global.ORE_MINERAL.length;
        switch (meta) {
            case 35:
            case 49:
            case 0: return MT.Cu.mID;
            case 36:
            case 50:
            case 1: return MT.Au.mID;
            case 37:
            case 51:
            case 2: return MT.Pt.mID;
            case 38:
            case 52:
            case 3: return MT.Fe2O3.mID;
            case 39:
            case 53:
            case 4: return MT.Ag.mID;
            case 40:
            case 54:
            case 5: return MT.OREMATS.Cassiterite.mID;
            case 41:
            case 55:
            case 6: return MT.OREMATS.Galena.mID;
            case 42:
            case 56:
            case 7: return MT.Bi.mID;
            case 43:
            case 57:
            case 8: return MT.OREMATS.Garnierite.mID;
            case 44:
            case 58:
            case 9: return MT.OREMATS.Malachite.mID;
            case 45:
            case 59:
            case 10:return MT.OREMATS.Magnetite.mID;
            case 46:
            case 60:
            case 11:return MT.OREMATS.YellowLimonite.mID;
            case 47:
            case 61:
            case 12:return MT.OREMATS.Sphalerite.mID;
            case 48:
            case 62:
            case 13:return MT.OREMATS.Tetrahedrite.mID;
            case 14:
            case 15:return MT.Coal.mID;
            case 16:return MT.Kaolinite.mID;
            case 17:return MT.Gypsum.mID;
            case 18:return MT.OREMATS.Trona.mID;
            case 19:return MT.OREMATS.Chromite.mID;
            case 20:return MT.Graphite.mID;
            case 21:return MT.Diamond.mID;
            case 22:return MT.PetrifiedWood.mID;
            case 23:return MT.S.mID;
            case 24:return MT.OREMATS.Stibnite.mID;
            case 25:return MT.MnO2.mID;
            case 26:return MT.OREMATS.Uraninite.mID;
            case 27:return MT.OREMATS.Cinnabar.mID;
            case 28:return MT.Cryolite.mID;
            case 29:return MT.Niter.mID;
            case 30:return MT.OREMATS.Malachite.mID;
            case 31:return MT.KCl.mID;
            case 32:return MT.OREMATS.Borax.mID;
            case 33:return MT.Olivine.mID;
            case 34:return MT.Lapis.mID;
            default:return 0;
        }
    }
}
