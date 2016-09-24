package net.atherialrunes.practiceserver.api.handler.handlers.enchant;

import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.handler.handlers.item.ItemGenerator;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.GearType;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by Matthew E on 9/24/2016 at 12:31 PM.
 */
public class EnchantHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onUnload() {

    }

    public boolean isGear(ItemStack item) {
        GearType type = GearType.getGearType(item);
        return (type != null) ? true : false;
    }

    public static boolean canEnchant(ItemStack scroll, ItemStack item) {
        ItemStack t1scrollwep = ItemGenerator.generateEnchant(scroll.getAmount(), Tier.T1, "Weapon").build();
        ItemStack t2scrollwep = ItemGenerator.generateEnchant(scroll.getAmount(), Tier.T2, "Weapon").build();
        ItemStack t3scrollwep = ItemGenerator.generateEnchant(scroll.getAmount(), Tier.T3, "Weapon").build();
        ItemStack t4scrollwep = ItemGenerator.generateEnchant(scroll.getAmount(), Tier.T4, "Weapon").build();
        ItemStack t5scrollwep = ItemGenerator.generateEnchant(scroll.getAmount(), Tier.T5, "Weapon").build();
        ItemStack t1scrollarmor = ItemGenerator.generateEnchant(scroll.getAmount(), Tier.T1, "Armor").build();
        ItemStack t2scrollarmor = ItemGenerator.generateEnchant(scroll.getAmount(), Tier.T2, "Armor").build();
        ItemStack t3scrollarmor = ItemGenerator.generateEnchant(scroll.getAmount(), Tier.T3, "Armor").build();
        ItemStack t4scrollarmor = ItemGenerator.generateEnchant(scroll.getAmount(), Tier.T4, "Armor").build();
        ItemStack t5scrollarmor =ItemGenerator.generateEnchant(scroll.getAmount(), Tier.T5, "Armor").build();

        if (scroll.equals(t1scrollarmor)) {
            if (Utils.isArmor(item)) {
                return (Utils.getTier(item) == 1) ? true : false;

            }
        }
        if (scroll.equals(t2scrollarmor)) {
            if (Utils.isArmor(item)) {
                return (Utils.getTier(item) == 2) ? true : false;

            }
        }
        if (scroll.equals(t3scrollarmor)) {
            if (Utils.isArmor(item)) {
                return (Utils.getTier(item) == 3) ? true : false;

            }
        }
        if (scroll.equals(t4scrollarmor)) {
            if (Utils.isArmor(item)) {
                return (Utils.getTier(item) == 4) ? true : false;

            }
        }
        if (scroll.equals(t5scrollarmor)) {
            if (Utils.isArmor(item)) {
                return (Utils.getTier(item) == 5) ? true : false;

            }
        }
        if (scroll.equals(t1scrollwep)) {
            if ((Utils.isAxe(item)) || (Utils.isSword(item))) {
                return (Utils.getTier(item) == 1) ? true : false;

            }
        }
        if (scroll.equals(t2scrollwep)) {
            if ((Utils.isAxe(item)) || (Utils.isSword(item))) {
                return (Utils.getTier(item) == 2) ? true : false;

            }
        }
        if (scroll.equals(t3scrollwep)) {
            if ((Utils.isAxe(item)) || (Utils.isSword(item))) {
                return (Utils.getTier(item) == 3) ? true : false;

            }
        }
        if (scroll.equals(t4scrollwep)) {
            if ((Utils.isAxe(item)) || (Utils.isSword(item))) {
                return (Utils.getTier(item) == 4) ? true : false;

            }
        }
        if (scroll.equals(t5scrollwep)) {
            if ((Utils.isAxe(item)) || (Utils.isSword(item))) {
                return (Utils.getTier(item) == 5) ? true : false;

            }
        }
        return false;
    }

    @EventHandler
    public void onEnchant(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory().equals(p.getInventory())) {
            if ((e.getCurrentItem() != null) && (e.getCursor() != null)) {
                ItemStack cur = e.getCurrentItem();
                ItemStack cursor = e.getCursor();
                if (canEnchant(cursor, cur)) {
                    e.setCancelled(true);
                    int plus = getPlus(cur.getItemMeta().getDisplayName());
                    double failRate = 0;
                    switch (plus) {
                        case 0:
                            failRate = 0;
                            break;
                        case 1:
                            failRate = 0;
                            break;
                        case 2:
                            failRate = 0;
                            break;
                        case 3:
                            failRate = 20;
                            break;
                        case 4:
                            failRate = 30;
                            break;
                        case 5:
                            failRate = 50;
                            break;
                        case 6:
                            failRate = 60;
                            break;
                        case 7:
                            failRate = 70;
                            break;
                        case 8:
                            failRate = 80;
                            break;
                        case 9:
                            failRate = 85;
                            break;
                        case 10:
                            failRate = 90;
                            break;
                        case 11:
                            failRate = 95;
                            break;
                    }
                    Random r = new Random();
                    if (failRate == 0) {
                        if (Utils.isArmor(cur)) {
                            AtherialItem item = AtherialItem.fromItemStack(cur);
                            item.enchant("Armor");
                            e.setCurrentItem(item.build());
                            return;
                        }
                        if (Utils.isWeapon(cur)) {
                            AtherialItem item = AtherialItem.fromItemStack(cur);
                            item.enchant("Weapon");
                            e.setCurrentItem(item.build());
                            return;

                        }
                        if (cursor.getAmount() > 1) {
                            cursor.setAmount((cursor.getAmount() - 1));
                        } else {
                            e.setCursor(new ItemStack(Material.AIR));
                        }
                    }
                    if (r.nextInt(100) <= failRate) {
                        e.setCurrentItem(new ItemStack(Material.AIR));
                        if (cursor.getAmount() > 1) {
                            cursor.setAmount((cursor.getAmount() - 1));
                        } else {
                            e.setCursor(new ItemStack(Material.AIR));
                        }
                    } else {
                        if (Utils.isArmor(cur)) {
                            AtherialItem item = AtherialItem.fromItemStack(cur);
                            item.enchant("Armor");
                            e.setCurrentItem(item.build());
                            return;
                        }
                        if (Utils.isWeapon(cur)) {
                            AtherialItem item = AtherialItem.fromItemStack(cur);
                            item.enchant("Weapon");
                            e.setCurrentItem(item.build());
                            return;

                        }
                        if (cursor.getAmount() > 1) {
                            cursor.setAmount((cursor.getAmount() - 1));
                        } else {
                            e.setCursor(new ItemStack(Material.AIR));
                        }
                    }
                }
            }
        }
    }

    public static int getPlus(String name) {
        int plus = 0;
        if (name != null) {
            try {
                name = ChatColor.stripColor(name);
                if (name.startsWith("[")) {
                    plus = Integer.parseInt(name.substring(name.indexOf("+") + 1, name.lastIndexOf("]")));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return plus;
    }
}
