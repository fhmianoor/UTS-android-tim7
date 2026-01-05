package com.example.uts_biodata_pribadi

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Biodata(
    var id: Int = 0,
    var nama: String,
    var alamat: String,
    var umur: String,
    var pekerjaan: String
) : Parcelable
