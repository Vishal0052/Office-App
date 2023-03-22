package com.example.officeapp.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.officeapp.model.LoginDataModel
import com.example.officeapp.utils.Constants
import com.example.officeapp.utils.Utils

@Composable
fun CommonTf(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    maxLine: Int = 2,
    onTextChange: (String) -> Unit,
) {



        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            maxLines = maxLine,
            label = { Text(text = label) },
            modifier=modifier
        )



}

@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    onClick: () -> Unit,
    elevation: Dp = 4.dp
) {

    Card(
        modifier = modifier
            .padding(all = 4.dp)
            .clickable {
                onClick.invoke()
            }, shape = CircleShape,
        elevation = elevation
    )
    {
        Icon(imageVector = imageVector, contentDescription = "Plus or minus icon")

    }

}

@Composable
fun CommonButton(
    text: String,
    onClickBtn: () -> Unit,
    modifier: Modifier = Modifier,
) {

//    OutlinedButton(
//              onClick = {
//                  onClickBtn.invoke()
//
//                          }, shape = RoundedCornerShape(5.dp), elevation = ButtonDefaults.elevation(4.dp) ,
//                border = BorderStroke(4.dp, color = Color.Red ),
//                modifier = modifier.fillMaxWidth()
//                    .background(Color.Red)
//
//            ) {
//                Text(
//                    text = "Post Order",
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp, color = Color.Green, textAlign = TextAlign.Center
//
//                )
//            }

    Button(
        onClick = onClickBtn,
        modifier = modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color("#e46c47".toColorInt()))

    ) {

        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 20.sp
        )
    }


}


@Composable
fun AleartDialogBox(){
    var openDialog = remember { mutableStateOf(true)}
    if(openDialog.value){
        AlertDialog(
            onDismissRequest = { openDialog.value=false },
            title = { Text(text = "Aleart Dialogue")},
            text = { Text(text = "Are You Really Want to Logout", color = Color.Black, fontSize = 18.sp)},
             confirmButton = {
                 TextButton(onClick = {
                     openDialog.value=false

                 }) {
                     Text(text = "Confirm", color = Color.Black)
                 }
             },
            dismissButton = {
                TextButton(onClick = {
                    openDialog.value=false
                }) {
                    Text(text = "Dismiss", color = Color.Black)
                }
            },
            backgroundColor = Color.White,
            contentColor = Color.White
            )

    }

}


@Preview
@Composable
fun check(){
    Column(modifier = Modifier.background(Color.White)) {
        Row {
            Text(text = "Hi Antino",
                fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color =( Color.Black))
            Icon(imageVector = Icons.Filled.Face, contentDescription = "Go Back", modifier = Modifier.align(Alignment.CenterVertically))
        }

        Row {
            Text(text = "Welcome to ", fontSize = 17.sp, fontWeight = FontWeight.Light)
            Text(text = "Antino Office App", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        }
    }
}