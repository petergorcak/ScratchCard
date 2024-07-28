package com.gorcak.scratchcard.card.presentation.di

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import com.gorcak.scratchcard.card.presentation.MainViewModel
import com.gorcak.scratchcard.card.presentation.scratch.ScratchViewModel
import org.koin.androidx.compose.defaultExtras
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.compose.currentKoinScope
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::ScratchViewModel)
    viewModelOf(::MainViewModel)

}


@Composable
public inline fun <reified T : ViewModel> activityKoinViewModel(
    qualifier: Qualifier? = null,
    key: String? = null,
    extras: CreationExtras = defaultExtras(LocalContext.current as ComponentActivity),
    scope: Scope = currentKoinScope(),
    noinline parameters: ParametersDefinition? = null,
) = koinViewModel<T>(
    qualifier = qualifier,
    viewModelStoreOwner = LocalContext.current as ComponentActivity,
    key = key,
    extras = extras,
    scope = scope,
    parameters = parameters,
)