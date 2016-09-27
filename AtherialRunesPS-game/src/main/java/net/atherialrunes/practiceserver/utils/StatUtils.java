package net.atherialrunes.practiceserver.utils;

import net.atherialrunes.practiceserver.api.handler.handlers.mob.GearType;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class StatUtils {

    private static String getDamageString(ItemStack item) {
        GearType gearType = GearType.getGearType(item);
        if ((gearType == GearType.AXE) || (gearType == GearType.SWORD)) {
            return item.getItemMeta().getLore().get(0).split("DMG:")[1];
        }
        return null;
    }

    public static int getMinDamage(ItemStack item) {
        String damageString = getDamageString(item);
        if (damageString == null) {
            return 0;
        }
        return Integer.parseInt(damageString.replaceAll(" ", "").split("-")[0]);
    }

    public static int getMaxDamage(ItemStack item) {
        String damageString = getDamageString(item);
        if (damageString == null) {
            return 0;
        }
        return Integer.parseInt(damageString.replaceAll(" ", "").split("-")[1]);
    }

    public static boolean hasStat(ItemStack is, String stat) {
        boolean has = false;
        try {
            if ((is.hasItemMeta()) && (is.getItemMeta().hasLore())) {
                List<String> lore = is.getItemMeta().getLore();
                for (int i = 0; i < lore.size(); i++) {
                    if (((String) lore.get(i)).contains(stat)) {
                        has = true;
                    }
                }
            }
        } catch (Exception e) {
            has = false;
        }
        return has;
    }

    public static double getStatFromLore(ItemStack item, String value, String value2) {
        double returnVal = 0.0D;
        ItemMeta meta = item.getItemMeta();
        try {
            List<String> lore = meta.getLore();
            if (lore != null) {
                for (int i = 0; i < lore.size(); i++) {
                    if (((String)lore.get(i)).contains(value)) {
                        if (!value2.equals("")) {
                            String vals = ((String)lore.get(i)).split(value)[1].split(value2)[0];
                            vals = ChatColor.stripColor(vals).trim();
                            vals = vals.replaceAll(" ", "");
                            vals = vals.replaceAll(value2, "");
                            returnVal = Integer.parseInt(vals.trim());
                        } else {
                            String vals = ((String)lore.get(i)).split(value)[1];
                            vals = ChatColor.stripColor(vals).trim();
                            vals = vals.replaceAll(" ", "");
                            vals = vals.replaceAll(value2, "");
                            returnVal = Integer.parseInt(vals.trim());
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    public static int getPlus(ItemStack item) {
        ItemMeta im = item.getItemMeta();
        int plus = 0;
        if (im.hasDisplayName()) {
            try {
                String name = ChatColor.stripColor(im.getDisplayName());
                if (name.startsWith("[")) {
                    plus = Integer.parseInt(name.substring(name.indexOf("+") + 1, name.lastIndexOf("]")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return plus;
    }

    public static int getPrice(ItemStack is) {
        return (int) getStatFromLore(is, "Price:", "g");
    }

    public static int getDamage(ItemStack item) {
        return RandomUtils.random(getMinDamage(item), getMaxDamage(item));
    }

    public static float getEnergy(Player p) {
        double vit = 0.0D;
        if ((p.getEquipment().getHelmet() != null) && (p.getEquipment().getHelmet().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getHelmet(), "ENERGY REGEN")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getHelmet(), "ENERGY REGEN: +", "%"));
            }
        }
        if ((p.getEquipment().getChestplate() != null) && (p.getEquipment().getChestplate().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getChestplate(), "ENERGY REGEN")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getChestplate(), "ENERGY REGEN: +", "%"));
            }
        }
        if ((p.getEquipment().getLeggings() != null) && (p.getEquipment().getLeggings().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getLeggings(), "ENERGY REGEN")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getLeggings(), "ENERGY REGEN: +", "%"));
            }
        }
        if ((p.getEquipment().getBoots() != null) && (p.getEquipment().getBoots().getItemMeta().hasLore())) {
            if (StatUtils.hasStat(p.getEquipment().getBoots(), "ENERGY REGEN")) {
                vit += (StatUtils.getStatFromLore(p.getEquipment().getBoots(), "ENERGY REGEN: +", "%"));
            }
        }
        return (float) vit;
    }

    public static int getMaxDPS(ItemStack item) {
        return (StatUtils.hasStat(item, "DPS")) ? Integer.parseInt(item.getItemMeta().getLore().get(0).replaceAll(" ", "").split("DPS:")[1].split("-")[1].split("%")[0]) : 0;
    }

    public static int getMinDPS(ItemStack item) {
        return (StatUtils.hasStat(item, "DPS")) ? Integer.parseInt(item.getItemMeta().getLore().get(0).replaceAll(" ", "").split("DPS:")[1].split("-")[0]) : 0;
    }

    public static int getMaxArmor(ItemStack item) {
        return (StatUtils.hasStat(item, "ARMOR")) ? Integer.parseInt(item.getItemMeta().getLore().get(0).replaceAll(" ", "").split("ARMOR:")[1].split("-")[1].split("%")[0]) : 0;
    }

    public static int getMinArmor(ItemStack item) {
        return (StatUtils.hasStat(item, "ARMOR")) ? Integer.parseInt(item.getItemMeta().getLore().get(0).replaceAll(" ", "").split("ARMOR:")[1].split("-")[0]) : 0;
    }
}
