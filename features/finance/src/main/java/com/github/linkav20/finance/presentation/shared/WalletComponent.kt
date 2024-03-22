package com.github.linkav20.finance.presentation.shared

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.linkav20.core.utils.copyToClipboard
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaTextFiled
import com.github.linkav20.coreui.ui.TamadaTextWithBackground
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.finance.R
import com.github.linkav20.coreui.R as CoreR

@Composable
fun WalletComponent(
    isManager: Boolean,
    cartNumber: String,
    phoneNumber: String,
    owner: String?,
    bank: String?,
    onEditClick: () -> Unit
) = TamadaCard(
    colorScheme = ColorScheme.FINANCE
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.wallet_component_title),
                style = TamadaTheme.typography.head,
                color = TamadaTheme.colors.textHeader,
            )
            if (isManager) {
                TamadaButton(
                    colorScheme = ColorScheme.FINANCE,
                    iconPainter = painterResource(id = CoreR.drawable.edit_icon),
                    type = ButtonType.SECONDARY,
                    onClick = onEditClick,
                )
            }
        }
        Text(
            text = stringResource(id = R.string.wallet_component_subtitle),
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain
        )
        if (cartNumber.isNotEmpty() && phoneNumber.isNotEmpty()) {
            val context = LocalContext.current
            Row(
                modifier = Modifier.height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val scroll = rememberScrollState(0)
                TamadaTextWithBackground(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(scroll),
                    text = cartNumber,
                    colorScheme = ColorScheme.FINANCE
                )
                TamadaButton(
                    colorScheme = ColorScheme.FINANCE,
                    iconPainter = painterResource(id = CoreR.drawable.copy_icon),
                    onClick = { context.copyToClipboard(cartNumber) }
                )
            }
            Row(
                modifier = Modifier.height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val scroll = rememberScrollState(0)
                TamadaTextWithBackground(
                    modifier = Modifier
                        .weight(1f)
                        .horizontalScroll(scroll),
                    text = phoneNumber,
                    colorScheme = ColorScheme.FINANCE
                )
                TamadaButton(
                    colorScheme = ColorScheme.FINANCE,
                    iconPainter = painterResource(id = CoreR.drawable.copy_icon),
                    onClick = { context.copyToClipboard(phoneNumber) }
                )
            }
            if (bank != null && owner != null) BankInformation(bank = bank, owner = owner)
        } else {
            Text(
                text = stringResource(id = R.string.wallet_component_empty),
                style = TamadaTheme.typography.caption,
                color = TamadaTheme.colors.textMain
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditableWalletComponent(
    cartNumber: String?,
    phoneNumber: String?,
    cardOwner: String?,
    bank: String?,
    onCardNumberChanged: (String) -> Unit,
    onPhoneNumberChanged: (String) -> Unit,
    onBankChanged: (String) -> Unit,
    onCardOwnerChanged: (String) -> Unit,
    onSaveDataClick: () -> Unit
) = TamadaCard(
    colorScheme = ColorScheme.FINANCE
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Text(
            text = stringResource(id = R.string.wallet_component_title),
            style = TamadaTheme.typography.head,
            color = TamadaTheme.colors.textHeader,
        )
        Text(
            text = stringResource(id = R.string.wallet_component_subtitle),
            style = TamadaTheme.typography.caption,
            color = TamadaTheme.colors.textMain
        )
        TamadaTextFiled(
            placeholder = stringResource(id = R.string.wallet_component_card_number_placeholder),
            onValueChange = onCardNumberChanged,
            value = cartNumber ?: "",
            colorScheme = ColorScheme.FINANCE,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        )
        TamadaTextFiled(
            placeholder = stringResource(id = R.string.wallet_component_phone_number_placeholder),
            onValueChange = onPhoneNumberChanged,
            value = phoneNumber ?: "",
            colorScheme = ColorScheme.FINANCE,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        )
        TamadaTextFiled(
            placeholder = stringResource(id = R.string.wallet_component_card_owner_placeholder),
            onValueChange = onCardOwnerChanged,
            value = cardOwner ?: "",
            colorScheme = ColorScheme.FINANCE,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        )
        TamadaTextFiled(
            placeholder = stringResource(id = R.string.wallet_component_bank_placeholder),
            onValueChange = onBankChanged,
            value = bank ?: "",
            colorScheme = ColorScheme.FINANCE,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        )
        TamadaButton(
            colorScheme = ColorScheme.FINANCE,
            title = stringResource(id = R.string.wallet_save),
            onClick = onSaveDataClick
        )
    }
}

@Composable
fun BankInformation(
    bank: String,
    owner: String
) = Column {
    Text(
        text = stringResource(id = R.string.wallet_component_bank, bank),
        style = TamadaTheme.typography.caption,
        color = TamadaTheme.colors.textMain
    )
    Text(
        text = stringResource(id = R.string.wallet_component_card_owner, owner),
        style = TamadaTheme.typography.caption,
        color = TamadaTheme.colors.textMain
    )
}
