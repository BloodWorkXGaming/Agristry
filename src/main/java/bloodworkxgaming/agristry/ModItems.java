package bloodworkxgaming.agristry;

import bloodworkxgaming.agristry.Items.ItemMeta;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Jonas on 09.03.2017.
 */
public class ModItems {

    // public  static FirstItem firstItem;
    public static ItemMeta itemMeta;


    public static void init(){
        // init of all items
        // firstItem = new FirstItem();
        itemMeta = new ItemMeta();

    }


    @SideOnly(Side.CLIENT)
    public static void initModels(){
        // simpleTexturedItem.initModel();
    }
}
