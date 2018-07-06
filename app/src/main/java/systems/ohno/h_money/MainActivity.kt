package systems.ohno.h_money

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import systems.ohno.h_money.model.ArrText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewer  = topView(applicationContext)
        viewer.setText(ArrText(title="kawaii",user="yuki"))
        setContentView(viewer)
    }
}
