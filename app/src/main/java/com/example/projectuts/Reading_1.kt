package com.example.projectuts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Reading_1 : Fragment(){
    lateinit var thisParent : Dashboard
    lateinit var v :View
    lateinit var ft : FragmentTransaction
    lateinit var fragApp : FragmentTest
    var txtQuestion: TextView?=null
    var txtResult: TextView?=null
    var txtNum: TextView?=null
    var radioGroup: RadioGroup?=null
    var btnNext:Button?=null
    var rbOption1: RadioButton?=null
    var rbOption2: RadioButton?=null
    var rbOption3: RadioButton?=null
    var rbOption4: RadioButton?=null
    private var inputText: Int? = null
    private var questionsList:QuestionsListResponse?=null

    var nil = Dashboard.result
    var til = Dashboard.total
    override fun onCreateView(inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        thisParent = activity as Dashboard
        fragApp = FragmentTest()
        inputText = arguments?.getInt("note")
        v = inflater.inflate(R.layout.reading_1,container,false)
        val bt = v.findViewById<ImageButton>(R.id.btnbackapp4)
        txtQuestion=v.findViewById<TextView>(R.id.quest)
        txtResult=v.findViewById<TextView>(R.id.rest)
        txtNum=v.findViewById<TextView>(R.id.num)
        radioGroup=v.findViewById<RadioGroup>(R.id.radioGroup)
        btnNext=v.findViewById<Button>(R.id.btnfinish)
        rbOption1=v.findViewById<RadioButton>(R.id.rb1)
        rbOption2=v.findViewById<RadioButton>(R.id.rb2)
        rbOption3=v.findViewById<RadioButton>(R.id.rb3)
        rbOption4=v.findViewById<RadioButton>(R.id.rb4)

        bt.setOnClickListener {
            val fragManager = requireActivity().supportFragmentManager
            ft = fragManager.beginTransaction()
            ft.replace(R.id.frameLayout, fragApp).commit()
        }

        var i=1
        btnNext?.setOnClickListener(View.OnClickListener {
            val selectedOption=radioGroup?.checkedRadioButtonId
            if (selectedOption!=-1) {
                val radbutton = v.findViewById<View>(selectedOption!!) as RadioButton
                questionsList.let {

                    if (i < it?.size!!) {
                        til = it.size
                        if (radbutton.text.toString().equals(it[i-1].correct_option)) {
                            nil++
                            txtResult?.text = "Nilai : $nil"
                        }
                        txtQuestion?.text = questionsList!![i].question
                        txtNum?.text = "No. ${i + 1}"
                        rbOption1?.text = it[i].option1
                        rbOption2?.text = it[i].option2
                        rbOption3?.text = it[i].option3
                        rbOption4?.text = it[i].option4
                        if (i == it?.size!!.minus(1)) {
                            btnNext?.text = "Finish"
                        }
                        radioGroup?.clearCheck()
                        i++
                    } else {
                        if (radbutton.text.toString().equals(it[i-1].correct_option)) {
                            nil++
                            txtResult?.text = "Nilai : $nil"
                        }

                        val intent = Intent(thisParent, ResultActivity::class.java)
                        intent.putExtra("tutal", til!!)
                        intent.putExtra("nunal", nil!!)
                        startActivity(intent)
                    }
                }
            }else {
                Toast.makeText(thisParent,"kosong",Toast.LENGTH_SHORT).show()
            }
        })

        val quesApi=RetrofitHelper.getInstance().create(QuestionsAPI::class.java)
        GlobalScope.launch {
            val result=quesApi.getSoal(inputText!!)
                questionsList =result.body()!!
                GlobalScope.launch(Dispatchers.Main) {
                    txtQuestion?.text= questionsList!![0].question
                    rbOption1?.text= questionsList!![0].option1
                    rbOption2?.text= questionsList!![0].option2
                    rbOption3?.text= questionsList!![0].option3
                    rbOption4?.text= questionsList!![0].option4

            }
        }

        return v
    }
}