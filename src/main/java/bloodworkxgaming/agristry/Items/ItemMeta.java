package bloodworkxgaming.agristry.Items;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.*;


/**
 * Created by Jonas on 04.05.2017.
 */
public class ItemMeta extends ItemBase {

    private static final int INITIAL_REGISTERED_CAPACITY = EnumItemTypes.values().length;
    private static final Comparator<Map.Entry<Integer, ItemTypeWithVariant>> REGISTERED_COMPARATOR = new RegisteredComparator();

    public static ItemMeta instance;
    private final Map<Integer, ItemTypeWithVariant> registered;


    public ItemMeta(){
        super();
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.MATERIALS);

        this.registered = new HashMap<>(INITIAL_REGISTERED_CAPACITY);

        instance = this;
    }


    @Override
    public String getUnlocalizedName(final ItemStack stack) {

        return super.getUnlocalizedName() + "." + stack.getItemDamage();

    }


    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        final List<Map.Entry<Integer, ItemTypeWithVariant>> types = new ArrayList<>(this.registered.entrySet());
        Collections.sort(types, REGISTERED_COMPARATOR);

        for(final Map.Entry<Integer, ItemTypeWithVariant> item: types){
            subItems.add(new ItemStack(this, 1, item.getKey()));
        }
    }

    private static final class ItemTypeWithVariant{

        private final EnumItemTypes itemType;
        private final int variant;

        private ItemTypeWithVariant(final EnumItemTypes itemType, final int variant){
            assert itemType != null;
            assert variant >= 0;

            this.itemType = itemType;
            this.variant = variant;
        }

        @Override
        public String toString() {
            return "ItemTypeWithVariant{" + "item=" + this.itemType + ", variant=" + this.variant + '}';
        }
    }


    private static final class RegisteredComparator implements Comparator<Map.Entry<Integer, ItemTypeWithVariant>>
    {
        @Override
        public int compare(final Map.Entry<Integer, ItemTypeWithVariant> o1, final Map.Entry<Integer, ItemTypeWithVariant> o2 )
        {
            return o1.getValue().itemType.name().compareTo( o2.getValue().itemType.name() );
        }
    }

}












