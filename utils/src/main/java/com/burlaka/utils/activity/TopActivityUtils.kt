package com.burlaka.utils.activity

import android.app.ActivityManager
import android.app.AppOpsManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.*


/**
 * @author Stas
 * @since 1/30/19.
 */
object TopActivityUtils {

    private var sLastTopApp: String? = null

    fun getTopActivity(context: Context, activityManager: ActivityManager): ComponentName? {
        if (Build.VERSION.SDK_INT >= 21) {
            val topApp = getTopPackageOnAndroidLPlus(context)
            val topActivity = getActivityByPackageName(context, topApp)
            if (!TextUtils.isEmpty(topApp) && !TextUtils.isEmpty(topActivity)) {
                return ComponentName(topApp!!, topActivity!!)
            }
        } else {
            val lstTask: List<ActivityManager.RunningTaskInfo>?
            try {
                lstTask = activityManager.getRunningTasks(1)
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
            if (lstTask != null && lstTask.isNotEmpty()) {
                return lstTask[0].topActivity ?: return null
            }
        }
        return null
    }


    private fun getActivityByPackageName(context: Context, packageName: String?): String? {

        if (TextUtils.isEmpty(packageName))
            return null

        var activityName: String? = null
        val packageMgr = context.packageManager

        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)

        var list: List<ResolveInfo>?

        try {
            list = packageMgr.queryIntentActivities(intent, 0)
        } catch (e: Exception) {
            list = ArrayList(1)
        }

        if (list != null && list.isNotEmpty()) {
            for (resolveInfo in list) {
                try {
                    val pkgName = resolveInfo.activityInfo.applicationInfo.packageName
                    val activity = resolveInfo.activityInfo.name

                    if (pkgName == packageName) {
                        activityName = activity
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            }
        }

        if (TextUtils.isEmpty(activityName)) {
            try {
                val packageInfo = context.packageManager.getPackageInfo(packageName, 0)
                val resolveIntent = Intent(Intent.ACTION_MAIN, null)
                resolveIntent.setPackage(packageInfo.packageName)
                resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
                val resolveInfoList = context.packageManager.queryIntentActivities(resolveIntent, 0)
                val resolveInfo = resolveInfoList.iterator().next()
                if (resolveInfo != null) {
                    activityName = resolveInfo.activityInfo.name
                }
            } catch (e: Exception) {
                activityName = packageName
            }

        }

        return activityName
    }


    fun isStatAccessPermissionSet(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return true
        }
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            android.os.Process.myUid(), context.packageName
        )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    private fun requestReadNetworkHistoryAccess(context: Context) {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        context.startActivity(intent)
    }

    private fun getTopPackageOnAndroidLPlus(context: Context): String? {

        if (Build.VERSION.SDK_INT >= 22) {

            var pkgName = getTopPackageByUsageStats(context)
            if (!TextUtils.isEmpty(pkgName)) {
                return pkgName
            }

            pkgName = getTopPackageByRunningAppProcesses(context)
            if (!TextUtils.isEmpty(pkgName)) {
                return pkgName
            }
        } else {

            var pkgName: String? =
                getTopPackageByRunningAppProcesses(context)
            if (!TextUtils.isEmpty(pkgName)) {
                return pkgName
            }

            pkgName = getTopPackageByUsageStats(context)
            if (!TextUtils.isEmpty(pkgName)) {
                return pkgName
            }
        }

        return ""
    }

    private fun getTopPackageByRunningAppProcesses(c: Context?): String {
        if (c == null) {
            throw IllegalArgumentException("context can not be null when call getTopPackageByRunningAppProcesses(...)")
        }

        val am = c.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appList = am.runningAppProcesses
        if (appList != null) {
            for (running in appList) {

                if (running.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    if (running.pkgList != null && running.pkgList.isNotEmpty()) {
                        return running.pkgList[0]
                    }
                }
            }
        }

        return ""
    }

    private fun getUsageStatsList(context: Context): List<*>? {

        try {
            val clazz = Class.forName("android.app.usage.UsageStatsManager")
            val receiver = context.getSystemService(Context.USAGE_STATS_SERVICE)
            val method = clazz.getMethod("queryUsageStats", Integer.TYPE, java.lang.Long.TYPE, java.lang.Long.TYPE)

            val currentTime = System.currentTimeMillis()

            return method.invoke(
                receiver,
                0,
                currentTime - 30 * 60 * 1000,
                currentTime
            ) as List<*>
        } catch (e: Exception) {
            //ignore
        }

        return null
    }


    private fun getTopPackageByUsageStats(context: Context?): String? {
        if (context == null) {
            throw IllegalArgumentException("context can not be null when call getTopPackageByUsageStats(...)")
        }

        val dataList = getUsageStatsList(context)
        if (dataList == null || dataList.isEmpty()) {
            //                Log.d(TAG, "return sLastTopApp:" + sLastTopApp);
            return sLastTopApp
        }
        try {
            val clz: Class<*>? = Class.forName("android.app.usage.UsageStats")
            val med: Method? = clz?.getMethod("getLastTimeUsed")
            med?.let {
                val latestObj = Collections.max(dataList) { lhs, rhs ->
                    try {
                        val stampL = med.invoke(lhs) as Long
                        val stampR = med.invoke(rhs) as Long
                        return@max if (stampL >= stampR) 1 else -1
                    } catch (e: IllegalAccessException) {
                        // ignore
                    } catch (e: InvocationTargetException) {
                    }
                    0
                }

                val result = invokeMethod(
                    "android.app.usage.UsageStats",
                    "getPackageName",
                    latestObj!!
                )
                if (result != null) {
                    if (!TextUtils.isEmpty(result.toString())) {
                        sLastTopApp = result.toString()
                    }
                    return sLastTopApp
                }
            }

        } catch (e: java.lang.Exception) {
        }

        return ""
    }

    private fun invokeMethod(className: String, methodName: String, param1: Any): Any? {

        try {
            val clz: Class<*>? = Class.forName(className)
            if (clz != null) {
                val med: Method? = clz.getMethod(methodName)
                if (med != null) {
                    return med.invoke(param1)
                }
            }
        } catch (e: java.lang.Exception) {
        }
        return null
    }
}