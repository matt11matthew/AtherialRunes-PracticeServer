package net.atherialrunes.practiceserver.api.handler.database;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import net.atherialrunes.practiceserver.Constants;
import net.atherialrunes.practiceserver.api.handler.Handler;
import net.atherialrunes.practiceserver.api.handler.database.concurrent.SingleUpdateQuery;
import net.atherialrunes.practiceserver.api.handler.database.concurrent.UpdateThread;
import net.atherialrunes.practiceserver.api.handler.handlers.rank.Rank;
import org.bson.Document;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
/**
 * Created by Matthew E on 9/21/2016.
 */
public class DatabaseAPI implements Handler {

    static DatabaseAPI instance = null;

    public static MongoClient mongoClient = null;
    public static MongoClientURI mongoClientURI = null;
    public static MongoDatabase database = null;

    public volatile ConcurrentHashMap<UUID, Document> PLAYERS = new ConcurrentHashMap<>();

    private final ExecutorService serverExecutorThread = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("MONGODB Server Collection Thread").build());

    public static MongoCollection<Document> playerData = null;

    public static DatabaseAPI getInstance() {
        if (instance == null) {
            instance = new DatabaseAPI();
        }
        return instance;
    }

    @Override
    public void onLoad() {
        mongoClientURI = new MongoClientURI(Constants.MONGO_DATABASE_URI);
        mongoClient = new MongoClient(mongoClientURI);
        database = mongoClient.getDatabase("practiceserver");
        playerData = database.getCollection("player_data");
        new UpdateThread().start();
    }

    @Override
    public void onUnload() {
        serverExecutorThread.shutdown();
    }

    public Object getData(EnumData data, UUID uuid) {
        Document doc;

        if (PLAYERS.containsKey(uuid)) {
            doc = PLAYERS.get(uuid);
        } else {
            long currentTime = 0;
            doc = playerData.find(Filters.eq("info.uuid", uuid.toString())).first();
        }

        String[] key = data.getKey().split("\\.");
        Document rootDoc = (Document) doc.get(key[0]);
        if (rootDoc == null) return null;

        Object dataObj = rootDoc.get(key[1]);

        if (dataObj == null) return null;
        Class<?> clazz = dataObj.getClass();

        return rootDoc.get(key[1], clazz);
    }

    public boolean requestPlayer(UUID uuid, String ign) {
        Document doc = playerData.find(Filters.eq("info.uuid", uuid.toString())).first();

        if (doc == null) {
            addNewPlayer(uuid, ign);
            System.out.println("DOCUMENT IS NULL, ADDING NEW PLAYER");
        } else {
            PLAYERS.put(uuid, doc);
            System.out.println("PUTTING PLAYER UNIQUE WITH DOCUMENT");
        }
        System.out.println("Returning true");
        return true;
    }

    private void addNewPlayer(UUID uuid, String ign) {
        Document newPlayerDocument =
                new Document("info",
                        new Document("uuid", uuid.toString())
                                .append("ign", ign)
                                .append("firstLogin", System.currentTimeMillis() / 1000L)
                                .append("rank", Rank.DEFAULT.toString())
                                .append("gems", 0)
                                .append("health", 100)
                                .append("foodLevel", 10)
                                .append("exp", 0)
                                .append("level", 0)
                                .append("location", "")
                                .append("bank_inventory_1", "")
                                .append("bank_inventory_2", "")
                                .append("bank_inventory_3", "")
                                .append("bank_inventory_4", "")
                                .append("bank_page_amount", 1)
                                .append("bank_page_1_slots", 54)
                                .append("bank_page_2_slots", 0)
                                .append("bank_page_3_slots", 0)
                                .append("bank_page_4_slots", 0))
                        .append("ban",
                                new Document("banned", 0L)
                                        .append("banReason", "")
                                        .append("unbanReason", "")
                                        .append("whoUnbanned", "")
                                        .append("whoBanned", ""))
                        .append("mute",
                                new Document("muted", 0L)
                                        .append("muteReason", "")
                                        .append("unmuteReason", "")
                                        .append("whoUnmuted", "")
                                        .append("whoMuted", ""));
        playerData.insertOne(newPlayerDocument);
        requestPlayer(uuid, ign);
    }

    public void update(UUID uuid, EnumOperators EO, EnumData variable, Object object, boolean async, Consumer<UpdateResult> doAfterOptional) {
        if (PLAYERS.containsKey(uuid)) { // update local data
            Document localDoc = PLAYERS.get(uuid);
            String[] key = variable.getKey().split("\\.");
            Document rootDoc = (Document) localDoc.get(key[0]);
            Object data = rootDoc.get(key[1]);
            switch (EO) {
                case $SET:
                    rootDoc.put(key[1], object);
                    break;
                case $INC:
                    if (data instanceof Integer)
                        rootDoc.put(key[1], ((Integer) object) + ((Integer) data));
                    else if (data instanceof Double)
                        rootDoc.put(key[1], ((Double) object) + ((Double) data));
                    else if (data instanceof Float)
                        rootDoc.put(key[1], ((Float) object) + ((Float) data));
                    else if (data instanceof Long)
                        rootDoc.put(key[1], ((Long) object) + ((Long) data));
                    break;
                case $MUL:
                    if (data instanceof Integer)
                        rootDoc.put(key[1], ((Integer) object) * ((Integer) data));
                    else if (data instanceof Double)
                        rootDoc.put(key[1], ((Double) object) * ((Double) data));
                    else if (data instanceof Float)
                        rootDoc.put(key[1], ((Float) object) * ((Float) data));
                    else if (data instanceof Long)
                        rootDoc.put(key[1], ((Long) object) * ((Long) data));
                    break;
                case $PUSH:
                    ((ArrayList) data).add(object);
                    break;
                case $PULL:
                    ((ArrayList) data).remove(object);
                    break;
                default:
                    break;
            }
        }

        if (async)
            UpdateThread.CONCURRENT_QUERIES.add(new SingleUpdateQuery<>(Filters.eq("info.uuid", uuid.toString()), new Document(EO.getUO(), new Document(variable.getKey(), object)), doAfterOptional));
        else {
            UpdateResult result = playerData.updateOne(Filters.eq("info.uuid", uuid.toString()), new Document(EO.getUO(), new Document(variable.getKey(), object)), UpdateThread.uo);
            if (doAfterOptional != null) {
                doAfterOptional.accept(result);
            }
        }
    }

    /**
     * {@link #update(UUID, EnumOperators, EnumData, Object, boolean, Consumer)}
     */
    public void update(UUID uuid, EnumOperators EO, EnumData variable, Object object, boolean async) {
        update(uuid, EO, variable, object, async, null);
    }
}
