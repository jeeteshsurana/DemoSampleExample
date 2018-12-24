package com.freemusic.utlis.utils

import android.util.Log
import com.freemusic.BuildConfig

open class DebugLog {

    private var className: String? = null
    private var methodName: String? = null
    private var lineNumber: Int = 0

    private val isDebuggable: Boolean
        get() = BuildConfig.DEBUG

    private fun createLog(log: String): String {

        val builder = StringBuilder()
        builder.append("[")
        builder.append(methodName)
        builder.append(":")
        builder.append(lineNumber)
        builder.append("]")
        builder.append(log)
        return builder.toString()
    }

    private fun getMethodNames(sElements: Array<StackTraceElement>) {
        className = sElements[1].fileName
        methodName = sElements[1].methodName
        lineNumber = sElements[1].lineNumber
    }

    fun e(message: String) {
        if (!isDebuggable)
            return
        // Throwable instance must be created before any methods
        getMethodNames(Throwable().stackTrace)
        Log.e(className, createLog(message))
    }

    fun i(message: String) {
        if (!isDebuggable)
            return

        getMethodNames(Throwable().stackTrace)
        Log.i(className, createLog(message))
    }

    fun d(message: String) {
        if (!isDebuggable)
            return

        getMethodNames(Throwable().stackTrace)
        Log.d(className, createLog(message))
    }

    fun v(message: String) {
        if (!isDebuggable)
            return

        getMethodNames(Throwable().stackTrace)
        Log.v(className, createLog(message))
    }

    fun w(message: String) {
        if (!isDebuggable)
            return

        getMethodNames(Throwable().stackTrace)
        Log.w(className, createLog(message))
    }

    fun wtf(message: String) {
        if (!isDebuggable)
            return

        getMethodNames(Throwable().stackTrace)
        Log.wtf(className, createLog(message))
    }

}
