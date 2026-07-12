package com.example

import com.mongodb.client.MongoClients
import org.bson.Document
import org.junit.Assert.*
import org.junit.Test
import java.io.File
import java.util.Properties

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testMongoDBConnection() {
    // 1. Locate .env.example file
    var envFile = File(".env.example")
    if (!envFile.exists()) {
      envFile = File("../.env.example")
    }
    if (!envFile.exists()) {
      envFile = File("../../.env.example")
    }

    assertTrue("Could not find .env.example file", envFile.exists())

    // 2. Parse MONGODB_URI manually from .env.example
    val lines = envFile.readLines()
    var mongoUri = ""
    for (line in lines) {
      if (line.startsWith("MONGODB_URI=")) {
        mongoUri = line.substringAfter("MONGODB_URI=").trim()
        break
      }
    }

    assertFalse("MONGODB_URI is empty or placeholder in .env.example", mongoUri.isEmpty() || mongoUri == "your-mongodb-connection-string-uri")

    println("Attempting to connect to MongoDB with URI: ${mongoUri.take(25)}...")

    // 3. Connect to MongoDB and execute ping
    try {
      MongoClients.create(mongoUri).use { client ->
        val database = client.getDatabase("admin")
        val pingCommand = Document("ping", 1)
        val pingResult = database.runCommand(pingCommand)
        
        println("MongoDB connection successful! Ping result: $pingResult")
        assertNotNull("Ping response should not be null", pingResult)
        assertEquals("Ping response ok should be 1.0 or 1", 1, (pingResult.get("ok") as? Number)?.toInt() ?: 0)
        
        // Also list databases to show connection is fully authorized and works
        val dbs = client.listDatabaseNames().toList()
        println("Available databases: $dbs")
      }
    } catch (e: Exception) {
      fail("Failed to connect to MongoDB: ${e.message}\n${e.stackTraceToString()}")
    }
  }
}

