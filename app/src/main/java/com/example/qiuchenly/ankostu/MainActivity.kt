package com.example.qiuchenly.ankostu

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.qiuchenly.ankostu.Fragment.TodoEditFragment
import com.example.qiuchenly.ankostu.Fragment.TodosFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            val todoFragment = TodoEditFragment()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.content_main, todoFragment, todoFragment.javaClass.simpleName)
                    .addToBackStack(todoFragment.javaClass.simpleName)
                    .commit()
            hideFab()
        }

        val todos = TodosFragment.newInstance()

        supportFragmentManager.beginTransaction()
                .replace(R.id.content_main, todos, todos.javaClass.simpleName)
                .commit()
        supportFragmentManager.addOnBackStackChangedListener {
            val count = supportFragmentManager.backStackEntryCount
            if (count == 0) showFab()
        }
    }

    fun hideFab() {
        fab?.visibility = View.GONE
    }

    fun showFab() {
        fab?.visibility = View.VISIBLE
    }

}
