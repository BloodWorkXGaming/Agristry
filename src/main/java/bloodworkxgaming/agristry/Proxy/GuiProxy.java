package bloodworkxgaming.agristry.Proxy;

import bloodworkxgaming.agristry.Blocks.growthpot.ContainerGrowthPot;
import bloodworkxgaming.agristry.Blocks.growthpot.GUIGrowthPot;
import bloodworkxgaming.agristry.Blocks.growthpot.TEGrowthPot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by Jonas on 23.04.2017.
 */
public class GuiProxy implements IGuiHandler{
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y ,z);
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof TEGrowthPot){
            return new ContainerGrowthPot(player.inventory, (TEGrowthPot) te);
        }
        return null;
    }

    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);

        if (te instanceof TEGrowthPot){
            TEGrowthPot containerTileEntity = (TEGrowthPot) te;
            return new GUIGrowthPot(containerTileEntity, new ContainerGrowthPot(player.inventory, containerTileEntity));
        }

        return null;
    }
}
