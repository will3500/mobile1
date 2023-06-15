package com.example.mobile1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class recuperarActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonVoltar;
    private Button buttonRecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperarsenha);

        editTextEmail = findViewById(R.id.editTextEmail);
        buttonVoltar = findViewById(R.id.buttonVoltar);
        buttonRecuperar = findViewById(R.id.buttonAlterar);

        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(recuperarActivity.this, "Um email de recuperação de senha foi enviado para " + email, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(recuperarActivity.this, "Falha ao enviar email de recuperação de senha. Verifique o endereço de email e tente novamente.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
