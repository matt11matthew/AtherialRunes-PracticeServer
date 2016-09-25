package net.atherialrunes.practiceserver.api.handler.handlers.player;

import lombok.Data;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;
import net.atherialrunes.practiceserver.api.handler.database.EnumData;
import net.atherialrunes.practiceserver.api.handler.database.EnumOperators;
import net.atherialrunes.practiceserver.api.handler.handlers.party.Party;
import net.atherialrunes.practiceserver.api.handler.handlers.pvp.Alignment;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import net.atherialrunes.practiceserver.utils.InventoryUtils;
import net.atherialrunes.practiceserver.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.UUID;

/**
 * Created by Matthew E on 9/21/2016.
 */
@Data
public class GamePlayer {

    private String name;
    private UUID uniqueId;
    private long firstLogin;
    private Rank rank;
    private Player player = null;
    private int gems;
    private Party party = null;
    private String chatChannel = "local";
    private int hp;
    private int foodLevel;
    private float exp;
    private int level;
    private Location location;
    private int bankPageAmount;
    private int page_1_slots;
    private int page_2_slots;
    private int page_3_slots;
    private int page_4_slots;
    private Alignment alignment;
    private int neutralTime;
    private int chaoticTime;
    private int combatTime;
    private boolean save = true;
    private PlayerStatistics playerStatistics;
    private boolean newPlayer = false;

    public GamePlayer(Player player) {
        this.player = player;
        new GamePlayer(player.getName(), player.getUniqueId());
    }

    public GamePlayer(String name, UUID uuid) {
        this.name = name;
        this.uniqueId = uuid;
        this.firstLogin = (long) DatabaseAPI.getInstance().getData(EnumData.FIRST_LOGIN, uniqueId);
        this.rank = Rank.valueOf(DatabaseAPI.getInstance().getData(EnumData.RANK, uniqueId) + "");
        this.gems = (int) DatabaseAPI.getInstance().getData(EnumData.GEMS, uniqueId);
        this.hp = (int) DatabaseAPI.getInstance().getData(EnumData.HEALTH, uniqueId);
        this.foodLevel = (int) DatabaseAPI.getInstance().getData(EnumData.FOOD_LEVEL, uniqueId);
        this.exp = 0.0F;//(double) DatabaseAPI.getInstance().getData(EnumData.EXP, uniqueId);
        this.level = (int) DatabaseAPI.getInstance().getData(EnumData.LEVEL, uniqueId);
        this.bankPageAmount = (int) DatabaseAPI.getInstance().getData(EnumData.BANK_PAGE_AMOUNT, uniqueId);
        this.page_1_slots = (int) DatabaseAPI.getInstance().getData(EnumData.PAGE_1_SLOTS, uniqueId);
        this.page_2_slots = (int) DatabaseAPI.getInstance().getData(EnumData.PAGE_2_SLOTS, uniqueId);
        this.page_3_slots = (int) DatabaseAPI.getInstance().getData(EnumData.PAGE_3_SLOTS, uniqueId);
        this.page_4_slots = (int) DatabaseAPI.getInstance().getData(EnumData.PAGE_4_SLOTS, uniqueId);
        this.alignment = Alignment.valueOf(DatabaseAPI.getInstance().getData(EnumData.ALIGNMENT, uniqueId) + "");
        this.chaoticTime = (int) DatabaseAPI.getInstance().getData(EnumData.CHAOTIC_TIME, uniqueId);
        this.neutralTime = (int) DatabaseAPI.getInstance().getData(EnumData.NEUTRAL_TIME, uniqueId);
        this.combatTime = (int) DatabaseAPI.getInstance().getData(EnumData.COMBAT_TIME, uniqueId);
        this.newPlayer = (boolean) DatabaseAPI.getInstance().getData(EnumData.NEW_PLAYER, uniqueId);
        //this.location = getLocationNotParsed();
       // getPlayer().teleport(getLocation());
        setParty(null);
       // this.playerStatistics = new PlayerStatistics(this);
    }

    private Location getLocationNotParsed() {
        String location = getLocation().toString();
        return new Location(Bukkit.getWorld(location.split("world=")[1].split(",")[0]), Double.parseDouble(location.split("x=")[1].split(",")[0]), Double.parseDouble(location.split("y=")[1].split(",")[0]),Double.parseDouble(location.split("z=")[1].split(",")[0]));
    }

    public Player getPlayer() {
        if (player == null) {
            player = Bukkit.getPlayerExact(name);
        }
        return player;
    }

