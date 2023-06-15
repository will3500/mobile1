package com.example.mobile1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mobile1.placeholder.Vagas;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CadastroVagasFragment extends Fragment {

    private EditText edtArea, edtDescricao, edtRemuneracao, edtLocalidade, edtEmail, edtTelefone, edtDataInicio, edtDataFim;

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
        String anuncianteUid = "";

        if (area.isEmpty() || descricao.isEmpty() || remuneracao.isEmpty() || localidade.isEmpty() || email.isEmpty() || telefone.isEmpty() || dataInicio.isEmpty() || dataFim.isEmpty()) {
            Toast.makeText(requireContext(), "preencha todos os campos", Toast.LENGTH_SHORT).show();
        }else{
            cadastrarVagaFirebase(area,descricao,remuneracao,localidade,email,telefone,dataInicio,dataFim);
        }
    }


    private void cadastrarVagaFirebase(String area, String descricao, String remuneracao, String localidade, String email, String telefone, String dataInicio, String dataFim) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String anuncianteUid = currentUser.getUid();
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("vagas");
            String vagaId = databaseRef.push().getKey();

            // Obter o número de elementos no banco de dados
            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long count = snapshot.getChildrenCount();
                    String vagaId = String.valueOf(count);

                    // Criar a instância de Vagas com o número de elementos + 1
                    Vagas vaga = new Vagas((int) count, area, descricao, remuneracao, localidade, email, telefone, dataInicio, dataFim, anuncianteUid, true);

                    databaseRef.child(vagaId).setValue(vaga)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(requireContext(), "Vaga cadastrada com sucesso", Toast.LENGTH_SHORT).show();
                                    limparCampos();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(requireContext(), "Erro ao cadastrar a vaga", Toast.LENGTH_SHORT).show();
                                }
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(requireContext(), "Erro ao obter o número de elementos", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


}
