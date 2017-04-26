package bloodworkxgaming.agristry.Blocks.growthpot;

import bloodworkxgaming.agristry.Agristry;
import bloodworkxgaming.agristry.ModBlocks;
import mcjty.lib.compat.CompatBlock;
import mcjty.lib.jei.JeiCompatTools;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Created by Jonas on 20.04.2017.
 */
public class BlockGrowthPotHelper extends CompatBlock {

    public static AxisAlignedBB boundBox = new AxisAlignedBB(0, -1, 0, 0.9375, 0.96, 0.9375).offset(0.03125, 0, 0.03125);
    public static AxisAlignedBB hitBox = new AxisAlignedBB(0, 0, 0, 0.9375, 0.96, 0.9375).offset(0.03125, 0, 0.03125);



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

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ModBlocks.blockGrowthPot);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return boundBox;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return hitBox;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        return ModBlocks.blockGrowthPot.onBlockActivated(worldIn, pos.down(), worldIn.getBlockState(pos.down()), playerIn, hand, side, hitX, hitY +1, hitZ );
    }
}
