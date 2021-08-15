package com.luoyingmm.activity;


import android.content.SharedPreferences;

import android.os.Handler;
import android.util.Log

import com.luoyingmm.R;
import com.luoyingmm.util.StringUtils;

//欢迎界面
public class WelcomeActivity : BaseActivity() {
    override fun initLayout(): Int {
        return R.layout.activity_welcome;
    }

    override fun initView() {

    }

    override fun initData() {
        val loginFlag = getSharedPreferences("LoginFlag", MODE_PRIVATE)
         StringUtils.username = loginFlag.getString("loginKey","")
        if (!StringUtils.username.equals("")){
            Log.e("logwe", "12344", )
            navigateTo(MainActivity::class.java)

            finish()
        }else{
            Handler().postDelayed(Runnable {
              navigateTo(LoginActivity::class.java)

                finish()
            },1000);
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0,0)
    }

}