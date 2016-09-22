package net.atherialrunes.practiceserver.api.handler.database;

public enum  EnumData {

    UUID("info.uuid"),
    IGN("info.ign"),
    FIRST_LOGIN("info.firstLogin"),
    RANK("info.rank"),
    GEMS("info.gems");

    private String key;

    EnumData(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
