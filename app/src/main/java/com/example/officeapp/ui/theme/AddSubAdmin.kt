package com.example.officeapp.ui.theme

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.officeapp.utils.showError


@Composable
fun RegisterSubAdmin(navController: NavController){



    Surface(elevation = 8.dp,
    shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {
        Column(verticalArrangement = Arrangement.Center) {

            var name by remember { mutableStateOf("" )}
            var email by remember { mutableStateOf("" )}
            var designation by remember { mutableStateOf("" )}

            var isErroremail by remember { mutableStateOf(false)}

            Text(
                text = "Register Here", fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(start = 16.dp)
                )
            Text(
                text = "Enter Your Registration Details Here", fontSize = 15.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center, modifier = Modifier.padding(start = 16.dp)

                )
            OutlinedTextField(
                value = name, onValueChange = {
                    name = it

                },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "person")
                },
                label = { Text(text = "Enter Your Name") }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 15.dp),


            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email=it
                    isErroremail = !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                },

                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "person")
                },
                isError = isErroremail,
                label = { Text(text = "Enter Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 15.dp)
            )
                   if(isErroremail){

                       showError(errorText = "Email is not valid", errorcheck = isErroremail
                       )
                   }

            OutlinedTextField(
                value = designation, onValueChange = {
                    designation = it

                },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "person")
                },
                label = { Text(text = "Enter Designation") }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, top = 15.dp),


                )

            val mRole = listOf("SUBADMIN","OPERATOR")
            var expanded by remember { mutableStateOf(false)}
            var selectedRole by remember {mutableStateOf("")}
            var textFieldSize by remember {mutableStateOf(Size.Zero)}

            var icon =if (expanded){
                Icons.Filled.KeyboardArrowUp
            }else{
                Icons.Filled.KeyboardArrowDown
            }

            OutlinedTextField(
                value = selectedRole, onValueChange = {
                    selectedRole = it

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned {

                        // This value is used to assign to
                        // the DropDown the same width

                        textFieldSize =it.size.toSize()
                    }
                    .padding(start = 20.dp, end = 20.dp, top = 15.dp),enabled = false,
                trailingIcon = {
                               Icon(icon , contentDescription = null,Modifier.clickable {
                                   expanded=!expanded
                               })
                },
                label = { Text(text = "Select Role")}

                )
            
            DropdownMenu(expanded = expanded, onDismissRequest = {expanded=false},
                modifier = Modifier
                    .width(with(LocalDensity.current){textFieldSize.width.toDp()})
                )
            {
               mRole.forEach { label ->
                   DropdownMenuItem(onClick = {
                       selectedRole=label
                       expanded=false
                   }) {

                       Text(text = label)
                   }
               }
            }

            val context = LocalContext.current
            Button(onClick = {

                             if(checkEmpty(email, context,name,designation)){
                                 Toast.makeText(context, "Registration Sucess", Toast.LENGTH_SHORT).show()
                             }

            }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp, end = 50.dp, top = 15.dp),
                shape = RoundedCornerShape(50.dp))
            {
                Text(text = "Register", textAlign = TextAlign.Center)
            }

        }

    }


}
fun checkEmpty(email: String, context: Context, name: String, designation:String): Boolean {

    if (email.isBlank()) {
        Toast.makeText(context, "Email Can't Be Blank", Toast.LENGTH_SHORT).show()
        return false
    } else if (name.isBlank()) {
        Toast.makeText(context, "Name Can't Be Blank", Toast.LENGTH_SHORT).show()
        return false
    }
    else if (designation.isBlank()) {
        Toast.makeText(context, "designation Can't Be Blank", Toast.LENGTH_SHORT).show()
        return false
    }
    return true
}


@Preview
@Composable
fun display(){
    RegisterSubAdmin(navController = rememberNavController())
}
