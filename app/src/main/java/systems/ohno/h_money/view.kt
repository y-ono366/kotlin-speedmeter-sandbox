package systems.ohno.h_money


import android.widget.FrameLayout
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import systems.ohno.h_money.model.Speed


class topView: FrameLayout{
    constructor(context: Context?): super(context)

    var speedView: TextView? = null
    var remaingAmountView: TextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.activity_main,this)
        speedView =findViewById(R.id.hour_speed) as TextView
        remaingAmountView =findViewById(R.id.reaming_amount) as TextView
    }

    fun setText(speed:Speed) {
        speedView?.text = speed.hour_speed.toString()
        remaingAmountView?.text = speed.remaing_amount.toString()
    }
}

