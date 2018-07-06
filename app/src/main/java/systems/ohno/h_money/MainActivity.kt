package systems.ohno.h_money

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import systems.ohno.h_money.topView
import systems.ohno.h_money.Model.ArrText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewer  = topView(applicationContext)
        viewer.setText(ArrText(title="kawaii",user="yuki"))
        setContentView(viewer)
    }
}
