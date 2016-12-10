package com.chidi.cloud.controller

import com.chidi.cloud.model.User
import com.jfinal.core.Controller

/**
 * Created by xu on 2016/12/9.
 */
class LoginController : Controller() {

    fun page() {
        render("login.html")
    }

    fun submit() {
        setSessionAttr("user", User())
        redirect("/")
    }

    fun out() {
        removeSessionAttr("user")
        redirect("/")
    }
}