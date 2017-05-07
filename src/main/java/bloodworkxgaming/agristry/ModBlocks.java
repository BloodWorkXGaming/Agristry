package bloodworkxgaming.agristry;


import bloodworkxgaming.agristry.Blocks.BlockSoilBase;
import bloodworkxgaming.agristry.Blocks.greenhouse.BlockGreenhouseBasic;
import bloodworkxgaming.agristry.Blocks.growthpot.BlockGrowthPot;
import bloodworkxgaming.agristry.Blocks.growthpot.BlockGrowthPotHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static BlockSoilBase soilBase;
    public static BlockGrowthPot GrowthPot;
    public static BlockGrowthPotHelper GrowthPotHelper;
    public static BlockGreenhouseBasic GreenhouseBasic;

    public static void init(){
        soilBase = new BlockSoilBase();
        GrowthPot = new BlockGrowthPot();
        GrowthPotHelper = new BlockGrowthPotHelper();
        GreenhouseBasic = new BlockGreenhouseBasic();


    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        soilBase.initModel();
        GrowthPot.initModel();
        GrowthPotHelper.initModel();
        GreenhouseBasic.initModel();
    }



}
