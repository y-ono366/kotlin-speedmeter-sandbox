package systems.ohno.h_money


import android.widget.FrameLayout
import android.content.Context
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import systems.ohno.h_money.Model.ArrText


class topView: FrameLayout{
    constructor(context: Context?): super(context)
    constructor(context: Context?, attrs: AttributeSet?):super(context,attrs)

    var titleTextView: TextView? = null
    var userNameTextView: TextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.activity_main,this)
        titleTextView =findViewById(R.id.text_view) as TextView
        userNameTextView = findViewById(R.id.user_name_view) as TextView
    }

    fun setText(text:ArrText) {
        titleTextView?.text = text.title
        userNameTextView?.text = text.user
    }

    fun render() {
    }
}

