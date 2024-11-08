@file:OptIn(ExperimentalGlideComposeApi::class)

package com.byteclassy.littlelemon.view.screens

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
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
import com.byteclassy.littlelemon.data.AppDatabase
import com.byteclassy.littlelemon.data.MenuItemRoom
import com.byteclassy.littlelemon.utils.Padding
import com.byteclassy.littlelemon.view.components.TopAppBar
import com.byteclassy.littlelemon.view.components.UpperPanel
import com.byteclassy.littlelemon.view.theme.LittleLemonColor
import com.littlelemon.littlelemon.R

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val database = Room.databaseBuilder(context, AppDatabase::class.java, "database").build()

    // Retrieves all menu items from the database and observes them as a state
    val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())

    // Initializes state variables for search phrase and selected category
    var searchPhrase by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    // Filters menu items by search phrase and category
    val menuItems = databaseMenuItems.filter {
        (selectedCategory == null || it.category == selectedCategory) &&
                (searchPhrase.isEmpty() || it.title.contains(searchPhrase, ignoreCase = true))
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Padding.HorizontalPadding)
    ) {
        TopAppBar(navController)
//        Spacer(modifier = Modifier.height(12.dp))
        UpperPanel()
        Spacer(modifier = Modifier.height(12.dp))

        // Search field
        OutlinedTextField(
            value = searchPhrase,
            onValueChange = { searchPhrase = it },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            trailingIcon = {
                if (searchPhrase.isNotEmpty()) {
                    IconButton(onClick = { searchPhrase = "" }) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "Clear",
                        )
                    }
                }
            },
            label = { Text(stringResource(R.string.enter_search_phrase)) },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done // Change enter key to "Done"
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = Padding.HorizontalPadding, end = Padding.HorizontalPadding)
        )

        Text(
            text = stringResource(R.string.order_for_delivery),
            fontWeight = FontWeight(weight = 400),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Padding.VerticalPadding, bottom = 8.dp)
        )

        CategoryButtons(
            categories = databaseMenuItems.groupBy { it.category }.keys.toList(),
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        MenuItemsList(menuItems)
    }
}

@Composable
fun CategoryButtons(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 4.dp)
    ) {
        items(categories) { category ->
            Button(
                onClick = { onCategorySelected(if (selectedCategory == category) null else category) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedCategory == category) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                ),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = category,
                    color = if (selectedCategory == category) Color.White else Color.Black,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun MenuItemsList(items: List<MenuItemRoom>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 8.dp)
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
                    fontWeight = FontWeight(weight = 800),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = menuItem.description,
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 4.dp, end = 4.dp)
                        .fillMaxWidth(0.75f),
                    fontWeight = FontWeight(weight = 400),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "$${menuItem.price}",
                    fontWeight = FontWeight(weight = 500),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            ResizedGlideImage(
                imageUrl = menuItem.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 8.dp),
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
