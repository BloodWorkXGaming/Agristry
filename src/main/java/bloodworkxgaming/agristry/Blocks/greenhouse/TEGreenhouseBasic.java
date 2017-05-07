package bloodworkxgaming.agristry.Blocks.greenhouse;

import bloodworkxgaming.agristry.HelperClasses.Vec2i;
import net.minecraft.block.BlockCrops;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Created by Jonas on 26.04.2017.
 */

public class TEGreenhouseBasic extends TileEntity implements ITickable{

    public static Vec3i dimensionLimit = new Vec3i(10, 5, 10);


    private int counter = 0;
    @Override
    public void update() {
        counter++;
        if (counter >= 40){
            counter = 0;

            checkMultiblock();

        }
    }


    private boolean checkMultiblock(){




    return true;
    }

    private Vec2i calculateMultiblockWidthX(){
        // check - direction
        Vec2i distance = new Vec2i(0, 0);
        while (true){
            if (world.isAirBlock(pos.add(distance.X, 0, 0)) || world.getBlockState(pos.add(distance.X, 0, 0)).getBlock() instanceof BlockCrops){
                distance.X++;
                break;
            } else if (OreDictionary.getOres("log").contains(new ItemStack( world.getBlockState(pos.add(distance.X, 0, 0)).getBlock() )) ) {

            }
        }

        return distance;
    }

}
