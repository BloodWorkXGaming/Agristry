package bloodworkxgaming.agristry.Blocks.growthpot;

import com.google.common.collect.ImmutableMap;
import javafx.beans.property.IntegerProperty;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Jonas on 20.04.2017.
 */
public class TEGrowthPot extends TileEntity implements ITickable{

    public static final int SIZE = 9;

    private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE){

        @Override
        protected void onContentsChanged(int slot) {
            TEGrowthPot.this.markDirty();
        }
    };


    int counter = 0;
    @Override
    public void update() {
        if (!world.isRemote) {
            if (counter >= 10) {
                counter = 0;
                ItemStack seeds = itemStackHandler.getStackInSlot(0);
                if (!ItemStackTools.isEmpty(seeds)) {
                    if (seeds.getItem() instanceof IPlantable) {
                        IBlockState plant = ((IPlantable) seeds.getItem()).getPlant(world, pos);
                        // System.out.println(plant.getBlock().getLocalizedName());
                        Block plantBlock = plant.getBlock();
                        if (plantBlock instanceof BlockCrops){
                                List<ItemStack> drops = plantBlock.getDrops(world, null, (((BlockCrops) plantBlock).withAge(((BlockCrops) plantBlock).getMaxAge())), 0);


                            // Add drops to the output
                            addOutputDrops(drops);
                        }
                    }
                }
            }
            counter++;
        }
    }

    public void addOutputDrops(List<ItemStack> drops){

        boolean removedSeed = false;
        for (int l = 0; l < drops.size(); l++) {

            // Output slots are 2-4
            for (int i = 2; i <= 4; i++){

                ItemStack potSlot = itemStackHandler.getStackInSlot(i);

                if (drops.get(l).getItem() == itemStackHandler.getStackInSlot(0).getItem() && !removedSeed){
                    System.out.println("Count before: " + drops.get(l).getCount());
                    drops.get(l).setCount(drops.get(l).getCount() - 1);
                    System.out.println("Count after: " + drops.get(l).getCount());
                    removedSeed = true;

                }

                if (!ItemStackTools.isEmpty(drops.get(l)) && (ItemStackTools.isEmpty(potSlot) || ( potSlot.getItem().equals(drops.get(l).getItem())) && potSlot.getCount() < potSlot.getItem().getItemStackLimit(potSlot))){
                    // System.out.println("Trying to output: " + drops.get(l).getItem().getUnlocalizedName() + " Count: " + drops.get(l).getCount());

                    if (potSlot.getCount() + drops.get(l).getCount() > potSlot.getItem().getItemStackLimit(potSlot)){
                        itemStackHandler.setStackInSlot(i, new ItemStack(drops.get(l).getItem(), potSlot.getItem().getItemStackLimit(potSlot)));
                        drops.get(l).setCount(potSlot.getItem().getItemStackLimit(potSlot) - potSlot.getCount());
                        continue;
                    }

                    itemStackHandler.setStackInSlot(i, new ItemStack(drops.get(l).getItem(), drops.get(l).getCount() + itemStackHandler.getStackInSlot(i).getCount()));
                    ItemStackTools.makeEmpty(drops.get(l));


                }

            }
        }

    }


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")){
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        return compound;
    }


    public boolean canInteractWith(EntityPlayer playerIn){
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5, 0.5, 0.5 )) <= 64;
    }


    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T) itemStackHandler;
        }
        return super.getCapability(capability, facing);
    }


}
