package bloodworkxgaming.agristry.Items;

import bloodworkxgaming.agristry.Agristry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.logging.Logger;
// import net.minecraft.util.NonNullList;

/**
 * Created by Jonas on 26.04.2017.
 */

public class ItemMetaBase extends ItemBase {

    private static final String unlocalizedNameBase = "item.metaitem";

    public ItemMetaBase(){
        setHasSubtypes(true);
        this.setNoRepair();
        this.setMaxDamage(0);
        setRegistryName("metaitem");
        GameRegistry.register(this);
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        for (EnumItemTypes itemTypes: EnumItemTypes.values()){
            ModelLoader.setCustomModelResourceLocation(this, itemTypes.getMetadata(),new ModelResourceLocation(getRegistryName(), "inventory"));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int i = stack.getMetadata();
        return super.getUnlocalizedName() + "." + EnumItemTypes.byMetadata(i).getUnlocalizedName();
    }


    @Override
    @SideOnly(Side.CLIENT)
    protected void clGetSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (EnumItemTypes itemTypes: EnumItemTypes.values()){
            subItems.add(new ItemStack(itemIn, 1, itemTypes.getMetadata()));
        }
    }




}

