package com.gorcak.scratchcard.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gorcak.scratchcard.core.ui.theme.ScratchCardTheme

@Composable
fun LoadingButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    isEnabled: Boolean = true,
    label: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier,
        shape = RoundedCornerShape(100f),
        enabled = isEnabled,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(20.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 2.5.dp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .alpha(if (isLoading) 0f else 1f),
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
            )
        }

    }
}

@Preview
@Composable
private fun LoadingButtonPreview() {
    ScratchCardTheme {
        LoadingButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            isLoading = false,
            label = "Button"
        )
    }
}