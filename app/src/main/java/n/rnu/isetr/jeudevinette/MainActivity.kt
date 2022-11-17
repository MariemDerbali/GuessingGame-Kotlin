package n.rnu.isetr.jeudevinette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import java.util.*
import android.widget.*


class MainActivity : AppCompatActivity() {
    private lateinit var sqliteHelper:SQLiteHelper

    var randomNumber = Random().nextInt(10)
    var m: Int=1
    var BestScore: Int=100

    private var countdown_timer: CountDownTimer? = null
    private var time_in_milliseconds = 60000L
    private var pauseOffSet = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sqliteHelper=SQLiteHelper(this)


        var npBtn: Button= findViewById(R.id.btn_np)

        var guessBtn: Button =findViewById(R.id.btn_valider)

        var countdownText: TextView =findViewById(R.id.counter)

        countdownText.text= "${(time_in_milliseconds/1000).toString()}"

        starTimer(pauseOffSet)

        npBtn.setOnClickListener{
            refresh()
        }

        guessBtn.setOnClickListener{
            guessing()
        }

    }




    private fun starTimer(pauseOffSetL : Long){
        var countdownText: TextView =findViewById(R.id.counter)

        countdown_timer = object : CountDownTimer(time_in_milliseconds - pauseOffSetL, 1000){
            override fun onTick(millisUntilFinished: Long) {
                pauseOffSet = time_in_milliseconds - millisUntilFinished
                countdownText.text= (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Game Over. time is finished...try next time.", Toast.LENGTH_LONG).show()
                finish()
            }
        }.start()
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
       // var scoreTV:TextView=findViewById(R.id.score)

        var countdownText: TextView =findViewById(R.id.counter)

        var his= history.getText().toString();
            his=his + "\n" + nbr.text.toString();

        if(nbr.text.toString().length==0){
            Toast.makeText(this.applicationContext,"Empty field!",Toast.LENGTH_SHORT).show()
        }else{
            if(randomNumber.toString()==nbr.text.toString()){

                if (countdown_timer!= null){
                    countdown_timer!!.cancel()
                }
                //score=100-(nbre d'essai + nbre de secondes passées)
                val FinalScore=BestScore-(m+ (60-Integer.parseInt(countdownText.text.toString())))
                Toast.makeText(applicationContext,"Score:${(FinalScore).toString()}",Toast.LENGTH_SHORT).show()


                val status = sqliteHelper.updateLastScore(FinalScore)
                if(status>-1){
                    Toast.makeText(this,"   score is updated..." , Toast.LENGTH_SHORT).show()

                    intent = Intent(this, ResultActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"   score has not been updated" , Toast.LENGTH_SHORT).show()

                }

                result.setText("")
                history.text=his+"\n"+"You guessed correctly in $m tries!!"



            }else if(randomNumber.toString()>nbr.text.toString()){
                result.text="Your number is too small!!"
                history.setText(his);
                m++

            }else{
                result.text="Your number is too big!!"
                history.setText(his);
                m++

            }
        }
        }
    }
