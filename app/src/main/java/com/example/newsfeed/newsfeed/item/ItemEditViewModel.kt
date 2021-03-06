package com.example.newsfeed.newsfeed.item

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.newsfeed.core.TAG
import com.example.newsfeed.newsfeed.data.Item
import com.example.newsfeed.newsfeed.data.ItemRepository

class ItemEditViewModel : ViewModel() {
    private val mutableItem = MutableLiveData<Item>().apply { value = Item("", "", "", "", 1, false) }
    private val mutableFetching = MutableLiveData<Boolean>().apply { value = false }
    private val mutableCompleted = MutableLiveData<Boolean>().apply { value = false }
    private val mutableException = MutableLiveData<Exception>().apply { value = null }

    val item: LiveData<Item> = mutableItem
    val fetching: LiveData<Boolean> = mutableFetching
    val fetchingError: LiveData<Exception> = mutableException
    val completed: LiveData<Boolean> = mutableCompleted

    fun loadItem(itemId: String) {
        viewModelScope.launch {
            Log.i(TAG, "loadItem...")
            mutableFetching.value = true
            mutableException.value = null
            try {
                mutableItem.value = ItemRepository.load(itemId)
                Log.i(TAG, "loadItem succeeded")
                mutableFetching.value = false
            } catch (e: Exception) {
                Log.w(TAG, "loadItem failed", e)
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }

    fun saveOrUpdateItem(title: String, content: String, ) {
        viewModelScope.launch {
            Log.i(TAG, "saveOrUpdateItem...");
            val item = mutableItem.value ?: return@launch
            item.title = title
            item.text = content
            mutableFetching.value = true
            mutableException.value = null
            try {
                if (item._id.isNotEmpty()) {
                    mutableItem.value = ItemRepository.update(item)
                } else {
                    mutableItem.value = ItemRepository.save(item)
                }
                Log.i(TAG, "saveOrUpdateItem succeeded");
                mutableCompleted.value = true
                mutableFetching.value = false
            } catch (e: Exception) {
                Log.w(TAG, "saveOrUpdateItem failed", e);
                mutableException.value = e
                mutableFetching.value = false
            }
        }
    }
}
