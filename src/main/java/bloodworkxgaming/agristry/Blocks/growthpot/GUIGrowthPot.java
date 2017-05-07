package bloodworkxgaming.agristry.Blocks.growthpot;

import bloodworkxgaming.agristry.Agristry;
import bloodworkxgaming.agristry.HelperClasses.GuiBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jonas on 23.04.2017.
 */
public class GUIGrowthPot extends GuiBase{

    public static final int WIDTH = 180;
    public static final int HEIGHT = 178;

    private  static final ResourceLocation background = new ResourceLocation(Agristry.MODID, "textures/gui/growthpot_gui.png");
    private final TEGrowthPot te;

    public GUIGrowthPot(TEGrowthPot tileEntity, ContainerGrowthPot container){
        super(tileEntity, container);
        xSize = WIDTH;
        ySize = HEIGHT;
        te = tileEntity;
        TEXTURE = background;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        // Agristry.logger.info("x: " + x + " y: " + y);

        String deviceName = "Growth Pot";
        fontRenderer.drawString(deviceName, this.xSize / 2 - this.fontRenderer.getStringWidth(deviceName) / 2, 6, 4210752);

        //fuel
        if(mouseX >= 12+x && mouseX <= 24+x && mouseY >= 23+y && mouseY <= 61+y){
            String[] text = {this.te.getFertilizerAmount() + "/" + te.FERTILIZER_MAX + " Fertilizer"};
            List<String> tooltip = Arrays.asList(text);
            drawHoveringText(tooltip, mouseX - x, mouseY - y, fontRenderer);
        }


    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        // fertilizer Bar
        // Agristry.logger.info("fert: " + te.getFertilizerAmount());

        if (te.getFertilizerAmount() > 0){
            int k = this.getBarScaled(50, te.getFertilizerAmount(), TEGrowthPot.FERTILIZER_MAX);
            // Agristry.logger.info("k: " + k);

            drawRect(x+ 12, y + 23, 24, 61, 16777215);

        }


    }
}
