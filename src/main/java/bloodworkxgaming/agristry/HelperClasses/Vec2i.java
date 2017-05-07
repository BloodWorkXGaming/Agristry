package bloodworkxgaming.agristry.HelperClasses;

/**
 * Created by Jonas on 27.04.2017.
 */

public class Vec2i{
    public int X;
    public int Y;

    public Vec2i(int x, int y){
        this.X = x;
        this.Y = y;
    }

    @Override
    public String toString() {
        return "["+ X + ", " + Y + "]";
    }


}