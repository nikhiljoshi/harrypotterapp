package com.nikhil.harrypotterworld.ui.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.nikhil.harrypotterworld.R
import com.nikhil.harrypotterworld.data.model.CharacterModel
import com.nikhil.harrypotterworld.ui.homescreen.components.MainAppBar
import com.nikhil.harrypotterworld.ui.homescreen.components.SearchWidgetState
import com.nikhil.harrypotterworld.ui.Screen
import com.nikhil.harrypotterworld.ui.ui.theme.BackgroundDarkColor
import com.nikhil.harrypotterworld.ui.ui.theme.BackgroundDarkColorTwo

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
    onSelect: (CharacterModel) -> Unit
) {

    val charactersState = viewModel.characters.value
    val searchWidgetState by viewModel.searchWidgetState
    val searchString by viewModel.searchString
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDarkColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundDarkColorTwo)
        ) {

            Image(
                painter = painterResource(id = R.drawable.harry),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 60.dp, end = 60.dp, top = 50.dp)
            )

            Scaffold(
                topBar = {
                    MainAppBar(
                        searchWidgetState = searchWidgetState,
                        searchStringState = searchString,
                        onTextChange = {
                            viewModel.setSearchString(it)
                        },
                        onCloseClicked = {
                            viewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                            viewModel.getCharacters()
                        },
                        onSearchClicked = { _ ->
                            keyboardController?.hide()
                            viewModel.getCharacters(viewModel.searchString.value)
                        },
                        onSearchTriggered = {
                            viewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                        },
                        modifier = Modifier
                    )
                },
                modifier = Modifier
                    .padding(start = 0.dp, end = 0.dp, top = 14.dp),
                containerColor = BackgroundDarkColorTwo
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                        .background(BackgroundDarkColor)

                ) {

                    LazyVerticalGrid(
                        GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 8.dp, end = 16.dp)
                    ) {
                        items(charactersState.characters) { data ->
                            CharacterCard(
                                data = data,
                                openCharacterDetails = { character ->
                                    onSelect(character)
                                    navController.navigate(Screen.DetailScreen.route)
                                }
                            )
                        }
                    }

                    if(charactersState.error.isNotBlank()) {
                        Text(
                            text = charactersState.error,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .align(Alignment.Center)
                        )
                    }
                    if (charactersState.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterCard(
    data: CharacterModel,
    openCharacterDetails: (CharacterModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .size(160.dp, 180.dp)
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
                .clip(
                    RoundedCornerShape(10.dp)
                ).clickable {
                            openCharacterDetails(data)
                },
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )

        ) {
            val image: Painter =
                rememberAsyncImagePainter(model = data.image)
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = image,
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = data.name,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            maxLines = 1
        )
    }
}