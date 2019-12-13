package com.restaurants.presentation.common

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.restaurants.R

abstract class BaseActivity : AppCompatActivity() {

    private var uploadingDialog: AlertDialog? = null

    fun showUploading(show: Boolean) {
        if (show) showUploading() else hideUploading()
    }

    private fun showUploading() {
        if (uploadingDialog == null) {
            uploadingDialog = AlertDialog.Builder(this)
                .setView(R.layout.view_loading_dialog)
                .show()
            uploadingDialog!!.setCancelable(false)
        } else {
            uploadingDialog!!.show()
        }
    }

    private fun hideUploading() {
        if (uploadingDialog != null) {
            uploadingDialog!!.dismiss()
        }
    }


}
