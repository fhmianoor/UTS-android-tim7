package com.example.uts_biodata_pribadi

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.SearchView
import com.example.uts_biodata_pribadi.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var db: DatabaseHelper
    private lateinit var adapter: BiodataAdapter
    private var mode: String? = null

    private var biodataList: ArrayList<Biodata> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
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
                    intent.putExtra("biodata", biodata)
                    startActivity(intent)
                }
                "hapus" -> {
                    AlertDialog.Builder(this)
                        .setTitle("Hapus Data")
                        .setMessage("Yakin ingin menghapus ${biodata.nama}?")
                        .setPositiveButton("Ya") { _, _ ->
                            db.deleteBiodata(biodata.id)
                            loadData()
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
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText.orEmpty())
                return true
            }
        })
    }

    private fun filterList(query: String) {
        val filtered = if (query.isEmpty()) {
            biodataList
        } else {
            biodataList.filter {
                it.nama.contains(query, ignoreCase = true) ||
                        it.alamat.contains(query, ignoreCase = true) ||
                        it.pekerjaan.contains(query, ignoreCase = true)
            }
        }
        adapter.updateData(filtered.toList())
    }

    private fun loadData() {
        val dataDariDb = db.getAllBiodata()

        biodataList.clear()
        biodataList.addAll(dataDariDb)

        adapter.updateData(biodataList)
    }
}