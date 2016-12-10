package com.chidi.cloud.controller

import com.jfinal.core.Controller

/**
 * Created by xu on 2016/12/10.
 */
class CloudController : Controller(){

    fun paas(){
        render("paas.html")
    }

    fun iaas(){
        render("iaas.html")
    }

}