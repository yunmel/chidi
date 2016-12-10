package com.chidi.cloud.controller

import com.chidi.cloud.model.User
import com.jfinal.core.Controller

/**
 * Created by xu on 2016/12/9.
 */
class IndexController : Controller() {

    fun index() {
        if (getSessionAttr<User>("user") == null) {
            redirect("/login/page")
        } else {
            render("index.html")
        }
    }

}