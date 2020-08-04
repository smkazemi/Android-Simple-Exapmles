package ir.smr.ActivityForResultSample

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        setResult(Activity.RESULT_OK, Intent().putExtra("ex_key", "data from SecondActivity"))


    }
}
