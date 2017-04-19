package bloodworkxgaming.agristry;

import bloodworkxgaming.agristry.Proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Agristry.MODID, version = Agristry.VERSION)

public class Agristry
{
    public static final String MODID = "agristry";
    public static final String VERSION = "0.1";

    @Mod.Instance
    public static Agristry instance;

    @SidedProxy(clientSide = "bloodworkxgaming.agristry.Proxy.ClientProxy", serverSide = "bloodworkxgaming.agristry.Proxy.ServerProxy")
    public static CommonProxy proxy;


    public static org.apache.logging.log4j.Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        proxy.postInit();
    }

}
