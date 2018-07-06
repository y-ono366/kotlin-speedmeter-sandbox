package systems.ohno.h_money


import android.widget.FrameLayout
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import systems.ohno.h_money.model.Speed


class topView: FrameLayout{
    constructor(context: Context?): super(context)

    var titleTextView: TextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.activity_main,this)
        titleTextView =findViewById(R.id.hour_speed) as TextView
    }

    fun setText(speed:Speed) {
        titleTextView?.text = speed.hour_speed.toString()
    }
}

