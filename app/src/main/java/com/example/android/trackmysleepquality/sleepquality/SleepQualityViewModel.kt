/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.trackmysleepquality.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.trackmysleepquality.database.SleepDatabaseDao
import kotlinx.coroutines.*

//TODO (03) Using the code in SleepTrackerViewModel for reference, create SleepQualityViewModel
//with coroutine setup and navigation setup.
class SleepQualityViewModel(
        private val sleepNightKey: Long = 0L,
        val database: SleepDatabaseDao): ViewModel() {

    private val viewModleJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModleJob)

    // for navigate back to SleepTrackFragment
    private val _navigateToSleepTrack = MutableLiveData<Boolean?>()
    val navigateToSleepTracker: LiveData<Boolean?>
        get() = _navigateToSleepTrack

    fun doneNavigating() {
        _navigateToSleepTrack.value = null
    }

    //TODO (04) implement the onSetSleepQuality() click handler using coroutines.
    //Using coroutine in uiScope set sleep quality and update to database
    fun onSetSleepQuality(quality: Int) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val tonight = database.get(sleepNightKey) ?: return@withContext
                tonight.sleepQuality = quality
                database.update(tonight)
            }
            _navigateToSleepTrack.value = true
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModleJob.cancel()
    }




}


