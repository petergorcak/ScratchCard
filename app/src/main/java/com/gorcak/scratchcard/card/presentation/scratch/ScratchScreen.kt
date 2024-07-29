package com.gorcak.scratchcard.card.presentation.scratch

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gorcak.scratchcard.R
import com.gorcak.scratchcard.core.ui.components.LoadingButton
import com.gorcak.scratchcard.core.ui.components.ObserveEvents
import com.gorcak.scratchcard.core.ui.components.ScratchCard
import com.gorcak.scratchcard.core.ui.components.ScratchCardAppBar
import com.gorcak.scratchcard.core.ui.components.ScratchCardScaffold
import com.gorcak.scratchcard.core.ui.theme.ScratchCardTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScratchScreenRoot(
    onBack: () -> Unit,
    viewModel: ScratchViewModel = koinViewModel()
) {
    val context = LocalContext.current
    ObserveEvents(flow = viewModel.events) { event ->
        when (event) {
            is ScratchEvent.Error -> {
                Toast.makeText(
                    context,
                    event.message.get(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            is ScratchEvent.ScratchSuccess -> {
                Toast.makeText(
                    context,
                    R.string.success_scratch,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    ScratchScreen(
        state = viewModel.state,
        onAction = {
            when (it) {
                ScratchAction.OnBack -> {
                    onBack()
                }
                else -> {
                    viewModel.onAction(it)
                }
            }
        }
    )
}


@Composable
private fun ScratchScreen(
    state: ScratchState,
    onAction: (ScratchAction) -> Unit
) {
    ScratchCardScaffold(
        appBar = {
            ScratchCardAppBar(
                title = stringResource(id = R.string.scratch_screen),
                onBackClick = {
                    onAction(ScratchAction.OnBack)
                }
            )
        },
        contentVerticalArrangement = Arrangement.Top,
        content = {
            ScratchCard(data = state.scratchData)
            Spacer(modifier = Modifier.height(32.dp))
            LoadingButton(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(id = R.string.button_scratch),
                isLoading = state.isScratching,
                isEnabled = !state.isScratching && state.canScratch,
                onClick = { onAction(ScratchAction.Scratch) }
            )
        }
    )
}

@Preview
@Composable
private fun ScratchScreenPreview() {
    ScratchCardTheme {
        ScratchScreen (
            state = ScratchState( ),
            onAction = {}
        )
    }
}