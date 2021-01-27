package ir.smr.kazemi.flickrapiclient.extension

import android.app.Activity
import android.graphics.Paint
import android.util.Log
import android.widget.TextView
import android.widget.Toast

/**
 * By the grace of Allah, Created by Sayed-MohammadReza Kazemi
 * on 12/8/20.
 */


fun String.toLog(mes: String?) {

    if (mes != null) {
        Log.d("TAG", "$mes -> $this")
    } else {
        Log.d("TAG", this)
    }
}

fun String.toLog() {
    this.toLog(null)
}


fun Activity.longToast(mes: String) {
    Toast.makeText(this, mes, Toast.LENGTH_LONG).show()
}

fun Activity.shortToast(mes: String) {
    Toast.makeText(this, mes, Toast.LENGTH_SHORT).show()

}
