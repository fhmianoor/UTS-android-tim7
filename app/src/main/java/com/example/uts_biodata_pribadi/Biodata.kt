package com.example.uts_biodata_pribadi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class Biodata(
    var id: Int = 0,
    var nama: String,
    var umur: String,
    var alamat: String,
    var pekerjaan: String
) : Serializable

