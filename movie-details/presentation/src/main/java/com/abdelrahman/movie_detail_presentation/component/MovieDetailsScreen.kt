package com.abdelrahman.movie_detail_presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.abdelrahman.domain.models.ErrorModels
import com.abdelrahman.movie_details_domain.entity.MovieDetailsDTO
import com.abdelrahman.presentation.LoadingTypes
import com.abdelrahman.presentation.R

@Composable
fun MovieDetailsScreen(
    movieDetailsDTO: MovieDetailsDTO? = null,
    loadingTypes: LoadingTypes = LoadingTypes.None,
    errorModels: ErrorModels? = null
) {
    if (errorModels != null) {

    } else {
        if (loadingTypes == LoadingTypes.FullScreenLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if (movieDetailsDTO != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.dimen_16))
                        .systemBarsPadding()
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(dimensionResource(R.dimen.dimen_16)))
                    ) {
                        AsyncImage(
                            model = movieDetailsDTO.backdropPath,
                            contentDescription = movieDetailsDTO.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(dimensionResource(R.dimen.dimen_16)))
                                .padding(bottom = dimensionResource(R.dimen.dimen_64))
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f)
                                .clip(RoundedCornerShape(dimensionResource(R.dimen.dimen_16)))
                                .padding(bottom = dimensionResource(R.dimen.dimen_64))
                                .background(Color.Black.copy(alpha = 0.8f))
                        )
                        Row(
                            modifier = Modifier
                                .padding(
                                    start = dimensionResource(R.dimen.dimen_16),
                                    bottom = dimensionResource(R.dimen.dimen_24)
                                )
                                .align(Alignment.BottomStart),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = movieDetailsDTO.posterPath,
                                contentDescription = movieDetailsDTO.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(dimensionResource(R.dimen.dimen_90))
                                    .clip(RoundedCornerShape(dimensionResource(R.dimen.dimen_90)))

                            )
                            Column {
                                Spacer(
                                    Modifier.height(
                                        dimensionResource(R.dimen.dimen_8)
                                    )
                                )
                                Text(
                                    movieDetailsDTO.title.orEmpty(), style = TextStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = dimensionResource(R.dimen.text_12).value.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dimen_8)))

                                Text(
                                    movieDetailsDTO.tagline.orEmpty(), style = TextStyle(
                                        color = Color.Black,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = dimensionResource(R.dimen.text_12).value.sp
                                    )
                                )
                            }
                        }
                    }

                    LazyRow(
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.dimen_16)
                        ),
                        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.dimen_4))
                    ) {
                        items(movieDetailsDTO.genres.orEmpty()) { text ->
                            if (text != null) {
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.DarkGray
                                    ),
                                    shape = RoundedCornerShape(dimensionResource(R.dimen.dimen_8)),
                                    content = {
                                        Text(
                                            modifier = Modifier.padding(
                                                vertical = dimensionResource(R.dimen.dimen_4),
                                                horizontal = dimensionResource(R.dimen.dimen_8)

                                            ), text = text, style = TextStyle(
                                                color = Color.White,
                                                textAlign = TextAlign.Center,
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.text_16)))
                    Text(
                        modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.text_16)
                        ), text = movieDetailsDTO.overview.orEmpty(),
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = dimensionResource(R.dimen.text_14).value.sp,
                            textAlign = TextAlign.Start
                        )
                    )

                }
            }
        }
    }

}