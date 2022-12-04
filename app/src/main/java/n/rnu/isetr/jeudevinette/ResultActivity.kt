package n.rnu.isetr.jeudevinette

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ResultActivity : AppCompatActivity() {
    private lateinit var sqliteHelper:SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }


        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class Joueur
        var data: ArrayList<Joueur>

        sqliteHelper=SQLiteHelper(this)

        data=sqliteHelper.getTop10Joueurs()

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter



    }



}
