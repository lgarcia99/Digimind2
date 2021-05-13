package com.example.digimind

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val btn_registrarse: Button = findViewById(R.id.btn_registrarse)
        val btn_contra: TextView = findViewById(R.id.tv_olvidasteContra)
        val btn_ingresar: Button = findViewById(R.id.btn_ingresar)

        btn_registrarse.setOnClickListener{
            val intent: Intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        btn_contra.setOnClickListener{
            val intent: Intent = Intent(this, ContrasenaActivity::class.java)
            startActivity(intent)
        }

        btn_ingresar.setOnClickListener{
            valida_ingreso()
        }
    }

    /*override fun onStart() {
        super.onStart()

        var user = auth.currentUser

        if (user != null) {
            // User is signed in.
            var intent = Intent(this, MainActivity::class.java)
        }
    }*/

    private fun valida_ingreso(){
        val et_correo: EditText = findViewById(R.id.et_correo)
        val et_contra: EditText = findViewById(R.id.et_contra)

        var correo: String = et_correo.text.toString()
        var contra: String = et_contra.text.toString()

        if(!(correo.isNullOrBlank() || contra.isNullOrBlank())){
            ingresaFirebase(correo, contra)
        }else{
            Toast.makeText(
                this, "Ingresar datos",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    private fun ingresaFirebase(correo: String, contra: String) {
        auth.signInWithEmailAndPassword(correo, contra)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}