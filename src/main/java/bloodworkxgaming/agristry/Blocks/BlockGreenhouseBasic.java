package bloodworkxgaming.agristry.Blocks;

import bloodworkxgaming.agristry.Agristry;
import mcjty.lib.compat.CompatBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Jonas on 20.04.2017.
 */
public class BlockGreenhouseBasic extends CompatBlock{

    public BlockGreenhouseBasic(){
        super(Material.ROCK);
        setRegistryName("greenhouse_basic");
        setUnlocalizedName(Agristry.MODID + ".greenhouse_basic");
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
    }



}
