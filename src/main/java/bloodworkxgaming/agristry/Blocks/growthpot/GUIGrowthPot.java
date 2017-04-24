package bloodworkxgaming.agristry.Blocks.growthpot;

import bloodworkxgaming.agristry.Agristry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Jonas on 23.04.2017.
 */
public class GUIGrowthPot extends GuiContainer{

    public static final int WIDTH = 180;
    public static final int HEIGHT = 178;

    private  static final ResourceLocation background = new ResourceLocation(Agristry.MODID, "textures/gui/growthpot_gui.png");

    public GUIGrowthPot(TEGrowthPot tileEntity, ContainerGrowthPot container){
        super(container);
        xSize = WIDTH;
        ySize = HEIGHT;
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
