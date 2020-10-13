package com.nesp.sdk.android.util

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.nesp.sdk.android.core.ktx.cast

/**
 *
 * Author: <a href="mailto:jinzhaolu@numob.com">Jack Email:jinzhaolu@numob.com</a>
 * Time: Created 2020/10/13 4:34 PM
 * Project: NespAndroidSdk
 * Description:
 **/
class AttrUtil {
    companion object {

        fun getDimensionAttrValue(context: Context, id: Int): Float {
            val outTypeValue = getAttrOutTypeValue(context, id)
            val windowManager =
                context.getSystemService(Context.WINDOW_SERVICE).cast<WindowManager>()
            val outDisplayMetrics = DisplayMetrics()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && context is AppCompatActivity) {
                context.display!!.getRealMetrics(outDisplayMetrics)
            } else {
                windowManager.defaultDisplay!!.getRealMetrics(outDisplayMetrics)
            }
            return outTypeValue.getDimension(outDisplayMetrics)
        }

        fun getAttrOutTypeValue(context: Context, id: Int): TypedValue {
            val outTypeValue = TypedValue()
            context.theme.resolveAttribute(id, outTypeValue, true)
            return outTypeValue
        }
    }

}