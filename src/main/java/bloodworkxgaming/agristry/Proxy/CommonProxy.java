package bloodworkxgaming.agristry.Proxy;


import bloodworkxgaming.agristry.ModBlocks;
import bloodworkxgaming.agristry.ModItems;

public abstract class CommonProxy {

    public void preInit() {
        ModBlocks.init();
        ModItems.init();
    }

    public void init() {

    }


    public void postInit() {

    }

    // leaving out isCreative and isDedicatedServer for now


}
