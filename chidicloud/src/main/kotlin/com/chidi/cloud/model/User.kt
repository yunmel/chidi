package com.chidi.cloud.model

import com.jfinal.plugin.activerecord.Model
import com.jfinal.plugin.activerecord.Page


/**
 * Created by xu on 2016/12/9.
 */
class User : Model<User>() {
    companion object{
        val me = User()
    }

    fun paginate(pageNumber: Int, pageSize: Int): Page<User> {
        return paginate(pageNumber, pageSize, "select *", "from cloud_user order by id asc")
    }

    fun getUser(username: String): User {
        return findFirst("select * from cloud_user u where u.username='$username'")
    }
}