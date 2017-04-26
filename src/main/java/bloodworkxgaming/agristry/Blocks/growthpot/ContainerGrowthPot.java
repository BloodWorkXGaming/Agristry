package bloodworkxgaming.agristry.Blocks.growthpot;

import bloodworkxgaming.agristry.Blocks.ItemSlots.SlotOutput;
import bloodworkxgaming.agristry.Blocks.ItemSlots.SlotSeeds;
import mcjty.lib.compat.CompatItemTool;
import mcjty.lib.tools.ItemStackTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;
import java.awt.*;

/**
 * Created by Jonas on 23.04.2017.
 */
public class ContainerGrowthPot extends net.minecraft.inventory.Container {

    private TEGrowthPot te;

    public ContainerGrowthPot(IInventory playerInventory, TEGrowthPot te){
        this.te = te;


        addPlayerSlots(playerInventory);
        addOwnSlots();
    }


    private void addPlayerSlots(IInventory playerInventory){

        // main inv
        for (int row = 0; row < 3; ++row){
            for (int col = 0; col < 9; ++col){
                int x = 10 + col*18;
                int y = 96 + row * 18;
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, x, y));
            }
        }

        // hot bar
        for (int row = 0; row < 9; ++row){
            int x = 10 + row * 18;
            int y = 154;
            this.addSlotToContainer(new Slot(playerInventory, row, x, y));
        }
    }

    private void addOwnSlots(){
        IItemHandler itemHandler = this.te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        int slotIndex = 0;

        // Main Slot / Seed slot (0)
        addSlotToContainer(new SlotSeeds(itemHandler, slotIndex, 82, 32));
        slotIndex++;

        // Fuel / RFSlot (1)
        addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, 10, 68));
        slotIndex++;

        // Output slots (2-4)
        for (int i = 0; i < 3; i++){
            addSlotToContainer(new SlotOutput(itemHandler, slotIndex, 64 + i * 18, 68));
            slotIndex++;
        }

        // Upgrades (5 - 8)
        for (int i = 0; i < 4; i++){
            addSlotToContainer(new SlotItemHandler(itemHandler, slotIndex, 154, 14 + i * 18));
            slotIndex++;
        }
    }

    


    @Nullable
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {

        ItemStack previous = ItemStackTools.getEmptyStack();

        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            previous = current.copy();

            if (index < TEGrowthPot.SIZE) {
                if (!this.mergeItemStack(current, TEGrowthPot.SIZE, this.inventorySlots.size(), true)) {
                    return ItemStackTools.getEmptyStack();
                }
            } else if (!this.mergeItemStack(current, 0, TEGrowthPot.SIZE, false)) {
                return ItemStackTools.getEmptyStack();
            }

            if (ItemStackTools.isEmpty(current) || current.getCount() == 0) {
                slot.putStack(ItemStackTools.getEmptyStack());
            } else {
                slot.onSlotChanged();
            }
        }

        return previous;
    }


    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return te.canInteractWith(playerIn);
    }
}
