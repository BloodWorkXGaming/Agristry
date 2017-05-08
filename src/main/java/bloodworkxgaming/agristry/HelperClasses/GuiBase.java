package bloodworkxgaming.agristry.HelperClasses;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Jonas on 06.05.2017.
 */
public abstract class GuiBase extends GuiContainer{

    protected static ResourceLocation TEXTURE = new ResourceLocation("");

    public GuiBase(TileEntity tile, Container container) {
        super(container);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, this.xSize, this.ySize);
    }

    protected int getBarScaled(int pixels, int count, int max) {
        return count > 0 && max > 0 ? count * pixels / max : 0;
    }
}
