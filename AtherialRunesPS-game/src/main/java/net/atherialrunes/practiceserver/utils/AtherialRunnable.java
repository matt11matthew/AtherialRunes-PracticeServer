package net.atherialrunes.practiceserver.utils;

import net.atherialrunes.practiceserver.PracticeServer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by Matthew E on 9/21/2016.
 */
public class AtherialRunnable implements org.bukkit.scheduler.BukkitScheduler {

    static AtherialRunnable instance = null;

    public static AtherialRunnable getInstance() {
        if (instance == null) {
            instance = new AtherialRunnable();
        }
        return instance;
    }
    public int runRepeatingTask(Runnable runnable, long l, long l2) {
        return scheduleSyncRepeatingTask(PracticeServer.getInstance(), runnable, l, l2);
    }

    public int runAsyncRepeatingTask(Runnable runnable, long l, long l2) {
        return scheduleAsyncRepeatingTask(PracticeServer.getInstance(), runnable, l, l2);
    }

    @Override
    public <T> Future<T> callSyncMethod(Plugin arg0, Callable<T> arg1) {
        return Bukkit.getScheduler().callSyncMethod(arg0, arg1);
    }

    @Override
    public void cancelAllTasks() {
        Bukkit.getScheduler().cancelAllTasks();

    }

    @Override
    public void cancelTask(int arg0) {
        Bukkit.getScheduler().cancelTask(arg0);

    }

    @Override
    public void cancelTasks(Plugin arg0) {
        Bukkit.getScheduler().cancelTasks(arg0);

    }

    @Override
    public List<BukkitWorker> getActiveWorkers() {
        return Bukkit.getScheduler().getActiveWorkers();
    }

    @Override
    public List<BukkitTask> getPendingTasks() {
        return Bukkit.getScheduler().getPendingTasks();
    }

    @Override
    public boolean isCurrentlyRunning(int arg0) {
        return Bukkit.getScheduler().isCurrentlyRunning(arg0);
    }

    @Override
    public boolean isQueued(int arg0) {
        return Bukkit.getScheduler().isQueued(arg0);
    }

    @Override
    public BukkitTask runTask(Plugin arg0, Runnable arg1) throws IllegalArgumentException {
        return Bukkit.getScheduler().runTask(arg0, arg1);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BukkitTask runTask(Plugin arg0, BukkitRunnable arg1) throws IllegalArgumentException {
        return Bukkit.getScheduler().runTask(arg0, arg1);
    }

    @Override
    public BukkitTask runTaskAsynchronously(Plugin arg0, Runnable arg1) throws IllegalArgumentException {
        return Bukkit.getScheduler().runTaskAsynchronously(arg0, arg1);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BukkitTask runTaskAsynchronously(Plugin arg0, BukkitRunnable arg1) throws IllegalArgumentException {
        return Bukkit.getScheduler().runTaskAsynchronously(arg0, arg1);
    }

    @Override
    public BukkitTask runTaskLater(Plugin arg0, Runnable arg1, long arg2) throws IllegalArgumentException {
        return Bukkit.getScheduler().runTaskLater(arg0, arg1, arg2);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BukkitTask runTaskLater(Plugin arg0, BukkitRunnable arg1, long arg2) throws IllegalArgumentException {
        return Bukkit.getScheduler().runTaskLater(arg0, arg1, arg2);
    }

    @Override
    public BukkitTask runTaskLaterAsynchronously(Plugin arg0, Runnable arg1, long arg2) throws IllegalArgumentException {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(arg0, arg1, arg2);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BukkitTask runTaskLaterAsynchronously(Plugin arg0, BukkitRunnable arg1, long arg2) throws IllegalArgumentException {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(arg0, arg1, arg2);
    }

    @Override
    public BukkitTask runTaskTimer(Plugin arg0, Runnable arg1, long arg2, long arg3) throws IllegalArgumentException {
        return Bukkit.getScheduler().runTaskTimer(arg0, arg1, arg2, arg3);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BukkitTask runTaskTimer(Plugin arg0, BukkitRunnable arg1, long arg2, long arg3) throws IllegalArgumentException {
        return Bukkit.getScheduler().runTaskTimer(arg0, arg1, arg2, arg3);
    }

    @Override
    public BukkitTask runTaskTimerAsynchronously(Plugin arg0, Runnable arg1, long arg2, long arg3) throws IllegalArgumentException {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(arg0, arg1, arg2, arg3);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BukkitTask runTaskTimerAsynchronously(Plugin arg0, BukkitRunnable arg1, long arg2, long arg3) throws IllegalArgumentException {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(arg0, arg1, arg2, arg3);
    }

    @SuppressWarnings("deprecation")
    @Override
    public int scheduleAsyncDelayedTask(Plugin arg0, Runnable arg1) {
        return Bukkit.getScheduler().scheduleAsyncDelayedTask(arg0, arg1);
    }

    @SuppressWarnings("deprecation")
    @Override
    public int scheduleAsyncDelayedTask(Plugin arg0, Runnable arg1, long arg2) {
        return Bukkit.getScheduler().scheduleAsyncDelayedTask(arg0, arg1, arg2);
    }

    @SuppressWarnings("deprecation")
    @Override
    public int scheduleAsyncRepeatingTask(Plugin arg0, Runnable arg1, long arg2, long arg3) {
        return Bukkit.getScheduler().scheduleAsyncRepeatingTask(arg0, arg1, arg2, arg3);
    }

    @Override
    public int scheduleSyncDelayedTask(Plugin arg0, Runnable arg1) {
        return Bukkit.getScheduler().scheduleSyncDelayedTask(arg0, arg1);
    }

    @SuppressWarnings("deprecation")
    @Override
    public int scheduleSyncDelayedTask(Plugin arg0, BukkitRunnable arg1) {
        return Bukkit.getScheduler().scheduleSyncDelayedTask(arg0, arg1);
    }

    @Override
    public int scheduleSyncDelayedTask(Plugin arg0, Runnable arg1, long arg2) {
        return Bukkit.getScheduler().scheduleSyncDelayedTask(arg0, arg1, arg2);
    }

    @SuppressWarnings("deprecation")
    @Override
    public int scheduleSyncDelayedTask(Plugin arg0, BukkitRunnable arg1, long arg2) {
        return Bukkit.getScheduler().scheduleSyncDelayedTask(arg0, arg1, arg2);
    }

    @Override
    public int scheduleSyncRepeatingTask(Plugin arg0, Runnable arg1, long arg2, long arg3) {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(arg0, arg1, arg2, arg3);
    }

    @SuppressWarnings("deprecation")
    @Override
    public int scheduleSyncRepeatingTask(Plugin arg0, BukkitRunnable arg1, long arg2, long arg3) {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(arg0, arg1, arg2, arg3);
    }
}
