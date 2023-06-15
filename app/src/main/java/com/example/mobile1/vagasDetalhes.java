package com.example.mobile1;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile1.R;

public class vagasDetalhes extends AppCompatActivity {

    private ImageView imageView;
    private String telefone;
    private TextView tvAreaConhecimento, tvDescricao, tvLocal, tvSalario, tvTelefone, buttonLigar, buttonVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vagasdetalhes);

        imageView = findViewById(R.id.imageView);
        tvAreaConhecimento = findViewById(R.id.tvAreaConhecimento);
        tvDescricao = findViewById(R.id.tvDescricao);
        tvLocal = findViewById(R.id.tvLocal);
        tvSalario = findViewById(R.id.tvSalario);
        tvTelefone = findViewById(R.id.tvTelefone);
        buttonLigar = findViewById(R.id.buttonLigar);
        buttonVoltar = findViewById(R.id.buttonVoltar);

        // Recebe os valores dos TextViews de outra classe
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String areaConhecimento = extras.getString("area_conhecimento");
            String descricao = extras.getString("descricao");
            String local = extras.getString("local");
            String salario = extras.getString("salario");
            telefone = extras.getString("telefone");
            String anunciante = extras.getString("anunciante");

            // Define os valores nos TextViews
            tvAreaConhecimento.setText(areaConhecimento);
            tvDescricao.setText(descricao);
            tvLocal.setText("Local: " + local);
            tvSalario.setText("Salario: "+salario);
            tvTelefone.setText("Telefone: "+telefone.toString());
           // tvAnunciante.setText("Anunciante: "+anunciante);
        }

        // Definindo listener para o botão voltar
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        // Definindo listener para o botão Limpar
        buttonLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ligarAnunciante();
            }
        });


    }


    private void ligarAnunciante() {

    }
}