    public void upload() {
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.RANK, getRank().toString(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.GEMS, getGems(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.HEALTH, getHp(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.FOOD_LEVEL, getFoodLevel(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.EXP, getExp(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.LEVEL, getLevel(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.LOCATION, getLocationParsed(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.LEVEL, getLevel(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.BANK_PAGE_AMOUNT, getBankPageAmount(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.PAGE_1_SLOTS, getPage_1_slots(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.PAGE_2_SLOTS, getPage_2_slots(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.PAGE_3_SLOTS, getPage_3_slots(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.PAGE_4_SLOTS, getPage_4_slots(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.ALIGNMENT, getAlignment().toString(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.NEUTRAL_TIME, getNeutralTime(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.CHAOTIC_TIME, getChaoticTime(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.COMBAT_TIME, getCombatTime(), true);
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.NEW_PLAYER, false, true);
        //playerStatistics.upload();
    }

    private String getLocationParsed() {
        return "";
    }

    public void msg(String msg) {
        getPlayer().sendMessage(Utils.colorCodes(msg));
    }

    public ItemStack getWeapon() {
        return getPlayer().getEquipment().getItemInMainHand();
    }

    public Inventory getBankInventory(int page) {
        Inventory bank = null;
        try {
            switch (page) {
                case 1:
                    bank = InventoryUtils.convertStringToInventory(getPlayer(), DatabaseAPI.getInstance().getData(EnumData.BANK_INVENTORY_1, uniqueId) + "", "Bank Chest (1/" + getBankPageAmount() + ")", getBankSlots(1));
                    break;
                case 2:
                    bank = InventoryUtils.convertStringToInventory(getPlayer(), DatabaseAPI.getInstance().getData(EnumData.BANK_INVENTORY_2, uniqueId) + "", "Bank Chest (2/" + getBankPageAmount() + ")", getBankSlots(2));
                    break;
                case 3:
                    bank = InventoryUtils.convertStringToInventory(getPlayer(), DatabaseAPI.getInstance().getData(EnumData.BANK_INVENTORY_3, uniqueId) + "", "Bank Chest (3/" + getBankPageAmount() + ")", getBankSlots(3));
                    break;
                case 4:
                    bank = InventoryUtils.convertStringToInventory(getPlayer(), DatabaseAPI.getInstance().getData(EnumData.BANK_INVENTORY_4, uniqueId) + "", "Bank Chest (4/" + getBankPageAmount() + ")", getBankSlots(4));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            bank = Bukkit.createInventory(null, 54, "Bank Chest (" + page + "/" + getBankPageAmount() + ")");
        }
        return bank;
    }

    private int getBankSlots(int i) {
        switch (i) {
            case 1:
                return getPage_1_slots();
            case 2:
                return getPage_2_slots();
            case 3:
                return getPage_3_slots();
            case 4:
                return getPage_4_slots();
            default:
                break;
        }
        return 0;
    }

    public void uploadBank(Inventory inventory) {
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.BANK_INVENTORY_1, InventoryUtils.convertInventoryToString(getName(), inventory, false), true);
//        int page = Integer.parseInt(inventory.getTitle().split("\\(")[1].split("/")[0].trim());
//        switch (page) {
//            case 1:
//                DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.BANK_INVENTORY_1, InventoryUtils.convertInventoryToString(getName(), inventory, false), true);
//                break;
//            case 2:
//                DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.BANK_INVENTORY_2, InventoryUtils.convertInventoryToString(getName(), inventory, false), true);
//                break;
//            case 3:
//                DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.BANK_INVENTORY_3, InventoryUtils.convertInventoryToString(getName(), inventory, false), true);
//                break;
//            case 4:
//                DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, EnumData.BANK_INVENTORY_4, InventoryUtils.convertInventoryToString(getName(), inventory, false), true);
//                break;
//            default:
//                break;
//        }
    }

    public boolean isInCombat() {
        return (combatTime > 0);
    }

    public void sendAction(String msg) {
        Utils.sendActionBar(getPlayer(), Utils.colorCodes(msg));
    }

    public void fw() {
        Player player = getPlayer();
        Firework f = (Firework) player.getWorld().spawn(player.getLocation(), Firework.class);

        FireworkMeta fm = f.getFireworkMeta();
        fm.addEffect(FireworkEffect.builder()
                .flicker(false)
                .trail(true)
                .with(FireworkEffect.Type.BURST)
                .withColor(Color.ORANGE)
                .withFade(Color.WHITE)
                .build());
        fm.setPower(1);
        f.setFireworkMeta(fm);

    }

    public boolean isTogglePvP() {
        return (boolean) DatabaseAPI.getInstance().getData(EnumData.TOGGLE_PVP, uniqueId);
    }

    public boolean isToggleDebug() {
        return (boolean) DatabaseAPI.getInstance().getData(EnumData.TOGGLE_DEBUG, uniqueId);
    }

    public boolean isToggleChaos() {
        return (boolean) DatabaseAPI.getInstance().getData(EnumData.TOGGLE_CHAOTIC_PREVENTION, uniqueId);
    }

    public void wipe() {
        setSave(false);
        DatabaseAPI.getInstance().playerData.deleteOne(DatabaseAPI.getInstance().PLAYERS.get(uniqueId));
        getPlayer().kickPlayer(Utils.colorCodes("&cYou have been wiped!"));
    }

    public Object getStatistic(String data) {
        return DatabaseAPI.getInstance().getData(data, uniqueId);
    }

    public void setStatistic(String name, Object value) {
        DatabaseAPI.getInstance().update(getUniqueId(), EnumOperators.$SET, "statistics." + name, value, true);
    }
}
