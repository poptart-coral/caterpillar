package com.example.adoptacaterpillar.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object RandomCat : Screen("random_cat")
    object About : Screen("about")
    object CatList : Screen("cat_list")
    object CatDetail : Screen("cat_detail/{catId}") {
        fun createRoute(catId: String) = "cat_detail/$catId"
    }
}
