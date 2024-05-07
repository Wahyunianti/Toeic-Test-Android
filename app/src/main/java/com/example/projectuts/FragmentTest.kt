package com.example.projectuts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentTest : Fragment(){
    lateinit var thisParent : Dashboard
    lateinit var v : View
    lateinit var fragApp : Reading_1
    lateinit var ft : FragmentTransaction
    private val api by lazy { ApiRetrofit().endpoint }
    private lateinit var ktgAdapter: KategoriListAdapter
    private lateinit var lstAdapter: ReadingListAdapter

    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        thisParent = activity as Dashboard


        v = inflater.inflate(R.layout.frag_data_test,container,false)
        val listKtg = v.findViewById<RecyclerView>(R.id.list)
        val listRd = v.findViewById<RecyclerView>(R.id.rid)
        val manager = LinearLayoutManager(thisParent)
        val baru = LinearLayoutManager(thisParent)


        listRd.setLayoutManager(manager)
        listRd.setHasFixedSize(true)
        lstAdapter = ReadingListAdapter(arrayListOf(), object : ReadingListAdapter.OnAdapterListener {
            override fun onClick(note: ReadingItem.Data) {
            val bundle = Bundle()
            bundle.putInt("note", note.id_kategori!!)
                val fragManager = requireActivity().supportFragmentManager
                fragApp = Reading_1()
                fragApp.arguments = bundle
                ft = fragManager.beginTransaction()
                ft.replace(R.id.frameLayout, fragApp).addToBackStack(tag).commit()
            }

        })
        listRd.adapter = lstAdapter

        listKtg.setLayoutManager(baru)
        listKtg.setHasFixedSize(true)
        ktgAdapter = KategoriListAdapter(arrayListOf())
        listKtg.adapter = ktgAdapter

        api.rad().enqueue(object : Callback<ReadingItem> {
            override fun onFailure(call: Call<ReadingItem>, t: Throwable) {
                Log.e("MainActivity", t.toString())
            }

            override fun onResponse(call: Call<ReadingItem>, response: Response<ReadingItem>) {
                if (response.isSuccessful){
                    val listdata = response.body()!!.kategori
                    lstAdapter.setData(listdata)
                }
            }
        })

        api.ktg().enqueue(object : Callback<KategoriItem> {
            override fun onFailure(call: Call<KategoriItem>, t: Throwable) {
                Log.e("MainActivity", t.toString())
            }

            override fun onResponse(call: Call<KategoriItem>, response: Response<KategoriItem>) {
                if (response.isSuccessful){
                    val listdata2 = response.body()!!.kategori
                    ktgAdapter.setData(listdata2)
                }
            }
        })

//
//        val bt = v.findViewById<Button>(R.id.btnRtc)
//
//        bt.setOnClickListener {
//            val fragManager = requireActivity().supportFragmentManager
//            ft = fragManager.beginTransaction()
//            ft.replace(R.id.frameLayout, fragApp).addToBackStack(tag).commit()
//        }

        return v
    }
}