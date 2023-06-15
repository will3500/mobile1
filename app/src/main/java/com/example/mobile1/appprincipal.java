package com.example.mobile1;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class appprincipal extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;

    private CadastroVagasFragment cadastroVagasFragment;

    private vagasListFragment vagasfragment;
    private UserFragment userFragment;
    boolean anunciante;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainapp);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentContainer = findViewById(R.id.fragment_container);


        userFragment = new UserFragment();


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            verificarTipoContaAnunciante(currentUser, new OnTipoContaAnuncianteVerificadoListener() {
                @Override
                public void onTipoContaAnuncianteVerificado(boolean isAnunciante) {
                    if (isAnunciante) {
                        anunciante = true;
                        cadastroVagasFragment = new CadastroVagasFragment();

                    }else{
                    }
                }
            });

            vagasfragment = new vagasListFragment();

            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {




                    if (item.getItemId() == R.id.action_home) {
                        // Carrega o fragment "MyFragment"

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, userFragment).commit();

                        return true;
                    } else if (item.getItemId() == R.id.action_vagas) {

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, vagasfragment).commit();

                        return true;
                    } else if (item.getItemId() == R.id.action_cadastro) {
                        if(anunciante){
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cadastroVagasFragment).commit();
                        }else{
                            Toast.makeText(getApplicationContext(), "Você não tem permissão, apenas anunciantes", Toast.LENGTH_SHORT).show();
                            return false;
                        }

                        return true;
                    } else {

                        return false;
                    }

                }
            });

            // Carrega o fragment "MyFragment" por padrão
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, userFragment).commit();
        }
    }


    private void verificarTipoContaAnunciante(FirebaseUser user, OnTipoContaAnuncianteVerificadoListener listener) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("usuarios").child(user.getUid());
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String tipoConta = snapshot.child("tipoConta").getValue(String.class);
                    if (tipoConta != null && tipoConta.equals("Anunciante")) {
                        listener.onTipoContaAnuncianteVerificado(true);
                    } else {
                        listener.onTipoContaAnuncianteVerificado(false);
                    }
                } else {
                    listener.onTipoContaAnuncianteVerificado(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onTipoContaAnuncianteVerificado(false);
            }
        });
    }

    // Interface para retornar o resultado da verificação
    interface OnTipoContaAnuncianteVerificadoListener {
        void onTipoContaAnuncianteVerificado(boolean isAnunciante);
    }




}