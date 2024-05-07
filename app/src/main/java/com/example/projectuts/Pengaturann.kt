package com.example.projectuts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.zxing.integration.android.IntentIntegrator

class Pengaturann : Fragment(){
    lateinit var thisParent : Dashboard
    lateinit var v :View
    lateinit var ft : FragmentTransaction
    lateinit var fragApp : FragmentProfil

    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        thisParent = activity as Dashboard
        fragApp = FragmentProfil()
        v = inflater.inflate(R.layout.activity_pengaturan,container,false)
        val bt = v.findViewById<ImageButton>(R.id.btnbackapp2)
        val btu = v.findViewById<Button>(R.id.bturl)
        val btq = v.findViewById<Button>(R.id.qrcode)

        bt.setOnClickListener {
            val fragManager = requireActivity().supportFragmentManager
            ft = fragManager.beginTransaction()
            ft.replace(R.id.frameLayout, fragApp).commit()
        }

        btu.setOnClickListener {
            var url = "https://toeic-testpro.com"
            var intentWebsite = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intentWebsite)
        }

        btq.setOnClickListener {
            val integrator = IntentIntegrator(thisParent).apply {
                setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                setPrompt("Scan a QR Code")
                setOrientationLocked(false)
                setBeepEnabled(true)
                setBarcodeImageEnabled(true)
            }
            integrator.initiateScan()
        }



        return v
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        val kd = v.findViewById<TextView>(R.id.kodeqr)

        if(intentResult != null){
            if(intentResult.contents == null){
                Toast.makeText(thisParent,"Dibatalkan", Toast.LENGTH_SHORT).show()
            }else{
                val data = intentResult.contents
                kd.text = "GET : "+ data
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

}