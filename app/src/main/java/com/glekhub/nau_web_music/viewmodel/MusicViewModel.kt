package com.glekhub.nau_web_music.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glekhub.nau_web_music.Constants.AMOUNT
import com.glekhub.nau_web_music.Constants.CATEGORY
import com.glekhub.nau_web_music.Constants.TYPE
import com.glekhub.nau_web_music.repository.MusicRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.glekhub.nau_web_music.models.Result

class MusicViewModel : ViewModel() {

    private val repository = MusicRepository()

    private val _resultLiveData = MutableLiveData<List<Result>?>()
    val resultLiveData: LiveData<List<Result>?> = _resultLiveData

    private val _currentState = MutableLiveData(0)
    val currentState: LiveData<Int>
        get() = _currentState

    private val _currentQuestionCount = MutableLiveData(0)
    val currentQuestionCount: LiveData<Int>
        get() = _currentQuestionCount

    private val currentDifficulty = MutableLiveData("easy")

    private val _currentFragment = MutableLiveData(0)
    val currentFragment: LiveData<Int>
        get() = _currentQuestionCount

    fun getResult() {
        viewModelScope.launch {
            _resultLiveData.postValue(
                repository.getQuestion(AMOUNT, CATEGORY, currentDifficulty.value.toString(), TYPE)
            )
        }
    }

    fun refreshCount() {
        viewModelScope.launch {
            delay(500L)
            _currentQuestionCount.value = _currentQuestionCount.value?.plus(1)
            Log.d("GG", _currentQuestionCount.value.toString())
        }
    }

    fun refreshState() {
        viewModelScope.launch {
            delay(500L)
            _currentState.value = _currentState.value?.plus(1)
        }
    }

    fun restart() {
        getResult()
        _currentQuestionCount.value = 0
        _currentState.value = 0
    }

    fun setDifficulty(isChecked: Boolean) {
        if (isChecked) currentDifficulty.value = "medium"
        Log.d("GG", "$isChecked\n$currentDifficulty")
        _currentFragment.value = 1
        getResult()
    }
}