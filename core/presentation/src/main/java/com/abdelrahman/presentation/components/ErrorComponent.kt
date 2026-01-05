package com.abdelrahman.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.abdelrahman.domain.models.ErrorModels
import com.abdelrahman.presentation.R

@Composable
fun ErrorModels.ErrorComponent(modifier: Modifier) {
    val context = LocalContext.current
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimen_8))
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = errorMessage.getStringFromWrapper(context),
            modifier = Modifier.size(48.dp)
        )
        Text(
            modifier = modifier,
            text = errorMessage.getStringFromWrapper(context)
        )
    }

}