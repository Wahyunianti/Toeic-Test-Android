package com.example.projectuts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_editprofil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfil : Fragment() {
    lateinit var thisParent : Dashboard
    lateinit var v : View
    lateinit var ft : FragmentTransaction
    lateinit var fragApp : FragmentProfil
    lateinit var adapterACTV: ArrayAdapter<String>
    lateinit var adapterSpinner: ArrayAdapter<String>
    private val api by lazy { ApiRetrofit().endpoint }

    val NmACTV = arrayOf("Pelajar", "Mahasiswa", "Guru", "Dosen", "Karyawan", "Wiraswasta")
    val noSpinner = arrayOf("Laki-laki", "Perempuan")

    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        thisParent = activity as Dashboard
        fragApp = FragmentProfil()
        v = inflater.inflate(R.layout.activity_editprofil,container,false)
        val bt = v.findViewById<ImageButton>(R.id.btnbackapp3)
        val btpr = v.findViewById<ImageButton>(R.id.edtpoto)
        val btsv = v.findViewById<Button>(R.id.btnsave)
        val ccl = v.findViewById<Button>(R.id.btncancel)

        val  nm = v.findViewById<EditText>(R.id.namamu)
        val  un = v.findViewById<EditText>(R.id.usrmu)
        val  pw = v.findViewById<EditText>(R.id.pwmu)
        val  st = v.findViewById<TextView>(R.id.sttmu)


        adapterACTV = ArrayAdapter<String>(
            requireActivity().applicationContext,
            android.R.layout.simple_list_item_1, NmACTV
        )
        adapterSpinner = ArrayAdapter<String>(
            requireActivity().applicationContext,
            android.R.layout.simple_list_item_1, noSpinner
        )

        bt.setOnClickListener {
            val fragManager = requireActivity().supportFragmentManager
            ft = fragManager.beginTransaction()
            ft.replace(R.id.frameLayout, fragApp).commit()
        }

        btpr.setOnClickListener {
            var intentGALERI = Intent()

            intentGALERI.setType("image/*")
            intentGALERI.setAction(Intent.ACTION_GET_CONTENT)

            startActivity(Intent.createChooser(intentGALERI, "Pilih Gambar ..."))
        }


        ccl.setOnClickListener {
            val fragManager = requireActivity().supportFragmentManager
            ft = fragManager.beginTransaction()
            ft.replace(R.id.frameLayout, fragApp).commit()
        }

        api.usr().enqueue(object : Callback<UserItem> {
            override fun onFailure(call: Call<UserItem>, t: Throwable) {
                Log.e("MainActivity", t.toString())
            }

            override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {
                if (response.isSuccessful){
                    val listdata2 = response.body()!!.user
                    nm?.setText(listdata2!![0].nama)
                    un?.setText(listdata2!![0].username)
                    pw?.setText(listdata2!![0].password)
                    st?.setText(listdata2!![0].status)
                }
            }
        })



        btsv.setOnClickListener {
            var nami = nm.text.toString()
            var unam = un.text.toString()
            var pewe = pw.text.toString()
            var stat = st.text.toString()
            val alertBuilder = AlertDialog.Builder(thisParent)
            alertBuilder.setTitle("Informasi")
                .setMessage("Data hasil akan ditambah ke database?")
            alertBuilder.setPositiveButton("OK") { dialog, whichButton ->
                api.update(1, nami, unam, pewe, stat).enqueue(object : Callback<UserItem> {
                    override fun onFailure(call: Call<UserItem>, t: Throwable) {
                        Log.e("MainActivity", t.toString())
                    }

                    override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {
                        if(response.isSuccessful){
                            val submit = response.body()
                        }
                    }
                })


                val fragManager = requireActivity().supportFragmentManager
                ft = fragManager.beginTransaction()
                Toast.makeText(thisParent, "Data Tersimpan", Toast.LENGTH_SHORT).show()
                ft.replace(R.id.frameLayout, fragApp).commit()
            }
            alertBuilder.setNegativeButton("Cancel") { dialog, whichButton ->
            }
            val b = alertBuilder.create()
            b.show()


        }

        return v


    }

}