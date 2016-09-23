package net.atherialrunes.practiceserver.api.handler.handlers.item.tier;

import lombok.Data;
import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.GearType;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import net.atherialrunes.practiceserver.utils.RandomUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@Data
public abstract class TierItem {

    private String swordMinDamageRange;
    private String swordMaxDamageRange;
    private String axeMinDamageRange;
    private String axeMaxDamageRange;
    private String helmetHpRange;
    private String chestplateHpRange;
    private String leggingsHpRange;
    private String bootsHpRange;
    private String elementDamageRange;
    private String criticalHitRange;
    private Material swordType;
    private Material axeType;
    private Material helmetType;
    private Material chestplateType;
    private Material leggingType;
    private Material bootType;
    private short axeDurability;
    private short swordDurability;
    private short helmetDurability;
    private short chestplateDurability;
    private short leggingDurability;
    private short bootDurability;
    private int tierNumber;
    private Tier tier;

    public TierItem() {

    }

    public ItemStack build(GearType gearType) {
        Material type = null;
        short durability = 0;
        boolean weapon = false;
        int damageMin = 0;
        int damageMax = 0;
        int hp = 0;
        switch (gearType) {
            case HELMET:
                type = helmetType;
                durability = helmetDurability;
                hp = decideValue(helmetHpRange);
                break;
            case CHESTPLATE:
                type = chestplateType;
                durability = chestplateDurability;
                hp = decideValue(chestplateHpRange);
                break;
            case LEGGINGS:
                type = leggingType;
                durability = leggingDurability;
                hp = decideValue(leggingsHpRange);
                break;
            case BOOTS:
                type = bootType;
                durability = bootDurability;
                hp = decideValue(bootsHpRange);
                break;
            case SWORD:
                type = swordType;
                durability = swordDurability;
                weapon = true;
                damageMin = decideValue(swordMinDamageRange);
                damageMax = decideValue(swordMaxDamageRange);
                break;
            case AXE:
                type = axeType;
                durability = axeDurability;
                weapon = true;
                damageMin = decideValue(axeMinDamageRange);
                damageMax = decideValue(axeMaxDamageRange);
                break;
            default:
                break;
        }
        AtherialItem item = new AtherialItem(type);
        if (weapon) {
            item.addLore("&cDMG: " + damageMin + " - " + damageMax);
        } else {
            item.addLore("&cHP: +" + hp);
        }
        item.setDurability(durability);
        return item.build();
    }

    private int decideValue(String value) {
        return RandomUtils.random(Integer.parseInt(value.split("-")[0]), Integer.parseInt(value.split("-")[1]));
    }
}
