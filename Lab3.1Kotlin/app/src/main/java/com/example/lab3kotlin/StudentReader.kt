package com.example.lab3kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StudentReader : AppCompatActivity() {

    lateinit var recycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_reader)

        recycleView = findViewById(R.id.recyclerView)

        var layoutManager = LinearLayoutManager(this)
        recycleView.layoutManager = layoutManager;
        recycleView.setHasFixedSize(true)

        val myHelper = MainActivity.DBHelper(this)
        var db = myHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Students", null)
        recycleView.adapter = MyAdapter(cursor, this)
    }
}
