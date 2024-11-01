package com.littlelemon.littlelemon

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.testing.TestNavHostController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val database = Room.databaseBuilder(context, AppDatabase::class.java, "database").build()
    val menuItems = database.menuItemDao().getAll().observeAsState(initial = emptyList())

    Column(modifier = Modifier.fillMaxWidth()) {
        TopAppBar(navController)
        UpperPanel()
        MenuItemsList(items = menuItems.value)
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(menuItem: MenuItemRoom) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(menuItem.title)
        Text(text = "%.2f".format(menuItem.price))
        Text(menuItem.description)
        GlideImage(
            model = menuItem.image,
            contentDescription = menuItem.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    val context = LocalContext.current
    val navController = TestNavHostController(context) // Use a TestNavHostController for preview
    HomeScreen(navController = navController)
}
