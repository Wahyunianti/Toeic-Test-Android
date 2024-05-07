package com.example.projectuts

import com.google.gson.annotations.SerializedName

data class QuestionsListResponseItem(
    val id : Int,
    val id_kategori : Int,
    val correct_option: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val question: String,
)
