package net.atherialrunes.practiceserver.utils;

import com.google.common.base.Joiner;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by Matthew E on 9/23/2016.
 */
public class InventoryUtils {

    public static String convertInventoryToString(String p_name, Inventory inv, boolean player) {
        // @item@Slot:ItemID-Amount.Durability#Item_Name#$Item_Lore$[lam1]lam_color[lam2]
        // @item@1:267-1.54#Magic Sword#$DMG: 5 - 7, CRIT: 5%$@item@

        String return_string = "";
        int slot = -1;
        for (ItemStack is : inv.getContents()) {
            slot++;
            if (is == null || is.getType() == Material.AIR) {
                continue;
            }

            String i_name = "";
            if (is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                i_name = is.getItemMeta().getDisplayName();
            } else {
                // Default name.
                i_name = "null";
            }

            String i_lore = "";
            if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
                for (String s : is.getItemMeta().getLore()) {
                    i_lore = i_lore + "," + s;
                }
            } else {
                // No lore.
                i_lore = "null";
            }

            while (i_lore.contains(",,")) {
                i_lore = i_lore.replace(",,", ",");
            }

            return_string = return_string + ("@item@" + slot + ":" + is.getTypeId() + "-" + is.getAmount() + "." + is.getDurability() + "#" + i_name + "#" + "$" + i_lore + "$");
            if (is.hasItemMeta() && is.getItemMeta() instanceof LeatherArmorMeta) {
                return_string = return_string + "[lam1]" + ((LeatherArmorMeta) is.getItemMeta()).getColor().asBGR() + "[lam2]";
            }
        }

        List<ItemStack> armor_contents = new ArrayList<ItemStack>();
        if (player) {
            if (Bukkit.getPlayer(p_name) != null) {
                Player owner = Bukkit.getPlayer(p_name);
                for (ItemStack is : owner.getInventory().getArmorContents()) {
                    armor_contents.add(is);
                }
            }

            if (armor_contents.size() > 0) {
                for (ItemStack is : armor_contents) {
                    slot++;
                    if (is == null) {
                        continue;
                    }

                    String i_name = "";
                    if (is.hasItemMeta() && is.getItemMeta().hasDisplayName()) {
                        i_name = is.getItemMeta().getDisplayName();
                    } else {
                        // Default name.
                        i_name = "null";
                    }

                    String i_lore = "";
                    if (is.hasItemMeta() && is.getItemMeta().hasLore()) {
                        for (String s : is.getItemMeta().getLore()) {
                            i_lore = i_lore + "," + s;
                        }
                    } else {
                        // No lore.
                        i_lore = "null";
                    }

                    return_string = return_string + ("@item@" + slot + ":" + is.getTypeId() + "-" + is.getAmount() + "." + is.getDurability() + "#" + i_name + "#" + "$" + i_lore + "$");
                    if (is.hasItemMeta() && is.getItemMeta() instanceof LeatherArmorMeta) {
                        return_string = return_string + "[lam1]" + ((LeatherArmorMeta) is.getItemMeta()).getColor().asBGR() + "[lam2]";
                    }
                }
            }
        }

        return return_string;
    }


    public static Inventory convertStringToInventory(Player pl, String inventory_string, String inventory_name, int slots) {
        Inventory inv = null;
        // int slot_cache = -1;
        int expected_item_size = inventory_string.split("@item@").length - 1;

        if (pl == null && inventory_name != null) {
            // Using inventory.
            inv = Bukkit.createInventory(null, slots, inventory_name);
        }
        for (String s : inventory_string.split("@item@")) {
            // slot_cache++;

            if (s.length() <= 1 || s.equalsIgnoreCase("null")) {
                continue;
            }

            int slot = Integer.parseInt(s.substring(0, s.indexOf(":")));

            if (inventory_name != null && inventory_name.startsWith("Bank Chest")) {
                if (slot > expected_item_size && (slot > (slots - 1))) { // slots - 1, 0 index = start
                    slot = inv.firstEmpty();
                }
            }

            if (s.length() <= 1) {
                continue;
            }

            int item_id = Integer.parseInt(s.substring(s.indexOf(":") + 1, s.indexOf("-")));
            int amount = Integer.parseInt(s.substring(s.indexOf("-") + 1, s.indexOf(".")));
            short durability = Short.parseShort(s.substring(s.indexOf(".") + 1, s.indexOf("#")));

            String i_name = s.substring(s.indexOf("#") + 1, s.lastIndexOf("#"));
            String i_lore = s.substring(s.indexOf("$") + 1, s.lastIndexOf("$"));

            Color leather_armor_color = null;
            if (s.contains("[lam1]")) {
                leather_armor_color = Color.fromBGR(Integer.parseInt(s.substring(s.indexOf("[lam1]") + 6, s.lastIndexOf("[lam2]"))));
            }

            ItemStack is = new ItemStack(Material.getMaterial(item_id), amount, durability);

            List<String> splitlore = new ArrayList<String>(new LinkedHashSet<String>(Arrays.asList(i_lore.split(","))));
            i_lore = Joiner.on(',').join(splitlore);

            if (is.getType() == Material.POTION && is.getDurability() > 0) {
                // Renames potion to Instant Heal.

                is = signNewCustomItem(Material.getMaterial(item_id), durability, i_name, i_lore);
                if (pl != null) {
                    pl.getInventory().setItem(slot, is);
                } else if (inv != null) {
                    inv.setItem(slot, is);
                }
                continue;
            }

            if (is.getType() == Material.WRITTEN_BOOK) {
                continue; // TODO: Code book loading.
            }

            ItemMeta im = is.getItemMeta();

            if (!(i_name.equalsIgnoreCase("null"))) {
                // Custom name!
                im.setDisplayName(i_name);
            }

            if (!(i_lore.equalsIgnoreCase("null"))) {
                // Lore!
                List<String> all_lore = new ArrayList<String>();

                for (String lore : i_lore.split(",")) {
                    if (lore.length() > 1) {
                        all_lore.add(lore);
                    }
                }
                im.setLore(all_lore);
            }

            if (!(leather_armor_color == null)) {
                ((LeatherArmorMeta) im).setColor(leather_armor_color);
            }

            if (!(i_name.equalsIgnoreCase("null")) || !(i_lore.equalsIgnoreCase("null"))) {
                is.setItemMeta(im);
            }

            if (pl != null) {
                pl.getInventory().setItem(slot, is);
            } else if (inv != null) {
                inv.setItem(slot, is);
            }
        }

        if (inv != null) {
            return inv;
        } else {
            return null;
        }
    }

    public static ItemStack signNewCustomItem(Material m, short meta_data, String name, String desc) {
        ItemStack is = new ItemStack(m, 1, meta_data);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(name);

        List<String> new_lore = new ArrayList<String>();
        if (desc.contains(",")) {
            for (String s : desc.split(",")) {
                new_lore.add(s);
            }
        } else {
            new_lore.add(desc);
        }

        im.setLore(new_lore);
        is.setItemMeta(im);
        return is;
    }
}
