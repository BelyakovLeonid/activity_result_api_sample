package com.example.activityresultapp

import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

class PermissionHelper(
    private val permission: String,
    private val activity: ComponentActivity,
    private val onGranted: (() -> Unit)? = null,
    private val onRefused: (() -> Unit)? = null,
    private val onShouldShowRationale: (() -> Unit)? = null,
    private val onDoNotAskAgain: (() -> Unit)? = null,
) : LifecycleObserver {
    lateinit var permissionLauncher: ActivityResultLauncher<String>

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun registerContract() {
        permissionLauncher = activity.activityResultRegistry.register(
            REGISTRY_KEY,
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            when {
                granted -> onGranted?.invoke()
                !shouldShowRationale() -> onDoNotAskAgain?.invoke()
                else -> onRefused?.invoke()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun unregisterContract() {
        permissionLauncher.unregister()
    }

    fun requestPermission(isNeedRationaleCheck: Boolean = true) {
        if (isNeedRationaleCheck && shouldShowRationale()) {
            onShouldShowRationale?.invoke()
        } else {
            permissionLauncher.launch(permission)
        }
    }

    private fun shouldShowRationale(): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    companion object {
        private const val REGISTRY_KEY = "permission-observer-key"
    }
}

fun Fragment.registerPermissionHelper(
    permission: String,
    onGranted: (() -> Unit)? = null,
    onRefused: (() -> Unit)? = null,
    onShouldShowRationale: (() -> Unit)? = null,
    onDoNotAskAgain: (() -> Unit)? = null,
): PermissionHelper {
    return PermissionHelper(
        permission,
        requireActivity(),
        onGranted,
        onRefused,
        onShouldShowRationale,
        onDoNotAskAgain
    ).also { lifecycle.addObserver(it) }
}