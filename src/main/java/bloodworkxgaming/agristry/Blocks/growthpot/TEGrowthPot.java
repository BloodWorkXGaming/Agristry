package bloodworkxgaming.agristry.Blocks.growthpot;

import bloodworkxgaming.agristry.Config.MainConfig;
import bloodworkxgaming.agristry.HelperClasses.GenericTileEntity;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonas on 20.04.2017.
 */
public class TEGrowthPot extends GenericTileEntity implements ITickable, ICapabilityProvider{


    public static final int SIZE = 9;

    // Data Vars
    // Seed: 0, BoneMeal: 1
    private ItemStackHandler itemStackHandlerInput = new ItemStackHandler(2){

        @Override
        protected void onContentsChanged(int slot) {
            TEGrowthPot.this.markDirty();
        }

        @Nonnull
        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStackTools.getEmptyStack();
        }
    };

    private ItemStackHandler itemStackHandlerOutput = new ItemStackHandler(3){

        @Override
        protected void onContentsChanged(int slot) {
            TEGrowthPot.this.markDirty();
        }

        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            return stack;
        }
    };

    private ItemStackHandler itemStackHandlerUpgrades = new ItemStackHandler(4){

        @Override
        protected void onContentsChanged(int slot) {
            TEGrowthPot.this.markDirty();
        }
    };


    private int fertilizerAmount = 0;



    public List<ItemStack> getItemsInInventory(){
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < itemStackHandlerInput.getSlots(); i++) {
            items.add(itemStackHandlerInput.getStackInSlot(i));
        }
        for (int i = 0; i < itemStackHandlerOutput.getSlots(); i++) {
            items.add(itemStackHandlerOutput.getStackInSlot(i));
        }
        for (int i = 0; i < itemStackHandlerUpgrades.getSlots(); i++) {
            items.add(itemStackHandlerUpgrades.getStackInSlot(i));
        }

        return items;
    }

    ItemStackHandler getItemStackHandlerInput() {
        return itemStackHandlerInput;
    }

    ItemStackHandler getItemStackHandlerOutput() {
        return itemStackHandlerOutput;
    }

    ItemStackHandler getItemStackHandlerUpgrades() {
        return itemStackHandlerUpgrades;
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
                ItemStack seeds = itemStackHandlerInput.getStackInSlot(0);
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
            if (fertilizerAmount <= MainConfig.Blocks.growthPot.FERTILIZER_MAX - MainConfig.Blocks.growthPot.FERTILIZER_PER_ITEM && ItemStackTools.getStackSize(itemStackHandlerInput.getStackInSlot(1)) > 0) {
                // TODO: Fix when adding own type of fertilizer/compost
                ItemStack fertilizer =  itemStackHandlerInput.getStackInSlot(1);
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
        for (int i = 0; i < itemStackHandlerOutput.getSlots(); i++) {
            ItemStack item = itemStackHandlerOutput.getStackInSlot(i);
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
            for (int i = 0; i < itemStackHandlerOutput.getSlots(); i++) {

                ItemStack potSlot = itemStackHandlerOutput.getStackInSlot(i);

                if (drop.getItem() == itemStackHandlerInput.getStackInSlot(0).getItem() && !removedSeed) {
                    drop.splitStack(1);
                    removedSeed = true;

                }

                if (!ItemStackTools.isEmpty(drop) && (ItemStackTools.isEmpty(potSlot) || (potSlot.getItem().equals(drop.getItem())) && ItemStackTools.getStackSize(potSlot) < potSlot.getItem().getItemStackLimit(potSlot))) {
                    // System.out.println("Trying to output: " + drops.get(l).getItem().getUnlocalizedName() + " Count: " + drops.get(l).getCount());

                    if (ItemStackTools.getStackSize(potSlot) + ItemStackTools.getStackSize(drop) > potSlot.getItem().getItemStackLimit(potSlot)) {
                        itemStackHandlerOutput.setStackInSlot(i, new ItemStack(drop.getItem(), potSlot.getItem().getItemStackLimit(potSlot)));
                        ItemStackTools.setStackSize(drop, potSlot.getItem().getItemStackLimit(potSlot) - ItemStackTools.getStackSize(potSlot));
                        continue;
                    }

                    itemStackHandlerOutput.setStackInSlot(i, new ItemStack(drop.getItem(), ItemStackTools.getStackSize(drop) + ItemStackTools.getStackSize(itemStackHandlerOutput.getStackInSlot(i))));
                    ItemStackTools.makeEmpty(drop);


                }

            }
        }

    }


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("itemsIn")){
            itemStackHandlerInput.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
            itemStackHandlerOutput.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
            itemStackHandlerUpgrades.deserializeNBT((NBTTagCompound) compound.getTag("itemsUp"));


        }
        fertilizerAmount = compound.getInteger("fertilizer");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsIn", itemStackHandlerInput.serializeNBT());
        compound.setTag("itemsOut", itemStackHandlerOutput.serializeNBT());
        compound.setTag("itemsUp", itemStackHandlerUpgrades.serializeNBT());

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
            if (facing == EnumFacing.SOUTH || facing == EnumFacing.NORTH || facing == EnumFacing.WEST || facing == EnumFacing.EAST){
                return (T) itemStackHandlerInput;
            }
            return (T) itemStackHandlerOutput;
        }
        return super.getCapability(capability, facing);
    }


    public int getFertilizerAmount(){
        return fertilizerAmount;
    }


}

// >> https://github.com/al132/ATM-Rockhounding/blob/master/src/main/java/al132/atmrockhounding/tile/TileMachine.java
// >> https://mcforge.readthedocs.io/en/latest/datastorage/capabilities/