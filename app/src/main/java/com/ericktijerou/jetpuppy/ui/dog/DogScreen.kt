/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ericktijerou.jetpuppy.ui.dog

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ericktijerou.jetpuppy.R
import com.ericktijerou.jetpuppy.ui.entity.Dog
import com.ericktijerou.jetpuppy.util.EMPTY
import com.ericktijerou.jetpuppy.util.SuperellipseCornerShape
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun DogScreen(viewModel: DogViewModel, dogId: String, modifier: Modifier = Modifier) {
    val dog = viewModel.getPuppyById(dogId)
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (image, containerInfo) = createRefs()
        CoilImage(
            data = dog.imageUrl,
            contentScale = ContentScale.Crop,
            contentDescription = EMPTY,
            modifier = Modifier.constrainAs(image) {
                linkTo(
                    start = parent.start,
                    top = parent.top,
                    end = parent.end,
                    bottom = containerInfo.top,
                    bottomMargin = (-32).dp
                )
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )
        Surface(
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp,),
            modifier = Modifier.constrainAs(containerInfo) {
                linkTo(start = parent.start, end = parent.end)
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            }
        ) {
            InfoContainer(dog)
        }
    }
}

@Composable
fun InfoContainer(dog: Dog) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(start = 24.dp, top = 16.dp, end = 24.dp, bottom = 36.dp)
    ) {

        val rowsModifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
        TitleRow(title = "${dog.name}, ${dog.age}", breed = dog.breed, modifier = rowsModifier)
        Text(
            text = stringResource(id = dog.summary),
            style = MaterialTheme.typography.body2,
            overflow = TextOverflow.Ellipsis,
            modifier = rowsModifier
        )
        listOf(
            R.string.label_sex to dog.gender,
            R.string.label_weight to dog.weight,
            R.string.label_color to dog.color
        ).forEach {
            InfoItemRow(modifier = rowsModifier, label = it.first, value = it.second)
        }
        AdoptButton()
    }
}

@Composable
fun AdoptButton() {
    Card(
        modifier = Modifier
            .padding(top = 12.dp)
            .height(48.dp)
            .fillMaxSize(),
        shape = SuperellipseCornerShape(12.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        elevation = 12.dp
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable { }
        ) {
            Text(
                text = stringResource(R.string.label_adopt),
                style = MaterialTheme.typography.button
            )
        }
    }
}

@Composable
fun TitleRow(title: String, breed: String, modifier: Modifier) {
    Row(modifier = modifier) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5
            )
            Text(
                text = breed,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(top = 4.dp),
                color = Color.Black.copy(alpha = 0.6f)
            )
        }

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Filled.FavoriteBorder,
                contentDescription = EMPTY,
                Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun InfoItemRow(@StringRes label: Int, value: String, modifier: Modifier) {
    Row(modifier) {
        Text(
            text = stringResource(label),
            style = MaterialTheme.typography.body2,
            modifier = Modifier.width(120.dp),
            color = Color.Black.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.W500)
        )
    }
}