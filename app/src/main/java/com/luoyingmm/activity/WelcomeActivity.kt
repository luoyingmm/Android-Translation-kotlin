package com.luoyingmm.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
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
        val loginFlag:SharedPreferences = getSharedPreferences("LoginFlag", MODE_PRIVATE)
         StringUtils.username = loginFlag.getString("loginKey","")
        if (!StringUtils.username.equals("")){
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