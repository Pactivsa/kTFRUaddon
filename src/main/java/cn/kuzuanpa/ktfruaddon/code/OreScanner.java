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
import com.bioxx.tfc.TileEntities.TEOre;
import com.bioxx.tfc.api.TFCBlocks;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameData;
import gregapi.block.multitileentity.MultiTileEntityRegistry;
import gregapi.block.prefixblock.PrefixBlockTileEntity;
import gregapi.data.FL;
import gregapi.data.MD;
import gregapi.data.MT;
import gregapi.oredict.OreDictMaterial;
import gregapi.util.UT;
import gregapi.util.WD;
import gregtech.tileentity.misc.MultiTileEntityFluidSpring;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OreScanner {
    public static final byte ORE_TYPE_GT_NORMAL=0,ORE_TYPE_GT_SMALL=1,ORE_TYPE_GT_BEDROCK=2,ORE_TYPE_GT_BEDROCK_SMALL=3,ORE_TYPE_TFC=4, ORE_TYPE_VANILLA=5, ORE_TYPE_FLUID_SPRING=6;
    public int range, xPos, yPos, zPos, yPointer, xChunkPos, zChunkPos;
    public final int xChunkStart, zChunkStart;
    public short speed = 1;
    public World world;
    public final boolean includeSmallOre, includeBedRockOre, includeFluidSpring;
    public boolean finished = false;
    public List<discoveredOres> discoveredOres = new ArrayList<>();
    protected IOreScanner theScanner = null;
    final static MultiTileEntityRegistry gRegistry = MultiTileEntityRegistry.getRegistry("gt.multitileentity");
    public OreScanner setScanner(IOreScanner theScanner) {
        this.theScanner = theScanner;
        return this;
    }

    public OreScanner(int range, int xPos, int yPos, int zPos, World world) {
        this(range, xPos, yPos,zPos,world,false, false, false);
    }
    public OreScanner(int range, int xPos, int yPos, int zPos, World world, boolean includeSmallOre) {
        this(range, xPos, yPos,zPos,world,includeSmallOre, false, false);

    }

    public OreScanner(int range, int xPos, int yPos, int zPos, World world, boolean includeSmallOre, boolean includeBedRockOre) {
        this(range, xPos, yPos,zPos,world,includeSmallOre, includeBedRockOre, false);
    }

    public OreScanner(int range, int xPos, int yPos, int zPos, World world, boolean includeSmallOre, boolean includeBedRockOre, boolean includeFluidSpring) {
        this.range = range;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        this.world = world;
        this.yPointer =0;
        this.includeSmallOre = includeSmallOre;
        this.includeBedRockOre = includeBedRockOre;
        this.includeFluidSpring = includeFluidSpring;
        xChunkPos = xChunkStart =  (xPos >> 4)- range;
        zChunkPos = zChunkStart =  (zPos >> 4)- range;
    }

    public OreScanner setSpeed(short speed) {
        this.speed = speed;
        return this;
    }

    /**
     * @return is scanning finished.
     */
    public boolean startOrContinueScanOres() {
        if (finished) return true;

        if (yPointer > yPos) {
            yPointer = 0;
            xChunkPos++;
            if (xChunkPos > xChunkStart + range * 2) {
                xChunkPos = xChunkStart;
                zChunkPos++;
                if (zChunkPos > zChunkStart + range * 2) {
                    if(theScanner !=null) theScanner.onFinished();
                    finished = true;
                    return true;
                }
            }
        }

        scanOres();
        return false;
    }

    public void resetScanOres() {
        clear();
        yPointer = 0;
    }

    protected boolean scanOres() {
        if (world == null) return false;
        for (int y = 0; y < speed; y++){
            for (int x = 0; x < 16; x++) for (int z = 0; z < 16; z++)  {
                int xPos=xChunkPos * 16 + x;
                int yPos= yPointer;
                int zPos=zChunkPos * 16 + z;
                Block block = world.getBlock(xPos,yPos,zPos);
                String name = GameData.getBlockRegistry().getNameForObject(block);
                if (name.startsWith("minecraft")) {
                    if(getVanillaOreMaterialID(name) > 0)addDiscoveredOres(getVanillaOreMaterialID(name), xPos,yPos,zPos, ORE_TYPE_VANILLA);
                    continue;
                }
                if (MD.TFC.mLoaded && isTFCOre(block)) {
                    int matID = getMaterialIDForTFCOre(world,xPos,yPos,zPos);
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
                if (includeFluidSpring && WD.te(world,new ChunkCoordinates(xPos,yPos,zPos),false) instanceof MultiTileEntityFluidSpring){
                    short fakeMatID = getFakeFluidSpringMaterialID(WD.te(world,new ChunkCoordinates(xPos,yPos,zPos),false));
                    if(fakeMatID > 0)addDiscoveredOres(fakeMatID,xPos,yPos,zPos,ORE_TYPE_FLUID_SPRING);
                    continue;
                }


            }
            yPointer++;
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
            dos.writeInt(scanner.yPointer);
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
            int timerB= dis.readInt();
            int xChunkPos = dis.readInt();
            int zChunkPos = dis.readInt();
            int world = dis.readInt();
            boolean includeSmallOre = dis.readBoolean();
            boolean includeBedRockOre = dis.readBoolean();
            if(DimensionManager.getWorld(world) == null)return null;
            OreScanner scanner = new OreScanner(range,xPos,yPos,zPos, DimensionManager.getWorld(world), includeSmallOre,includeBedRockOre);
            scanner.yPointer =timerB;
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
        if(name.startsWith("terrafirmacraft")) matID = getMaterialIDForTFCOre(world,x,y,z);
        if(name.startsWith("gregtech")&&world.getTileEntity(x, y, z) instanceof PrefixBlockTileEntity)matID = OreDictMaterial.get(((PrefixBlockTileEntity) world.getTileEntity(x, y, z)).mMetaData).mID;
        OreDictMaterial oreDictMaterial = OreDictMaterial.get(matID);
        if(matID>0 && oreDictMaterial!=null)return oreDictMaterial.mRGBaSolid;
        return new short[0];
    }
    public static short getFakeFluidSpringMaterialID(TileEntity tile){
        if (!(tile instanceof MultiTileEntityFluidSpring))return -1;
        FluidStack fluid = ((MultiTileEntityFluidSpring) tile).mFluid;
        if(FL.Oil_ExtraHeavy.is(fluid))return MT.Oil.mID;
        if(FL.Oil_Heavy.is(fluid))return MT.Oil.mID;
        if(FL.Oil_Medium.is(fluid))return MT.Oil.mID;
        if(FL.Oil_Light.is(fluid))return MT.Oil.mID;
        if(FL.Water_Geothermal.is(fluid))return MT.Water.mID;
        if(FL.Gas_Natural.is(fluid))return MT.MethaneIce.mID;
        if(FL.Lava.is(fluid))return MT.Lava.mID;
        return MT.U_235.mID;
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

    public boolean isTFCOre(Block block) {
        return block!=null&& block == TFCBlocks.ore;
    }
    public static short getMaterialIDForTFCOre(World world, int x, int y, int z) {
        TileEntity tile =  world.getTileEntity(x, y, z);

        int meta = tile instanceof TEOre ? ((TEOre) tile).droppedOreID: -1;

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
