package com.udacity

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import java.lang.Integer.min
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private var buttonBackgroundColor = 0
    private var textColor = 0
    private var loadingColor = 0
    private var circleColor = 0
    private var textString = ""
    private var progress = 0


    private val valueAnimator = ValueAnimator.ofInt(0, 360)

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

        when (new) {
            ButtonState.Clicked -> {
                valueAnimator.setDuration(2000).start()
                textString = resources.getString(R.string.button_loading)

            }
            ButtonState.Completed -> {
                valueAnimator.cancel()
                textString = resources.getString(R.string.button_name)
                progress = 0
            }
        }

        invalidate()
    }



    init {

        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            buttonBackgroundColor = getColor(R.styleable.LoadingButton_buttonBackgroundColor, 0)
            textColor = getColor(R.styleable.LoadingButton_textColor, 0)
            loadingColor = getColor(R.styleable.LoadingButton_loadingColor, 0)
            circleColor = getColor(R.styleable.LoadingButton_circleColor, 0)
        }

        buttonState = ButtonState.Completed

         setupAnimation()
    }

    private fun setupAnimation() {
        valueAnimator.apply {
            addUpdateListener {
                progress = it.animatedValue as Int
                invalidate()
            }
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }
    private val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 50.0f
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawViewCircle(canvas)
        /*get center of circle*/
        val coordinateX = (widthSize-100 ).toFloat()
        val centerY = (heightSize / 2).toFloat()
        val centerX=(widthSize/2).toFloat()
        /*get radius*/
        val radius = min(widthSize, heightSize) / 4


        val rectF = RectF(
            coordinateX - radius,
            centerY - radius,
            coordinateX + radius,
            centerY + radius
        )

        paint.color = buttonBackgroundColor
        canvas?.drawRect(0f,0f,widthSize.toFloat(), heightSize.toFloat(), paint)
        paint.color = loadingColor
        canvas?.drawRect(0f, 0f, widthSize * progress/360f, heightSize.toFloat(), paint)
        paint.color = textColor
        canvas?.drawText(textString, centerX, centerY + 30.0f, paint)
        paint.color = circleColor
        canvas?.drawArc(rectF,0f, progress.toFloat(), true, paint)

    }

    private fun drawViewCircle(canvas: Canvas?) {

    }

}