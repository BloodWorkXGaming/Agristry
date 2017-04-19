package bloodworkxgaming.agristry;


import bloodworkxgaming.agristry.Blocks.SoilBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    public static SoilBase soilBase;

    public static void init(){
    soilBase = new SoilBase();

    }

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        soilBase.initModel();

    }



}
