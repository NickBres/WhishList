package com.example.whishlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import data.Wish
import kotlinx.coroutines.launch

@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
) {

    val snackMessage = remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    if(id != 0L){
        val wish = viewModel.getWishById(id).collectAsState(initial = Wish(0L,"", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescState = wish.value.desc
    }else{
        viewModel.wishTitleState = ""
        viewModel.wishDescState = ""
    }

    Scaffold(
        topBar = {
            AppBar(
                title = if (id != 0L) stringResource(id = R.string.update_wish)
                else stringResource(id = R.string.add_wish),
                onBackNavClick = {
                    navController.navigateUp()
                }
            )
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            WishTextField(
                label = "Wish",
                value = viewModel.wishTitleState,
                onValueChanged = { viewModel.onWishTitleChanged(it) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            WishTextField(
                label = "Description",
                value = viewModel.wishDescState,
                onValueChanged = { viewModel.onWishDescChanged(it) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                if(viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescState.isNotEmpty()){
                    if(id != 0L){
                        // Update Wish
                        viewModel.updateWish(
                            Wish(
                                id = id,
                                title = viewModel.wishTitleState.trim(),
                                desc = viewModel.wishDescState.trim()
                            )
                        )
                        snackMessage.value = "Wish updated"

                    }else{
                        // New wish
                        viewModel.addWish(
                            Wish(
                                title = viewModel.wishTitleState.trim(),
                                desc = viewModel.wishDescState.trim()
                            )
                        )
                        snackMessage.value = "Wish has created"
                    }
                }else{
                    // Fields are empty
                    snackMessage.value = "Enter fields to finish"
                }
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(snackMessage.value)
                    navController.navigateUp()
                }
            },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if(viewModel.wishTitleState.isNotEmpty()
                        && viewModel.wishDescState.isNotEmpty()) colorResource(id = R.color.p4)
                    else colorResource(id = R.color.p2),
                    contentColor = colorResource(id = R.color.white)
                ),) {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_wish)
                    else stringResource(id = R.string.add_wish),
                    style = TextStyle(fontSize = 16.sp)
                )
            }

        }
    }

}

@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.p4),
            unfocusedBorderColor = colorResource(id = R.color.p3),
            cursorColor = colorResource(id = R.color.p4),
            focusedLabelColor = colorResource(id = R.color.p4),
            unfocusedLabelColor = colorResource(id = R.color.p3)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewWishTextField() {
    WishTextField(
        label = "Preview Label",
        value = "Preview Value",
        onValueChanged = {}
    )
}