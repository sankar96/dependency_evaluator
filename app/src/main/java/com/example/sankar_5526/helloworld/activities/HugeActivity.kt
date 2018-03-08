package com.example.sankar_5526.helloworld.activities

import android.content.ComponentCallbacks2
import android.os.Bundle
import android.os.Debug
import android.os.Handler
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.sankar_5526.helloworld.DependencyEvaluator
import com.example.sankar_5526.helloworld.R

/**
 * Created by sankar-5526 on 02/03/18.
 */
class HugeActivity : AppCompatActivity(), ComponentCallbacks2 {

    private lateinit var button1: Button

    private lateinit var button2: Button


    private val CHECK_MEMORY_FREQ_SECONDS = 300L
    private val LOW_MEMORY_THRESHOLD_PERCENT = 5.0f

    private lateinit var cppApi: DependencyEvaluator
    private lateinit var memoryHandler_: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        memoryHandler_ = Handler()

        setContentView (R.layout.activity_main)
        cppApi = DependencyEvaluator.create()
        button1 = findViewById (R.id.huge)
        button2 = findViewById(R.id.helloButton)
        button1.visibility = View.GONE
        button2.visibility = View.GONE
        checkAppMemory()
        cppApi.createHugeMemory()

    }

    override fun onLowMemory() {
        super.onLowMemory()
        Toast.makeText (this, "LOW memory - Closing app", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun checkAppMemory() {

        Log.d ("lowMemory", Debug.getNativeHeapSize().toString() + "  " + Debug.getNativeHeapFreeSize() + "  " +  Debug.getNativeHeapAllocatedSize().toString())

        if (Debug.getNativeHeapSize() < Debug.getNativeHeapAllocatedSize()) {
            Toast.makeText (this, "LOW MEM", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Repeat after a delay
        memoryHandler_.postDelayed(Runnable { checkAppMemory() }, (CHECK_MEMORY_FREQ_SECONDS))
    }

    fun handleLowMemory() {

        Log.d ("lowMemory", "af,hs")
        // Free Memory Here
        Toast.makeText (this, "LOW memory - Closing app", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onTrimMemory(level: Int) {

        // Determine which lifecycle or system event was raised.
        when (level) {

            ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN -> {
                Log.d ("lowMemorycase1", "")
            }

            ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE, ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW, ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL -> {
                Log.d ("lowMemorycase2", "")

            }

            ComponentCallbacks2.TRIM_MEMORY_BACKGROUND, ComponentCallbacks2.TRIM_MEMORY_MODERATE, ComponentCallbacks2.TRIM_MEMORY_COMPLETE -> {
                Log.d ("lowMemorycase3", "")

            }

            else -> {
                Log.d ("lowMemorycase4", "")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    companion object {
        init {
            System.loadLibrary ("dependency_evaluator")
        }
    }
}