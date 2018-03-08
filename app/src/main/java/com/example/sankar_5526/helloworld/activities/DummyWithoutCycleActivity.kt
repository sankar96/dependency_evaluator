package com.example.sankar_5526.helloworld.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import com.example.sankar_5526.helloworld.DependencyEvaluator
import com.example.sankar_5526.helloworld.R
import com.example.sankar_5526.helloworld.UpdatedValues
import model.Cell


/**
 * Created by sankar-5526 on 27/02/18.
 *
 */


class DummyWithoutCycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cppApi = DependencyEvaluator.create()
        setContentView (R.layout.dummy_layout)
        rowSelector1 = findViewById (R.id.rowSelector1)
        tableRow = findViewById (R.id.tableRow1)
        editTextA1 = findViewById (R.id.editTextA1)
        formulaEditTextWatcher = findViewById (R.id.formulaEditText)
        formulaEditTextWatcher.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                currentCell?.editText!!.text = s
            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
            }
        })

        formulaEditTextWatcher.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val text = formulaEditTextWatcher.text.toString()
                if (text[0] == '=') {
                    val parsedTokens = cppApi.getParsedString(text)

                    for (i in parsedTokens)
                        Log.d ("sheetValuesParsed", i)

                    cppApi.evaluateExpression (text, parsedTokens, currentCell!!.rowNumber, getCurrentColumn (currentCell!!.columnNumber), currentCell!!.columnNumber + currentCell!!.rowNumber.toString())
                    val values = cppApi.returnUpdatedValues(text, currentCell!!.rowNumber, getCurrentColumn (currentCell!!.columnNumber), currentCell!!.columnNumber + currentCell!!.rowNumber.toString(), 2)
                    updateValues(values)
                }
                else {
                    cppApi.storeCellValueConstant(formulaEditTextWatcher.text.toString(), currentCell!!.rowNumber, getCurrentColumn(currentCell!!.columnNumber))
                }
            }
            true
        })
        initToolbar()
        initEditTextNames(this)
        setUpEditTextListeners()
    }

    private fun getCurrentColumn(column: Char) : Int{
        return when (column) {
            'A', 'a' -> 0;
            'B', 'b' -> 1;
            'C', 'c'-> 2;
            'D', 'd' -> 3
            else -> 3;
        }
    }
    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.back_arrow)
        toolbar.title = "ZS"
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setBackgroundColor (resources.getColor(R.color.colorPrimary))
        toolbar.setNavigationOnClickListener({
            Log.d ("bool", isClosed.toString())
            if (isClosed) {
                onBackPressed()
            }
            else {
                closeEditText()
            }
        })
    }

    private fun closeEditText() {
        var text = formulaEditTextWatcher.text.toString()
        var bool = false
        if (text.isEmpty()) {
            text = "0"
            bool = true
        }
        if (text[0] == '=') {
            val parsedTokens = cppApi.getParsedString(text)

            for (i in parsedTokens)
                Log.d ("sheetValuesParsed", i)

            cppApi.evaluateExpression (text, parsedTokens, currentCell!!.rowNumber, getCurrentColumn (currentCell!!.columnNumber), currentCell!!.columnNumber + currentCell!!.rowNumber.toString())
            values = cppApi.returnUpdatedValues(text, currentCell!!.rowNumber, getCurrentColumn (currentCell!!.columnNumber), currentCell!!.columnNumber + currentCell!!.rowNumber.toString(), 2)
            updateValues(values)
        }
        else {
            cppApi.storeCellValueConstant(formulaEditTextWatcher.text.toString(), currentCell!!.rowNumber, getCurrentColumn(currentCell!!.columnNumber))

            for (i in cells) {
                if (i.rowNumber == currentCell!!.rowNumber && (i.columnNumber - 'A') == getCurrentColumn(currentCell!!.columnNumber)) {
                    if (!bool) {
                        i.editText!!.setText(text, TextView.BufferType.EDITABLE)
                        i.formulaValue = text
                    }
                    else {
                        i.editText!!.setText ("")
                        i.formulaValue = ""
                    }
                }
            }
        }
        formulaEditTextWatcher.visibility = View.GONE
        hidekeyBoard()
        isClosed = true
        initToolbar()
    }

    private fun hidekeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(formulaEditTextWatcher.windowToken, 0)
    }
    private var editTextClickListener = View.OnClickListener {
        formulaEditTextWatcher.visibility = View.VISIBLE
        formulaEditTextWatcher.requestFocus()
        toolbar.setBackgroundColor (Color.WHITE)
        toolbar.setNavigationIcon (R.drawable.ic_done_black_24dp)

    }

    private fun getResourceIds (activity: Activity, string: String, row: Int, column: Char) {
        try {
            val res = R.id::class.java
            val field = res.getField(string)
            val drawableId = field.getInt(null)
            editTextResIds.add (drawableId)
            val editText = activity.findViewById<EditText> (drawableId)
            editText.isFocusable = false
            cells.add (
                    Cell (editText, row, column, "")
            )
        } catch (e: Exception) {
            Log.d ("values", "exception")
        }

    }

    private fun initEditTextNames (activity: Activity) {
        for (i in 1 until 21) {
            for (j in 1 until 5) {
                val c = 'A' + j - 1
                val value = "editText" + c + i.toString()
                editTextNames.add (value)
                getResourceIds (activity, value, i, c)
            }
        }
    }

    private fun setUpEditTextListeners() {
        var c = 0
        for (i in cells) {
            val editText = i.editText
            editText!!.setOnClickListener ( View.OnClickListener {
                if (currentCell != null)
                    currentCell!!.editText!!.background = getDrawable (R.drawable.cell_shape)
                currentCell = i

                editText.background = getDrawable(R.drawable.cell_selector)
                formulaEditTextWatcher.visibility = View.VISIBLE
                formulaEditTextWatcher.requestFocus()
                editText.clearFocus()
                toolbar.setBackgroundColor (Color.WHITE)
                toolbar.setNavigationIcon (R.drawable.ic_done_black_24dp)
                formulaEditTextWatcher.setText(i.formulaValue, TextView.BufferType.EDITABLE)
                formulaEditTextWatcher.setSelection (editText.text.toString().length)
                isClosed = false
            })
            c++
        }

    }

    private fun updateValues(values: ArrayList <UpdatedValues>) {
        //Update the UI here

        val coloringList = ArrayList <EditText>()
        for (i in values) {
            if (i.actualValue.equals("#CIRCULARREF")) {
                val simpleAlert = AlertDialog.Builder(this@DummyWithoutCycleActivity).create()
                simpleAlert.setTitle("CYCLE in the graph")
                simpleAlert.setMessage("There is a cyclic reference in the graph. Please enter a valid input")

                simpleAlert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", {
                    dialogInterface, i ->
                    //Toast.makeText(applicationContext, "You clicked on OK", Toast.LENGTH_SHORT).show()
                })
                simpleAlert.show()
                break
            }
            Log.d ("updatedValuesKotlin", i.actualValue + "  " + i.formulaValue + "  " + i.row.toString() + " " + i.column.toString())

            for (j in cells) {
                if (j.rowNumber == i.row && (j.columnNumber - 'A').toString() == i.column.toString()) {
                    j.editText!!.setText(i.actualValue, TextView.BufferType.EDITABLE)
                    //j.editText.setBackgroundColor(resources.getColor(R.color.grey))
                    coloringList.add (j.editText)
                    j.formulaValue = i.formulaValue
                }
            }
        }

        paintColor(coloringList)

    }

    private fun paintColor(coloringList: ArrayList <EditText>) {

        for (i in coloringList) {
            i.setBackgroundColor (resources.getColor(R.color.colorTransition))
        }
        mHandler.removeCallbacksAndMessages(null)
        mHandler.postDelayed({
            for (j in coloringList) {
                //j.setBackgroundColor (Color.WHITE)
                j.setBackgroundResource(R.drawable.cell_shape)
            }
        }, 500)
    }

    companion object {
        init {
            System.loadLibrary ("dependency_evaluator")
        }
    }

    private lateinit var formulaEditTextWatcher: EditText
    private lateinit var rowSelector1: TextView
    private lateinit var editTextA1: EditText
    private lateinit var tableRow: TableRow
    private lateinit var toolbar: Toolbar
    private var editTextResIds = ArrayList <Int>()
    private var editTextNames = ArrayList <String>()
    private var cells  =  ArrayList <Cell> ()
    private var currentCell : Cell ?= null
    private var isClosed = true
    private lateinit var cppApi: DependencyEvaluator
    private var values = ArrayList<UpdatedValues> ()

    private var mHandler = Handler()

}