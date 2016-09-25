package net.atherialrunes.practiceserver.api.handler.handlers.item;

import net.atherialrunes.practiceserver.api.handler.handlers.mob.GearType;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.Tier;
import net.atherialrunes.practiceserver.utils.StatUtils;
import net.atherialrunes.practiceserver.utils.Utils;
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

    public AtherialItem setLoreLine(int line, String lore) {
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
        ItemMeta im = itemStack.getItemMeta();
        im.spigot().setUnbreakable(true);
        itemStack.setItemMeta(im);
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
        return (int) StatUtils.getStatFromLore(itemStack, "HP: +", "");
    }

    public static AtherialItem fromItemStack(ItemStack item) {
        AtherialItem atherialItem = new AtherialItem(item.getType());
        atherialItem.itemStack = item;
        return atherialItem;
    }

    public static AtherialItem generate(GearType gearType, Tier tier) {
        return AtherialItem.fromItemStack(ItemGenerator.generateGear(tier.getTier(), gearType));
    }

    public int getDamage() {
        return StatUtils.getDamage(itemStack);
    }

    public void enchant(String type) {
        setName("&c[+" + (StatUtils.getPlus(itemStack) + 1) + "] " + itemStack.getItemMeta().getDisplayName());
        switch (type) {
            case "Armor":
                int hp = getHP();
                hp *= 1.05;
                setLoreLine(1, "&cHP: +" + hp);
                if (StatUtils.hasStat(itemStack, "ENERGY REGEN")) {
                    int energy = (int) StatUtils.getStatFromLore(itemStack, "ENERGY REGEN: ", "%");
                    energy += 1;
                    setLoreLine(2, "&cENERGY REGEN: " + energy + "%");
                    break;
                } else if (StatUtils.hasStat(itemStack, "HP REGEN")) {
                    int hps = (int) StatUtils.getStatFromLore(itemStack, "HP REGEN: ", "HP/s");
                    hps *= 1.05;
                    setLoreLine(2, "&cHP REGEN: " + hps + " HP/s");
                    break;
                }
                break;
            case "Weapon":
                int minDamage = StatUtils.getMinDamage(itemStack);
                int maxDamage = StatUtils.getMaxDamage(itemStack);
                minDamage *= 1.05;
                maxDamage *= 1.05;
                setLoreLine(0, "&cDMG: " + minDamage + " - " + maxDamage);
                break;
            default:
                break;
        }
    }
}