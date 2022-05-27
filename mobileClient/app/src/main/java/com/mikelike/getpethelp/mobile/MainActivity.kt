package com.mikelike.getpethelp.mobile

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.mikelike.getpethelp.mobile.fragment.*
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApi
import com.mikelike.getpethelp.mobile.rest.GetPetHelpApiImpl
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val getPetHelpApi: GetPetHelpApi = GetPetHelpApiImpl(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val firstFragment=TasksFragment()
        val secondFragment=WorkersFragment()
        val thirdFragment=MyProfileFragment()
        val text: Spannable = SpannableString(supportActionBar?.title)
        text.setSpan(
            ForegroundColorSpan(Color.rgb(254,208,83)),
            0,
            text.length,
            Spannable.SPAN_INCLUSIVE_INCLUSIVE
        )
        supportActionBar?.title = text
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        val sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE)
        if(sharedPreferences.getString("key","")=="") {
            setCurrentFragment(LoginFragment())
        }
        else{
            setCurrentFragment(firstFragment)
        }
        val imageLoader: ImageLoader =  ImageLoader.getInstance()
        imageLoader.init(ImageLoaderConfiguration.createDefault(this))
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.page_1->setCurrentFragment(firstFragment)
                R.id.page_2->setCurrentFragment(secondFragment)
                R.id.page_3->setCurrentFragment(thirdFragment)

            }
            true
        }

    }
    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    private var doubleBackToExitPressedOnce = false
    @SuppressLint("ObsoleteSdkInt")
    override fun onBackPressed() {
        Log.d("qwe","wqewqe")
        val f = supportFragmentManager.findFragmentById(R.id.flFragment)
        Log.d("qwe",f.toString())
        when (f) {
            is MyProfileFragment -> {
                if (doubleBackToExitPressedOnce) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        finishAffinity()
                    } else {
                        finish()
                    }
                }
                this.doubleBackToExitPressedOnce = true
                Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            }
            is CreateCVFragment, is CreateTaskFragment -> supportFragmentManager.beginTransaction()
                .replace(R.id.flFragment, MyProfileFragment())
                .addToBackStack("tag").commit()
            is FullTaskInfoFragment -> supportFragmentManager.beginTransaction()
                .replace(R.id.flFragment, TasksFragment())
                .addToBackStack("tag").commit()
            else -> super.onBackPressed()
        }

    }

}