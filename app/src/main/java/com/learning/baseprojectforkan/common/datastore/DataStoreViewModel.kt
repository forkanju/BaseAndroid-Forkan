package com.learning.baseprojectforkan.common.datastore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataStoreViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DataStoreRepository(application)
    val readFromDataStore = repository.readNameFromDataStore.asLiveData()

    fun saveToDataStore(name: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveNameToDataStore(name)
    }

}