package bloodworkxgaming.agristry.Blocks.ItemSlots;

import mcjty.lib.tools.ItemStackTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * Created by Jonas on 26.04.2017.
 */
public class SlotOutput extends SlotItemHandler{

    public SlotOutput (IItemHandler itemHandler, int index, int xPosition, int yPosition){

        super(itemHandler, index, xPosition, yPosition);

    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        return true;
    }


}
