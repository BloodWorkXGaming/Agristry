package bloodworkxgaming.agristry.Blocks.growthpot;

import bloodworkxgaming.agristry.HelperClasses.GenericTileEntity;
import com.google.common.collect.ImmutableMap;
import javafx.beans.property.IntegerProperty;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonas on 20.04.2017.
 */
public class TEGrowthPot extends GenericTileEntity implements ITickable{

    public static final int SIZE = 9;

    // [CONFIG]
    private static final int GROWTHS_SPEED = 10;
    public static final int FERTILIZER_PER_ITEM = 100;
    public static final int FERTILIZER_MAX = 1000;
    public static final int FERTILIZER_PER_GROWTH = 25;

    // Data Vars
    private ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE){

        @Override
        protected void onContentsChanged(int slot) {
            TEGrowthPot.this.markDirty();
        }
    };
    private int fertilizerAmount = 0;



    public List<ItemStack> getItemsInInventory(){
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            items.add(itemStackHandler.getStackInSlot(i));
        }

        return items;
    }

    private int counter = 0;

    @Override
    public void update() {
        if (!world.isRemote) {

            /*
            // remove fertilizer every second
            if (counter % 20 == 0 && fertilizerAmount > (FERTILIZER_PER_GROWTH / GROWTHS_SPEED) * 20){
                fertilizerAmount -= (FERTILIZER_PER_GROWTH / GROWTHS_SPEED) * 20;
                markDirtyClient();
            } */


            // calculation of the plant growing
            if (counter % GROWTHS_SPEED == 0 && checkCanWork()) {
                ItemStack seeds = itemStackHandler.getStackInSlot(0);
                if (ItemStackTools.isValid(seeds)) {
                    if (seeds.getItem() instanceof IPlantable) {
                        IBlockState plant = ((IPlantable) seeds.getItem()).getPlant(world, pos);
                        // System.out.println(plant.getBlock().getLocalizedName());
                        Block plantBlock = plant.getBlock();
                        if (plantBlock instanceof BlockCrops){

                            List<ItemStack> drops = plantBlock.getDrops(world, null, (((BlockCrops) plantBlock).withAge(((BlockCrops) plantBlock).getMaxAge())), 0);
                            // Add drops to the output
                            addOutputDrops(drops);
                            fertilizerAmount -= FERTILIZER_PER_GROWTH;
                            markDirtyClient();
                        }
                    }
                }
            }

            // loading the fertilizer in the slot
            if (fertilizerAmount <= FERTILIZER_MAX - FERTILIZER_PER_ITEM && itemStackHandler.getStackInSlot(1).getCount() > 0) {
                // TODO: Fix when adding own type of fertilizer/compost
                ItemStack fertilizer =  itemStackHandler.getStackInSlot(1);
                if (fertilizer.getItem() == Items.DYE && fertilizer.getItemDamage() == 15){
                    fertilizer.setCount(fertilizer.getCount() - 1);
                    fertilizerAmount += FERTILIZER_PER_ITEM;
                    markDirtyClient();
                }
            }


            counter++;
        }
    }

    private boolean checkCanWork(){
        if (fertilizerAmount < FERTILIZER_PER_GROWTH) return false;
        // Output slots are 2-4
        boolean slotHasSpace = false;
        for (int i = 2; i <= 4; i++) {
            ItemStack item = itemStackHandler.getStackInSlot(i);
            if (item.getCount() < item.getItem().getItemStackLimit(item)){
                slotHasSpace = true;
            }
        }
        return slotHasSpace;
    }

    private void addOutputDrops(List<ItemStack> drops){

        boolean removedSeed = false;
        for (ItemStack drop : drops) {

            // Output slots are 2-4
            for (int i = 2; i <= 4; i++) {

                ItemStack potSlot = itemStackHandler.getStackInSlot(i);

                if (drop.getItem() == itemStackHandler.getStackInSlot(0).getItem() && !removedSeed) {
                    drop.setCount(drop.getCount() - 1);
                    removedSeed = true;

                }

                if (!ItemStackTools.isEmpty(drop) && (ItemStackTools.isEmpty(potSlot) || (potSlot.getItem().equals(drop.getItem())) && potSlot.getCount() < potSlot.getItem().getItemStackLimit(potSlot))) {
                    // System.out.println("Trying to output: " + drops.get(l).getItem().getUnlocalizedName() + " Count: " + drops.get(l).getCount());

                    if (potSlot.getCount() + drop.getCount() > potSlot.getItem().getItemStackLimit(potSlot)) {
                        itemStackHandler.setStackInSlot(i, new ItemStack(drop.getItem(), potSlot.getItem().getItemStackLimit(potSlot)));
                        drop.setCount(potSlot.getItem().getItemStackLimit(potSlot) - potSlot.getCount());
                        continue;
                    }

                    itemStackHandler.setStackInSlot(i, new ItemStack(drop.getItem(), drop.getCount() + itemStackHandler.getStackInSlot(i).getCount()));
                    ItemStackTools.makeEmpty(drop);


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
        fertilizerAmount = compound.getInteger("fertilizer");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        compound.setInteger("fertilizer", fertilizerAmount);

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

    public int getFertilizerAmount(){
        return fertilizerAmount;
    }

}
