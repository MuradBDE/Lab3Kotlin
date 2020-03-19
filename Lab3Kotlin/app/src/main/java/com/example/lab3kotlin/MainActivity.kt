package com.example.lab3kotlin

import android.annotation.TargetApi
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.random.Random


class MainActivity : AppCompatActivity(), View.OnClickListener {
    val ikbo12 = arrayListOf<String>("Алексеев Никита Евгеньевич",
        "Баранников Вадим Сергеевич",
        "Булыгин Андрей Геннадьевич",
        "Геденидзе Давид Темуриевич",
        "Горак Никита Сергеевич",
        "Грачев Александр Альбертович",
        "Гусейнов Илья Алексеевич",
        "Жарикова Екатерина Сергеевна",
        "Журавлев Владимир Евгеньевич",
        "Иванов Алексей Дмитриевич",
        "Карипова Лейсан Вильевна",
        "Копотов Михаил Алексеевич",
        "Копташкина Татьяна Алексеевна",
        "Косогоров Кирилл Станиславович",
        "Кошкин Артем Сергеевич",
        "Логецкая Светлана Александровна",
        "Магомедов Мурад Магамедович",
        "Миночкин Антон Андреевич",
        "Опарин Иван Алексеевич",
        "Паршаков Никита Алексеевич",
        "Самохин Олег Романович",
        "Сахаров Владислав Игоревич",
        "Смирнов Сергей Юрьевич",
        "Трошин Дмитрий Вадимович",
        "Чехуров Денис Александрович",
        "Эльшейх Самья Ахмед",
        "Юров Илья Игоревич",
        "Загребельный Александр Русланович")
    lateinit var btnRead : Button
    lateinit var btnAdd : Button
    lateinit var btnReplace : Button
    lateinit var myHelper : DBHelper
    lateinit var myText: EditText
    lateinit var edText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // initialize button 1
        btnRead = findViewById(R.id.btnRead)
        btnRead.setOnClickListener(this)
        // initialize button 2
        btnAdd = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener(this)
        // initialize button 3
        btnReplace = findViewById(R.id.btnReplace)
        btnReplace.setOnClickListener(this)

        myText = findViewById(R.id.txtInsert)
        edText = findViewById(R.id.test)

        myHelper = DBHelper(this)
        initSQL()
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btnRead -> {
                var db = myHelper.readableDatabase
                val cursor = db.rawQuery("SELECT * FROM Students", null)
                val intent = Intent(this, StudentReader::class.java)
                startActivity(intent)
                /*cursor.moveToPosition(2)
                var string = cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2)
                edText.text = string*/

            }
            R.id.btnAdd -> {
                val fullName = myText.text.toString()
                addToSQL(fullName)
                myText.text.clear()
            }
            R.id.btnReplace -> {
                var db = myHelper.readableDatabase
                db.execSQL("DELETE FROM Students WHERE id = (SELECT MAX(id) FROM Students)")
                addToSQL("Иванов Иван Иванович")
            }
        }
    }

    class DBHelper(context: Context) : SQLiteOpenHelper(context, "myDB", null, 1)
    {
        override fun onCreate(db: SQLiteDatabase?) {
            db!!.execSQL("CREATE TABLE Students(id integer, fullName text, addTime text)")

        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("DROP TABLE IF EXISTS Students")
            onCreate(db)
        }

        fun clear()
        {
            var db = this.writableDatabase
            db.execSQL("DELETE FROM Students")
        }
    }

    fun initSQL()
    {
        myHelper.clear()
        for (i in 0 .. 5)
        {
            addToSQL(ikbo12.get(Random.nextInt(0, ikbo12.size)))
        }
    }


    @TargetApi(Build.VERSION_CODES.O)
    fun addToSQL(fullName: String)
    {
        var dbWriter = myHelper.writableDatabase
        var dbReader = myHelper.readableDatabase
        val cursor = dbReader.rawQuery("SELECT * FROM Students", null)
        val values = ContentValues().apply {
            put("id", cursor.count)
            put("fullName", fullName)
            put("addTime", LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)).removeSuffix(" AM").removeSuffix(" PM").toString())
        }
        dbWriter?.insert("Students", null, values)
    }

}

