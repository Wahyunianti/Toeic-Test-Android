package com.example.projectuts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultActivity : AppCompatActivity() {
    var btnKbl: Button?=null
    private lateinit var retService: QuestionsAPI
    private val api by lazy { ApiRetrofit().endpoint }


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        var txtAnswer = findViewById<TextView>(R.id.textView4)
        var terimaData: Bundle? = intent.extras


        txtAnswer.text="Total Nilai : "+terimaData?.getInt("nunal")+"/"+terimaData?.getInt("tutal")
        retService = RetrofitHelper
            .getInstance()
            .create(QuestionsAPI::class.java)

        btnKbl=findViewById(R.id.button2)
        btnKbl?.setOnClickListener{
            val alertBuilder = AlertDialog.Builder(this)
            alertBuilder.setTitle("Informasi")
                .setMessage("Data hasil akan ditambah ke database?")
            alertBuilder.setPositiveButton("OK") { dialog, whichButton ->
                var tt = terimaData?.getInt("tutal")
                var nn = terimaData?.getInt("nunal")
                api.create(tt!!,nn!!)
                    .enqueue(object : Callback<HasilItem>{
                        override fun onResponse(
                            call: Call<HasilItem>,
                            response: Response<HasilItem>
                        ) {
                            if(response.isSuccessful){
                                val submit = response.body()
                            }
                        }
                        override fun onFailure(call: Call<HasilItem>, t: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })


                Dashboard.result =0
                Dashboard.total =0
                var antent = Intent(this,
                    Dashboard::class.java)
                antent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(antent)
            }
            alertBuilder.setNegativeButton("Cancel") { dialog, whichButton ->
            }
            val b = alertBuilder.create()
            b.show()



        }

//        btnKbl?.setOnClickListener(View.OnClickListener {
//            val upload = HasilItem(txtAnswer.toString(),txtAnswer.toString())
//                        val postResponse: LiveData<Response<HasilItem>> = liveData{
//                val response = retService.uploadHasil(upload)
//                emit(response)
//            }
//            postResponse.observe(this, Observer {
//                val receivedHasilItem = it.body()
//                val result = "${receivedHasilItem?.hasil}" + "${receivedHasilItem?.total}"
//                txtTest.text = result
//            })
//
//            val quesApi=RetrofitHelper.getInstance().create(QuestionsAPI::class.java)
//            GlobalScope.launch {
//                PostApi.apiService.uploadHasil(upload)
//                val result= PostApi.apiService.uploadHasil(upload)
//                val receivedHasilItem = quesApi.uploadHasil(upload).body()
//                if (result!=null){
//                    Log.e("api respoonse is ", result.body().toString())
//                }
//            }
//
//            Dashboard.result =0
//            Dashboard.total =0
//            var intent = Intent(this,
//                Dashboard::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//
//
//        })
    }
    private fun setupListener(){

    }

}