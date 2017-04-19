package bloodworkxgaming.agristry;


import bloodworkxgaming.agristry.Blocks.SoilBase;
import bloodworkxgaming.agristry.Blocks.growthpot.BlockGrowthPot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static SoilBase soilBase;
    public static BlockGrowthPot blockGrowthPot;

    public static void init(){
        soilBase = new SoilBase();
        blockGrowthPot = new BlockGrowthPot();

    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        soilBase.initModel();
        blockGrowthPot.initModel();

    }



}
