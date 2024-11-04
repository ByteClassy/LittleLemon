@file:OptIn(ExperimentalGlideComposeApi::class)

package com.littlelemon.littlelemon

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestOptions
import com.littlelemon.littlelemon.ui.theme.LittleLemonColor


@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val database = Room.databaseBuilder(context, AppDatabase::class.java, "database").build()

    // Retrieves all menu items from the database and observes them as a state
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())

    // Initializes a state variable to track whether to order menu items by name
    var orderMenuItems by remember {
        mutableStateOf(false)
    }

    // Sorts menu items by name if orderMenuItems is true, otherwise returns the original list
    var menuItems = if (orderMenuItems) {
        databaseMenuItems.sortedBy { it.title }
    } else {
        databaseMenuItems
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TopAppBar(navController)
        UpperPanel()

        // Initializes a state variable to track the search phrase
        var searchPhrase by remember {
            mutableStateOf("")
        }

        OutlinedTextField(
            value = searchPhrase,
            onValueChange = {
                searchPhrase = it
            },
            leadingIcon = { Icon( imageVector = Icons.Default.Search, contentDescription = "") },
            label = { Text("Enter search phrase") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp)
        )

        // Filters menu items by search phrase if it's not empty
        if (searchPhrase.isNotEmpty()) {
            menuItems = menuItems.filter { it.title.contains(searchPhrase, ignoreCase = true) }
        }

        MenuItemsList(menuItems)
    }
}

@Composable
fun MenuItemsList(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 20.dp)
    ) {
        items(items = items) { menuItem ->
            MenuItem(menuItem)
        }
    }
}


@Composable
fun MenuItem(menuItem: MenuItemRoom) {

    Card(
        modifier = Modifier
            .clickable {
                Log.d("AAA", "Click ${menuItem.id}")
            },
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column {
                Text(
                    text = menuItem.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = menuItem.description,
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 4.dp, end = 4.dp)
                        .fillMaxWidth(0.75f),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "$${menuItem.price}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
//            ResizedGlideImage(
//                imageUrl = menuItem.image,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(100.dp)
//            )
            GlideImage(
                model = menuItem.image,
                contentDescription = menuItem.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(start = 8.dp, end = 8.dp),
        thickness = 1.dp,
        color = LittleLemonColor.yellow
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ResizedGlideImage(imageUrl: String, modifier: Modifier = Modifier) {
    GlideImage(
        model = imageUrl,
        contentDescription = "Resized Image",
        modifier = modifier,
        requestBuilderTransform = {
            it.apply(
                RequestOptions()
                    .override(512, 512) // Set the width to 512 px, maintaining the aspect ratio
                    .downsample(DownsampleStrategy.AT_MOST)
                    .transform(CenterInside())
            )
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context) // Use a TestNavHostController for preview
    HomeScreen(navController = navController)
}
