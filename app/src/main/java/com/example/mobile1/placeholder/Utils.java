package com.example.mobile1.placeholder;

import android.os.Bundle;

public class Utils {

    public static Bundle getVagaDetalhesBundle(String areaConhecimento, String descricao, String local, String salario, String telefone, String anunciante) {
        Bundle bundle = new Bundle();
        bundle.putString("area_conhecimento", areaConhecimento);
        bundle.putString("descricao", descricao);
        bundle.putString("local", local);
        bundle.putString("salario", salario);
        bundle.putString("telefone", telefone);
        bundle.putString("anunciante", anunciante);
        return bundle;
    }

}
