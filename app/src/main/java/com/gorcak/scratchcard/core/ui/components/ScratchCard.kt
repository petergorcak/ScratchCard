package com.gorcak.scratchcard.core.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorcak.scratchcard.R
import com.gorcak.scratchcard.card.domain.ScratchCardState
import com.gorcak.scratchcard.core.ui.theme.ScratchCardTheme

private const val DUMMY_ID_PLACEHOLDER = "e4180600-ec78-4179-b29b-c870b1b20f00"

@Composable
fun ScratchCard(
    modifier: Modifier = Modifier,
    data: ScratchCardState
) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 25.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .animateContentSize()
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = when(data) {
                        ScratchCardState.Unscratched -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                        is ScratchCardState.Scratched -> MaterialTheme.colorScheme.secondary
                        is ScratchCardState.Activated -> MaterialTheme.colorScheme.primary
                    })
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = when (data) {
                        ScratchCardState.Unscratched -> stringResource(id = R.string.unscratched)
                        is ScratchCardState.Scratched -> stringResource(id = R.string.scratched)
                        is ScratchCardState.Activated -> stringResource(id = R.string.activated)
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onPrimary,

                    )
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (data) {
                ScratchCardState.Unscratched -> {
                    Text(
                        modifier = Modifier
                            .blur(
                                radius = 10.dp,
                                edgeTreatment = BlurredEdgeTreatment.Unbounded
                            )
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = DUMMY_ID_PLACEHOLDER,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                else -> {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = data.code(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ScratchCardPreview() {
    ScratchCardTheme {
        ScratchCard(
            data = ScratchCardState.Activated(DUMMY_ID_PLACEHOLDER)
        )
    }
}