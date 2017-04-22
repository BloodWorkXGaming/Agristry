package bloodworkxgaming.agristry.Blocks.growthpot;

import bloodworkxgaming.agristry.Agristry;
import mcjty.lib.compat.CompatBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * Created by Jonas on 20.04.2017.
 */
public class BlockGrowthPotHelper extends CompatBlock {

    public BlockGrowthPotHelper(){
        super(Material.ROCK);
        setRegistryName("helperblock_growthpot");
        setUnlocalizedName(Agristry.MODID + ".helperblock_growthpot");
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        setLightOpacity(0);
        setHardness(2);
    }


    //region >> Rendering stuff to make it invisible
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullyOpaque(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    //endregion


    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        IBlockState mainBlock = worldIn.getBlockState(pos.down());
        if (mainBlock.getBlock() instanceof BlockGrowthPot){
            worldIn.destroyBlock(pos.down(), true);
        }
    }

    @Override
    public boolean hasCustomBreakingProgress(IBlockState state) {
        return true;
    }


}
