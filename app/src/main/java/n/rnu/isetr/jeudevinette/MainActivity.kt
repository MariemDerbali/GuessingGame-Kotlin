package n.rnu.isetr.jeudevinette

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import android.content.Intent
import android.widget.*


class MainActivity : AppCompatActivity() {
    var randomNumber = Random().nextInt(10)
    var m: Int=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var npBtn: Button= findViewById(R.id.btn_np)

        var guessBtn: Button =findViewById(R.id.btn_valider)

        npBtn.setOnClickListener{
            refresh()
        }

        guessBtn.setOnClickListener{
            guessing()
        }

    }

    private fun refresh(){
        val intent = intent
        finish()
        startActivity(intent)
    }

    private fun guessing(){
        var result: TextView = findViewById(R.id.res)
        var history: TextView=findViewById(R.id.history)
        var nbr:EditText=findViewById(R.id.nbr)

        var his= history.getText().toString();
            his=his + "\n" + nbr.text.toString();
if(nbr.text.toString().length==0){
    Toast.makeText(this.applicationContext,"Champ vide!",Toast.LENGTH_SHORT).show()
}else{

    if(randomNumber.toString()==nbr.text.toString()){
        result.setText("")

        history.text=his+"\n"+"Vous devinez correctement en $m essais!!"

    }else if(randomNumber.toString()>nbr.text.toString()){
        result.text="Votre chiffre est trop petit!!"
        history.setText(his);
        m++
    }else{
        result.text="Votre chiffre est trop grand!!"
        history.setText(his);
        m++
    }
}
        }
    }
