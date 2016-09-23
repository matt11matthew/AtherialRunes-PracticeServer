package net.atherialrunes.practiceserver.api.handler.handlers.damage;

import net.atherialrunes.practiceserver.GameAPI;
import net.atherialrunes.practiceserver.api.handler.ListenerHandler;
import net.atherialrunes.practiceserver.api.handler.handlers.player.GamePlayer;
import net.atherialrunes.practiceserver.api.handler.handlers.zone.RegionUtils;
import net.atherialrunes.practiceserver.api.handler.handlers.zone.Zone;
import net.atherialrunes.practiceserver.utils.RandomUtils;
import net.atherialrunes.practiceserver.utils.StatUtils;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Effect;
import org.bukkit.EntityEffect;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class DamageHandler extends ListenerHandler {

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onUnload() {

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

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if ((isPlayer(e.getDamager()) && (!isPlayer(e.getEntity())))) {
            e.setCancelled(true);
            GamePlayer attacker = getGamePlayer(e.getDamager());
            LivingEntity mob = (LivingEntity) e.getEntity();
            double dmg = getFinalDMGMob(attacker, mob);
            if (dmg == -1) {
                return;
            }
            mob.setVelocity(attacker.getPlayer().getLocation().getDirection().multiply(0.4));
            mob.playEffect(EntityEffect.HURT);
            attacker.getPlayer().playSound(mob.getLocation(), Sound.ENTITY_GENERIC_HURT, 1.0F, 1.0F);
            if (dmg >= mob.getHealth()) {
                mob.playEffect(EntityEffect.DEATH);
                mob.damage(mob.getHealth());
                attacker.msg("&c            " + (int) dmg + "&c&l DMG -> &r" + mob.getCustomName() + " [0]");
            } else {
                mob.setHealth((mob.getHealth() - dmg));
                mob.damage(0);
                attacker.msg("&c            " + (int) dmg + "&c&l DMG -> &r" + mob.getCustomName() + " [" + (int) mob.getHealth() + "]");
            }
        }
    }
}
