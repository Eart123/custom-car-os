package com.customcaros.designsystem.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Dashboard layout container with top bar, content area, and bottom app dock.
 */
@Composable
fun AutoDashboardLayout(
    topBarContent: @Composable () -> Unit,
    appDockContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        topBarContent()

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            content()
        }

        appDockContent()
    }
}
