package model

import android.widget.EditText

/**
 * Created by sankar-5526 on 27/02/18.
 */

data class Cell (
        val editText: EditText?,
        val rowNumber: Int,
        val columnNumber: Char,
        var formulaValue: String
)