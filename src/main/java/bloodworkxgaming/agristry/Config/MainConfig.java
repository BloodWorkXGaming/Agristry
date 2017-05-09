package bloodworkxgaming.agristry.Config;

import bloodworkxgaming.agristry.Agristry;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Jonas on 09.05.2017.
 */

@Config(modid = Agristry.MODID)
public class MainConfig {

    @Config.Comment("Client-sided Settings, none so far")
    public static Client Client = new Client();

    @Config.Comment("Block related Settings")
    public static ConfigBlocks Blocks = new ConfigBlocks();


    private static class Client{
        @Config.Comment("TestConfig")
        public int blub = 200;
    }


    public static class ConfigBlocks {

        @Config.Comment("GrowthPot Settings")
        public GrowthPot growthPot = new GrowthPot();

        @Config.Comment("SoilBase Settings")
        public SoilBase soilBase = new SoilBase();

        public class GrowthPot{
            @Config.Comment({"Ticks to wait between growth", "[range: 1-2147483647]"})
            public int GROWTHS_SPEED = 10;
            @Config.Comment({"Fertilizer Value per Item inserted", "[range: 0-2147483647]"})
            public int FERTILIZER_PER_ITEM = 100;
            @Config.Comment({"Max Fertilizer Amount that fits in the growthpot", "[range: 1-2147483647]"})
            public int FERTILIZER_MAX = 1000;
            @Config.Comment({"Fertilizer Value per Crop grown inserted", "[range: 0-2147483647]"})
            public int FERTILIZER_PER_GROWTH = 25;
        }

        public class SoilBase{
            @Config.Comment({"Change to grow on tick: 1/value", "[range: 1-2147483647]"})
            public int TICK_CHANCE = 30;

            @Config.Comment({"Wait time between ticks, lower is better", "[range: 0-2147483647]"})
            public int TICK_RATE = 10;
        }

    }


    @Mod.EventBusSubscriber
    static class ConfigurationHolder {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event){
            if (event.getModID().equals(Agristry.MODID)){
                ConfigManager.load(Agristry.MODID, Config.Type.INSTANCE);
            }
        }
    }
}

// <https://github.com/Choonster-Minecraft-Mods/TestMod3/blob/b3d71dfddf7f212f0d86ef36e6ae1d06b8493ebc/src/main/java/choonster/testmod3/config/ModConfig.java>