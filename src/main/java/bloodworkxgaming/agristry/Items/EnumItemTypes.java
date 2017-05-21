package bloodworkxgaming.agristry.Items;



import io.netty.util.collection.IntObjectHashMap;

import javax.annotation.Nullable;


/**
 * Created by Jonas on 04.05.2017.
 */
public enum EnumItemTypes {

    Default(0, "default", "bla", null),
    UpgradeSpeed(1, "upgrade.speed", "bla", null),
    UpgradeEfficiency(2, "upgrade.efficiency", "bla", null),
    UpgradeEnergy(3, "upgrade.energy", "bla", null);



    // private static final EnumItemTypes[] META_LOOKUP = new EnumItemTypes[values().length];
    private static final IntObjectHashMap<EnumItemTypes> META_LOOKUP = new IntObjectHashMap<>(values().length);
    private int metaValue;
    private String unlocalizedName;


    EnumItemTypes(final int metaValue, final String unlocalizedName, final String itemModel, @Nullable Class specialBehavior){
        this.metaValue = metaValue;
        this.unlocalizedName = unlocalizedName;
    }


    public int getMetadata() {
        return this.metaValue;
    }

    public static EnumItemTypes byMetadata(int meta)
    {
        if (meta < 0 || meta >= values()[values().length - 1].getMetadata())
        {
            meta = 0;
        }

        return META_LOOKUP.get(meta);
    }


    public String getUnlocalizedName() {
        return unlocalizedName;
    }



    static
    {
        for (EnumItemTypes enumItemTypes : values())
        {
            META_LOOKUP.put(enumItemTypes.getMetadata(), enumItemTypes);
            // META_LOOKUP[enumItemTypes.getMetadata()] = enumItemTypes;
        }
    }

}
