package com.example.projectuts

import com.google.gson.annotations.SerializedName

data class AbsensiItem(
    val absensi: List<Data>)
{
    data class Data (val id: Int?,
                     val nama: String?,
                     val tanggal: String?,
                     val waktu: String?)
}


