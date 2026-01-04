package com.abdelrahman.movies_list_presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.abdelrahman.movies_list_domain.entity.Movie
import com.abdelrahman.presentation.R
import java.util.Locale

@Composable
fun MoviesListItem(
    modifier: Modifier = Modifier,
    movie: Movie = Movie()
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(),
        shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_16)),
        elevation = CardDefaults.elevatedCardElevation(dimensionResource(R.dimen.dimen_0)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        ) {
            Box(modifier = Modifier.aspectRatio(16f / 9f)) {
                AsyncImage(
                    model = movie.backdropPath,
                    contentDescription = movie.movieName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.8f))
                )
                AsyncImage(
                    model = movie.posterPath,
                    contentDescription = "poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(dimensionResource(R.dimen.dimen_120))
                        .clip(RoundedCornerShape(dimensionResource(R.dimen.dimen_120)))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(paddingValues = PaddingValues(dimensionResource(R.dimen.dimen_8))),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(2f, fill = false),
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(
                                end = dimensionResource(R.dimen.dimen_16)
                            ),
                        text = movie.overview.orEmpty(),
                        style = TextStyle(
                            color = Color.Black,
                        ),
                        fontSize = dimensionResource(R.dimen.text_12).value.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Card(
                    modifier = modifier
                        .wrapContentWidth(),
                    shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_16)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Yellow
                    ),
                    elevation = CardDefaults.elevatedCardElevation(dimensionResource(R.dimen.dimen_0)),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(PaddingValues(8.dp, 16.dp)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(R.string.rating), style = TextStyle(
                                color = Color.Black,
                            ), fontSize = 12.sp, fontWeight = FontWeight.Normal
                        )
                        Text(
                            String.format(
                                Locale.US,
                                stringResource(R.string.rating_format),
                                movie.voteAverage
                            ), style = TextStyle(
                                color = Color.Black,
                            ), fontSize = dimensionResource(R.dimen.text_12).value.sp, fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = .5f))
                    .padding(PaddingValues(vertical = dimensionResource(R.dimen.dimen_8))),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = movie.movieName.orEmpty(), style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = dimensionResource(R.dimen.text_16).value.sp
                    )
                )
            }
        }
    }
}