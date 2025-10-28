package com.example.uts_biodata_pribadi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_biodata_pribadi.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditBinding
    private lateinit var db: DatabaseHelper
    private var biodata: Biodata? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DatabaseHelper(this)
        biodata = intent.getSerializableExtra("biodata") as? Biodata

        if (biodata != null) {
            binding.etNama.setText(biodata!!.nama)
            binding.etUmur.setText(biodata!!.umur)
            binding.etAlamat.setText(biodata!!.alamat)
            binding.etPekerjaan.setText(biodata!!.pekerjaan)
            binding.btnSave.text = "Update Data"
        }

        binding.btnSave.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val umur = binding.etUmur.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val pekerjaan = binding.etPekerjaan.text.toString()

            if (nama.isEmpty() || umur.isEmpty() || alamat.isEmpty() || pekerjaan.isEmpty()) {
                Toast.makeText(this, "Lengkapi semua field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (biodata == null) {
                db.insertBiodata(Biodata(0, nama, umur, alamat, pekerjaan))
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
            } else {
                db.updateBiodata(Biodata(biodata!!.id, nama, umur, alamat, pekerjaan))
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
            }

            finish()
        }
    }
}
