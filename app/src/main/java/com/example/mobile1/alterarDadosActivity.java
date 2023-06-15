package com.example.mobile1;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class alterarDadosActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText editTextNome;
    private EditText editTextEmail;
    private EditText editTextSenha;
    private Spinner spinnerConta;
    private Button buttonVoltar;
    private Button buttonLimpar;
    private Button buttonAlterar;
    private Button buttonExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alterardados);

        editTextNome = findViewById(R.id.editTextNome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSenha = findViewById(R.id.editTextSenha);
        spinnerConta = findViewById(R.id.spinnerConta);
        buttonVoltar = findViewById(R.id.buttonVoltar);
        buttonLimpar = findViewById(R.id.buttonLimpar);
        buttonAlterar = findViewById(R.id.buttonAlterar);
        buttonExcluir = findViewById(R.id.buttonExcluir);


// Criando ArrayAdapter para popular o Spinner com os valores do array tipo_conta
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tipo_conta, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerConta.setAdapter(adapter);

        // Definindo listener para o Spinner
        spinnerConta.setOnItemSelectedListener(this);


        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos();
            }
        });

        buttonAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextNome.getText().toString().isEmpty() ||!editTextEmail.getText().toString().isEmpty() ||!editTextSenha.getText().toString().isEmpty() || !spinnerConta.getSelectedItem().toString().isEmpty() || editTextSenha.length() >=6 || isEmailValid(editTextEmail.getText().toString()) ) {

                        String novoNome = editTextNome.getText().toString();
                        String novoEmail = editTextEmail.getText().toString();
                        String novaConta = spinnerConta.getSelectedItem().toString();
                        String novaSenha = editTextSenha.getText().toString();

                        // Chame a função para realizar a alteração dos dados
                        alterarDadosUsuario(novoNome, novoEmail, novaConta, novaSenha);


                }else{
                    Toast.makeText(getApplicationContext(), "digite todos os campos corretamente " , Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirContaDialog();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void limparCampos() {
        // Limpar valores dos campos de texto e Spinner
        editTextNome.setText("");
        editTextEmail.setText("");
        editTextSenha.setText("");
        spinnerConta.setSelection(0);

    }

    private void alterarDadosUsuario(String novoNome, String novoEmail, String novaConta, String novaSenha) {
        // Obtenha a referência do usuário atualmente logado
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            currentUser.updatePassword(novaSenha)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Atualize os dados no Firebase Authentication
                            AuthCredential credential = EmailAuthProvider.getCredential(currentUser.getEmail(), novaSenha);
                            currentUser.reauthenticate(credential);
                            currentUser.updateEmail(novoEmail)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Sucesso ao atualizar o email

                                            // Atualize o nome no Firebase Authentication
                                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                    .setDisplayName(novoNome)
                                                    .build();

                                            currentUser.updateProfile(profileUpdates)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void aVoid) {
                                                            // Sucesso ao atualizar o nome

                                                            // Atualize os dados no Firebase Realtime Database
                                                            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("usuarios").child(userId);
                                                            Map<String, Object> updates = new HashMap<>();
                                                            updates.put("nome", novoNome);
                                                            updates.put("email", novoEmail);
                                                            updates.put("tipoConta", novaConta);
                                                            databaseRef.updateChildren(updates, new DatabaseReference.CompletionListener() {
                                                                @Override
                                                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                                                    if (error != null) {
                                                                        Toast.makeText(getApplicationContext(), "Erro ao salvar os dados do usuário no db: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        FirebaseAuth.getInstance().signOut(); // Faz logout do usuário

                                                                        // Volta para a Activity desejada
                                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                        startActivity(intent);
                                                                        finish();

                                                                    }
                                                                }
                                                            });
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(getApplicationContext(), "Erro ao salvar o nome do usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Erro ao salvar o email do usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Erro ao salvar a senha do usuário: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });



        }
    }




    private void excluirUsuario() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Excluir o usuário do Firebase Authentication
            currentUser.delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Sucesso ao excluir o usuário do Firebase Authentication

                            // Excluir o usuário da tabela "usuarios" do Firebase Realtime Database
                            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("usuarios").child(userId);
                            databaseRef.removeValue()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Sucesso ao excluir o usuário da tabela "usuarios"
                                            Toast.makeText(getApplicationContext(), "Usuário excluído com sucesso", Toast.LENGTH_SHORT).show();

                                            // Redirecionar para a tela de login ou qualquer outra tela desejada
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Falha ao excluir o usuário da tabela "usuarios"
                                            Toast.makeText(getApplicationContext(), "Erro ao excluir usuário", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Falha ao excluir o usuário do Firebase Authentication
                            Toast.makeText(getApplicationContext(), "Erro ao excluir usuário", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void excluirContaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir conta");
        builder.setMessage("Tem certeza de que deseja excluir sua conta?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                excluirUsuario(); // Chama a função para excluir o usuário
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // Fecha o diálogo sem excluir a conta
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



    private void okToast(String msg){
        Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show();

    }

    private boolean isEmailValid(String email) {
        // Verifica se o email não está vazio
        if (TextUtils.isEmpty(email)) {
            return false;
        }

        // Verifica se o email possui um formato válido usando uma expressão regular
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(emailPattern)) {
            return false;
        }

        return true;
    }


}