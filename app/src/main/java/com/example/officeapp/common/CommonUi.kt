package com.example.officeapp.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.officeapp.model.LoginDataModel
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
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        maxLines = maxLine,
        label = { Text(text = label) }, modifier = modifier
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
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)

    ) {

        Text(
            text = "Order Placed",
            textAlign = TextAlign.Center,
            color = Color.Green,
            fontSize = 20.sp
        )
    }


}
