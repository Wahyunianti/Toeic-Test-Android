package com.example.projectuts

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.frag_data_profil.*
import kotlinx.android.synthetic.main.frag_data_profil.view.*

class FragmentProfil  : Fragment(), View.OnClickListener{
    lateinit var thisParent : Dashboard
    lateinit var v :View
    lateinit var builder : android.app.AlertDialog.Builder
    lateinit var ft : FragmentTransaction
    lateinit var fragApp : Pengaturann
    lateinit var fragApp2 : EditProfil


    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        thisParent = activity as Dashboard
        fragApp = Pengaturann()
        fragApp2 = EditProfil()
        v = inflater.inflate(R.layout.frag_data_profil,container,false)
        builder = android.app.AlertDialog.Builder(thisParent)

        val bt = v.findViewById<Button>(R.id.btnaplikasi)
        val bt2 = v.findViewById<Button>(R.id.btnprofil)

        bt.setOnClickListener {
            val fragManager = requireActivity().supportFragmentManager
            ft = fragManager.beginTransaction()
            ft.replace(R.id.frameLayout, fragApp).addToBackStack(tag).commit()
        }

        bt2.setOnClickListener {
            val fragManager = requireActivity().supportFragmentManager
            ft = fragManager.beginTransaction()
            ft.replace(R.id.frameLayout, fragApp2).addToBackStack(tag).commit()
        }

        v.btnlogout.setOnClickListener(this)
        return v
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnlogout -> {
                val alertBuilder = AlertDialog.Builder(requireContext())
                alertBuilder.setTitle("Informasi")
                    .setMessage("Anda ingin logout?")
                alertBuilder.setPositiveButton("OK") { dialog, whichButton ->
                    val logoutt = Intent(requireContext(), MainActivity::class.java)
                    logoutt.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(logoutt)
                }

                alertBuilder.setNegativeButton("Cancel") { dialog, whichButton ->
                }
                val b = alertBuilder.create()
                b.show()
            }
        }
    }


}

