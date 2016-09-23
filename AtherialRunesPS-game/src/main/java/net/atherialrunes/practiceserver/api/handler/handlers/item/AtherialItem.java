package net.atherialrunes.practiceserver.api.handler.handlers.item;

import net.atherialrunes.practiceserver.api.handler.handlers.mob.GearType;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import net.atherialrunes.practiceserver.utils.StatUtils;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Matthew E on 9/22/2016.
 */
public class AtherialItem {

    private ItemStack itemStack = null;

    public AtherialItem(Material type, short dura, int amount) {
        itemStack = new ItemStack(type, amount, dura);
    }

    public AtherialItem(Material type) {
        this(type, (short) 0, 1);
    }

    public AtherialItem(Material type, short dura) {
        this(type, dura, 1);
    }

    public AtherialItem(Material type, int amount) {
        this(type, (short) 0, amount);
    }

    public int getAmount() {
        return itemStack.getAmount();
    }

    public AtherialItem setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public AtherialItem setLore(List<String> lore) {
        ItemMeta im = itemStack.getItemMeta();
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    public AtherialItem addLore(String lore) {
        ItemMeta im = itemStack.getItemMeta();
        List<String> itemLore = null;
        if (!im.hasLore()) {
            itemLore = new ArrayList<String>();
        } else {
            itemLore = im.getLore();
        }
        itemLore.add(Utils.colorCodes(lore));
        im.setLore(itemLore);
        itemStack.setItemMeta(im);
        return this;
    }

    public AtherialItem addItemFlag(ItemFlag itemFlag) {
        ItemMeta im = itemStack.getItemMeta();
        im.addItemFlags(itemFlag);
        itemStack.setItemMeta(im);
        return this;
    }

    public AtherialItem setLoreline(int line, String lore) {
        ItemMeta im = itemStack.getItemMeta();
        List<String> itemLore = null;
        if (!im.hasLore()) {
            itemLore = new ArrayList<String>();
        } else {
            itemLore = im.getLore();
        }
        itemLore.set(line, Utils.colorCodes(lore));
        im.setLore(itemLore);
        itemStack.setItemMeta(im);
        return this;
    }

    public AtherialItem setName(String name) {
        ItemMeta im = itemStack.getItemMeta();
        im.setDisplayName(Utils.colorCodes(name));
        itemStack.setItemMeta(im);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    public AtherialItem setPrice(int price) {
        addLore("&aPrice: &f" + price + "g");
        return this;
    }

    public AtherialItem setDurability(int i) {
        itemStack.setDurability((short) i);
        return this;
    }

    public AtherialItem addGlow() {
        itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return this;
    }

    public int getPrice() {
        return StatUtils.getPrice(build());
    }

    public AtherialItem removePrice() {
        ItemMeta im = itemStack.getItemMeta();
        List<String> lore = im.getLore();
        lore.remove(lore.size() - 1);
        im.setLore(lore);
        itemStack.setItemMeta(im);
        return this;
    }

    public int getHP() {
        return Integer.parseInt(ChatColor.stripColor(itemStack.getItemMeta().getLore().get(1).split("HP: +")[1]).trim());
    }

    public static AtherialItem fromItemStack(ItemStack item) {
        AtherialItem atherialItem = new AtherialItem(item.getType());
        atherialItem.itemStack = item;
        return atherialItem;
    }

    public static AtherialItem generate(GearType gearType, Tier tier) {
        return AtherialItem.fromItemStack(ItemGenerator.generateGear(tier.getTier(), gearType));
    }
}