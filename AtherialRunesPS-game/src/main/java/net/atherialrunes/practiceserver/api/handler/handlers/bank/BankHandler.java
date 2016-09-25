package net.atherialrunes.practiceserver.api.handler.handlers.bank;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.command.AtherialCommandManager;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.bank.commands.CommandBank;
import net.atherialrunes.practiceserver.api.handler.handlers.item.AtherialItem;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;
import net.atherialrunes.practiceserver.utils.IntegerUtils;
import net.atherialrunes.practiceserver.utils.StatUtils;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;
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
        task();

        AtherialCommandManager cm = new AtherialCommandManager();
        cm.registerCommand(new CommandBank("bank"));
    }

    @Override
    public void onUnload() {

    }
/*
   public void saveBank(GamePlayer gamePlayer, Inventory inventory) {
       if (inventory.getTitle().startsWith("Bank Chest")) {
           String inv = InventoryUtils.convertInventoryToString(gamePlayer.getName(), inventory, false);
           gamePlayer.msg(inv);
           DatabaseAPI.getInstance().update(gamePlayer.getUniqueId(), EnumOperators.$SET, EnumData.BANK_INVENTORY_1, inv, true);
       }
   }
*/
   /* @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        Inventory inventory = e.getInventory();
        if (inventory.getTitle().startsWith("Bank Chest")) {
            saveBank(GameAPI.getGamePlayer(player), inventory);
            return;
        }
    }*/

    public ItemStack getGem(GamePlayer gamePlayer) {
        AtherialItem gem = new AtherialItem(Material.EMERALD);
        gem.setName("&a" + gamePlayer.getGems() + " &lGEM(s)");
        gem.addLore("&7Right Click to create &aA GEM NOTE");
        return gem.build();
    }

   /* public void openBank(Player player) {
        Inventory bank = null;
        try {
            String bankinv = DatabaseAPI.getInstance().getData(EnumData.BANK_INVENTORY_1, player.getUniqueId()) + "";
            bank = InventoryUtils.convertStringToInventory(player, bankinv, "Bank Chest (1/1)", 54);
            player.sendMessage(bankinv);
            player.openInventory(bank);
        } catch (Exception e) {
            e.printStackTrace();
            bank = Bukkit.createInventory(null, 54, "Bank Chest (1/1)");
        }
       // bank.setItem(53, getGem(GameAPI.getGamePlayer(player)));
       // player.openInventory(bank);
        return;
    }*/

    public static int GEM_SLOT = 26;


    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e) {
        if (e.getInventory().getType() == InventoryType.ENDER_CHEST) {
            Player p = (Player) e.getPlayer();
            Inventory inv = p.getEnderChest();
            inv.setItem(GEM_SLOT, getGem(GameAPI.getGamePlayer(p)));
        }
    }
