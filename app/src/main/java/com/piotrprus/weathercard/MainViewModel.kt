package com.piotrprus.weathercard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _selected = MutableLiveData(0)
    val selected: LiveData<Int> = _selected

    fun onValueChanged(selected: Int) {
        _selected.value = selected
    }
}