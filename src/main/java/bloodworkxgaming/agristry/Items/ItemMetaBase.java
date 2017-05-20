package bloodworkxgaming.agristry.Items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
// import net.minecraft.util.NonNullList;

/**
 * Created by Jonas on 26.04.2017.
 */
public class ItemMetaBase extends ItemBase {

        public ItemMetaBase(){
        setHasSubtypes(true);
        this.setNoRepair();
        this.setMaxDamage(0);
        setRegistryName("metaitem");
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0,new ModelResourceLocation(getRegistryName(), "inventory"));
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

