package com.example.mobile1;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private Spinner spinnerConta;
    private Button buttonCadastrar;
    private Button buttonLimpar;

    private Button buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrousuario);

        // Referenciando elementos da interface
        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        spinnerConta = findViewById(R.id.spinnerConta);
        buttonCadastrar = findViewById(R.id.buttonCadastrar);
        buttonLimpar = findViewById(R.id.buttonLimpar);
        buttonVoltar = findViewById(R.id.buttonVoltar);

        // Criando ArrayAdapter para popular o Spinner com os valores do array tipo_conta
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipo_conta, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConta.setAdapter(adapter);

        // Definindo listener para o Spinner
        spinnerConta.setOnItemSelectedListener(this);

        // Definindo listener para o botão Cadastrar
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });

        // Definindo listener para o botão Limpar
        buttonLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparCampos();
            }
        });

        // Definindo listener para o botão voltar
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void cadastrar() {
        // Obter valores dos campos de texto e Spinner
        String nome = editTextNome.getText().toString();
        String email = editTextEmail.getText().toString();
        String senha = editTextSenha.getText().toString();
        String tipoConta = spinnerConta.getSelectedItem().toString();


        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return; // Encerra o método caso algum campo esteja vazio
        }

        // Exibir mensagem com os valores obtidos
        cadastrarFirebase(email, senha, nome, tipoConta);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private void limparCampos() {
        // Limpar valores dos campos de texto e Spinner
        editTextNome.setText("");
        editTextEmail.setText("");
        editTextSenha.setText("");
        spinnerConta.setSelection(0);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Exibir mensagem com o item selecionado no Spinner
        String item = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, "Tipo de conta selecionado: " + item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Método vazio, não é necessário implementar
    }


    private void cadastrarFirebase(String email, String senha, String nome, String tipoConta) {


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


        Map<String, Object> dadosUsuario = new HashMap<>();
        dadosUsuario.put("tipoConta", tipoConta.toString());
        dadosUsuario.put("nome", nome.toString());
        dadosUsuario.put("email", email.toString());

        firebaseAuth.createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        String uid = authResult.getUser().getUid();
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("usuarios");
                        databaseReference.child(uid).setValue(dadosUsuario)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Cadastro realizado com sucesso
                                        Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Tratar falha ao salvar os dados no banco de dados
                                        Toast.makeText(getApplicationContext(), "Erro ao salvar os dados do usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Tratar falha no cadastro do usuário
                        Toast.makeText(getApplicationContext(), "Erro ao cadastrar o usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }

}
