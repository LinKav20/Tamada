package com.github.linkav20.finance.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.linkav20.core.utils.copyToClipboard
import com.github.linkav20.coreui.R as CoreUi
import com.github.linkav20.coreui.theme.TamadaTheme
import com.github.linkav20.coreui.ui.ButtonType
import com.github.linkav20.coreui.ui.TamadaButton
import com.github.linkav20.coreui.ui.TamadaCard
import com.github.linkav20.coreui.ui.TamadaTextWithBackground
import com.github.linkav20.coreui.utils.ColorScheme
import com.github.linkav20.coreui.utils.getPrimaryColor
import com.github.linkav20.coreui.utils.getSecondaryColor
import com.github.linkav20.coreui.utils.getUserAvatar
import com.github.linkav20.finance.R
import com.github.linkav20.finance.domain.model.Expense
import com.github.linkav20.finance.domain.model.UserUI

@Composable
fun ExpandableExpensesCard(
    isDone: Boolean,
    user: UserUI,
    isManager: Boolean,
    isAccent: Boolean,
    step: Int,
    onExpand: (UserUI) -> Unit,
    onErrorInExpensesClick: (Long) -> Unit,
    onReceiptClick: (Long) -> Unit,
    onMyExpensesClick: () -> Unit,
    onSpecificButtonClick: () -> Unit
) = TamadaCard(
    modifier = Modifier.clickable { onExpand(user) },
    colorScheme = ColorScheme.FINANCE
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = getUserAvatar(user.avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                text = if (user.isMe) {
                    stringResource(R.string.step1_is_me_name, user.name)
                } else {
                    user.name
                },
                style = TamadaTheme.typography.head,
                color = getPrimaryColor(scheme = ColorScheme.FINANCE),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            TamadaTextWithBackground(
                colorScheme = ColorScheme.FINANCE,
                text = stringResource(R.string.step1_sum_formatter, user.focusSum.sum),
                textColor = if (isAccent) TamadaTheme.colors.textWhite else getPrimaryColor(scheme = ColorScheme.FINANCE),
                backgroundColor = if (isAccent) TamadaTheme.colors.statusNegative.copy(alpha = 0.8f) else getSecondaryColor(
                    scheme = ColorScheme.FINANCE
                )
            )
            Icon(
                painter = if (user.isExpanded) {
                    painterResource(id = CoreUi.drawable.dropup_icon)
                } else {
                    painterResource(id = CoreUi.drawable.dropdown_icon)
                },
                tint = getPrimaryColor(scheme = ColorScheme.FINANCE),
                contentDescription = ""
            )
        }
        if (step == 1 && isManager && isDone) {
            Spacer(modifier = Modifier.height(12.dp))
            TamadaButton(
                title = stringResource(id = R.string.progress_step2_money_send),
                type = ButtonType.SECONDARY,
                colorScheme = ColorScheme.FINANCE,
                onClick = onSpecificButtonClick
            )
        }
        if (step == 2 && isManager && isDone) {
            if (user.cardNumber.isNotEmpty() && user.cardPhoneNumber.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
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
                        text = user.cardNumber,
                        colorScheme = ColorScheme.FINANCE
                    )
                    TamadaButton(
                        colorScheme = ColorScheme.FINANCE,
                        iconPainter = painterResource(id = com.github.linkav20.coreui.R.drawable.copy_icon),
                        onClick = { context.copyToClipboard(user.cardNumber) }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
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
                        text = user.cardPhoneNumber,
                        colorScheme = ColorScheme.FINANCE
                    )
                    TamadaButton(
                        colorScheme = ColorScheme.FINANCE,
                        iconPainter = painterResource(id = com.github.linkav20.coreui.R.drawable.copy_icon),
                        onClick = { context.copyToClipboard(user.cardPhoneNumber) }
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                if (user.cardBank.isNotEmpty() && user.cardUser.isNotEmpty()) BankInformation(
                    bank = user.cardBank,
                    owner = user.cardUser
                )
            } else {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(id = R.string.wallet_component_empty_user),
                    style = TamadaTheme.typography.caption,
                    color = TamadaTheme.colors.textMain
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            TamadaButton(
                title = stringResource(id = R.string.progress_step3_money_send),
                type = ButtonType.PRIMARY,
                colorScheme = ColorScheme.FINANCE,
                onClick = onSpecificButtonClick
            )
        }
        if (user.isExpanded) {
            Spacer(modifier = Modifier.height(24.dp))
            user.expenses.forEach {
                ExpenseItem(it)
                Spacer(modifier = Modifier.height(12.dp))
            }
            Divider(
                color = TamadaTheme.colors.textLightGray,
                thickness = 2.dp
            )
            Spacer(modifier = Modifier.height(12.dp))
            ExpenseItem(
                expense = Expense(
                    name = stringResource(id = R.string.progress_user_total_sum),
                    sum = user.expenses.sumOf { it.sum }
                )
            )
            Spacer(modifier = Modifier.height(24.dp))
            if (user.isMe) {
                TamadaButton(
                    title = stringResource(R.string.open_my_expenses_button),
                    colorScheme = ColorScheme.FINANCE,
                    onClick = onMyExpensesClick
                )
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TamadaButton(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.receipt_button),
                        type = ButtonType.SECONDARY,
                        colorScheme = ColorScheme.FINANCE,
                        onClick = { onReceiptClick(user.id) }
                    )
                    TamadaButton(
                        modifier = Modifier.weight(3f),
                        type = ButtonType.ERROR,
                        title = stringResource(R.string.error_in_expenses_button),
                        colorScheme = ColorScheme.FINANCE,
                        onClick = { onErrorInExpensesClick(user.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = expense.name,
            style = TamadaTheme.typography.body,
            color = TamadaTheme.colors.textMain
        )
        Spacer(
            Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(R.string.step1_sum_formatter, expense.sum),
            style = TamadaTheme.typography.body,
            color = TamadaTheme.colors.textHeader
        )
    }
}

@Preview
@Composable
private fun Preview() {
    TamadaTheme {
        ExpandableExpensesCard(
            isDone = true,
            isAccent = false,
            step = 2,
            isManager = false,
            user = UserUI(
                id = 0,
                name = "Lina",
                expenses = listOf(Expense(id = 231312, "lol", 456.0)),
                focusSum = Expense(id = 876543, "Итого", 567.0),
                isExpanded = true,
                cardBank = "Bank",
                cardPhoneNumber = "+78758",
                cardNumber = "2345 3245 24356",
                cardUser = "Me Me Me"
            ),
            onExpand = {},
            onErrorInExpensesClick = {},
            onReceiptClick = {},
            onMyExpensesClick = {},
            onSpecificButtonClick = {}
        )
    }
}