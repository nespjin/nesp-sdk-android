/*
 *
 *   Copyright (c) 2020  NESP Technology Corporation. All rights reserved.
 *
 *   This program is not free software; you can't redistribute it and/or modify it
 *   without the permit of team manager.
 *
 *   Unless required by applicable law or agreed to in writing.
 *
 *   If you have any questions or if you find a bug,
 *   please contact the author by email or ask for Issues.
 *
 *   Author:JinZhaolu <1756404649@qq.com>
 */

package com.nesp.sdk.android.app

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

/**
 *
 *
 * @team NESP Technology
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * @time: Created 19-7-26 下午1:39
 * @project FishMovie
 **/
class PermissionManager(val activity: Activity) {

    fun requestPermissions(permissionRequests: MutableList<PermissionRequest>, onRequestPermissionsListener: OnRequestPermissionsListener) {

        var currentPermissionPos = 0

        var availablePermissionSize = 0
        var denyPermissionSize = 0

        val listener = object : OnRequestPermissionListener {

            override fun onPermissionAvailable() {
                onRequestPermissionsListener.onPermissionAvailable(permissionRequests[currentPermissionPos])

                availablePermissionSize++

                if (availablePermissionSize == permissionRequests.size) {
                    onRequestPermissionsListener.onAllPermissionAvailable()
                }

                currentPermissionPos++
                requestPermission(permissionRequests[currentPermissionPos], this)
            }

            override fun onDenyPermissionRequest() {
                onRequestPermissionsListener.onDenyPermissionRequest(permissionRequests[currentPermissionPos])

                denyPermissionSize++

                if (denyPermissionSize == permissionRequests.size) {
                    onRequestPermissionsListener.onAllDenyPermissionRequest()
                }

                currentPermissionPos++
                requestPermission(permissionRequests[currentPermissionPos], this)
            }
        }

        requestPermission(permissionRequests[currentPermissionPos], listener)
    }

    fun requestPermission(permissionRequest: PermissionRequest, onRequestPermissionListener: OnRequestPermissionListener) {
        if (activity.isDestroyed) return

        if (ActivityCompat.checkSelfPermission(activity, permissionRequest.permission) == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available
            onRequestPermissionListener.onPermissionAvailable()
        } else {
            // Permission has not been granted and must be requested.
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                            permissionRequest.permission)) {
                // Provide an additional rationale to the user if the permission was not granted
                // and the user would benefit from additional context for the use of the permission.
                // Display a SnackBar with cda button to request the missing permission.
                AlertDialog.Builder(activity)
                        .setTitle("未授予权限")
                        .setMessage(permissionRequest.introMessage)
                        .setNegativeButton("取消") { _, _ ->
                            onRequestPermissionListener.onDenyPermissionRequest()
                        }
                        .setPositiveButton("好的") { _, _ ->
                            // Request the permission
                            ActivityCompat.requestPermissions(activity,
                                    arrayOf(permissionRequest.permission),
                                    permissionRequest.requestCode)
                        }
                        .create().show()
            } else {
                // Request the permission. The result will be received in onRequestPermissionResult().
                ActivityCompat.requestPermissions(activity,
                        arrayOf(permissionRequest.permission), permissionRequest.requestCode)
            }
        }
    }

    data class PermissionRequest(
            val permission: String,
            val introMessage: String,
            val requestCode: Int
    )

    interface OnRequestPermissionListener {

        fun onPermissionAvailable()

        fun onDenyPermissionRequest()
    }

    interface OnRequestPermissionsListener {

        fun onAllPermissionAvailable()

        fun onAllDenyPermissionRequest()

        fun onPermissionAvailable(permissionRequest: PermissionRequest)

        fun onDenyPermissionRequest(permissionRequest: PermissionRequest)
    }
}
