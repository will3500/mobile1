package com.example.mobile1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonEntrar: Button
    private lateinit var buttonCadastro: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referenciando elementos da interface
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonEntrar = findViewById(R.id.buttonEntrar)
        buttonCadastro = findViewById(R.id.buttonCadastro)

        // Definindo ação ao clicar no botão "Entrar"
        buttonEntrar.setOnClickListener(View.OnClickListener {
            // Obter valores dos campos de email e senha
            val email = editTextEmail.text.toString()
            val senha = editTextPassword.text.toString()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show() // Encerra o método caso algum campo esteja vazio
            }else if (email == "user" && senha == "123") {
                val intent = Intent(this@MainActivity, appprincipal::class.java)
                startActivity(intent)
            }


        })

        // Definindo ação ao clicar no botão "Cadastre-se"
        buttonCadastro.setOnClickListener(View.OnClickListener {
            // Redirecionar para a tela de cadastro
            val intent = Intent(this@MainActivity, CadastroActivity::class.java)
            startActivity(intent)
        })
    }
}
