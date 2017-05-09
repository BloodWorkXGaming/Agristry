package bloodworkxgaming.agristry.Config;

import net.minecraftforge.common.config.Config;

/**
 * Created by Jonas on 09.05.2017.
 */
public class ConfigBlocks {

    @Config.Comment("Growth")
    GrowthPot growthPot = new GrowthPot();

    public static class GrowthPot{
        private static int GROWTHS_SPEED = 10;
        public static int FERTILIZER_PER_ITEM = 100;
        public static int FERTILIZER_MAX = 1000;
        public static int FERTILIZER_PER_GROWTH = 25;
    }
}
