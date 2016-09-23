package net.atherialrunes.practiceserver.api.handler.handlers.bank;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.IntegerUtils;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew E on 9/23/2016.
 */
public class BankHandler extends ListenerHandler {

    public static List<GamePlayer> withdraw = new ArrayList<>();

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onUnload() {

    }

    public void saveBank(GamePlayer gamePlayer, Inventory inventory) {
        if (inventory.getTitle().startsWith("Bank Chest")) {
            gamePlayer.uploadBank(inventory);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        saveBank(GameAPI.getGamePlayer((Player) e.getPlayer()), e.getInventory());
    }

    public ItemStack getGem(GamePlayer gamePlayer) {
        AtherialItem gem = new AtherialItem(Material.EMERALD);
        gem.setName("&a" + gamePlayer.getGems() + " &lGEM(s)");
        gem.addLore("&7Right Click to create &aA GEM NOTE");
        return gem.build();
    }

    public void openBank(GamePlayer gamePlayer) {
        Inventory bank = gamePlayer.getBankInventory(1);
        bank.setItem(53, getGem(gamePlayer));
        gamePlayer.getPlayer().openInventory(bank);
    }

    @EventHandler
    public void onBankClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        GamePlayer gamePlayer = GameAPI.getGamePlayer(player);
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) {
            return;
        }
        if (e.getClickedInventory().getTitle().contains("Bank Chest")) {
            if (e.getSlot() == 53) {
                e.setCancelled(true);
                if (e.isRightClick()) {
                    withdraw(gamePlayer);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onBankClick(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (e.getInventory().getTitle().contains("Bank Chest")) {
            if (e.getInventory().contains(Material.EMERALD)) {
                for (int i = 0; i < e.getInventory().getSize(); i++) {
                    if (e.getInventory().getItem(i) != null) {
                        if ((e.getInventory().getItem(i) != null) && (i != 53) && (e.getInventory().getItem(i).getType() == Material.EMERALD)) {
                            int amt = e.getInventory().getItem(i).getAmount();
                            Player p = (Player) e.getWhoClicked();
                            GamePlayer gp = GameAPI.getGamePlayer(p);
                            e.getInventory().removeItem(e.getInventory().getItem(i));
                            gp.setGems((gp.getGems()) + amt);
                            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                            e.getInventory().setItem(53, getGem(gp));
                            p.sendMessage(Utils.colorCodes("&a&l+&a" + amt + "&lG&a, &lNew Balance: &a" + gp.getGems() + " Gem(s)"));
                            p.updateInventory();
                        }
                    }
                }
            }

        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        GamePlayer gp = GameAPI.getGamePlayer(player);
        if (withdraw.contains(gp)) {
            e.setCancelled(true);
            String amount = e.getMessage();
            if (IntegerUtils.isInt(amount)) {
                int amt = Integer.parseInt(amount);
                if ((amt > 10000) || (amt < 1)) {
                    withdraw.remove(gp);
                    gp.msg("&cType a number from 1-10000");
                    return;
                } else {
                    if (amt > gp.getGems()) {
                        withdraw.remove(gp);
                        gp.msg("&cYou don't have enough gems!");
                        return;
                    } else {
                        if (gp.getPlayer().getInventory().firstEmpty() == -1) {
                            gp.msg("&c&lWarning &cYour inventory is full!");
                            gp.getPlayer().closeInventory();
                            withdraw.remove(gp);
                            return;
                        }
                        gp.getPlayer().getInventory().setItem(gp.getPlayer().getInventory().firstEmpty(), getBankNote(amt));
                        withdraw.remove(gp);
                        return;
                    }
                }
            } else {
                withdraw.remove(gp);
                gp.msg("&cType a number!");
                return;
            }
        }
    }

    private ItemStack getBankNote(int amt) {
        AtherialItem note = new AtherialItem(Material.PAPER);
        note.setName("&aBank Note");
        note.addLore("&f&lValue: " + amt + "G");
        return note.build();
    }

    private boolean isBankNote(ItemStack item) {
        return (item.getType() == Material.PAPER);
    }

    public static void withdraw(GamePlayer gamePlayer) {
        withdraw.add(gamePlayer);
        gamePlayer.msg("&aPlease type the amount you want to withdraw");
        return;

    }
}
