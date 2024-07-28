package com.gorcak.scratchcard

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.gorcak.scratchcard.card.presentation.activation.ActivationScreenRoot
import com.gorcak.scratchcard.card.presentation.home.HomeScreenRoot
import com.gorcak.scratchcard.card.presentation.scratch.ScratchScreenRoot

@Composable
fun RootNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController ,
        startDestination = "scratchRoute"
    ) {
        scratchGraph(navController)
    }
}

private fun NavGraphBuilder.scratchGraph(navController: NavHostController) {
    navigation(
        startDestination = "home",
        route = "scratchRoute"
    ) {
        composable(route = "home"){
            HomeScreenRoot(
                onActivate = {
                    navController.navigate("activation")
                },
                onScratch = {
                    navController.navigate("scratch")
                }
            )
        }
        composable(route = "scratch"){
            ScratchScreenRoot(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(route = "activation"){
            ActivationScreenRoot(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}