package net.atherialrunes.practiceserver.api.handler.database;

public enum  EnumData {

    UUID("info.uuid");

    private String key;

    EnumData(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
