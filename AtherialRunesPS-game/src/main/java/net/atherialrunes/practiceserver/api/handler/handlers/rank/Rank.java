package net.atherialrunes.practiceserver.api.handler.handlers.rank;

public enum Rank {

    DEFAULT(0, "&7%name%: %msg%", "Default");

    private int id;
    private String placeHolder;
    private String name;

    Rank(int id, String placeHolder, String name) {
        this.id = id;
        this.placeHolder = placeHolder;
        this.name = name;
    }

    public String getPlaceHolder() {
        return placeHolder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
