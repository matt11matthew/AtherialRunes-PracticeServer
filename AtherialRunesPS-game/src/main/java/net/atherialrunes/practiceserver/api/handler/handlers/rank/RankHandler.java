package net.atherialrunes.practiceserver.api.handler.handlers.rank;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.bank.BankHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.spawner.SpawnerHandler;
import net.atherialrunes.practiceserver.utils.Utils;
import net.atherialrunes.practiceserver.utils.json.JSONMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class RankHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onUnload() {

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player player = e.getPlayer();
        GamePlayer gp = GameAPI.getGamePlayer(player);
        if (SpawnerHandler.placing.containsKey(gp)) {
            return;
        }
        if (BankHandler.withdraw.contains(gp)) {
            return;
        }
        if (e.getMessage().contains("@i@") && e.getPlayer().getEquipment().getItemInMainHand() != null && e.getPlayer().getEquipment().getItemInMainHand().getType() != Material.AIR) {
            final Player p = e.getPlayer();
            String msg = e.getMessage();
            String aprefix = Rank.getChatPrefix(p);
            String[] split = msg.split("@i@");
            String after = "";
            String before = "";
            if (split.length > 0)
                before = split[0];
            if (split.length > 1)
                after = split[1];


            ItemStack stack = e.getPlayer().getInventory().getItemInMainHand();

            List<String> hoveredChat = new ArrayList<>();
            ItemMeta meta = stack.getItemMeta();
            hoveredChat.add((meta.hasDisplayName() ? meta.getDisplayName() : stack.getType().name()));
            if (meta.hasLore())
                hoveredChat.addAll(meta.getLore());
            final JSONMessage normal = new JSONMessage(ChatColor.WHITE + aprefix, ChatColor.WHITE);
            normal.addText(before + "");
            normal.addHoverText(hoveredChat, ChatColor.BOLD + ChatColor.UNDERLINE.toString() + "SHOW");
            normal.addText(after);

            Bukkit.getOnlinePlayers().forEach(normal::sendToPlayer);
            return;
        } else {
            String message = gp.getRank().getChatPrefix(e.getPlayer()) + ": &f" + ChatColor.stripColor(e.getMessage());
            Bukkit.getServer().getOnlinePlayers().forEach(players -> players.sendMessage(Utils.colorCodes(message)));
        }
//        String message = gp.getRank().getChatPrefix(e.getPlayer()) + ": &f" + ChatColor.stripColor(e.getMessage());
//        if (((message.contains("@i@")) || (message.contains("@I@")))) {
//            String part1 = message.split("@i@")[0];
//            String part2 = message.split("@i@")[1];
//            TextComponent textComponentPart1 = new TextComponent(Utils.colorCodes(part1));
//            TextComponent showMessage = new TextComponent(Utils.colorCodes("&f&lnSHOW"));
//            showMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(AtherialItem.fromItemStack(gp.getWeapon()).getShowText()).create()));
//            TextComponent textComponentPart2 = new TextComponent(Utils.colorCodes(part2));
//            textComponentPart1.addExtra(showMessage);
//            textComponentPart2.addExtra(textComponentPart1);
//            Bukkit.getServer().getOnlinePlayers().forEach(players -> players.spigot().sendMessage(textComponentPart2));
//            return;
//        }
//        Bukkit.getServer().getOnlinePlayers().forEach(players -> players.sendMessage(Utils.colorCodes(message)));
    }
}
