package com.nikhil.harrypotterworld.ui

sealed class Screen(val route: String) {
    object HomeScreen: Screen("character_list_screen")
    object DetailScreen: Screen("character_detail_screen")
}
