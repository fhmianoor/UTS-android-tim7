package com.example.uts_biodata_pribadi

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_biodata_pribadi.databinding.ItemBiodataBinding

class BiodataAdapter(
    private val context: Context,
    private val list: List<Biodata>,
    private val listener: (Biodata) -> Unit
) : RecyclerView.Adapter<BiodataAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemBiodataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Biodata) {
            binding.tvNama.text = item.nama
            binding.tvUmur.text = "Umur: ${item.umur}"
            binding.tvAlamat.text = "Alamat: ${item.alamat}"
            binding.tvPekerjaan.text = "Pekerjaan: ${item.pekerjaan}"

            binding.root.setOnClickListener { listener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBiodataBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
