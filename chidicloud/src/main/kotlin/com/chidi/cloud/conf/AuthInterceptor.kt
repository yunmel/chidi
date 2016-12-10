package com.chidi.cloud.conf

import com.chidi.cloud.model.User
import com.jfinal.aop.Interceptor
import com.jfinal.aop.Invocation
import com.jfinal.core.Controller

class AuthInterceptor : Interceptor {

    override fun intercept(inv: Invocation) {
        val controller = inv.controller
        val user = controller.getSessionAttr<User>("user")
//        println(controller.request.requestURL)
        val url = controller.request.requestURI
        if (user != null || url.startsWith("/login"))
            inv.invoke()
        else
            controller.redirect("/login/page")
    }
}