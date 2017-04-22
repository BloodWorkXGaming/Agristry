package bloodworkxgaming.agristry;


import bloodworkxgaming.agristry.Blocks.BlockSoilBase;
import bloodworkxgaming.agristry.Blocks.growthpot.BlockGrowthPot;
import bloodworkxgaming.agristry.Blocks.growthpot.BlockGrowthPotHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static BlockSoilBase soilBase;
    public static BlockGrowthPot blockGrowthPot;
    public static BlockGrowthPotHelper blockGrowthPotHelper;

    public static void init(){
        soilBase = new BlockSoilBase();
        blockGrowthPot = new BlockGrowthPot();
        blockGrowthPotHelper = new BlockGrowthPotHelper();


    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        soilBase.initModel();
        blockGrowthPot.initModel();
        blockGrowthPotHelper.initModel();
    }



}
