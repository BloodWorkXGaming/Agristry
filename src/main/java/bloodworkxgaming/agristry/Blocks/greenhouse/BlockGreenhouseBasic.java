package bloodworkxgaming.agristry.Blocks.greenhouse;

import bloodworkxgaming.agristry.Agristry;
import bloodworkxgaming.agristry.Blocks.growthpot.TEGrowthPot;
import mcjty.lib.compat.CompatBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by Jonas on 20.04.2017.
 */
public class BlockGreenhouseBasic extends CompatBlock implements ITileEntityProvider{

    public BlockGreenhouseBasic(){
        super(Material.ROCK);
        setRegistryName("greenhouse_basic");
        setUnlocalizedName(Agristry.MODID + ".greenhouse_basic");
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TEGreenhouseBasic();
    }

    private TEGreenhouseBasic getTE(IBlockAccess world, BlockPos pos){
        return (TEGreenhouseBasic) world.getTileEntity(pos);
    }


}
