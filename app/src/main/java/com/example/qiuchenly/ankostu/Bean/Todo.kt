package com.example.qiuchenly.ankostu.Bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Todo : RealmObject() {
    @PrimaryKey
    open var id = "-1"
    open var name = ""
    open var content = ""
}