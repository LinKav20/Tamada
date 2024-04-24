package com.github.linkav20.coreui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.github.linkav20.coreui.R

@Composable
fun getUserAvatar(id: Int?) = when (id) {
    0 -> painterResource(id = R.drawable.man_2)
    1 -> painterResource(id = R.drawable.woman_3)
    2 -> painterResource(id = R.drawable.man_4)
    3 -> painterResource(id = R.drawable.shark)
    4 -> painterResource(id = R.drawable.woman)
    5 -> painterResource(id = R.drawable.woman_2)
    6 -> painterResource(id = R.drawable.man_3)
    7 -> painterResource(id = R.drawable.woman_4)
    8 -> painterResource(id = R.drawable.rabbit)
    9 -> painterResource(id = R.drawable.bear)
    10 -> painterResource(id = R.drawable.dog)
    else -> painterResource(id = R.drawable.man)
}
