package com.example.officeapp.utils

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
object Utils{
    fun checkEmail(email: String, context: Context): Boolean {

        if (email.isBlank()) {
            Toast.makeText(context, "Email Can't Be Blank", Toast.LENGTH_SHORT).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Thats Not a Valid Email", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    fun checkPassword(passWord: String, context: Context): Boolean {

        if (passWord.length < 4) {
            Toast.makeText(context, "Password needs to consist of Atleast 8 Char", Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (passWord.isBlank()) {
            Toast.makeText(context, "Password Can't Be Blank", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}



@Composable
fun showError(errorText: String, errorcheck: Boolean){
    if (errorcheck) {
        Text(
            text = "${errorText}",
            color = MaterialTheme.colors.error,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = 20.dp),
            textAlign = TextAlign.Start

        )

    }

}