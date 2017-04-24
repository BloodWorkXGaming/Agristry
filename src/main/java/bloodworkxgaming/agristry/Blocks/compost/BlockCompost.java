package bloodworkxgaming.agristry.Blocks.compost;

import bloodworkxgaming.agristry.Agristry;
import mcjty.lib.compat.CompatBlock;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by Jonas on 23.04.2017.
 */
public class BlockCompost extends CompatBlock {


    public BlockCompost() {
        super(Material.ROCK);
        setRegistryName("compost");
        setUnlocalizedName(Agristry.MODID + ".compost");
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
    }


}
