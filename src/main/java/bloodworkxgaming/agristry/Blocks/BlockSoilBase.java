package bloodworkxgaming.agristry.Blocks;

import bloodworkxgaming.agristry.Agristry;
import bloodworkxgaming.agristry.Config.MainConfig;
import bloodworkxgaming.agristry.ModBlocks;
import mcjty.lib.compat.CompatBlock;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Jonas on 18.04.2017.
 */
public class BlockSoilBase extends CompatBlock{

    public static PropertyInteger GLOWSTONE = PropertyInteger.create("glowstone", 0, 15);

    AxisAlignedBB Hitbox = new AxisAlignedBB(0, 0, 0, 1, 0.9375, 1);
    public BlockSoilBase(){
        super(Material.GROUND);
        setRegistryName("soilbase");
        setUnlocalizedName(Agristry.MODID + ".soilbase");
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setTickRandomly(true);
        setHardness(1);
        setHarvestLevel("shovel", 0);
        setSoundType(SoundType.GROUND);
        setLightOpacity(255);

    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    public void initCrafting(){
        GameRegistry.addRecipe(new ItemStack(ModBlocks.soilBase),
                ".F.",
                "FDF",
                ".F.", 'D', Blocks.DIRT, 'F', new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage())
        );
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
        EnumPlantType plantType = plantable.getPlantType(world, pos);
        return plantType == EnumPlantType.Crop || plantType == EnumPlantType.Plains;
    }


    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        // System.out.println("Ayy mate, I got ticked at pos: " + pos);

        Block blockUp = worldIn.getBlockState(pos.up()).getBlock();
        if ( blockUp instanceof IGrowable && rand.nextInt(MainConfig.Blocks.soilBase.TICK_CHANCE) == 0){
            ((IGrowable)blockUp).grow(worldIn, rand, pos.up(), worldIn.getBlockState(pos.up()));
        }
    }

    @Override
    public int tickRate(World worldIn) {
        return MainConfig.Blocks.soilBase.TICK_RATE;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        switch (side)
        {
            case UP:
                return true;
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
                Block block = iblockstate.getBlock();
                return !iblockstate.isOpaqueCube() && block != Blocks.FARMLAND && block != Blocks.GRASS_PATH &&  block != ModBlocks.soilBase;
            default:
                return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return Hitbox;
    }


    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return Hitbox;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {

        return getGlowstoneAmount(state);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(GLOWSTONE, meta & 15);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        int output = 0;
        output = state.getValue(GLOWSTONE) | output;
        return output;
    }

    public int getGlowstoneAmount(IBlockState state){
        return state.getValue(GLOWSTONE);
    }

    public void setGlowstoneAmount(World worldIn, BlockPos pos, IBlockState state, int amount){
        worldIn.setBlockState(pos, state.withProperty(GLOWSTONE, amount));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, GLOWSTONE);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (playerIn.getHeldItemMainhand().getItem() == Items.GLOWSTONE_DUST && getGlowstoneAmount(state) < 15 && !playerIn.isSneaking()){
            if (!playerIn.isCreative()) playerIn.getHeldItemMainhand().setCount(playerIn.getHeldItemMainhand().getCount() - 1);
            setGlowstoneAmount(worldIn, pos, state, getGlowstoneAmount(state) + 1);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> drops =  new ArrayList<>();
        drops.add(new ItemStack(Items.GLOWSTONE_DUST, getGlowstoneAmount(state)));
        drops.add(new ItemStack(ModBlocks.soilBase, 1));

        return drops;
    }
}
