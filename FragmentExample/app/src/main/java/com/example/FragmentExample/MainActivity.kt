package com.example.FragmentExample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fragment.FirstFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FirstFragment.FragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = null

        // add fragment dynamically
        supportFragmentManager.beginTransaction().add(R.id.fragmentHolder, FirstFragment()).commit()

        btn_changeFragment.setOnClickListener {
            // TODO
        }

    }

    /*
    * return value for first fragment textView
    * */
    override fun getText(): String {

        return getString(R.string.dynamically_fragment)
    }


}
