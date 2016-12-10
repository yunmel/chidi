package com.chidi.cloud.controller

import com.chidi.cloud.model.User
import com.jfinal.core.Controller

/**
 * Created by xu on 2016/12/10.
 */
class UserController : Controller(){

    fun index(){
        val users = User.me.find("select * from cloud_user");
        setAttr("users",users)
        render("user.html")
    }

    fun add(){
        render("add.html")
    }
}