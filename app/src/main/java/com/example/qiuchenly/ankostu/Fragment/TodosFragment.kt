package com.example.qiuchenly.ankostu.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView
import com.easy.kotlin.mytodoapplication.TodoAdapter
import com.example.qiuchenly.ankostu.Bean.Todo
import com.example.qiuchenly.ankostu.MainActivity
import com.example.qiuchenly.ankostu.R
import io.realm.Realm

class TodosFragment : Fragment(), TodoAdapter.TodoItemClickListener {
    override fun onClick(caller: View, todo: Todo) {
        (activity as MainActivity).hideFab()
        val frame = TodoEditFragment.newInstance(todo.id)
        activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_main, frame, frame.javaClass.simpleName)
                .addToBackStack(frame.javaClass.simpleName)
                .commit()
    }

    @BindView(R.id.realmRV)
    lateinit var myRv: RealmRecyclerView

    lateinit var realm: Realm

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_todos, container, false)
        ButterKnife.bind(this, v)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        realm = Realm.getDefaultInstance()
    }

    override fun onResume() {
        super.onResume()
        val data = realm.where(Todo::class.java).findAll()
        val ada = TodoAdapter(activity as MainActivity, data, true, true, this)
        myRv.setAdapter(ada)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    companion object {
        fun newInstance(): TodosFragment {
            return TodosFragment()
        }
    }
}