package com.nikhil.harrypotterworld.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikhil.harrypotterworld.util.Resource
import com.nikhil.harrypotterworld.data.repository.GetCharactersUseCase
import com.nikhil.harrypotterworld.ui.homescreen.components.SearchWidgetState
import com.nikhil.harrypotterworld.ui.homescreen.state.CharactersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _characters = mutableStateOf(CharactersState())
    val characters: State<CharactersState> = _characters

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    private val _searchString = mutableStateOf("")
    val searchString: State<String> = _searchString

    fun setSearchString(value: String) {
        _searchString.value = value
    }

    init {
        getCharacters()
    }

    fun getCharacters(searchString: String = "") {
        _characters.value = characters.value.copy(
            isLoading = true,
            error = "",
            characters = emptyList()
        )

        viewModelScope.launch(ioDispatcher) {
            getCharactersUseCase().onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _characters.value = CharactersState(
                            characters = if (searchString.isEmpty()) {
                                result.data ?: emptyList()
                            } else {
                                result.data?.filter { character ->
                                    character.name.contains(
                                        searchString,
                                        ignoreCase = true
                                    ) || character.house?.contains(
                                        searchString,
                                        ignoreCase = true
                                    ) == true
                                } ?: emptyList()
                            }
                        )
                    }

                    is Resource.Error -> {
                        _characters.value = CharactersState(
                            error = result.message ?: "An unexpected error occurred"
                        )
                    }

                    is Resource.Loading -> {
                        _characters.value = CharactersState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }

    }
}