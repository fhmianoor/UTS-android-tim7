package com.example.uts_biodata_pribadi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_biodata_pribadi.databinding.ItemBiodataBinding

class BiodataAdapter(
    private val context: Context,
    private var list: List<Biodata>,
    private val listener: (Biodata) -> Unit
) : RecyclerView.Adapter<BiodataAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemBiodataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Biodata) {
            binding.textViewNama.text = item.nama
            binding.textViewUmur.text = "Umur: ${item.umur}"
            binding.textViewAlamat.text = "Alamat: ${item.alamat}"
            binding.textViewPekerjaan.text = "Pekerjaan: ${item.pekerjaan}"

            // Klik item → callback ke activity
            binding.root.setOnClickListener { listener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBiodataBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    // 🔥 Update data dari API
    fun updateData(newList: List<Biodata>) {
        list = newList
        notifyDataSetChanged()
    }
}
