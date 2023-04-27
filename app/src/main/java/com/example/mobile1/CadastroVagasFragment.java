package com.example.mobile1;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class CadastroVagasFragment extends Fragment {

    private EditText edtArea, edtDescricao, edtRemuneracao, edtLocalidade, edtEmail, edtTelefone, edtDataInicio, edtDataFim;
    private RadioGroup radioGroupAnunciante;
    private RadioButton radioAnuncianteSim, radioAnuncianteNao;
    private Button btnLimpar, btnCadastrar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cadastrovagas, container, false);

        edtArea = view.findViewById(R.id.edt_area);
        edtDescricao = view.findViewById(R.id.edt_descricao);
        edtRemuneracao = view.findViewById(R.id.edt_remuneracao);
        edtLocalidade = view.findViewById(R.id.edt_localidade);
        edtEmail = view.findViewById(R.id.edt_email);
        edtTelefone = view.findViewById(R.id.edt_telefone);
        edtDataInicio = view.findViewById(R.id.edt_data_inicio);
        edtDataFim = view.findViewById(R.id.edt_data_fim);

        radioGroupAnunciante = view.findViewById(R.id.radio_group_anunciante);
        radioAnuncianteSim = view.findViewById(R.id.radio_anunciante_sim);
        radioAnuncianteNao = view.findViewById(R.id.radio_anunciante_nao);

        btnLimpar = view.findViewById(R.id.btn_limpar);
        btnCadastrar = view.findViewById(R.id.btn_cadastrar);

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limparCampos();
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarVaga();
            }
        });

        return view;
    }

    private void limparCampos() {
        edtArea.setText("");
        edtDescricao.setText("");
        edtRemuneracao.setText("");
        edtLocalidade.setText("");
        edtEmail.setText("");
        edtTelefone.setText("");
        edtDataInicio.setText("");
        edtDataFim.setText("");
        radioGroupAnunciante.clearCheck();
    }

    private void cadastrarVaga() {
        String area = edtArea.getText().toString();
        String descricao = edtDescricao.getText().toString();
        String remuneracao = edtRemuneracao.getText().toString();
        String localidade = edtLocalidade.getText().toString();
        String email = edtEmail.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String dataInicio = edtDataInicio.getText().toString();
        String dataFim = edtDataFim.getText().toString();
        boolean anunciante = radioAnuncianteSim.isChecked();

        // Aqui você pode realizar a lógica de cadastro da vaga
        // para o backend

    }

}
