package net.atherialrunes.practiceserver.api.handler.database;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import net.atherialrunes.practiceserver.Constants;
import net.atherialrunes.practiceserver.api.handler.Handler;
import net.atherialrunes.practiceserver.api.handler.database.concurrent.UpdateThread;
import org.bson.Document;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                );
        playerData.insertOne(newPlayerDocument);
        requestPlayer(uuid, ign);
    }
}
