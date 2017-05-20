package bloodworkxgaming.agristry.Blocks.growthpot;

import bloodworkxgaming.agristry.Agristry;
import bloodworkxgaming.agristry.ModBlocks;
import mcjty.lib.compat.CompatBlock;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Jonas on 20.04.2017.
 */
public class BlockGrowthPot extends CompatBlock implements ITileEntityProvider{


    public static final int GUI_ID = 1;

    public static AxisAlignedBB boundBox = new AxisAlignedBB(0, 0, 0, 0.9375, 1.96, 0.9375).offset(0.03125, 0, 0.03125);
    public static AxisAlignedBB hitBox = new AxisAlignedBB(0, 0, 0, 0.9375, 1, 0.9375).offset(0.03125, 0, 0.03125);

    public BlockGrowthPot(){
        super(Material.ROCK);
        setRegistryName("growthpot");
        setUnlocalizedName(Agristry.MODID + ".growthpot");
        GameRegistry.register(this);
        GameRegistry.register(new ItemBlock(this), getRegistryName());
        GameRegistry.registerTileEntity(TEGrowthPot.class, "agristry_growthpot");
        setHardness(2);

    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TEGrowthPot();
    }

    private TEGrowthPot getTE(IBlockAccess world, BlockPos pos){
        return (TEGrowthPot) world.getTileEntity(pos);
    }

    //region >> Bounding Boxes
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return boundBox;
    }

    @Override
    protected void clAddCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list, Entity entity) {
        addCollisionBoxToList(pos, entityBox, list, hitBox);
    }
    //endregion

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.setBlockState(pos.up(), ModBlocks.GrowthPotHelper.getDefaultState());
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        for (ItemStack stack: getTE(worldIn, pos).getItemsInInventory()) {
            if (!ItemStackTools.isEmpty(stack) && ItemStackTools.isValid(stack)){
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
            }
        }

        IBlockState helperBlock = worldIn.getBlockState(pos.up());
        if (helperBlock.getBlock() instanceof BlockGrowthPotHelper){
            worldIn.destroyBlock(pos.up(), false);
        }

        super.breakBlock(worldIn, pos, state);

    }

    @Override
    public boolean clOnBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) return true;

        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TEGrowthPot)){
            return false;
        }
        playerIn.openGui(Agristry.instance, GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return worldIn.isAirBlock(pos.up());
    }

    //region >> Rendering options for the Block
    @Override
    public boolean isFullyOpaque(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }
    //endregion


}

