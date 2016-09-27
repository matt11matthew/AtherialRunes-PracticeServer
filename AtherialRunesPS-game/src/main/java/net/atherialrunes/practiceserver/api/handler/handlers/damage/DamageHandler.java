package net.atherialrunes.practiceserver.api.handler.handlers.damage;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.GameConstants;
import net.atherialrunes.practiceserver.PracticeServer;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.energy.EnergyHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.MobBuilder;
import net.atherialrunes.practiceserver.api.handler.handlers.mob.armor.MobArmor;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.pvp.Alignment;
import net.atherialrunes.practiceserver.api.handler.handlers.zone.RegionUtils;
import net.atherialrunes.practiceserver.api.handler.handlers.zone.Zone;
import net.atherialrunes.practiceserver.utils.AtherialRunnable;
import net.atherialrunes.practiceserver.utils.RandomUtils;
import net.atherialrunes.practiceserver.utils.StatUtils;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class DamageHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();

        damageTask();
        task();
    }

    @Override
    public void onUnload() {

    }

    public static List<Player> tagged = new ArrayList<>();

    public void tag(final Player p) {
        tagged.add(p);
        new BukkitRunnable() {
            public void run() {
                tagged.remove(p);
            }
        }.runTaskLater(PracticeServer.getInstance(), (20L * 10L));
    }

    public double getFinalDMGMob(GamePlayer gamePlayer, LivingEntity mob) {
        if ((RegionUtils.getZone(gamePlayer.getPlayer().getLocation()) == Zone.SAFE) || (RegionUtils.getZone(gamePlayer.getPlayer().getLocation()) == Zone.SAFE)) {
            return -1;
        }
        ItemStack wep = gamePlayer.getWeapon();
        double dmg = 0.0D;
        double crit = 0;
        if (StatUtils.hasStat(wep, "DMG")) {
            dmg = RandomUtils.random(StatUtils.getMinDamage(wep), StatUtils.getMaxDamage(wep));
            if (StatUtils.hasStat(wep, "ICE DMG")) {
                dmg += StatUtils.getStatFromLore(wep, "ICE DMG: +", "");
                mob.getWorld().playEffect(mob.getEyeLocation(), Effect.POTION_BREAK, 8194);
                mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (20 * 1), 0));
            }
            if (StatUtils.hasStat(wep, "FIRE DMG")) {
                dmg += StatUtils.getStatFromLore(wep, "FIRE DMG: +", "");
                mob.setFireTicks(10);
            }
            if (StatUtils.hasStat(wep, "PURE DMG")) {
                dmg += StatUtils.getStatFromLore(wep, "PURE DMG: +", "");
            }
            if (StatUtils.hasStat(wep, "POISON DMG")) {
                dmg += StatUtils.getStatFromLore(wep, "POISON DMG: +", "");
                mob.getWorld().playEffect(mob.getEyeLocation(), Effect.POTION_BREAK, 40);
                mob.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10, 1));
            }
            if (StatUtils.hasStat(wep, "CRITICAL HIT")) {
                crit += StatUtils.getStatFromLore(wep, "CRITICAL HIT: ", "%");
            }
            if (Utils.isAxe(wep)) {
                crit += 5;
            }
            if (crit > 0) {
                Random r = new Random();
                if (r.nextInt(100) <= crit) {
                    dmg *= 3;
                    gamePlayer.getPlayer().playSound(gamePlayer.getPlayer().getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 1.0F, 0.65F);
                }
            }


        } else {
            dmg = 1.0D;
        }
        return dmg;
    }

    public double getFinalDMGPlayer(GamePlayer gamePlayer, LivingEntity mob) {
        Player p = gamePlayer.getPlayer();
        if ((RegionUtils.getZone(mob.getLocation()) == Zone.SAFE) || (RegionUtils.getZone(p.getLocation()) == Zone.SAFE)) {
            return -1;
        }
        if ((RegionUtils.getZone(mob.getLocation()) == Zone.WILDERNESS) || (RegionUtils.getZone(p.getLocation()) == Zone.WILDERNESS)) {
            return -1;
        }
        if (gamePlayer.isTogglePvP()) {
            return -1;
        }
        Alignment mobAlignment = GameAPI.getGamePlayer((Player) mob).getAlignment();
        if ((mobAlignment == Alignment.LAWFUL) && (gamePlayer.isToggleChaos())) {
            return -1;
        }
        ItemStack wep = gamePlayer.getWeapon();
        double dmg = 0.0D;
        double crit = 0;
        if (StatUtils.hasStat(wep, "DMG")) {
            dmg = RandomUtils.random(StatUtils.getMinDamage(wep), StatUtils.getMaxDamage(wep));
            if (StatUtils.hasStat(wep, "ICE DMG")) {
                dmg += StatUtils.getStatFromLore(wep, "ICE DMG: +", "");
                mob.getWorld().playEffect(mob.getEyeLocation(), Effect.POTION_BREAK, 8194);
                mob.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, (20 * 1), 0));
            }
            if (StatUtils.hasStat(wep, "FIRE DMG")) {
                dmg += StatUtils.getStatFromLore(wep, "FIRE DMG: +", "");
                mob.setFireTicks(10);
            }
            if (StatUtils.hasStat(wep, "PURE DMG")) {
                dmg += StatUtils.getStatFromLore(wep, "PURE DMG: +", "");
            }
            if (StatUtils.hasStat(wep, "POISON DMG")) {
                dmg += StatUtils.getStatFromLore(wep, "POISON DMG: +", "");
                mob.getWorld().playEffect(mob.getEyeLocation(), Effect.POTION_BREAK, 40);
                mob.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 10, 1));
            }
            if (StatUtils.hasStat(wep, "CRITICAL HIT")) {
                crit += StatUtils.getStatFromLore(wep, "CRITICAL HIT: ", "%");
            }
            if (Utils.isAxe(wep)) {
                crit += 5;
            }
            if (crit > 0) {
                Random r = new Random();
                if (r.nextInt(100) <= crit) {
                    dmg *= 3;
                    gamePlayer.getPlayer().playSound(gamePlayer.getPlayer().getLocation(), Sound.BLOCK_WOOD_BUTTON_CLICK_ON, 1.0F, 0.65F);
                }
            }


        } else {
            dmg = 1.0D;
        }
        return dmg;
    }

    public void task() {
        AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    GamePlayer gp = GameAPI.getGamePlayer(player);
                    if (gp.getCombatTime() > 0) {
                        gp.setCombatTime((gp.getCombatTime() - 1));
                        if (gp.getCombatTime() == 0) {
                            gp.sendAction("&aLeaving Combat");
                            return;
                        }
                    }
                    if (gp.getAlignment() == Alignment.CHAOTIC) {
                        if (gp.getChaoticTime() > 0) {
                            gp.setChaoticTime((gp.getChaoticTime() - 1));
                            if (gp.getChaoticTime() == 0) {
                                addLawful(gp);
                                return;
                            }
                        }
                    }
                    if (gp.getAlignment() == Alignment.NEUTRAL) {
                        if (gp.getNeutralTime() > 0) {
                            gp.setNeutralTime((gp.getNeutralTime() - 1));
                            if (gp.getNeutralTime() == 0) {
                                addLawful(gp);
                                return;
                            }
                        }
                    }
                });
            }
        }, 20L, 20L);
    }

    public boolean isPlayer(Entity entity) {
        if (entity instanceof Arrow) {
            Arrow arrow = (Arrow) entity;
            return (arrow.getShooter() instanceof Player);
        }
        if (entity instanceof Player) {
            return true;
        }
        if (entity instanceof Projectile) {
            Projectile projectile = (Projectile) entity;
            return (projectile.getShooter() instanceof Player);
        }
        return false;
    }

    public GamePlayer getGamePlayer(Entity entity) {
        return (isPlayer(entity)) ? GameAPI.getGamePlayer((Player) entity) : null;
    }

    public Player getPlayer(Entity entity) {
        return (isPlayer(entity)) ? ((Player) entity) : null;
    }

    public void damageTask() {
       AtherialRunnable.getInstance().runRepeatingTask(new Runnable() {
           @Override
           public void run() {
               MobBuilder.mobArmors.keySet().forEach(mob -> {
                   MobArmor mobArmor = MobBuilder.mobArmors.get(mob);
                   Bukkit.getOnlinePlayers().forEach(player -> {
                       if (mob.getLocation().distance(player.getLocation()) <= 1) {
                           GamePlayer gp = GameAPI.getGamePlayer(player);
                           player.playEffect(EntityEffect.HURT);
                           player.setVelocity(mob.getLocation().getDirection().multiply(0.3));
                           int dmg = MobBuilder.getDamageBasedOnMobArmor(mobArmor);
                           tag(gp.getPlayer());
                           gp.getPlayer().playSound(mob.getLocation(), Sound.ENTITY_GENERIC_HURT, 1.0F, 1.0F);
                           if (dmg >= gp.getPlayer().getHealth()) {
                               gp.getPlayer().damage(gp.getPlayer().getHealth());
                               if (gp.isToggleDebug()) {
                                   gp.msg("&c            -" + (int) dmg + "&lHP &7[-0%A ➜ -0&lDMG&7] &a[0&lHP&a]");
                               }
                               String msg = gp.getRank().getChatPrefix(gp.getPlayer()) + "&f was killed a(n) &r" + mobArmor.getName();
                               Bukkit.getServer().broadcastMessage(Utils.colorCodes(msg));
                           } else {
                               gp.getPlayer().setHealth((gp.getPlayer().getHealth() - dmg));
                               if (gp.isToggleDebug()) {
                                   gp.msg("&c            -" + (int) dmg + "&lHP &7[-0%A ➜ -0&lDMG&7] &a[" + gp.getHp() + "&lHP&a]");
                               }
                           }
                       }
                   });
               });
           }
       }, GameConstants.CPS, GameConstants.CPS);
    }

    @EventHandler
    public void onFire(EntityCombustEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if ((e.getDamager() instanceof Player) && (e.getEntity() instanceof Player)) {
            e.setCancelled(true);
            GamePlayer attacker = getGamePlayer(e.getDamager());
            GamePlayer gpMob = getGamePlayer(e.getEntity());
            LivingEntity mob = (LivingEntity) e.getEntity();
            double dmg = getFinalDMGPlayer(attacker, mob);
            if (dmg == -1) {
                return;
            }
            mob.setVelocity(attacker.getPlayer().getLocation().getDirection().multiply(0.4));
            mob.playEffect(EntityEffect.HURT);
            attacker.getPlayer().playSound(mob.getLocation(), Sound.ENTITY_GENERIC_HURT, 1.0F, 1.0F);
            if (attacker.getCombatTime() == 0) {
                attacker.sendAction("&c&lEntering Combat");
            }
            EnergyHandler.removeEnergyFromPlayerAndUpdate(attacker.getUniqueId(), EnergyHandler.getWeaponSwingEnergyCost(attacker.getWeapon()));
            attacker.setCombatTime(10);
            boolean dead = false;
            tag(attacker.getPlayer());
            tag(gpMob.getPlayer());
            if (dmg >= mob.getHealth()) {
                mob.damage(mob.getHealth());
                if (gpMob.isTogglePvP()) {
                    gpMob.msg("&c            -" + (int) dmg + "&lHP &7[-0%A ➜ -0&lDMG&7] &a[0&lHP&a]");
                }
                if (attacker.isTogglePvP()) {
                    attacker.msg("&c            " + (int) dmg + "&c&l DMG ➜ &c" + gpMob.getName());
                }
                dead = true;
            } else {
                mob.setHealth((mob.getHealth() - dmg));
                mob.damage(0);
                if (gpMob.isTogglePvP()) {
                    gpMob.msg("&c            -" + (int) dmg + "&lHP &7[-0%A ➜➜ -0&lDMG&7] &a[" + gpMob.getHp() + "&lHP&a]");
                }
                if (attacker.isTogglePvP()) {
                    attacker.msg("&c            " + (int) dmg + "&c&l DMG ➜ &c" + gpMob.getName());
                }
            }
            testAlignment(attacker, gpMob, dead);
            if (dead) {
                String msg = gpMob.getRank().getPrefix() + " &fwas killed by &r" + attacker.getRank().getPrefix() + "&f with a(n) " + attacker.getPlayer().getItemInHand().getItemMeta().getDisplayName();
                Bukkit.getServer().broadcastMessage(Utils.colorCodes(msg));
            }
        }
        if ((isPlayer(e.getDamager()) && (!isPlayer(e.getEntity())))) {
            e.setCancelled(true);
            GamePlayer attacker = getGamePlayer(e.getDamager());
            LivingEntity mob = (LivingEntity) e.getEntity();
            double dmg = getFinalDMGMob(attacker, mob);
            if (dmg == -1) {
                return;
            }
            EnergyHandler.removeEnergyFromPlayerAndUpdate(attacker.getUniqueId(), EnergyHandler.getWeaponSwingEnergyCost(attacker.getWeapon()));
            tag(attacker.getPlayer());
            mob.setVelocity(attacker.getPlayer().getLocation().getDirection().multiply(0.4));
            mob.playEffect(EntityEffect.HURT);
            attacker.getPlayer().playSound(mob.getLocation(), Sound.ENTITY_GENERIC_HURT, 1.0F, 1.0F);
            if (dmg >= mob.getHealth()) {
                mob.damage(mob.getHealth());
                if (attacker.isToggleDebug()) {
                    attacker.msg("&c            " + (int) dmg + "&c&l DMG ➜ &r" + MobBuilder.mobArmors.get(mob).getName() + " [0]");
                }
            } else {
                mob.setHealth((mob.getHealth() - dmg));
                mob.damage(0);
                if (attacker.isTogglePvP()) {
                    attacker.msg("&c            " + (int) dmg + "&c&l DMG ➜ &r" + MobBuilder.mobArmors.get(mob).getName() + " [" + (int) mob.getHealth() + "]");
                }
            }
        }
    }

    private void testAlignment(GamePlayer attacker, GamePlayer gpMob, boolean isDead) {
        Alignment mob = gpMob.getAlignment();
        Alignment alignment = attacker.getAlignment();
        if ((alignment == Alignment.LAWFUL) && (!isDead)) {
            addNeutral(attacker);
            return;
        } else if ((alignment == Alignment.NEUTRAL) && (isDead) && (mob == Alignment.LAWFUL)) {
            addChaotic(attacker, gpMob);
            return;
        } else if ((alignment == Alignment.CHAOTIC) && (isDead) && (mob == Alignment.CHAOTIC)) {
            addChaotic(attacker, gpMob);
            return;
        } else if ((alignment == Alignment.LAWFUL) && (isDead) && (mob == Alignment.LAWFUL)) {
            addChaotic(attacker, gpMob);
            return;
        } else if ((alignment == Alignment.NEUTRAL) && (isDead) && (mob == Alignment.CHAOTIC)) {
            addNeutral(attacker);
            return;
        } else if ((alignment == Alignment.NEUTRAL) && (!isDead)) {
            addNeutral(attacker);
            return;
        } else if ((alignment == Alignment.CHAOTIC) && (!isDead)) {
            return;
        }
    }

    public void addChaotic(GamePlayer gp, GamePlayer killed) {
        Alignment alignment = gp.getAlignment();
        Player p = gp.getPlayer();
        switch (alignment) {
            case LAWFUL:
                gp.setAlignment(Alignment.CHAOTIC);
                gp.setChaoticTime(GameConstants.CHAOTIC_TIME);
                p.sendMessage("          §c* YOU ARE NOW §lCHAOTIC §cALIGNMENT *");
                p.sendMessage("§7If you are killed while chaotic, you will lose enerything");
                p.sendMessage("§7in your inventory. Chaotic alignment will expire 5 minutes after");
                p.sendMessage("§7your last player kill.");
                p.sendMessage("          §c* YOU ARE NOW §lCHAOTIC §cALIGNMENT *");
                p.sendMessage("§cLAWFUL player slain, §l+"+GameConstants.CHAOTIC_TIME +"s §cadded to Chaotic timer");
                break;
            case NEUTRAL:
                gp.setAlignment(Alignment.CHAOTIC);
                gp.setChaoticTime(GameConstants.CHAOTIC_TIME);
                p.sendMessage("          §c* YOU ARE NOW §lCHAOTIC §cALIGNMENT *");
                p.sendMessage("§7If you are killed while chaotic, you will lose enerything");
                p.sendMessage("§7in your inventory. Chaotic alignment will expire 5 minutes after");
                p.sendMessage("§7your last player kill.");
                p.sendMessage("          §c* YOU ARE NOW §lCHAOTIC §cALIGNMENT *");
                p.sendMessage("§cLAWFUL player slain, §l+"+GameConstants.CHAOTIC_TIME +"s §cadded to Chaotic timer");
                break;
            case CHAOTIC:
                switch (killed.getAlignment()) {
                    case LAWFUL:
                        p.sendMessage("§cLAWFUL player slain, §l+" + GameConstants.CHAOTIC_TIME + "s §cadded to Chaotic timer");
                        gp.setAlignment(Alignment.CHAOTIC);
                        gp.setChaoticTime((gp.getChaoticTime() + GameConstants.CHAOTIC_TIME));
                        break;
                    case NEUTRAL:
                        break;
                    case CHAOTIC:
                        p.sendMessage("§cCHAOTIC player slain, §l-" + GameConstants.CHAOTIC_TIME_LOST + "s §cremoved from Chaotic timer");
                        if ((gp.getChaoticTime() - GameConstants.CHAOTIC_TIME_LOST) > 0) {
                            gp.setChaoticTime((gp.getChaoticTime() - GameConstants.CHAOTIC_TIME_LOST));
                            gp.setAlignment(Alignment.CHAOTIC);
                            break;
                        } else {
                            addNeutral(gp);
                            break;
                        }
                }
                break;
        }
    }

    public void addLawful(GamePlayer gp) {
        if (gp.getAlignment() != Alignment.LAWFUL) {
            Player p = gp.getPlayer();
            p.sendMessage("          §a* YOU ARE NOW §lLAWFUL §aALIGNMENT *");
            p.sendMessage("§7While lawful, you will not lose any equiped armor on death.");
            p.sendMessage("§7Any players who kill you while you're lawfully aligned will");
            p.sendMessage("§7become chaotic.");
            p.sendMessage("          §a* YOU ARE NOW §lLAWFUL §aALIGNMENT *");
            gp.setNeutralTime(0);
            gp.setChaoticTime(0);
            gp.setAlignment(Alignment.LAWFUL);
            return;
        }
    }

    public void addNeutral(GamePlayer gp) {
        Alignment alignment = gp.getAlignment();
        Player p = gp.getPlayer();
        switch (alignment) {
            case LAWFUL:
                gp.setAlignment(Alignment.NEUTRAL);
                gp.setNeutralTime(120);
                p.sendMessage("          §e* YOU ARE NOW §e§lNEUTRAL §eALIGNMENT *");
                p.sendMessage("§7While neutral, players who kill you will not become chaotic. You");
                p.sendMessage("§7have a 50% chance of dropping your weapon, and a 25%");
                p.sendMessage("§7chance of dropping each piece of equiped armor on death.");
                p.sendMessage("§7Neutral alignment will expire 2 minute after last hit on player.");
                p.sendMessage("          §e* YOU ARE NOW §e§lNEUTRAL §eALIGNMENT *");
                break;
            case NEUTRAL:
                gp.setNeutralTime(120);
                break;
            case CHAOTIC:
                gp.setAlignment(Alignment.NEUTRAL);
                gp.setNeutralTime(120);
                p.sendMessage("          §e* YOU ARE NOW §e§lNEUTRAL §eALIGNMENT *");
                p.sendMessage("§7While neutral, players who kill you will not become chaotic. You");
                p.sendMessage("§7have a 50% chance of dropping your weapon, and a 25%");
                p.sendMessage("§7chance of dropping each piece of equiped armor on death.");
                p.sendMessage("§7Neutral alignment will expire 2 minute after last hit on player.");
                p.sendMessage("          §e* YOU ARE NOW §e§lNEUTRAL §eALIGNMENT *");
                break;
            default:
                break;
        }
    }
}
