package net.atherialrunes.practiceserver.api.handler.database.concurrent;

import org.bson.conversions.Bson;

import java.util.function.Consumer;

public class SingleUpdateQuery<T> {

    private final Bson searchQuery, newDocument;

    private final Consumer<T> doAfterOptional;

    /**
     * @param searchQuery     Search query
     * @param newDocument     New Document to replace
     * @param doAfterOptional Consumer task to do after query is complete.
     */
    public SingleUpdateQuery(Bson searchQuery, Bson newDocument, Consumer<T> doAfterOptional) {
        this.searchQuery = searchQuery;
        this.newDocument = newDocument;

        this.doAfterOptional = doAfterOptional;
    }

    public Bson getNewDocument() {
        return newDocument;
    }

    public Bson getSearchQuery() {
        return searchQuery;
    }

    public Consumer<T> getDoAfterOptional() {
        return doAfterOptional;
    }
}