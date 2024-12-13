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
package cn.kuzuanpa.ktfruaddon;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EnvironmentHelper {
    public static boolean isInTFRU = false, isGregtechTFRU = false, isAdvancedRocketryTFRU = false, isTFCTFRU = false, isBotaniaTFRU = false, isDraconicEvolutionTFRU = false, isForestryTFRU = false;

    public static String checkedTFRUVer = null, TFRUVer = "";
    public static void updateTFRUEnvironment(FMLPreInitializationEvent event){
        try {if(                         gregtech.TFRUEnvHelper.isModTFRU)isGregtechTFRU          = true;}catch (Throwable e){isGregtechTFRU          = false;}
        try {if(      zmaster587.advancedRocketry.TFRUEnvHelper.isModTFRU)isAdvancedRocketryTFRU  = true;}catch (Throwable e){isAdvancedRocketryTFRU  = false;}
        try {if(                com.bioxx.tfc.api.TFRUEnvHelper.isModTFRU)isTFCTFRU               = true;}catch (Throwable e){isTFCTFRU               = false;}
        try {if(            vazkii.botania.common.TFRUEnvHelper.isModTFRU)isBotaniaTFRU           = true;}catch (Throwable e){isBotaniaTFRU           = false;}
        try {if(com.brandon3055.draconicevolution.TFRUEnvHelper.isModTFRU)isDraconicEvolutionTFRU = true;}catch (Throwable e){isDraconicEvolutionTFRU = false;}
        try {if(                         forestry.TFRUEnvHelper.isModTFRU)isForestryTFRU          = true;}catch (Throwable e){isForestryTFRU          = false;}

        isInTFRU = isGregtechTFRU&&isAdvancedRocketryTFRU&&isTFCTFRU&&isBotaniaTFRU&&isDraconicEvolutionTFRU&&isForestryTFRU&&Loader.isModLoaded("tfc-mixin");

        if(!isInTFRU)return;
        try {                        gregtech.TFRUEnvHelper.isInTFRU=true;
                  zmaster587.advancedRocketry.TFRUEnvHelper.isInTFRU=true;
                            com.bioxx.tfc.api.TFRUEnvHelper.isInTFRU=true;
                        vazkii.botania.common.TFRUEnvHelper.isInTFRU=true;
            com.brandon3055.draconicevolution.TFRUEnvHelper.isInTFRU=true;
                                     forestry.TFRUEnvHelper.isInTFRU=true;
        }catch (Throwable e){
            FMLLog.log(Level.FATAL,e,"Error Occured when setting up TFRU pack environment! are you using right version of TFRU mods?");
            throw new IllegalArgumentException("Illegal TFRU Environment");
        }

        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        TFRUVer = config.getString("TFRUVer", "main", "0.0.0.0", "Version of TFRU Modpack");
        config.save();

        new Thread(new updateChecker()).start();
    }

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String MODDRINTH_URL = "https://api.modrinth.com/v2/project/o0CuW5i0/version";

    public static class updateChecker implements Runnable{
        @Override
        public void run() {
            try {
                URL obj = new URL(MODDRINTH_URL);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", USER_AGENT);

                int responseCode = con.getResponseCode();
                System.out.println("GET Response Code :: " + responseCode);

                if (responseCode != HttpURLConnection.HTTP_OK) return;

                //Success:
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                checkedTFRUVer = getVersionFromResponse(response.toString());
                if(!TFRUVer.equalsIgnoreCase(checkedTFRUVer))FMLLog.log(Level.ERROR, I18n.format("ktfru.msg.outdated"));
            }catch (Exception e) {}
        }
    }

    public static String getVersionFromResponse(String jsonResponse) {
        try {
            JsonArray jsonArray = new JsonParser().parse(jsonResponse).getAsJsonArray();
            if (jsonArray.size() <= 0) return null;
            JsonObject firstObject = jsonArray.get(0).getAsJsonObject();
            JsonElement versionNumberElement = firstObject.get("version_number");
            if (versionNumberElement != null && !versionNumberElement.isJsonNull()) return versionNumberElement.getAsString();
        }catch (Exception ignored){}
        return null;
    }
}
