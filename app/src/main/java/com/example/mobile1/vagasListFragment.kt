package com.example.mobile1

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mobile1.placeholder.Utils.getVagaDetalhesBundle
import com.example.mobile1.placeholder.Vagas

/**
 * A fragment representing a list of Items.
 */
class vagasListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vagas_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvContacts: RecyclerView? = getView()?.findViewById(R.id.rvVagas)
        rvContacts?.layoutManager = LinearLayoutManager(context)
        rvContacts?.adapter = MyItemRecyclerViewAdapter(
            getVagas(),
            ::printVaga

        )
    }
    private fun printVaga(vaga: Vagas) {
       val intent = Intent(context, vagasDetalhes::class.java).apply {
            putExtra("area_conhecimento", vaga.areaConhecimento)
            putExtra("descricao", vaga.descricao)
            putExtra("local", vaga.local)
            putExtra("salario", vaga.salario.toString())
            putExtra("telefone", vaga.telefone)
            putExtra("anunciante", vaga.anunciante)
        }
        context?.startActivity(intent)

    }

    private fun getVagas(): List<Vagas> {
        val vagasList = mutableListOf<Vagas>()
        val vaga = Vagas(0, "eng de software", "paga bem", 1232F, "ribeirao", "asd", "123", "proff")
        val vaga2 = Vagas(1, "eng de computacao", "paga mal", 12F, "ribeirao", "dsa", "321", "proff")
        vagasList.add(vaga)
        vagasList.add(vaga2)
        return vagasList
    }
}