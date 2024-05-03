package com.github.linkav20.tamada.presentation.invitation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.github.linkav20.coreui.theme.TamadaTheme

class InvitationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val id = intent.getIntExtra("partyId", 0)
        setContent {
            TamadaTheme {
               InvitationScreen(
                   id = id
               )
            }
        }
    }
}
