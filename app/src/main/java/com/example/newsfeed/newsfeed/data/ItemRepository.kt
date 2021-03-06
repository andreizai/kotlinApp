package com.example.newsfeed.newsfeed.data

import android.util.Log
import com.example.newsfeed.core.TAG
import com.example.newsfeed.newsfeed.data.remote.ItemApi

object ItemRepository {
    private var cachedItems: MutableList<Item>? = null;

    suspend fun loadAll(): List<Item> {
        Log.i(TAG, "loadAll")
        if (cachedItems != null) {
            return cachedItems as List<Item>;
        }
        cachedItems = mutableListOf()
        val items = ItemApi.service.find()
        cachedItems?.addAll(items)
        return cachedItems as List<Item>
    }

    suspend fun load(itemId: String): Item {
        Log.i(TAG, "load")
        val item = cachedItems?.find { it._id == itemId }
        if (item != null) {
            return item
        }
        return ItemApi.service.read(itemId)
    }

    suspend fun save(item: Item): Item {
        Log.i(TAG, "save")
        val createdItem = ItemApi.service.create(item)
        cachedItems?.add(createdItem)
        return createdItem
    }

    suspend fun update(item: Item): Item {
        Log.i(TAG, "update")
        val updatedItem = ItemApi.service.update(item._id, item)
        val index = cachedItems?.indexOfFirst { it._id == item._id }
        if (index != null) {
            cachedItems?.set(index, updatedItem)
        }
        return updatedItem
    }
}