package com.udacity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.app.NotificationManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*


    const val FILE_NAME="fileName"
    const val STATUS="status"
class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val notificationManager = ContextCompat.getSystemService(
            this,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()

        val fileName=intent.getStringExtra(FILE_NAME)
        if (fileName!=null&&fileName.isNotEmpty()){
            textViewFileName2.text=fileName
        }
        val status=intent.getStringExtra(STATUS)
        if (status!=null&&status.isNotEmpty()){
            textViewStatus2.text=status
        }
        scaler()
//        rotater()
//        fader()
//        buttonOk.setOnClickListener {
//            finish()
//        }
    }

    private fun rotater() {
        val animator = ObjectAnimator.ofFloat(textViewStatus2, View.ROTATION, -360f, 0f)
        animator.duration = 2000
        animator.repeatCount=2
        animator.start()

    }

    private fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
        val animator = ObjectAnimator.ofPropertyValuesHolder(
            textViewStatus2, scaleX, scaleY)
        animator.repeatCount = 3
        animator.repeatMode = ObjectAnimator.REVERSE
//        animator.disableViewDuringAnimation(scaleButton)
        animator.start()
    }
    private fun fader() {
        val animator = ObjectAnimator.ofFloat(textViewStatus2, View.ALPHA, 0f)
        animator.repeatCount = 3
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.start()
    }

    fun onButtonOkClicked(view: View) {
        finish()
    }

}
