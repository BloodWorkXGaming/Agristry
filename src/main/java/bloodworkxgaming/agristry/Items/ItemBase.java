package bloodworkxgaming.agristry.Items;

import bloodworkxgaming.agristry.Agristry;
import mcjty.lib.compat.CompatItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Jonas on 26.04.2017.
 */
public class ItemBase extends CompatItem {

    public ItemBase(String name){
        setRegistryName(name);
        setUnlocalizedName(Agristry.MODID + "." + name);
        GameRegistry.register(this);
        this.setNoRepair();


    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}
