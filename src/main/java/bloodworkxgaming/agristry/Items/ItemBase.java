package bloodworkxgaming.agristry.Items;

import mcjty.lib.compat.CompatItem;
import net.minecraft.item.ItemStack;


/**
 * Created by Jonas on 26.04.2017.
 */
public class ItemBase extends CompatItem {

    public ItemBase(){

        this.setNoRepair();

    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public String toString()
    {
        String regName = getRegistryName() != null ? getRegistryName().getResourcePath() : "unregistered";
        return getClass().getSimpleName() + "[" + regName  + "]";
    }


}
