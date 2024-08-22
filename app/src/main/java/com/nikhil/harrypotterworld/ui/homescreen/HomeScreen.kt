package com.nikhil.harrypotterworld.ui.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
                        GridCells.Fixed(1),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        items(charactersState.characters) { data ->
                            CharacterCard(
                                character = data,
                                openDetailScreen = { character ->
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
    character: CharacterModel,
    openDetailScreen: (CharacterModel) -> Unit
) {

    val mycolor:Color = if (character.house.equals("Gryffindor"))  Color(R.color.griffindor)

    else if (character.house.equals("Slytherin"))   Color(R.color.slytherin)
    else if (character.house.equals("Ravenclaw"))  Color(R.color.ravenclaw)
    else if (character.house.equals("Hufflepuff"))  Color(R.color.hufflepuff) else Color.Blue


    val imagePainter = rememberAsyncImagePainter(model = character.image)
    Card(
//        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(16.dp).clickable {
        openDetailScreen(character)
    },
        shape = RoundedCornerShape(size = 30.dp),



    ) {
        Box (modifier = Modifier
                .fillMaxSize()
            .background(mycolor,  RoundedCornerShape(size = 30.dp))){

            Image(
                painter = imagePainter, contentDescription = null,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )


            Surface(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .3f),
                modifier = Modifier.align(Alignment.BottomEnd),
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)


                ) {
                    Text(
                        text = "Real Name: ${character.actor} ",
                        Modifier.align(Alignment.End),
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = "Actor Name: ${character.name} ",
                        Modifier.align(Alignment.End),
                        style = MaterialTheme.typography.bodySmall

                    )

                    Text(
                        text = "Species: ${character.species} ",
                        Modifier.align(Alignment.End),
                        style = MaterialTheme.typography.bodySmall

                    )

                }




        }

        }

    }
}