package com.github.linkav20.tamada.presentation.invitation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.invitation.presentation.InvitationScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val PARTY_ID = "partyId"

@AndroidEntryPoint
class InvitationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val id = intent.getIntExtra(PARTY_ID, 0)
        setContent {
            TamadaTheme {
                InvitationScreen(
                    id = id,
                    viewModel = hiltViewModel()
                )
            }
        }
    }
}
