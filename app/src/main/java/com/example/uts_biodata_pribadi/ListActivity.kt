package com.example.uts_biodata_pribadi

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts_biodata_pribadi.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var db: DatabaseHelper
    private lateinit var adapter: BiodataAdapter
    private var mode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        mode = intent.getStringExtra("mode")
        loadData()
    }

    private fun loadData() {
        val list = db.getAllBiodata()
        adapter = BiodataAdapter(this, list) { biodata ->
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

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}
