package com.freemusic.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

abstract  class BaseFragment : Fragment(), BaseView {

     var mBaseActivity: BaseActivity? = null
     var mLastClickTime: Long = 0

    override fun showError(error: String) {
        try {
            if (mBaseActivity != null)
                mBaseActivity!!.showError(error)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun showConnectionError() {
        try {
            if (mBaseActivity != null)
                mBaseActivity!!.showConnectionError()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun showLoader() {
        try {
            if (mBaseActivity != null)
                mBaseActivity!!.showLoader()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun hideLoader() {
        try {
            if (mBaseActivity != null)
                mBaseActivity!!.hideLoader()
        } catch (e: Exception) {
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mBaseActivity = context as BaseActivity?
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    fun addReplaceFragment(@IdRes container: Int, fragment: Fragment, addFragment: Boolean, addToBackStack: Boolean, animationHolder: AnimationHolder?) {
        if (activity == null) return
        val transaction = activity!!.supportFragmentManager.beginTransaction()
        if (animationHolder != null) {
            //            transaction.setCustomAnimations(R.anim.animation_slide_from_right, R.anim.animation_slide_to_left, R.anim.animation_slide_from_left, R.anim.animation_slide_to_right);
            transaction.setCustomAnimations(animationHolder.enterAnim, animationHolder.exitAnim, animationHolder.popEnterAim, animationHolder.popExitAim
            )
        }
        if (addFragment) {
            transaction.add(container, fragment, fragment.javaClass.simpleName)
        } else {
            transaction.replace(container, fragment, fragment.javaClass.simpleName)
        }
        if (addToBackStack) {
            transaction.addToBackStack(fragment.tag)
        }
        transaction.commit()
    }


    fun addReplaceChildFragment(@IdRes container: Int, fragment: Fragment, addFragment: Boolean, addToBackStack: Boolean, animationHolder: AnimationHolder?) {
        val transaction = childFragmentManager.beginTransaction()
        if (animationHolder != null) {
            //            transaction.setCustomAnimations(R.anim.animation_slide_from_right, R.anim.animation_slide_to_left, R.anim.animation_slide_from_left, R.anim.animation_slide_to_right);
            transaction.setCustomAnimations(animationHolder.enterAnim, animationHolder.exitAnim, animationHolder.popEnterAim, animationHolder.popExitAim
            )
        }
        if (addFragment) {
            transaction.add(container, fragment, fragment.javaClass.simpleName)
        } else {
            transaction.replace(container, fragment, fragment.javaClass.simpleName)
        }
        if (addToBackStack) {
            transaction.addToBackStack(fragment.tag)
        }
        transaction.commit()
    }

    fun hideKeyboard(activity: Activity) {
        var imm : InputMethodManager= activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null)
            view = View(activity)
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }


    /**
     * set StatusBar Transparent enable
     */
    @SuppressLint("ObsoleteSdkInt")
    fun setFullScreenEnable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    /**
     * set StatusBar Transparent normal
     */
    @SuppressLint("ObsoleteSdkInt")
    fun setFullScreenDisable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = activity!!.window // in Activity's onCreate() for instance
            w.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
            w.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}