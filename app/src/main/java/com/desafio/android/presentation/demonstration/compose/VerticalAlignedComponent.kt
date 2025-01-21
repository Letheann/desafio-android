package com.desafio.android.presentation.demonstration.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.desafio.android.core.presentation.ViewResource
import com.desafio.android.core.theme.SimpleloginTheme
import com.desafio.android.presentation.login.presentation.LoginViewModel
import com.desafio.android.presentation.login.presentation.ViewIntent

@Composable
fun VerticalAlignedComponent(
    viewModel: LoginViewModel,
    encryptedText: String?,
    toLogs: () -> Unit
) {

    val lifecycleOwner = LocalLifecycleOwner.current

    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.RESUMED -> {
                viewModel.intent(ViewIntent.DoDecrypt)
            }

            else -> {}
        }
    }

    DisposableEffect(lifecycleOwner) {
        val lifecycle = lifecycleOwner.lifecycle
        lifecycle.addObserver(viewModel)
        onDispose {
            lifecycle.removeObserver(viewModel)
        }
    }

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    when (val states = uiState.item) {
        is ViewResource.Success -> {
            CentralizedTextScreen(encryptedText, states.data.decryptedString, toLogs)
        }

        else -> {}
    }
}

@Composable
fun CentralizedTextScreen(
    encryptedText: String?,
    decryptedString: String?,
    toLogs: () -> Unit
) {
    val scrollState = rememberScrollState()
    val text1 = remember { mutableStateOf("Dados criptografados: $encryptedText") }
    val text2 = remember { mutableStateOf("Dados descriptografados: $decryptedString") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text1.value,
            style = TextStyle(fontSize = 18.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        )

        Text(
            text = text2.value,
            style = TextStyle(fontSize = 18.sp, color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { toLogs.invoke() },
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Ir para logs",
                style = TextStyle(fontSize = 14.sp),
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVerticalAlignedComponent() {
    SimpleloginTheme(darkTheme = true) {
        CentralizedTextScreen("", "") {}
    }

}
