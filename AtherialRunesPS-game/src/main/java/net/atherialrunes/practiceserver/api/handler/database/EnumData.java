package net.atherialrunes.practiceserver.api.handler.database;

/**
 * Created by Matthew E on 9/21/2016.
 */
public enum  EnumData {

    UUID("info.uuid"),
    IGN("info.ign"),
    FIRST_LOGIN("info.firstLogin"),
    RANK("info.rank"),
    GEMS("info.gems"),
    HEALTH("info.health"),
    FOOD_LEVEL("info.foodLevel"),
    EXP("info.exp"),
    LEVEL("info.level"),
    LOCATION("info.location"),
    NEW_PLAYER("info.newplayer"),

    BANK_INVENTORY_1("info.bank_inventory_1"),
    BANK_INVENTORY_2("info.bank_inventory_2"),
    BANK_INVENTORY_3("info.bank_inventory_3"),
    BANK_INVENTORY_4("info.bank_inventory_4"),

    PAGE_1_SLOTS("info.bank_page_1_slots"),
    PAGE_2_SLOTS("info.bank_page_2_slots"),
    PAGE_3_SLOTS("info.bank_page_3_slots"),
    PAGE_4_SLOTS("info.bank_page_4_slots"),

    BANK_PAGE_AMOUNT("info.bank_page_amount"),

    ALIGNMENT("info.alignment"),

    NEUTRAL_TIME("info.neutralTime"),
    CHAOTIC_TIME("info.chaoticTime"),
    COMBAT_TIME("info.combatTime"),

    TOGGLE_DEBUG("toggles.debug"),
    TOGGLE_PVP("toggles.pvp"),
    TOGGLE_CHAOTIC_PREVENTION("toggles.chaoticPrevention"),
    ;

    private String key;

    EnumData(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
