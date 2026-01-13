package com.example.adoptacaterpillar.ui.navigation

sealed class Screen(val route: String) {
    object RandomCat : Screen("random_cat")
    object About : Screen("about")

    object CatFacts : Screen("cat_facts")
    object Breeds : Screen("breeds")
    object BreedDetail : Screen("breed_detail/{breedId}") {
        fun createRoute(breedId: String) = "breed_detail/$breedId"
    }
}
