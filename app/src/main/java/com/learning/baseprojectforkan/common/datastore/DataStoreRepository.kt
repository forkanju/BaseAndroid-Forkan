package com.learning.baseprojectforkan.common.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

const val PREFERENCE_NAME = "my_preference"

class DataStoreRepository(context: Context) {

    private object PreferenceKeys {
        val name = preferencesKey<String>("my_name")
    }

    //initialize datastore
    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = PREFERENCE_NAME
    )


    suspend fun saveNameToDataStore(name: String) {
        dataStore.edit { preference ->
            preference[PreferenceKeys.name] = name
        }
    }


    val readNameFromDataStore: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStore", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val myName = preference[PreferenceKeys.name] ?: "no data found!"
            myName
        }

}