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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.abdelrahman.movies_list_domain.entity.Movie
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
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        ) {
            Box(modifier = Modifier.aspectRatio(16f / 9f)) {
                AsyncImage(
                    model = movie.backdropPath,
                    contentDescription = "title",
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
                        .size(120.dp)
                        .clip(RoundedCornerShape(99.dp))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(paddingValues = PaddingValues(8.dp)),
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
                                end = 16.dp
                            ),
                        text = movie.overview.orEmpty(), style = TextStyle(
                            color = Color.Black,
                        ), fontSize = 12.sp, maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                }
                Card(
                    modifier = modifier
                        .wrapContentWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Yellow
                    ),
                    elevation = CardDefaults.elevatedCardElevation(0.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(PaddingValues(8.dp, 16.dp)),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Rating", style = TextStyle(
                                color = Color.Black,
                            ), fontSize = 12.sp, fontWeight = FontWeight.Normal
                        )
                        Text(
                            String.format(Locale.US, "%.2f", movie.voteAverage), style = TextStyle(
                                color = Color.Black,
                            ), fontSize = 12.sp, fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = .5f))
                    .padding(PaddingValues(vertical = 8.dp)), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = movie.movieName.orEmpty(), style = TextStyle(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}