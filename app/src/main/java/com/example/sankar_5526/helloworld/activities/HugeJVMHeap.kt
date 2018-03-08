package com.example.sankar_5526.helloworld.activities

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.sankar_5526.helloworld.DependencyEvaluator
import com.example.sankar_5526.helloworld.R

/**
 * Created by sankar-5526 on 02/03/18.
 */
class HugeJVMHeap : AppCompatActivity() {
    private lateinit var button1: Button

    private lateinit var button2: Button


    private val CHECK_MEMORY_FREQ_SECONDS = 2.0f
    private val LOW_MEMORY_THRESHOLD_PERCENT = 5.0f

    private lateinit var memoryHandler_: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memoryHandler_ = Handler()

        setContentView (R.layout.activity_main)
        button1 = findViewById (R.id.huge)
        button2 = findViewById(R.id.helloButton)
        button1.visibility = View.GONE
        button2.visibility = View.GONE

        val arrayList = arrayOfNulls<String>(100000000)

    }

    override fun onLowMemory() {
        super.onLowMemory()
        Toast.makeText (this, "LOW memory - Closing app", Toast.LENGTH_SHORT).show()
        finish()
    }

}