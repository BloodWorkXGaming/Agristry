package bloodworkxgaming.agristry.Items;

import javax.annotation.Nullable;

/**
 * Created by Jonas on 04.05.2017.
 */
public enum EnumItemTypes {

    UpgradeSpeed(1, "upgrade.speed", "bla", null);


    private int baseMetaValue;
    private String unlocalizedName;


    EnumItemTypes(final int baseMetaValue, final String unlocalizedName, final String itemModel, @Nullable Class specialBehavior){
        this.baseMetaValue = baseMetaValue;
        this.unlocalizedName = unlocalizedName;
    }


    public int getBaseDamage() {
        return this.baseMetaValue;
    }

    public String getUnlocalizedName() {
        return unlocalizedName;
    }
}