/*
    @EventHandler
    public void onBankClick(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) return;
        if (e.getInventory().getType() == InventoryType.ENDER_CHEST) {
            if (e.getInventory().contains(Material.EMERALD)) {
                for (int i = 0; i < e.getInventory().getSize(); i++) {
                    if (e.getInventory().getItem(i) != null) {
                        if ((e.getInventory().getItem(i) != null) && (i != GEM_SLOT) && (e.getInventory().getItem(i).getType() == Material.EMERALD)) {
                            int amt = e.getInventory().getItem(i).getAmount();
                            Player p = (Player) e.getWhoClicked();
                            e.getInventory().removeItem(e.getInventory().getItem(i));
                            GamePlayer gp = GameAPI.getGamePlayer(p);
                            gp.setGems((gp.getGems() + amt));
                            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                            e.getInventory().setItem(GEM_SLOT, getGem(gp));
                            p.sendMessage(Utils.colorCodes("&a&l+&a" + amt + "&lG&a, &lNew Balance: &a" + gp.getGems() + " Gem(s)"));
                            p.updateInventory();
                        }
                    }
                }
            }

        }
    }
*/


    public void task() {
        AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    GamePlayer gp = GameAPI.getGamePlayer(player);
                    if (CommandBank.bankOpening.containsKey(gp)) {
                        int time = CommandBank.bankOpening.get(gp);
                        if (time > 0) {
                            time = (time - 1);
                            if (time == 0) {
                                CommandBank.bankOpening.remove(gp);
                                gp.msg("&cOpened bank...");
                                player.openInventory(player.getEnderChest());
                                return;
                            }
                            CommandBank.bankOpening.remove(gp);
                            CommandBank.bankOpening.put(gp, time);
                            gp.msg("&cBank opening " + time + "&ls");
                        }
                    }
                });
            }
        }, 20L, 20L);
    }

    private void openBank(Player player) {
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        GamePlayer gp = GameAPI.getGamePlayer(player);
        if (CommandBank.bankOpening.containsKey(gp)) {
            Location from = e.getFrom();
            Location to = e.getTo();
            if ((from.getX() != to.getX()) || (from.getY() != to.getY()) || (from.getZ() != to.getZ())) {
                CommandBank.bankOpening.remove(gp);
                gp.msg("&cBank opening Cancelled");
                return;
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            GamePlayer gp = GameAPI.getGamePlayer(player);
            if (CommandBank.bankOpening.containsKey(gp)) {
                CommandBank.bankOpening.remove(gp);
                gp.msg("&cBank opening Cancelled");
                return;
            }

        }
    }

    @EventHandler
    public void onBankClickEvent(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        GamePlayer gamePlayer = GameAPI.getGamePlayer(player);
        if (e.getSlotType() == InventoryType.SlotType.OUTSIDE) {
            return;
        }
        if (e.getClickedInventory().getTitle().contains("Bank Chest")) {
            if (e.getSlot() == GEM_SLOT) {
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
        if ((e.getClickedInventory().getType() == InventoryType.ENDER_CHEST) && (e.getClickedInventory().equals(e.getWhoClicked().getEnderChest()))) {
            if (e.getInventory().contains(Material.PAPER)) {
                for (int i = 0; i < e.getInventory().getSize(); i++) {
                    if (e.getInventory().getItem(i) != null) {
                        if ((e.getInventory().getItem(i) != null) && (i != GEM_SLOT) && (e.getInventory().getItem(i).getType() == Material.PAPER)) {
                            int amt = getBankNoteValue(e.getInventory().getItem(i));
                            Player p = (Player) e.getWhoClicked();
                            GamePlayer gp = GameAPI.getGamePlayer(p);
                            e.getInventory().removeItem(e.getInventory().getItem(i));
                            gp.setGems((gp.getGems()) + amt);
                            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                            e.getInventory().setItem(GEM_SLOT, getGem(gp));
                            p.sendMessage(Utils.colorCodes("&a&l+&a" + amt + "&lG&a, &lNew Balance: &a" + gp.getGems() + " Gem(s)"));
                            p.updateInventory();
                        }
                    }
                }
            }
            if (e.getInventory().contains(Material.EMERALD)) {
                for (int i = 0; i < e.getInventory().getSize(); i++) {
                    if (e.getInventory().getItem(i) != null) {
                        if ((e.getInventory().getItem(i) != null) && (i != GEM_SLOT) && (e.getInventory().getItem(i).getType() == Material.EMERALD)) {
                            int amt = e.getInventory().getItem(i).getAmount();
                            Player p = (Player) e.getWhoClicked();
                            GamePlayer gp = GameAPI.getGamePlayer(p);
                            e.getInventory().removeItem(e.getInventory().getItem(i));
                            gp.setGems((gp.getGems()) + amt);
                            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                            e.getInventory().setItem(GEM_SLOT, getGem(gp));
                            p.sendMessage(Utils.colorCodes("&a&l+&a" + amt + "&lG&a, &lNew Balance: &a" + gp.getGems() + " Gem(s)"));
                            p.updateInventory();
                        }
                    }
                }
            }

        }
    }

//    @EventHandler
//    public void onInteract(PlayerInteractEvent e) {
//        Player player = e.getPlayer();
//        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
//            if ((e.getClickedBlock() != null) && (e.getClickedBlock().getType() == Material.ENDER_CHEST)) {
//                e.setCancelled(true);
//                openBank(player);
//                return;
//            }
//        }
//    }

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
                        gp.setGems((gp.getGems() - amt));
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

    private int getBankNoteValue(ItemStack item) {
        return (isBankNote(item)) ? (int) StatUtils.getStatFromLore(item, "Value: ", "G") : 0;
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
