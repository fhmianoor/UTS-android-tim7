package com.example.uts_biodata_pribadi

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts_biodata_pribadi.databinding.ActivityListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var adapter: BiodataAdapter
    private var mode: String? = null

    private var biodataList: ArrayList<Biodata> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mode = intent.getStringExtra("mode")

        setupRecyclerView()
        setupSearchView()
        loadData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = BiodataAdapter(this, biodataList) { biodata ->
            when (mode) {
                "edit" -> {
                    val intent = Intent(this, AddEditActivity::class.java)
                    intent.putExtra("biodata", biodata) // Pasti Parcelize
                    startActivity(intent)
                }
                "hapus" -> {
                    AlertDialog.Builder(this)
                        .setTitle("Hapus Data")
                        .setMessage("Yakin ingin menghapus ${biodata.nama}?")
                        .setPositiveButton("Ya") { _, _ ->
                            hapusDataApi(biodata.id)
                        }
                        .setNegativeButton("Tidak", null)
                        .show()
                }
                else -> {
                    AlertDialog.Builder(this)
                        .setTitle("Detail Biodata")
                        .setMessage(
                            """
                            Nama: ${biodata.nama}
                            Umur: ${biodata.umur}
                            Alamat: ${biodata.alamat}
                            Pekerjaan: ${biodata.pekerjaan}
                            """.trimIndent()
                        )
                        .setPositiveButton("OK", null)
                        .show()
                }
            }
        }

        binding.recyclerView.adapter = adapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText.orEmpty())
                return true
            }
        })
    }

    private fun filterList(query: String) {
        val filtered = if (query.isEmpty()) biodataList
        else biodataList.filter {
            it.nama.contains(query, true) ||
                    it.alamat.contains(query, true) ||
                    it.pekerjaan.contains(query, true)
        }
        adapter.updateData(filtered)
    }

    // 🔥 GET ALL DATA DARI API
    private fun loadData() {
        ApiClient.api.getAll().enqueue(object : Callback<List<Biodata>> {
            override fun onResponse(call: Call<List<Biodata>>, response: Response<List<Biodata>>) {
                if (response.isSuccessful) {
                    biodataList.clear()
                    biodataList.addAll(response.body() ?: emptyList())
                    adapter.updateData(biodataList)
                } else {
                    Log.e("API", "Gagal load data: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Biodata>>, t: Throwable) {
                Log.e("API", "Error: ${t.message}")
            }
        })
    }

    // 🔥 DELETE DATA KE API
    private fun hapusDataApi(id: Int) {
        ApiClient.api.delete(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) loadData()
                else Log.e("API", "Gagal hapus: ${response.code()}")
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("API", "Error hapus: ${t.message}")
            }
        })
    }
}
