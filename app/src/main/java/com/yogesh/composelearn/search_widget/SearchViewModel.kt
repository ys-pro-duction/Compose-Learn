package com.yogesh.composelearn.search_widget

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SearchViewModel : ViewModel() {
    private val _searchText: MutableStateFlow<String> = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    private val _appBar: MutableStateFlow<APP_BAR> = MutableStateFlow(APP_BAR.NORMAL)
    val appBar: StateFlow<APP_BAR> = _appBar.asStateFlow()

    fun updateText(text: String){
        _searchText.value = text
    }
    fun updateAppBar(type: APP_BAR){
        _appBar.value = type
    }
}
enum class APP_BAR{
    NORMAL,
    SEARCH
}