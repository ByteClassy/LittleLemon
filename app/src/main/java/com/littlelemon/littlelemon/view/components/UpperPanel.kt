package com.littlelemon.littlelemon.view.components

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
import com.littlelemon.littlelemon.R
import com.littlelemon.littlelemon.view.theme.LittleLemonColor
import com.littlelemon.littlelemon.view.theme.LittleLemonTypography
import com.littlelemon.littlelemon.utils.Padding

@Composable
fun UpperPanel() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LittleLemonColor.green)
            .padding(start = Padding.HorizontalPadding, end = Padding.HorizontalPadding)
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
                    .padding(
                        top = Padding.DescriptionTopPadding,
                        bottom = Padding.DescriptionBottomPadding,
                        end = Padding.DescriptionEndPadding
                    )
                    .fillMaxWidth(0.6f)
            )
            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Hero Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(Padding.ImageSize)
                    .clip(RoundedCornerShape(Padding.ImageCornerRadius))
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun UpperPanelPreview() {
    UpperPanel()
}
