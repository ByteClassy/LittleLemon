package com.littlelemon.littlelemon.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.littlelemon.littlelemon.R
import com.littlelemon.littlelemon.ui.theme.LittleLemonColor
import com.littlelemon.littlelemon.ui.theme.LittleLemonTypography


private val HorizontalPadding = 12.dp
private val VerticalPadding = 4.dp
private val DescriptionTopPadding = 30.dp
private val DescriptionBottomPadding = 16.dp
private val DescriptionEndPadding = 20.dp
private val ImageSize = 150.dp
private val ImageCornerRadius = 10.dp


@Composable
fun UpperPanel() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LittleLemonColor.green)
            .padding(start = HorizontalPadding, end = HorizontalPadding, top = VerticalPadding, bottom = VerticalPadding)
    ) {
        Text(
            text = stringResource(id = R.string.title),
            style = LittleLemonTypography.displayMedium,
            color = LittleLemonColor.yellow,
        )
        Text(
            text = stringResource(id = R.string.location),
            style = LittleLemonTypography.displaySmall,
            color = LittleLemonColor.cloud,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(id = R.string.description),
                color = LittleLemonColor.cloud,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(top = DescriptionTopPadding, bottom = DescriptionBottomPadding, end = DescriptionEndPadding)
                    .fillMaxWidth(0.6f)
            )
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(ImageSize)
                    .clip(RoundedCornerShape(ImageCornerRadius))
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpperPanelPreview() {
    UpperPanel()
}
