package com.gorcak.scratchcard.card.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gorcak.scratchcard.card.presentation.MainViewModel
import com.gorcak.scratchcard.R
import com.gorcak.scratchcard.card.presentation.MainState
import com.gorcak.scratchcard.core.ui.components.LoadingButton
import com.gorcak.scratchcard.core.ui.components.ScratchCard
import com.gorcak.scratchcard.core.ui.components.ScratchCardAppBar
import com.gorcak.scratchcard.core.ui.components.ScratchCardScaffold
import com.gorcak.scratchcard.card.presentation.di.activityKoinViewModel
import com.gorcak.scratchcard.core.ui.theme.ScratchCardTheme

@Composable
fun HomeScreenRoot(
    onActivate: () -> Unit,
    onScratch: () -> Unit,
    viewModelMain: MainViewModel = activityKoinViewModel()
) {
    HomeScreen(
        state = viewModelMain.state,
        onAction = {
            when (it) {
                HomeAction.OnActivate -> {
                    onActivate()
                }

                HomeAction.OnScratch -> {
                    onScratch()
                }
            }
        }
    )
}

@Composable
private fun HomeScreen(
    state: MainState,
    onAction: (HomeAction) -> Unit
) {
    ScratchCardScaffold(
        contentVerticalArrangement = Arrangement.Top,
        appBar = {
            ScratchCardAppBar(
                title = stringResource(id = R.string.home_screen),
            )
        }
    ) {
        ScratchCard(   data = state.scratchCardState)
        Spacer(modifier = Modifier.height(32.dp))
        LoadingButton(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.button_scratch),
            onClick = { onAction(HomeAction.OnScratch) }
        )
        Spacer(modifier = Modifier.height(32.dp))
        LoadingButton(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(id = R.string.button_activate),
            onClick = { onAction(HomeAction.OnActivate) }
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ScratchCardTheme {
        HomeScreen(
            state = MainState(),
            onAction = {}
        )
    }
}