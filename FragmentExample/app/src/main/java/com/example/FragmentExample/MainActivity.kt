package com.example.FragmentExample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import fragment.FirstFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FirstFragment.FragmentListener {

    private lateinit var fragment: FirstFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = null

        // add fragment dynamically
        fragment = FirstFragment()
        addFragment(savedInstanceState)


        // change text of fragment text View from MainActivity
        btn_changeFragmentText.setOnClickListener {
            fragment.changeTxt(getString(R.string.change_fragment_text))
        }

    }

    private fun addFragment(savedInstanceState: Bundle?) {

        intent.putExtra(getString(R.string.txtKey), getString(R.string.txt_value))
        fragment.arguments = intent.extras

        val fr = supportFragmentManager.beginTransaction()

        if (savedInstanceState != null) {

            fr.apply {
                replace(R.id.fragmentHolder, fragment)
                commit()
            }


        } else {

            fr.add(R.id.fragmentHolder, fragment).commit()

        }

    }

    /*
    * get state of first fragment
    * */
    override fun onFragmentStateChange(state: String) {

        Log.d("TAG", "Fragment is in : $state state")

    }

    override fun onSaveInstanceState(outState: Bundle) {

//        supportFragmentManager.beginTransaction()
//            .remove(fragment).commit()

        super.onSaveInstanceState(outState)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragmentHolder, fragment).commit()

    }

}
