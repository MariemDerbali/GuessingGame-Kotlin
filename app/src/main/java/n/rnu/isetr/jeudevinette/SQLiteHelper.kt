package n.rnu.isetr.jeudevinette

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.*

class SQLiteHelper(context: Context) :SQLiteOpenHelper(context, DATABASE_NAME,null , DATABASE_VERSION){

        companion object{
            private const val DATABASE_NAME="joueur.db"
            private const val DATABASE_VERSION=1
            private const val TBL_JOUEUR="tbl_joueur"
            private const val ID="id"
            private const val NOM="nom"
            private const val SCORE="score"
            private const val NIVEAU="niveau"

        }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTbljoueur=("CREATE TABLE "+ TBL_JOUEUR+"("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ NOM+ " TEXT,"+ NIVEAU+ " TEXT,"+ SCORE+ " TEXT"+")")
        db?.execSQL(createTbljoueur)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_JOUEUR")
        onCreate(db)
    }

    fun insertjoueur(joueur: Joueur):Long {
        val db=this.writableDatabase

        val contentValues=ContentValues()
         contentValues.put(NOM,joueur.nom)
        contentValues.put(SCORE,joueur.score)
        contentValues.put(NIVEAU,joueur.niveau)



        val success=db.insert(TBL_JOUEUR,null , contentValues)
        db.close()
        return success
    }

    fun getTop10Joueurs():ArrayList<Joueur>{

        val joueurList: ArrayList<Joueur> = ArrayList()
        val selectQuery="SELECT * FROM $TBL_JOUEUR ORDER BY $SCORE DESC limit 10 "
        val db=this.readableDatabase

        val cursor: Cursor?

        try{
            cursor=db.rawQuery(selectQuery,null)
        }catch(e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

         var nom:String
        var niveau:String
        var score:Int

        if(cursor.moveToFirst()){
            do{
                 nom=cursor.getString(cursor.getColumnIndexOrThrow("nom"))
                score=cursor.getInt(cursor.getColumnIndexOrThrow("score"))
                niveau=cursor.getString(cursor.getColumnIndexOrThrow("niveau"))


                val joueur=Joueur(nom = nom, score = score,niveau=niveau)
                joueurList.add(joueur)
            }while(cursor.moveToNext())
        }
        return joueurList

    }

    fun updateLastScore(FinalScore:Int): Int {
        val db=this.writableDatabase
        val contentValues=ContentValues()

        contentValues.put(SCORE, FinalScore)

       return db.update(
            TBL_JOUEUR, contentValues, ID + " = (SELECT max(id) FROM TBL_JOUEUR)",
            null) ;
        db.close()

     }

    fun getLastLevel():String{
        val selectQuery="SELECT $NIVEAU FROM $TBL_JOUEUR ORDER BY $ID DESC limit 1"
        val db=this.readableDatabase

        val cursor: Cursor?

        try{
            cursor=db.rawQuery(selectQuery,null)
        }catch(e:Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return toString()
        }

        var niveau:String=""

        if(cursor.moveToFirst()){
            do{

                niveau=cursor.getString(cursor.getColumnIndexOrThrow("niveau"))

            }while(cursor.moveToNext())
        }
        return niveau
    }

}
