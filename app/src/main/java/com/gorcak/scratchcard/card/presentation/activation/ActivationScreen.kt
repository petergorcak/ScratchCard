package com.gorcak.scratchcard.card.presentation.activation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorcak.scratchcard.R
import com.gorcak.scratchcard.card.presentation.MainEvent
import com.gorcak.scratchcard.card.presentation.MainState
import com.gorcak.scratchcard.card.presentation.MainViewModel
import com.gorcak.scratchcard.core.ui.components.LoadingButton
import com.gorcak.scratchcard.core.ui.components.ObserveEvents
import com.gorcak.scratchcard.core.ui.components.ScratchCard
import com.gorcak.scratchcard.core.ui.components.ScratchCardAppBar
import com.gorcak.scratchcard.core.ui.components.ScratchCardScaffold
import com.gorcak.scratchcard.core.ui.theme.ScratchCardTheme
import com.gorcak.scratchcard.card.presentation.di.activityKoinViewModel

@Composable
fun ActivationScreenRoot(
    onBack: () -> Unit,
    mainViewModel: MainViewModel = activityKoinViewModel()
) {
    val context = LocalContext.current
    val showErrorModal = remember { mutableStateOf<String?>(null) }

    ObserveEvents(flow = mainViewModel.events) { event ->
        when (event) {
            MainEvent.ActivationSuccess -> {
                Toast.makeText(context, R.string.success_activation, Toast.LENGTH_LONG).show()
            }

            is MainEvent.Error -> {
                showErrorModal.value = event.message.get(context)
            }
        }
    }
    showErrorModal.value?.also { errorMessage ->
        AlertDialog(
            onDismissRequest = { showErrorModal.value = null },
            confirmButton = {

                Button(
                    onClick = { showErrorModal.value = null }
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }

            },
            title = {
                Text(
                    text = stringResource(id = R.string.error_activating_title),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = errorMessage,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            },

            )
    }

    ActivationScreen(
        state = mainViewModel.state,
        onAction = {
            when (it) {
                ActivationAction.OnBack -> {
                    onBack()
                }

                ActivationAction.Activate -> {
                    mainViewModel.activateCard()
                }
            }
        }
    )
}

@Composable
private fun ActivationScreen(
    state: MainState,
    onAction: (ActivationAction) -> Unit
) {
    ScratchCardScaffold(
        appBar = {
            ScratchCardAppBar(
                title = stringResource(id = R.string.activation_screen),
                onBackClick = {
                    onAction(ActivationAction.OnBack)
                }
            )
        },
        contentVerticalArrangement = Arrangement.Top
    ) {
        ScratchCard(data = state.scratchCardState)
        Spacer(modifier = Modifier.height(32.dp))
        LoadingButton(
            label = stringResource(id = R.string.button_activate),
            isLoading = state.isActivating,
            isEnabled = !state.isActivating,
            onClick = {
                onAction(ActivationAction.Activate)
            }
        )

    }
}


@Preview
@Composable
private fun ActivationScreenPreview() {
    ScratchCardTheme {
        ActivationScreen(
            state = MainState(),
            onAction = {}
        )
    }
}