package com.luoyingmm.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.PersistableBundle
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.luoyingmm.R;
import com.luoyingmm.util.StatusBarUtil;
import com.luoyingmm.util.StringUtils;


import java.util.List;

//Activity的父类
public abstract class BaseActivity : AppCompatActivity() {
    public  var context:Context? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayout())
        context = this
        //绑定控件
        initView()
        //应用逻辑
        initData()
        //设置上方通知栏的颜色为白色
        StatusBarUtil.setStatusBarMode(this,true,R.color.white);

    }

    protected abstract fun initLayout():Int

    protected abstract fun  initView()

    protected abstract fun  initData()

    public fun showToast(msg:String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    public fun navigateTo(cls:Class<*>) {
        val intent:Intent = Intent(context,cls)
        startActivity(intent)
    }

    protected fun saveStringToSp( key:String, va:String){
        val sp = getSharedPreferences(StringUtils.username, MODE_PRIVATE)
       val edit = sp.edit()
        edit.putString(key,va)
        edit.apply()
    }
    protected fun getStringFromSp(key:String):String?{
        val sp:SharedPreferences = getSharedPreferences(StringUtils.username, MODE_PRIVATE)
        return sp.getString(key,"")
    }


    public fun showToastSync(msg:String){
        Looper.prepare()
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        Looper.loop()
    }

    public fun navigateToWithBundle( cls:Class<*>, bundle:Bundle){
       val  intent:Intent = Intent(context,cls)
        intent.putExtras(bundle)
        startActivity(intent)
    }
    public fun navigateToWithFlag( cls:Class<*>,flag:Int){
        val intent:Intent = Intent(context, cls);
        intent.setFlags(flag);
        startActivity(intent);
    }

}
