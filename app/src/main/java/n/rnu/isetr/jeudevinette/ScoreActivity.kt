package n.rnu.isetr.jeudevinette

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView


class ScoreActivity : AppCompatActivity() {
    lateinit var receiver_msg: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        receiver_msg = findViewById(R.id.result_score)
         val intent = intent

        val str = intent.getStringExtra("message_key")
         receiver_msg.text = str


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_all_scores,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.best_players ->
            startActivity(Intent(this, ResultActivity::class.java))
            R.id.quit -> this.finish()
        }
        return true
    }
}