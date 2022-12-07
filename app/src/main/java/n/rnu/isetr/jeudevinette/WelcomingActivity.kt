package n.rnu.isetr.jeudevinette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.*


class WelcomingActivity : AppCompatActivity() {

    private lateinit var tvNom:TextView
    var radioGroup: RadioGroup? = null
    lateinit var radioButton: RadioButton
    private lateinit var btnAdd:Button
    private lateinit var sqliteHelper:SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcoming)
        radioGroup = findViewById(R.id.radioGroup)


        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        initView()
        sqliteHelper=SQLiteHelper(this)

        btnAdd.setOnClickListener(){
            addjoueur()
        }

    }

    private fun initView(){
        tvNom=findViewById(R.id.tvnom)
        btnAdd=findViewById(R.id.btnAdd)
     }


    private fun addjoueur(){
        val nom=tvNom.text.toString()
        if(nom==""){
            Toast.makeText(this,"Please enter your name",Toast.LENGTH_SHORT).show()
        }else{
            val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
            radioButton = findViewById(intSelectButton)
           val niveau=radioButton.text.toString()

            val joueur=Joueur(nom=nom , niveau=niveau)
            val status = sqliteHelper.insertjoueur(joueur)

            if(status>-1){
                Toast.makeText(this,"   player $nom is added..." , Toast.LENGTH_SHORT).show()
                clearTextView()
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this,"   player has not been added" , Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun clearTextView(){
        tvNom.setText("")
    }
}