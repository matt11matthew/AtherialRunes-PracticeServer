package net.atherialrunes.practiceserver.api.handler.database.concurrent;

import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.result.UpdateResult;
import net.atherialrunes.practiceserver.api.handler.database.DatabaseAPI;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UpdateThread extends Thread {

    public static Queue<SingleUpdateQuery<UpdateResult>> CONCURRENT_QUERIES = new ConcurrentLinkedQueue<>();
    public final static UpdateOptions uo = new UpdateOptions().upsert(true);

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(250L);
            } catch (InterruptedException ignored) {}

            while (!CONCURRENT_QUERIES.isEmpty()) {
                SingleUpdateQuery<UpdateResult> query = CONCURRENT_QUERIES.poll();
                if (query == null) {
                    continue;
                }

                UpdateResult result = DatabaseAPI.getInstance().playerData.updateOne(query.getSearchQuery(), query.getNewDocument(), uo);

                if (result.wasAcknowledged()) {
                    if (query.getDoAfterOptional() != null) {
                        query.getDoAfterOptional().accept(result);
                    }
                }
            }
        }
    }
}