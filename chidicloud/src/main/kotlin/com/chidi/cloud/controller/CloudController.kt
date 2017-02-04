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

    fun cas(){
        render("cas.html")
    }

    fun cis(){
        render("cis.html")
    }

    fun eSight(){
        render("eSight.html")
    }

    fun fireHunter(){
        render("fireHunter.html")
    }

    fun firewall(){
        render("firewall.html")
    }

    fun fusionInsight(){
        render("fusionInsight.html")
    }

    fun hcloud(){
        render("hcloud.html")
    }

    fun monitor(){
        render("monitor.html")
    }

    fun onestore(){
        render("onestore.html")
    }

    fun sdnvcfc(){
        render("sdnvcfc.html")
    }
}