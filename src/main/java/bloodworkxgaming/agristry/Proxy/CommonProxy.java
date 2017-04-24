package bloodworkxgaming.agristry.Proxy;


import bloodworkxgaming.agristry.Agristry;
import bloodworkxgaming.agristry.Crafting.ModCrafting;
import bloodworkxgaming.agristry.ModBlocks;
import bloodworkxgaming.agristry.ModItems;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public abstract class CommonProxy {

    public void preInit() {
        ModBlocks.init();
        ModItems.init();
    }

    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Agristry.instance, new GuiProxy());
        ModCrafting.init();

    }


    public void postInit() {

    }

    // leaving out isCreative and isDedicatedServer for now


}
