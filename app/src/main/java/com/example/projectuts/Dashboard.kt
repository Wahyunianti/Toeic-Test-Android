package com.example.projectuts

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.view.ContextMenu
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.activity_pengaturan.*
import kotlinx.android.synthetic.main.frag_data_profil.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class Dashboard : AppCompatActivity(), View.OnClickListener, NavigationBarView.OnItemSelectedListener{

    lateinit var fragTest : FragmentTest
    lateinit var fragProfil : FragmentProfil
    lateinit var fragApp : Pengaturann
    lateinit var ft : FragmentTransaction
    lateinit var adapterListView: ArrayAdapter<String>
    lateinit var dataListView: ArrayList<String>
    private val api by lazy { ApiRetrofit().endpoint }
    private var backPressedTime = 0L


    companion object{
        var result=0
        var total=0
    }

    var tahun = 0
    var bulan = 0
    var hari = 0

    var jam = 0
    var menit = 0

    var ubahTanggalDialog = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        btntgl.text = "$dayOfMonth / ${month+1} / $year "
        btntime.text = "$jam : $menit"
        tahun = year
        bulan = month + 1
        hari = dayOfMonth
    }

    var ubahWaktuDialog = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        btntgl.text = "$hari / $bulan / $tahun"
        btntime.text = "$hourOfDay : $minute "
        jam = hourOfDay
        menit = minute
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        bottomNavigationView.setOnItemSelectedListener(this)
        fragTest = FragmentTest()
        fragProfil = FragmentProfil()
        fragApp = Pengaturann()

        dataListView = ArrayList()
        adapterListView = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataListView)

        listabsen.adapter = adapterListView

        btntgl.setOnClickListener(this)
        btntime.setOnClickListener(this)

        var terimaData: Bundle? = intent.extras
        txtusrnm.setText(terimaData?.getString("usrnm"))


        registerForContextMenu(listabsen)
        listabsen.onItemClickListener =
            OnItemClickListener { parent, view : View?, position, id ->
                val popup = PopupMenu(this, view)
                popup.menuInflater.inflate(R.menu.menu_itemdelete, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when(item.itemId){
                        R.id.itemDelete ->{
                            var alertBuilder = AlertDialog.Builder(this)
                            alertBuilder.setTitle("Informasi")
                                .setMessage("Hapus Data?")
                            alertBuilder.setNeutralButton("OK",null)
                            alertBuilder.show()
                        }
                    }
                    false
                }
                popup.show()
            }

        btnabsen.setOnClickListener {



            val tanggal = btntgl.text
            val waktu = btntime.text
            val nilai ="Absen pada $tanggal jam $waktu"
            dataListView.add(nilai)
            Toast.makeText(this, nilai, Toast.LENGTH_SHORT).show()
            adapterListView.notifyDataSetChanged()

        }

//        api.abs().enqueue(object : Callback<AbsensiItem> {
//            override fun onFailure(call: Call<AbsensiItem>, t: Throwable) {
//                Log.e("MainActivity", t.toString())
//            }
//
//            override fun onResponse(call: Call<AbsensiItem>, response: Response<AbsensiItem>) {
//                if (response.isSuccessful){
//                    val listdata2 = response.body()!!.absensi
//                    nm?.setText(listdata2!![0].nama)
//                    val tanggal = listdata2!!.tanggal
//                    val waktu = btntime.text
//                    val nilai ="Absen pada $tanggal jam $waktu"
//                    dataListView.add(listdata2)
//                }
//            }
//        })


        val kalender : Calendar = Calendar.getInstance()

        tahun = kalender.get(Calendar.YEAR)
        bulan = kalender.get(Calendar.MONTH) + 1
        hari = kalender.get(Calendar.DAY_OF_MONTH)

        jam = kalender.get(Calendar.HOUR)
        menit = kalender.get(Calendar.MINUTE)

        btntgl.text = "$hari / $bulan / $tahun "
        btntime.text = "$jam : $menit"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btntgl-> showDialog(10)
            R.id.btntime -> showDialog(20)
            R.id.btnlogout -> {
                var alertBuilder = AlertDialog.Builder(this)
                alertBuilder.setTitle("Informasi")
                    .setMessage("Anda ingin logout?")
                alertBuilder.setNeutralButton("OK",null)
                alertBuilder.show()
                val logoutt = Intent(this, MainActivity::class.java)
                startActivity(logoutt)
            }
        }
    }

    override fun onCreateDialog(id: Int): Dialog {
        when (id) {
            10 -> return DatePickerDialog(this, ubahTanggalDialog, tahun, bulan - 1, hari)
            20 -> return TimePickerDialog(this, ubahWaktuDialog, jam, menit, true)
        }
        return super.onCreateDialog(id)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        var mnuInflater = getMenuInflater()
        mnuInflater.inflate(R.menu.menu_itemdelete,menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.itemDelete ->{
                var alertBuilder = AlertDialog.Builder(this)
                alertBuilder.setTitle("Informasi")
                .setMessage("Hapus Data?")
                    alertBuilder.setNeutralButton("OK",null)
                alertBuilder.show()
            return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId) {
            R.id.itemTest -> {
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.frameLayout, fragTest).commit()
                frameLayout.visibility = View.VISIBLE
            }
            R.id.itemProfil -> {
                ft = supportFragmentManager.beginTransaction()
                ft.replace(R.id.frameLayout, fragProfil).commit()
                frameLayout.setBackgroundColor(
                    Color.rgb( 255, 255, 225)
                )
                frameLayout.visibility = View.VISIBLE
            }
            R.id.itemHome -> frameLayout.visibility = View.GONE
        }
        return true
        }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
        } else {
            Toast.makeText(applicationContext, "Tekan kembali lagi", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    }


