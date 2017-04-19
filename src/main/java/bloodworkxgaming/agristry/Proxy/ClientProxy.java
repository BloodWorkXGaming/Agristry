package bloodworkxgaming.agristry.Proxy;


import bloodworkxgaming.agristry.Agristry;
import bloodworkxgaming.agristry.ModBlocks;
import bloodworkxgaming.agristry.ModItems;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy {

    @SideOnly(Side.CLIENT)
    public void preInit() {
        super.preInit();

        OBJLoader.INSTANCE.addDomain(Agristry.MODID);

        ModBlocks.initModels();
        ModItems.initModels();
    }

    public void init() {
        super.init();
    }


    public void postInit() {
        super.postInit();
    }

}
