package com.freemusic.base

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.os.SystemClock
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.freemusic.R
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity(), BaseView {

    lateinit var progressDialog : ProgressDialog
    var mLastClickTime: Long = 0


    /**
     * show the progress
     */
    private fun showProgressDialog(){
        try {
            if (progressDialog == null)
                progressDialog = ProgressDialog(this)

            if (!progressDialog.isShowing){
                progressDialog.window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                progressDialog.setCancelable(false)
                progressDialog.show()
                progressDialog.setContentView(R.layout.progress_bar)
            }

        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    private fun hideProgressDialog(){
        try {
            if (progressDialog != null && progressDialog.isShowing)
                progressDialog.dismiss()

        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    /* To avoid multiple clicks on screen at a same time */

    fun isClickEnable(): Boolean {
        val timeBetweenClick = 600 //in ns
        if (SystemClock.elapsedRealtime() - mLastClickTime < timeBetweenClick)
            return false
        else {
            mLastClickTime = SystemClock.elapsedRealtime()
            return true
        }
    }

    override fun onPause() {
        hideProgressDialog()
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun showError(error: String) {
        Snackbar.make(findViewById(android.R.id.content),error, Snackbar.LENGTH_SHORT).show()
    }

    // ------------------------------------------------------------
    // Activity Transition Support functions
    // ------------------------------------------------------------

    fun startActivity(intent : Intent, animate : Boolean){
        super.startActivity(intent)
        if (animate)
            overridePendingTransition(R.anim.animation_slide_from_right,R.anim.animation_slide_to_left)
    }


    fun startActivityForResult(intent : Intent,requestCode : Int,animate : Boolean){
        super.startActivityForResult(intent,requestCode)

        if (animate)
            overridePendingTransition(R.anim.animation_slide_from_right,R.anim.animation_slide_to_left)
    }

    fun finish(animate: Boolean) {
        super.finish()
        if (animate)
            overridePendingTransition(R.anim.animation_slide_to_left, R.anim.animation_slide_from_right)
    }


    fun addReplaceFragment(@IdRes container : Int, fragment : Fragment, addFragment : Boolean, addToBackStack : Boolean, animationHolder : AnimationHolder?){

        var transaction : FragmentTransaction = supportFragmentManager.beginTransaction()

        if (animationHolder != null){
//            transaction.setCustomAnimations(R.anim.animation_slide_from_right, R.anim.animation_slide_to_left, R.anim.animation_slide_from_left, R.anim.animation_slide_to_right);
            transaction.setCustomAnimations(animationHolder.enterAnim, animationHolder.exitAnim, animationHolder.popEnterAim, animationHolder.popExitAim)
        }
        if (addFragment){
            transaction.add(container,fragment,fragment.javaClass.simpleName)
        }else{
            transaction.replace(container,fragment,fragment.javaClass.simpleName)
        }
        if (addToBackStack){
            transaction.addToBackStack(fragment.tag)
        }
        transaction.commit()
    }



    /**
     * it show the network connection are available or not
     */
    override fun showConnectionError() {
        showError(resources.getString(R.string.error_internet_connection_common))
    }

    override fun showLoader() {
        showProgressDialog()
    }

    override fun hideLoader() {
        hideProgressDialog()
    }
}