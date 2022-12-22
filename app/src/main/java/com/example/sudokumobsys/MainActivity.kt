package com.example.sudokumobsys

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playButton = findViewById<Button>(R.id.homeButton)
        playButton.setOnClickListener {
            val Intent = Intent(this, Hauptmenu::class.java)
            startActivity(Intent)
        }


    }
    }