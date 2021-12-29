package com.nesp.sdk.android.app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.nesp.sdk.android.R
import com.nesp.sdk.android.permission.AbsPermissionRequester
import com.nesp.sdk.android.permission.IPermissionRequestHost
import com.nesp.sdk.android.permission.PermissionRequestHost

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/12 9:55
 **/
abstract class BaseActivity : AppCompatActivity(), IComponent, IPermissionRequestHost {

    override val act: Activity
        get() = this
    override val ctx: Context
        get() = this

    private lateinit var permissionRequesterHost: PermissionRequestHost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionRequesterHost = PermissionRequestHost()
        lifecycle.addObserver(permissionRequesterHost)
    }

    override fun registerPermissionRequester(permissionRequester: AbsPermissionRequester) {
        permissionRequesterHost.registerPermissionRequester(permissionRequester)
    }

    override fun registerPermissionRequesters(permissionRequesters: ArrayList<AbsPermissionRequester>) {
        permissionRequesterHost.registerPermissionRequesters(permissionRequesters)
    }

    override fun unregisterPermissionRequester(permissionRequester: AbsPermissionRequester) {
        permissionRequesterHost.unregisterPermissionRequester(permissionRequester)
    }

    override fun unregisterPermissionRequesters(permissionRequesters: ArrayList<AbsPermissionRequester>) {
        permissionRequesterHost.unregisterPermissionRequesters(permissionRequesters)
    }

    override fun clearPermissionRequesters() {
        permissionRequesterHost.clearPermissionRequesters()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragments = supportFragmentManager.fragments
        if (fragments.isNotEmpty()) {
            notifyFragmentsActivityResult(fragments, requestCode, resultCode, data)
        }
    }

    private fun notifyFragmentsActivityResult(
        fragments: MutableList<Fragment>,
        requestCode: Int, resultCode: Int, data: Intent?
    ) {
        for (fragment in fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
            val childFragments = fragment.childFragmentManager.fragments
            if (childFragments.isNotEmpty()) {
                notifyFragmentsActivityResult(fragments, requestCode, resultCode, data)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val fragments = supportFragmentManager.fragments
        if (fragments.isNotEmpty()) {
            notifyFragmentsRequestPermissionResultRecursive(
                fragments,
                requestCode,
                permissions,
                grantResults
            )
        }

        permissionRequesterHost.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun notifyFragmentsRequestPermissionResultRecursive(
        fragments: MutableList<Fragment>,
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        for (fragment in fragments) {
            fragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
            val childFragments = fragment.childFragmentManager.fragments
            if (childFragments.isNotEmpty()) {
                notifyFragmentsRequestPermissionResultRecursive(
                    childFragments,
                    requestCode,
                    permissions,
                    grantResults
                )
            }
        }
    }

    fun popFragment(fragment: Fragment) {
        try {
            if (getFragmentContainerId() == -1) return
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction.setCustomAnimations(
                fragmentPopEnterAnimRes(),
                fragmentPopExitAnimRes()
            )
            beginTransaction.remove(fragment)
            beginTransaction.commit()
        } catch (e: Exception) {
        }
    }

    open fun fragmentPopEnterAnimRes(): Int = R.anim.fast_fade_in

    open fun fragmentPopExitAnimRes(): Int = R.anim.fast_fade_out

    fun pushFragment(fragment: Fragment) {
        try {
            if (getFragmentContainerId() == -1
                || supportFragmentManager.fragments.contains(fragment)
            ) {
                return
            }
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction.setCustomAnimations(
                fragmentPushEnterAnimRes(),
                fragmentPushExitAnimRes()
            )
            beginTransaction.add(
                getFragmentContainerId(),
                fragment, "Fragment." + fragment::class.java.name
            )
            beginTransaction.addToBackStack(fragment::class.java.name)
            beginTransaction.commit()
        } catch (e: Exception) {
        }
    }

    open fun fragmentPushEnterAnimRes(): Int = android.R.anim.fade_in

    open fun fragmentPushExitAnimRes(): Int = android.R.anim.fade_out

    open fun getFragmentContainerId(): Int = -1

    companion object {
        private const val TAG = "BaseActivity"
    }
}