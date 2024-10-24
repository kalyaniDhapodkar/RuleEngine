package com.ruleengine.utils;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static MongoDatabase database;

    public static MongoDatabase connect() {
        if (database == null) {
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            database = mongoClient.getDatabase("rule_engine_db");
        }
        return database;
    }
}
