package com.littlelemon.littlelemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.littlelemon.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    // Initializes an HTTP client for making network requests, with JSON content negotiation
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    // Initializes a Room database instance, lazily loaded when needed
    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database").build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Check shared preferences for user data
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val isUserOnboarded = sharedPreferences.getBoolean("is_onboarded", false)
//        isUserOnboarded = false

        setContent {
            LittleLemonTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    Navigation(
                        navController,
                        modifier = Modifier.padding(innerPadding),
                        isUserOnboarded
                    )

                    //////////////////////////

                    //////////////////////////
                }
            }
        }
        // Fetches menu items from the network if the database is empty
        // LifecycleScope is for running coroutines tied to the lifecycle of an Android component, such as an Activity or Fragment.
        // In this example, the coroutine is launched in the IO dispatcher,
        // allowing the app to perform network and database operations without blocking the main thread.
        lifecycleScope.launch(Dispatchers.IO) {
            if (database.menuItemDao().isEmpty()) {
                val menuItemsNetwork = fetchMenu()
                saveMenuToDatabase(menuItemsNetwork)
            }
        }
    }

    // Fetches menu items from the network using the HTTP client
    private suspend fun fetchMenu(): List<MenuItemNetwork> {
        return httpClient
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuNetwork>() // Parses the response body as a MenuNetwork object
            .menu
    }

    // Saves menu items to the database
    private fun saveMenuToDatabase(menuItemsNetwork: List<MenuItemNetwork>) {
        // Converts network menu items to Room menu items
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        // Inserts all menu items into the database
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LittleLemonTheme {
        Greeting("Android")
    }
}