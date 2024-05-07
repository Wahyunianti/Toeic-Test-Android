package com.example.projectuts

import com.google.gson.annotations.SerializedName

data class KategoriItem(
    val kategori: List<Data>)
{
    data class Data (val id_kategori: Int?,
                     val id_sesi: Int?,
                     val nama_kategori: String?)
}


