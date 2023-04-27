package com.example.mobile1

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.example.mobile1.placeholder.PlaceholderContent.PlaceholderItem
import com.example.mobile1.databinding.FragmentVagasListBinding
import com.example.mobile1.placeholder.Vagas

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val vagastList: List<Vagas>,
    private val onItemClickListener: (Vagas) -> Unit,
) :
    RecyclerView.Adapter<MyItemRecyclerViewAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setInfo(contactName: Int) {
            val imgContact: ImageView = itemView.findViewById(R.id.imgContact)
            val tvrvVagasName: TextView = itemView.findViewById(R.id.tvVagaName)
            tvrvVagasName.text = vagastList.get(contactName).areaConhecimento
            itemView.setOnClickListener {
                onItemClickListener(vagastList.get(contactName))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vagasitem, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vagastList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.setInfo(vagastList[position].id)
    }

}