package com.example.final_part2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mongodb.MongoClient
import org.bson.Document


class MongoDBHandler : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mongo_dbhandler)
        // Create a MongoClient instance
        val client = MongoClient("mongodb+srv://afbanales:PassWord%23123@cluster0.uwi1rkq.mongodb.net/")

        // Get the database named "mydb"
        val database = client.getDatabase("mydb2")

        // Get the collection named "users"
        val collection = database.getCollection("users")

        // Insert a new document into the collection
        val document = Document("name", "John Doe")
        collection.insertOne(document)
    }
}
