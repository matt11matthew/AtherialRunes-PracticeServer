package net.atherialrunes.practiceserver.api.handler.handlers.energy;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.PracticeServer;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import net.atherialrunes.practiceserver.utils.StatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

/**
 * Created by Matthew E on 9/25/2016 at 2:43 PM.
 */
public class EnergyHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
        Bukkit.getScheduler().runTaskTimerAsynchronously(PracticeServer.getInstance(), this::regenerateAllPlayerEnergy, 40, 1L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(PracticeServer.getInstance(), this::removePlayerEnergySprint, 40, 10L);
        Bukkit.getScheduler().runTaskTimerAsynchronously(PracticeServer.getInstance(), this::addStarvingPotionEffect, 40, 15L);
    }

    @Override
    public void onUnload() {

    }

    public static float getPlayerCurrentEnergy(Player player) {
        return player.getExp();
    }

    private void addStarvingPotionEffect() {
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPotionEffect(PotionEffectType.HUNGER) && player.hasMetadata("starving")).forEach(player -> {
            if (player.getFoodLevel() <= 0) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(PracticeServer.getInstance(), () -> player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 40, 0)), 0L);
            } else {
                player.removeMetadata("starving", PracticeServer.getInstance());
                Bukkit.getScheduler().scheduleSyncDelayedTask(PracticeServer.getInstance(), () -> player.removePotionEffect(PotionEffectType.HUNGER), 0L);
            }
        });
    }


    private void regenerateAllPlayerEnergy() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            GamePlayer gp = GameAPI.getGamePlayer(player);
            if (gp == null) {
                continue; // player data not yet loaded
            }
            if (getPlayerCurrentEnergy(player) == 1.0F) {
                continue;
            }
            if (getPlayerCurrentEnergy(player) > 1.0F) {
                player.setExp(1.0F);
                updatePlayerEnergyBar(player);
                continue;
            }
            // get regenAmount, 10% base energy regen (calculated here because it's hidden)
            float regenAmount = (StatUtils.getEnergy(player) / 100.0F) + 0.10F;
            if (!(player.hasPotionEffect(PotionEffectType.SLOW_DIGGING))) {
                if (player.hasMetadata("starving")) {
                    regenAmount = 0.05F;
                }
                regenAmount = regenAmount / 18.9F;
                if (gp == null) return;
                regenAmount += (int) (regenAmount * gp.getEnergyRegen());
                addEnergyToPlayerAndUpdate(player, regenAmount);
            }
        }
    }

    private static void updatePlayerEnergyBar(Player player) {
        float currExp = getPlayerCurrentEnergy(player);
        double percent = currExp * 100.00D;
        if (percent > 100) {
            percent = 100;
        }
        if (percent < 0) {
            percent = 0;
        }
    }

    private static void addEnergyToPlayerAndUpdate(Player player, float amountToAdd) {
        if (getPlayerCurrentEnergy(player) == 1) {
            return;
        }
        player.setExp(player.getExp() + amountToAdd);
        updatePlayerEnergyBar(player);
    }

    private void removePlayerEnergySprint() {
        Bukkit.getOnlinePlayers().stream().filter(Player::isSprinting).forEach(player -> {
            removeEnergyFromPlayerAndUpdate(player.getUniqueId(), 0.15F);
            if (getPlayerCurrentEnergy(player) <= 0 || player.hasMetadata("starving")) {
                player.setSprinting(false);
                player.removeMetadata("sprinting", PracticeServer.getInstance());
                if (!player.hasPotionEffect(PotionEffectType.JUMP)) {
                    int foodLevel = player.getFoodLevel();
                    if (player.getFoodLevel() > 1) {
                        player.setFoodLevel(1);
                    }
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "**EXHAUSTED**");
                    if (foodLevel > 1) {
                        Bukkit.getScheduler().scheduleSyncDelayedTask(PracticeServer.getInstance(), () -> player.setFoodLevel(foodLevel), 40L);
                    }
                }
            }
        });
    }

    public static void removeEnergyFromPlayerAndUpdate(UUID uuid, float amountToRemove) {
        Player player = Bukkit.getPlayer(uuid);
        if (Rank.isGM(player.getName())) {
            GamePlayer gp = GameAPI.getGamePlayer(player);
        }
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (player.hasMetadata("last_energy_remove")) {
            if ((System.currentTimeMillis() - player.getMetadata("last_energy_remove").get(0).asLong()) < 80) {
                return;
            }
        }
        player.setMetadata("last_energy_remove", new FixedMetadataValue(PracticeServer.getInstance(), System.currentTimeMillis()));
        if (getPlayerCurrentEnergy(player) <= 0) return;
        if ((getPlayerCurrentEnergy(player) - amountToRemove) <= 0) {
            player.setExp(0.0F);
            updatePlayerEnergyBar(player);
            Bukkit.getScheduler().scheduleSyncDelayedTask(PracticeServer.getInstance(), () -> player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 50, 4)), 0L);
            return;
        }
        player.setExp(getPlayerCurrentEnergy(player) - amountToRemove);
        updatePlayerEnergyBar(player);
    }


    public static float getWeaponSwingEnergyCost(ItemStack itemStack) {
        Material material = itemStack.getType();
        switch (material) {
            case AIR:
                return 0.05f;
            case WOOD_SWORD:
                return 0.06f;
            case STONE_SWORD:
                return 0.071f;
            case IRON_SWORD:
                return 0.0833f;
            case DIAMOND_SWORD:
                return 0.125f;
            case GOLD_SWORD:
                return 0.135f;
            case WOOD_AXE:
                return 0.0721F * 1.1F;
            case STONE_AXE:
                return 0.0833F * 1.1F;
            case IRON_AXE:
                return 0.10F * 1.1F;
            case DIAMOND_AXE:
                return 0.125F * 1.1F;
            case GOLD_AXE:
                return 0.135F * 1.1F;
            case WOOD_SPADE:
                return 0.0721F;
            case STONE_SPADE:
                return 0.0833F;
            case IRON_SPADE:
                return 0.10F;
            case DIAMOND_SPADE:
                return 0.125F;
            case GOLD_SPADE:
                return 0.135F;
            case WOOD_HOE:
                return 0.10F / 1.1F;
            case STONE_HOE:
                return 0.12F / 1.1F;
            case IRON_HOE:
                return 0.13F / 1.1F;
            case DIAMOND_HOE:
                return 0.14F / 1.1F;
            case GOLD_HOE:
                return 0.15F / 1.1F;
        }
        return 0.10F;
    }
}
