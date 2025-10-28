package com.example.uts_biodata_pribadi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.uts_biodata_pribadi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTambah.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }

        binding.btnLihat.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra("mode", "edit")
            startActivity(intent)
        }

        binding.btnHapus.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            intent.putExtra("mode", "hapus")
            startActivity(intent)
        }
    }
}
