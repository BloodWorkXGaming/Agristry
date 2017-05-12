package bloodworkxgaming.agristry.Blocks.growthpot;

import bloodworkxgaming.agristry.Agristry;
import bloodworkxgaming.agristry.Config.MainConfig;
import bloodworkxgaming.agristry.HelperClasses.GenericTileEntity;
import com.google.common.collect.ImmutableMap;
import javafx.beans.property.IntegerProperty;
import mcjty.lib.tools.ItemStackTools;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
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
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonas on 20.04.2017.
 */
public class TEGrowthPot extends GenericTileEntity implements ITickable, ISidedInventory{

    public static final int SIZE = 9;

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
            if (counter % MainConfig.Blocks.growthPot.GROWTHS_SPEED  == 0 && checkCanWork()) {
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
                            fertilizerAmount -= MainConfig.Blocks.growthPot.FERTILIZER_PER_GROWTH;
                            markDirtyClient();
                        }
                    }
                }
            }

            // loading the fertilizer in the slot
            if (fertilizerAmount <= MainConfig.Blocks.growthPot.FERTILIZER_MAX - MainConfig.Blocks.growthPot.FERTILIZER_PER_ITEM && ItemStackTools.getStackSize(itemStackHandler.getStackInSlot(1)) > 0) {
                // TODO: Fix when adding own type of fertilizer/compost
                ItemStack fertilizer =  itemStackHandler.getStackInSlot(1);
                if (fertilizer.getItem() == Items.DYE && fertilizer.getItemDamage() == 15){
                    fertilizer.splitStack(1);
                    fertilizerAmount += MainConfig.Blocks.growthPot.FERTILIZER_PER_ITEM;
                    markDirtyClient();
                }
            }


            counter++;
        }
    }

    private boolean checkCanWork(){
        if (fertilizerAmount < MainConfig.Blocks.growthPot.FERTILIZER_PER_GROWTH) return false;
        // Output slots are 2-4
        boolean slotHasSpace = false;
        for (int i = 2; i <= 4; i++) {
            ItemStack item = itemStackHandler.getStackInSlot(i);
            if (ItemStackTools.getStackSize(item) < item.getItem().getItemStackLimit(item)){
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
                    drop.splitStack(1);
                    removedSeed = true;

                }

                if (!ItemStackTools.isEmpty(drop) && (ItemStackTools.isEmpty(potSlot) || (potSlot.getItem().equals(drop.getItem())) && ItemStackTools.getStackSize(potSlot) < potSlot.getItem().getItemStackLimit(potSlot))) {
                    // System.out.println("Trying to output: " + drops.get(l).getItem().getUnlocalizedName() + " Count: " + drops.get(l).getCount());

                    if (ItemStackTools.getStackSize(potSlot) + ItemStackTools.getStackSize(drop) > potSlot.getItem().getItemStackLimit(potSlot)) {
                        itemStackHandler.setStackInSlot(i, new ItemStack(drop.getItem(), potSlot.getItem().getItemStackLimit(potSlot)));
                        ItemStackTools.setStackSize(drop, potSlot.getItem().getItemStackLimit(potSlot) - ItemStackTools.getStackSize(potSlot));
                        continue;
                    }

                    itemStackHandler.setStackInSlot(i, new ItemStack(drop.getItem(), ItemStackTools.getStackSize(drop) + ItemStackTools.getStackSize(itemStackHandler.getStackInSlot(i))));
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

    // Inventory
    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return itemStackHandler.getStackInSlot(index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        itemStackHandler.setStackInSlot(index, stack);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack s = itemStackHandler.getStackInSlot(index);
        itemStackHandler.setStackInSlot(index, ItemStackTools.getEmptyStack());
        return s;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        Agristry.logger.info(player.getDisplayName() + ": Opened the inventory at " + this.getPos());
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return (stack.getItem() == Items.DYE && stack.getItemDamage() == 15) && (index == 1);
    }


    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return index >= 2 && index <= 4;
    }

    @Override
    public int getSizeInventory() {
        return SIZE;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[]{2, 3, 4};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return (itemStackIn.getItem() == Items.DYE && itemStackIn.getItemDamage() == 15) && (index == 1);
    }

    // @Override
    public boolean isEmpty() {
        return ItemStackTools.isEmpty(itemStackHandler.getStackInSlot(2)) && ItemStackTools.isEmpty(itemStackHandler.getStackInSlot(3)) && ItemStackTools.isEmpty(itemStackHandler.getStackInSlot(4));
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        itemStackHandler.getStackInSlot(index).splitStack(count);
        return itemStackHandler.getStackInSlot(index);
    }

    @Override
    public int getInventoryStackLimit() {
        //this might break stuff
        return 0;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return false;
    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public void clear() {

    }

    @Override
    public String getName() {
        return getDisplayName().toString();
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }
}
