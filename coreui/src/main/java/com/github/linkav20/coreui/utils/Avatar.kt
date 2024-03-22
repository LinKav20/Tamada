package com.github.linkav20.coreui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.github.linkav20.coreui.R

@Composable
fun getUserAvatar(id: Int?) = painterResource(id = R.drawable.avatar)
