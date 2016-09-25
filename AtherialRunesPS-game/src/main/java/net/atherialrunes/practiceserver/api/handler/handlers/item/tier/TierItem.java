package net.atherialrunes.practiceserver.api.handler.handlers.item.tier;

import lombok.Data;
import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.GearType;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import net.atherialrunes.practiceserver.utils.RandomUtils;
import net.atherialrunes.practiceserver.utils.StatUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Matthew E on 9/22/2016.
 */
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
    private String bootName;
    private String chestplateName;
    private String leggingName;
    private String helmetName;
    private String axeName;
    private String swordName;
    private String reflectRange;
    private String blockRange;
    private String dodgeRange;
    private String energyRange;
    private String hpsRange;
    private String vitStatRange;
    private String strStatRange;


    public TierItem() {

    }

    public ItemStack rerollStats(ItemStack item) {
        return build(item, StatUtils.getMinDamage(item), StatUtils.getMaxDamage(item));
}

    public ItemStack build(GearType gearType) {
        Material type = null;
        short durability = 0;
        boolean weapon = false;
        int damageMin = 0;
        int damageMax = 0;
        int hp = 0;
        String name = "";
        switch (gearType) {
            case HELMET:
                type = helmetType;
                durability = helmetDurability;
                hp = decideValue(helmetHpRange);
                name = getHelmetName();
                break;
            case CHESTPLATE:
                type = chestplateType;
                durability = chestplateDurability;
                hp = decideValue(chestplateHpRange);
                name = getChestplateName();
                break;
            case LEGGINGS:
                type = leggingType;
                durability = leggingDurability;
                hp = decideValue(leggingsHpRange);
                name = getLeggingName();
                break;
            case BOOTS:
                type = bootType;
                durability = bootDurability;
                hp = decideValue(bootsHpRange);
                name = getBootName();
                break;
            case SWORD:
                type = swordType;
                durability = swordDurability;
                weapon = true;
                damageMin = decideValue(swordMinDamageRange);
                damageMax = decideValue(swordMaxDamageRange);
                name = getSwordName();
                break;
            case AXE:
                type = axeType;
                durability = axeDurability;
                weapon = true;
                damageMin = decideValue(axeMinDamageRange);
                damageMax = decideValue(axeMaxDamageRange);
                name = getAxeName();
                break;
            default:
                break;
        }
        AtherialItem item = new AtherialItem(type);
        if (weapon) {
            if (damageMin > damageMax) {
                damageMin = (damageMax - 5);
            }
            item.addLore("&cDMG: " + damageMin + " - " + damageMax);
            if (RandomUtils.random(1, 10) == 2) {
                item.addLore("&cCRITICAL HIT: " + decideValue(getCriticalHitRange()) + "%");
                name = "Critical " + name;
            }
            if (RandomUtils.random(1, 8) == 2) {
                int elementType = RandomUtils.random(1, 6);
                boolean ice = false;
                boolean fire = false;
                boolean poison = false;
                boolean pure = false;
                switch (elementType) {
                    case 1:
                        ice = true;
                        break;
                    case 2:
                        fire = true;
                        break;
                    case 3:
                        poison = true;
                        break;
                    case 4:
                        poison = true;
                        pure = true;
                        break;
                    case 5:
                        ice = true;
                        pure = true;
                        break;
                    case 6:
                        fire = true;
                        pure = true;
                        break;
                    default:
                        break;

                }
                if (ice) {
                    item.addLore("&cICE DMG: +" + decideValue(getElementDamageRange()));
                    name = name + " of Ice";
                }
                if (fire) {
                    item.addLore("&cFIRE DMG: +" + decideValue(getElementDamageRange()));
                    name = name + " of Fire";
                }
                if (poison) {
                    item.addLore("&cPOISON DMG: +" + decideValue(getElementDamageRange()));
                    name = name + " of Poison";
                }
                if (pure) {
                    item.addLore("&cPURE DMG: +" + decideValue(getElementDamageRange()));
                    name = "Pure " + name;
                }
            }
        } else {
            item.addLore("&cHP: +" + hp);
            boolean energy = false;
            if (RandomUtils.random(1, 2) == 2) {
                energy = true;
            }
            if (energy) {
                item.addLore("&cENERGY REGEN: +" + decideValue(getEnergyRange()) + "%");
                name = "Energetic " + name;
            } else {
                item.addLore("&cHP REGEN: +" + decideValue(getHpsRange()) + " HP/s");
                name = "Mending " + name;
            }
            if (RandomUtils.random(1, 3) == 2) {
                boolean vit = false;
                if (RandomUtils.random(1, 2) == 2) {
                    vit = true;
                }
                if (vit) {
                    item.addLore("&cVIT: +" + decideValue(getVitStatRange()));
                    name = name + " of Fortune";
                } else {
                    item.addLore("&cSTR: +" + decideValue(getStrStatRange()));
                    name = name + " of Strength";
                }
            }
            if (RandomUtils.random(1, 3) == 2) {
                int i = RandomUtils.random(1, 3);
                boolean block = false;
                boolean reflect = false;
                boolean dodge = false;
                switch (i) {
                    case 1:
                        block = true;
                        break;
                    case 2:
                        reflect = true;
                        break;
                    case 3:
                        dodge = true;
                        break;
                    default:
                        break;
                }
                if (block) {
                    item.addLore("&cBLOCK: " + decideValue(getBlockRange()) + "%");
                    name = "Blocking " + name;
                } else if (reflect) {
                    item.addLore("&cREFLECT: " + decideValue(getReflectRange()) + "%");
                    name = "Reflecting " + name;
                } else if (dodge) {
                    item.addLore("&cDODGE: " + decideValue(getDodgeRange()) + "%");
                    name = "Dodging " + name;
                }
            }
        }
        name = getTier().getColor() + name;
        item.setName(name);
        item.setDurability(durability);
        item.addItemFlag(ItemFlag.HIDE_ATTRIBUTES);
        return item.build();
    }

    public ItemStack build(ItemStack itemstack, int damageMin, int damageMax) {
        Material type = null;
        short durability = 0;
        boolean weapon = false;
        int hp = 0;
        String name = "";
        GearType gearType = GearType.getGearType(itemstack);
        switch (gearType) {
            case HELMET:
                type = helmetType;
                durability = helmetDurability;
                hp = decideValue(helmetHpRange);
                name = getHelmetName();
                break;
            case CHESTPLATE:
                type = chestplateType;
                durability = chestplateDurability;
                hp = decideValue(chestplateHpRange);
                name = getChestplateName();
                break;
            case LEGGINGS:
                type = leggingType;
                durability = leggingDurability;
                hp = decideValue(leggingsHpRange);
                name = getLeggingName();
                break;
            case BOOTS:
                type = bootType;
                durability = bootDurability;
                hp = decideValue(bootsHpRange);
                name = getBootName();
                break;
            case SWORD:
                type = swordType;
                durability = swordDurability;
                weapon = true;
                name = getSwordName();
                break;
            case AXE:
                type = axeType;
                durability = axeDurability;
                weapon = true;
                name = getAxeName();
                break;
            default:
                break;
        }
        AtherialItem item = new AtherialItem(type);
        if (weapon) {
            if (damageMin > damageMax) {
                damageMin = (damageMax - 5);
            }
            item.addLore("&cDMG: " + damageMin + " - " + damageMax);
            if (RandomUtils.random(1, 10) == 2) {
                item.addLore("&cCRITICAL HIT: " + decideValue(getCriticalHitRange()) + "%");
                name = "Critical " + name;
            }
            if (RandomUtils.random(1, 8) == 2) {
                int elementType = RandomUtils.random(1, 6);
                boolean ice = false;
                boolean fire = false;
                boolean poison = false;
                boolean pure = false;
                switch (elementType) {
                    case 1:
                        ice = true;
                        break;
                    case 2:
                        fire = true;
                        break;
                    case 3:
                        poison = true;
                        break;
                    case 4:
                        poison = true;
                        pure = true;
                        break;
                    case 5:
                        ice = true;
                        pure = true;
                        break;
                    case 6:
                        fire = true;
                        pure = true;
                        break;
                    default:
                        break;

                }
                if (ice) {
                    item.addLore("&cICE DMG: +" + decideValue(getElementDamageRange()));
                    name = name + " of Ice";
                }
                if (fire) {
                    item.addLore("&cFIRE DMG: +" + decideValue(getElementDamageRange()));
                    name = name + " of Fire";
                }
                if (poison) {
                    item.addLore("&cPOISON DMG: +" + decideValue(getElementDamageRange()));
                    name = name + " of Poison";
                }
                if (pure) {
                    item.addLore("&cPURE DMG: +" + decideValue(getElementDamageRange()));
                    name = "Pure " + name;
                }
            }
        } else {
            item.addLore("&cHP: +" + hp);
            boolean energy = false;
            if (RandomUtils.random(1, 2) == 2) {
                energy = true;
            }
            if (energy) {
                item.addLore("&cENERGY REGEN: +" + decideValue(getEnergyRange()) + "%");
                name = "Energetic " + name;
            } else {
                item.addLore("&cHP REGEN: +" + decideValue(getHpsRange()) + " HP/s");
                name = "Mending " + name;
            }
            if (RandomUtils.random(1, 3) == 2) {
                boolean vit = false;
                if (RandomUtils.random(1, 2) == 2) {
                    vit = true;
                }
                if (vit) {
                    item.addLore("&cVIT: +" + decideValue(getVitStatRange()));
                    name = name + " of Fortune";
                } else {
                    item.addLore("&cSTR: +" + decideValue(getStrStatRange()));
                    name = name + " of Strength";
                }
            }
            if (RandomUtils.random(1, 3) == 2) {
                int i = RandomUtils.random(1, 3);
                boolean block = false;
                boolean reflect = false;
                boolean dodge = false;
                switch (i) {
                    case 1:
                        block = true;
                        break;
                    case 2:
                        reflect = true;
                        break;
                    case 3:
                        dodge = true;
                        break;
                    default:
                        break;
                }
                if (block) {
                    item.addLore("&cBLOCK: " + decideValue(getBlockRange()) + "%");
                    name = "Blocking " + name;
                } else if (reflect) {
                    item.addLore("&cREFLECT: " + decideValue(getReflectRange()) + "%");
                    name = "Reflecting " + name;
                } else if (dodge) {
                    item.addLore("&cDODGE: " + decideValue(getDodgeRange()) + "%");
                    name = "Dodging " + name;
                }
            }
        }
        name = getTier().getColor() + name;
        item.setName(name);
        item.setDurability(durability);
        item.addItemFlag(ItemFlag.HIDE_ATTRIBUTES);
        return item.build();
    }

    private int decideValue(String value) {
        return RandomUtils.random(Integer.parseInt(value.split("-")[0]), Integer.parseInt(value.split("-")[1]));
    }
}
