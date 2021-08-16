package com.luoyingmm.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luoyingmm.util.StatusBarUtil
import com.luoyingmm.R
import android.widget.Toast
import android.content.Intent
import android.content.SharedPreferences
import android.os.Looper
import com.luoyingmm.util.StringUtils

//Activity的父类
abstract class BaseActivity : AppCompatActivity() {
    var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayout())
        context = this

        //绑定控件
        initView()
        //应用逻辑
        initData()
        //设置上方通知栏的颜色为白色
        StatusBarUtil.setStatusBarMode(this, true, R.color.white)
    }

    protected abstract fun initLayout(): Int
    protected abstract fun initView()
    protected abstract fun initData()
    fun showToast(msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun navigateTo(cls: Class<*>?) {
        val intent = Intent(context, cls)
        startActivity(intent)
    }

    protected fun saveStringToSp(key: String?, `val`: String?) {
        val sp = getSharedPreferences(StringUtils.username, MODE_PRIVATE)
        val edit = sp.edit()
        edit.putString(key, `val`)
        edit.apply()
    }

    protected fun getStringFromSp(key: String?): String? {
        val sp = getSharedPreferences(StringUtils.username, MODE_PRIVATE)
        return sp.getString(key, "")
    }

    fun showToastSync(msg: String?) {
        Looper.prepare()
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        Looper.loop()
    }

    fun navigateToWithBundle(cls: Class<*>?, bundle: Bundle?) {
        val intent = Intent(context, cls)
        intent.putExtras(bundle!!)
        startActivity(intent)
    }

    fun navigateToWithFlag(cls: Class<*>?, flag: Int) {
        val intent = Intent(context, cls)
        intent.flags = flag
        startActivity(intent)
    }
}