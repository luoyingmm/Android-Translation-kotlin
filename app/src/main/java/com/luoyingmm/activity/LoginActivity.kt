package com.luoyingmm.activity

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.luoyingmm.R
import com.luoyingmm.api.Api
import com.luoyingmm.api.ApiConfig
import com.luoyingmm.api.TtitCallback
import com.luoyingmm.entity.LoginResponse
import com.luoyingmm.util.DialogUtil
import com.luoyingmm.util.DialogUtil.AlertDialogBtnClickListener
import com.luoyingmm.util.StringUtils
import java.util.*

//登录界面设置
class LoginActivity : BaseActivity() {
    private var etUsername: EditText? = null
    private var etPassword: EditText? = null
    private var tvNoRegistered: TextView? = null
    private var btn_login: Button? = null
    private var btn_registered: Button? = null
    override fun initLayout(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        tvNoRegistered = findViewById(R.id.tv_registered)
        btn_login = findViewById(R.id.btn_login)
        btn_registered = findViewById(R.id.btn_registered)
    }

    override fun initData() {
        btn_registered!!.setOnClickListener {
            startActivity(
                Intent(
                    this@LoginActivity,
                    RegisteredActivity::class.java
                )
            )
        }

        //点击暂不登录
        tvNoRegistered!!.setOnClickListener {
            DialogUtil.showAlertDialog(this@LoginActivity as Activity,
                R.mipmap.jump,
                "提示",
                "为了更好的用户体验，建议登录账户",
                "继续登录",
                "狠心拒绝",
                true,
                object : AlertDialogBtnClickListener {
                    override fun clickPositive() {}
                    override fun clickNegative() {
                        //设置登录标记并跳转
                        saveSpFlag("temp")
                        StringUtils.username = "temp"
                        saveStringToSp("login_flag", "right")
                        //清除栈
                        navigateToWithFlag(
                            MainActivity::class.java,
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        )
                    }
                })
        }
        //点击登录按钮
        btn_login!!.setOnClickListener {
            val account = etUsername!!.text.toString().trim { it <= ' ' }
            val password = etPassword!!.text.toString().trim { it <= ' ' }
            login(account, password)
        }
    }

    private fun login(account: String, password: String) {
        if (StringUtils.isEmpty(account)) {
            showToast("请输入用户名")
            return
        }
        if (StringUtils.isEmpty(password)) {
            showToast("请输入密码")
            return
        }
        val params = HashMap<String, Any>()
        params["mobile"] = account
        params["password"] = password
        //根据OkHttp请求数据进行登录校验
        Api.config(ApiConfig.LOGIN, params).postRequest(object : TtitCallback {
            override fun onSuccess(res: String) {
                Log.e("onSuccess", res)
                val gson = Gson()
                val loginResponse = gson.fromJson(res, LoginResponse::class.java)
                if (loginResponse.code == 0) {
                    saveSpFlag(account)
                    StringUtils.username = account
                    saveStringToSp("username", account)
                    saveStringToSp("login_flag", "right")
                    navigateToWithFlag(
                        MainActivity::class.java,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    )
                    showToastSync("登陆成功")
                } else {
                    showToastSync("账号或密码错误")
                }
            }

            override fun onFailure(e: Exception) {
                showToastSync("网络异常，请检查你的网络连接")
            }
        })
    }

    private fun saveSpFlag(username: String) {
        val loginFlag = getSharedPreferences("LoginFlag", MODE_PRIVATE)
        val edit = loginFlag.edit()
        edit.putString("loginKey", username)
        edit.apply()
    }
}