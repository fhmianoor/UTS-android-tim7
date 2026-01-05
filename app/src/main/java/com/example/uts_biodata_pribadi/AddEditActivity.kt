package com.example.uts_biodata_pribadi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_biodata_pribadi.databinding.ActivityAddEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddEditActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddEditBinding
    private var biodata: Biodata? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔥 Ambil data dari intent (Parcelable Extension)
        biodata = intent.getParcelableCompat("biodata")

        // Jika edit mode
        biodata?.let {
            binding.etNama.setText(it.nama)
            binding.etUmur.setText(it.umur)
            binding.etAlamat.setText(it.alamat)
            binding.etPekerjaan.setText(it.pekerjaan)
            binding.btnSave.text = "Update Data"
        }

        binding.btnSave.setOnClickListener {
            simpanData()
        }
    }

    private fun simpanData() {
        val nama = binding.etNama.text.toString().trim()
        val umur = binding.etUmur.text.toString().trim()
        val alamat = binding.etAlamat.text.toString().trim()
        val pekerjaan = binding.etPekerjaan.text.toString().trim()

        if (nama.isEmpty() || umur.isEmpty() || alamat.isEmpty() || pekerjaan.isEmpty()) {
            Toast.makeText(this, "Lengkapi semua field", Toast.LENGTH_SHORT).show()
            return
        }

        val data = Biodata(
            id = biodata?.id ?: 0,
            nama = nama,
            umur = umur,
            alamat = alamat,
            pekerjaan = pekerjaan
        )

        if (biodata == null) {
            // ➕ INSERT (POST)
            ApiClient.api.insert(data).enqueue(object : Callback<Biodata> {
                override fun onResponse(call: Call<Biodata>, response: Response<Biodata>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddEditActivity, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddEditActivity, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Biodata>, t: Throwable) {
                    Toast.makeText(this@AddEditActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            // ✏️ UPDATE (PUT)
            ApiClient.api.update(biodata!!.id, data).enqueue(object : Callback<Biodata> {
                override fun onResponse(call: Call<Biodata>, response: Response<Biodata>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@AddEditActivity, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddEditActivity, "Gagal update data", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Biodata>, t: Throwable) {
                    Toast.makeText(this@AddEditActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
