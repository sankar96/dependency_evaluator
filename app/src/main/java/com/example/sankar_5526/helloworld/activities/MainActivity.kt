package com.example.sankar_5526.helloworld.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.sankar_5526.helloworld.R


class MainActivity : AppCompatActivity() {



    private lateinit var button: Button
    private lateinit var textView: TextView
    private lateinit var hugeButton: Button

    private lateinit var hugeButton1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById (R.id.helloButton)
        textView = findViewById (R.id.dummyTextView)
        hugeButton = findViewById (R.id.huge)
        hugeButton1 = findViewById (R.id.huge1)

        button.setOnClickListener(onButtonClickListener)
        hugeButton.setOnClickListener (onHugeButtonClickListener)
        hugeButton1.setOnClickListener(onHugeButton1ClickListener)
        // Example of a call to a native method
        //sample_text.text = stringFromJNI()
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */


    private var onButtonClickListener = View.OnClickListener {
        val intent = Intent (this, DummyActivity::class.java)
        startActivity (intent)
    }

    private var onHugeButtonClickListener = View.OnClickListener {
        val intent = Intent (this, DummyWithoutCycleActivity::class.java)
        startActivity (intent)
    }


    private var onHugeButton1ClickListener = View.OnClickListener {
        val intent = Intent (this, HugeJVMHeap::class.java)
        startActivity (intent)
    }


    external fun stringFromJNI(): String

    companion object {

    }

    private fun makeToast (message: String) {
        Toast.makeText (this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun cutString (string: String): String {
        var ans = ""
        for (i in 0 until 11) {
            ans += string[i]
        }
        return ans
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
