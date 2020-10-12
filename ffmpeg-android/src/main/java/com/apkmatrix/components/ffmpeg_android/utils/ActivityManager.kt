package com.apkmatrix.components.ffmpeg_android.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.*

class ActivityManager private constructor() {
    private var isRegister = false
    private var count = 0
    private val lifecycleStackActivity by lazy { Stack<Activity>() }
    private val baseActivities by lazy { HashSet<Activity>() }
    private val myActivityLifecycleCallbacks by lazy { MyActivityLifecycleCallbacks() }

    companion object {
        private var activityManager: ActivityManager? = null
        private var mApplication: Application? = null

        val instance: ActivityManager
            get() {
                if (activityManager == null) {
                    synchronized(ActivityManager::class.java) {
                        if (activityManager == null) {
                            activityManager = ActivityManager()
                        }
                    }
                }
                return activityManager!!
            }

        fun initial(mApplication: Application) {
            this.mApplication = mApplication
            instance.register()
        }
    }

    val stackActivityCount: Int
        get() = lifecycleStackActivity.size

    val isBackground: Boolean
        get() = count == 0

    val stackTopActiveActivity: Activity?
        get() = if (lifecycleStackActivity.isEmpty()) {
            null
        } else {
            lifecycleStackActivity.lastElement()
        }

    private inner class MyActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
        override fun onActivityResumed(p0: Activity) = Unit
        override fun onActivityPaused(p0: Activity) = Unit
        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) = Unit
        override fun onActivityCreated(p0: Activity, savedInstanceState: Bundle?) {
            p0.let {
                baseActivities.add(it)
            }
        }

        override fun onActivityDestroyed(p0: Activity) {
            p0.let {
                baseActivities.remove(it)
            }
        }

        override fun onActivityStarted(p0: Activity) {
            count++
            p0.let {
                lifecycleStackActivity.add(it)
            }
        }

        override fun onActivityStopped(p0: Activity) {
            count--
            p0.let {
                lifecycleStackActivity.remove(it)
            }
        }
    }

    private fun register() {
        if (!isRegister) {
            isRegister = true
            mApplication?.registerActivityLifecycleCallbacks(myActivityLifecycleCallbacks)
        }
    }

    private fun unregister() {
        if (isRegister) {
            isRegister = false
            mApplication?.unregisterActivityLifecycleCallbacks(myActivityLifecycleCallbacks)
        }
    }
}
