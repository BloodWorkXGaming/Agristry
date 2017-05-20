package bloodworkxgaming.agristry.JEI;

import bloodworkxgaming.agristry.ModBlocks;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.minecraft.item.ItemStack;

/**
 * Created by Jonas on 23.04.2017.
 */

@JEIPlugin
public class JeiAgristryPlugin extends BlankModPlugin{

    public static IJeiHelpers jeiHelpers;


    @Override
    public void register(IModRegistry registry) {
        jeiHelpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();


        registry.addDescription(new ItemStack(ModBlocks.soilBase), "jei.Agristry.desc.BlockSoilBase");

    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        super.onRuntimeAvailable(jeiRuntime);

    }
}
