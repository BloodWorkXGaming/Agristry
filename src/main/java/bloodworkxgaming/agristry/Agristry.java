package bloodworkxgaming.agristry;

import bloodworkxgaming.agristry.Proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@Mod(modid = Agristry.MODID, version = Agristry.VERSION)

public class Agristry
{
    public static final String MODID = "agristry";
    public static final String VERSION = "0.1.0";

    @Mod.Instance
    public static Agristry instance;

    @SidedProxy(clientSide = "bloodworkxgaming.agristry.Proxy.ClientProxy", serverSide = "bloodworkxgaming.agristry.Proxy.ServerProxy")
    public static CommonProxy proxy;


    public static final Logger logger = LogManager.getLogger(MODID);

    public Agristry(){
        logger.info("Hello Minecraft, nice to meet you.");
    }



    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
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


    @NetworkCheckHandler
    public boolean matchModVersion(Map<String, String> remoteVersions, Side side){
        // we don't accept clients without TiC
        if(side == Side.CLIENT) {
            return remoteVersions.containsKey(MODID);
        }
        // but we can connect to servers without TiC when TiC is present on the client
        return !remoteVersions.containsKey(MODID) || VERSION.equals(remoteVersions.get(MODID));

        //thanks SlimeKnigths
    }

}
