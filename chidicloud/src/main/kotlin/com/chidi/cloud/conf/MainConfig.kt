package com.chidi.cloud.conf

import com.jfinal.config.*
import com.jfinal.kit.PropKit
import org.beetl.ext.jfinal.BeetlRenderFactory
import com.jfinal.plugin.druid.DruidPlugin
import com.alibaba.druid.filter.stat.StatFilter
import com.alibaba.druid.wall.WallFilter
import com.chidi.cloud.controller.CloudController
import com.chidi.cloud.controller.IndexController
import com.chidi.cloud.controller.LoginController
import com.chidi.cloud.controller.UserController
import com.chidi.cloud.model.User
import com.jfinal.plugin.activerecord.ActiveRecordPlugin
import com.jfinal.plugin.activerecord.SqlReporter
import com.jfinal.plugin.activerecord.dialect.MysqlDialect

/**
 * Created by xu on 2016/12/9.
 */
class MainConfig : JFinalConfig() {

    override fun configConstant(me: Constants) {
        PropKit.use("config.properties")
        me.setMainRenderFactory(BeetlRenderFactory())
    }

    fun createDruidPlugin(): DruidPlugin {
        return DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"),
                PropKit.get("password").trim { it <= ' ' }, PropKit.get("driverClass").trim { it <= ' ' })
    }
    override fun configPlugin(me: Plugins) {
        // 配置DruidPlugin数据库连接池插件
        val dp = createDruidPlugin()
        dp.addFilter(StatFilter())
        val wall = WallFilter()
        wall.dbType = "mysql"
        dp.addFilter(wall)
        me.add(dp)
        //
        val arp = ActiveRecordPlugin(dp)
        arp.setShowSql(true)
        SqlReporter.setLog(true)
        arp.setContainerFactory(PropertyNameContainerFactory())
        arp.setDialect(MysqlDialect())
        arp.addMapping("cloud_user", "id", User::class.java)
        me.add(arp)
    }

    override fun configInterceptor(me: Interceptors) {

    }

    override fun configHandler(me: Handlers) {

    }

    override fun configRoute(me: Routes) {
        me.add("/",IndexController::class.java)
        me.add("/login",LoginController::class.java)
        me.add("/cloud",CloudController::class.java)
        me.add("/user",UserController::class.java)
    }

}