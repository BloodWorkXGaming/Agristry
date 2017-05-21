package bloodworkxgaming.agristry.Items;

import bloodworkxgaming.agristry.Agristry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Jonas on 20.05.2017.
 */
public class DebugItem extends ItemBase {

    public DebugItem(){
        setRegistryName("debugitem");
        setUnlocalizedName(Agristry.MODID + "." + getRegistryName());
        GameRegistry.register(this);
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(this, 0,new ModelResourceLocation(getRegistryName(), "inventory"));
    }


    @Override
    protected EnumActionResult clOnItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = world.getTileEntity(pos);
        if (te != null){
            Agristry.logger.info(te.serializeNBT().toString());
        }

        return EnumActionResult.SUCCESS;
    }
}
