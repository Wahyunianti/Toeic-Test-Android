package com.example.projectuts

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val user = findViewById<EditText>(R.id.txtUser)
        val pass = findViewById<EditText>(R.id.txtPass)
        val button = findViewById<Button>(R.id.btnlogin)
        val btndftr = findViewById<Button>(R.id.btndaftar)

        button.setOnClickListener {
            if (user.text.toString() == "Wahyuni" && pass.text.toString() == "12345"){
                Toast.makeText(this@MainActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                val move = Intent(this@MainActivity,Dashboard::class.java)
                move.putExtra("usrnm", user.text.toString())
                startActivity(move)
            }
            else{
                val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
                builder.setMessage("Username atau Password salah!")
                    .setNegativeButton("Retry",
                        null).create().show()
            }
        }

        btndftr.setOnClickListener{
            val daftar = Intent(this, RegisterActivity::class.java)
            startActivity(daftar)
        }
    }
}