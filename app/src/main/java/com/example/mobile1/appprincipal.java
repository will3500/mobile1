package com.example.mobile1;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class appprincipal extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout fragmentContainer;

    private CadastroVagasFragment cadastroVagasFragment;

    private vagasListFragment vagasfragment;
    private UserFragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainapp);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentContainer = findViewById(R.id.fragment_container);

        cadastroVagasFragment = new CadastroVagasFragment();
        userFragment = new UserFragment();
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, cadastroVagasFragment).commit();
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