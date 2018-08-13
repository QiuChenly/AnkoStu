package com.example.qiuchenly.ankostu.Fragment

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.qiuchenly.ankostu.Bean.Todo
import com.example.qiuchenly.ankostu.R
import io.realm.Realm
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.find
import java.util.*

class TodoEditFragment : Fragment() {
    val realm = Realm.getDefaultInstance()
    var todo: Todo? = null

    companion object {
        val TODO_ID_KEY = "todo_id_key"

        fun newInstance(id: String): TodoEditFragment {
            val args = Bundle()
            args.putString(TODO_ID_KEY, id)
            return newInstance()
                    .apply {
                        arguments = args
                    }
        }

        fun newInstance() = TodoEditFragment()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return UI {
            verticalLayout {
                padding = dip(30)
                val title = editText {
                    id = R.id.todo_title
                    hintResource = R.string.todo_title_hint
                }

                val content = editText {
                    id = R.id.todo_content
                    hintResource = R.string.todo_content_hint
                    height = 400
                }
                button {
                    id = R.id.todo_add
                    textResource = R.string.todo_add
                    textColor = Color.WHITE
                    backgroundColor = Color.DKGRAY
                    onClick { _ -> createTodoForm(title.text.toString(), content.text.toString()) }
                }
            }
        }.view
    }

    private fun createTodoForm(title: String, content: String) {
        realm.beginTransaction()
        val t = todo ?: realm.createObject(Todo::class.java, UUID.randomUUID().toString())
        t.name = title
        t.content = content
        realm.commitTransaction()
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null && arguments!!.containsKey(TODO_ID_KEY)) {
            todo = realm.where(Todo::class.java).equalTo("id", arguments!!.getString(TODO_ID_KEY)).findFirst()
            val title = find<TextView>(R.id.todo_title)
            val content = find<TextView>(R.id.todo_content)
            val add = find<Button>(R.id.todo_add)
            title.text = todo!!.name
            content.text = todo!!.content
            add.text = "保存"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}