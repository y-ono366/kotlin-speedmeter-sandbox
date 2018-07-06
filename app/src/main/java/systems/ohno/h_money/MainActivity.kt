package systems.ohno.h_money

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import systems.ohno.h_money.model.Speed

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewer  = topView(applicationContext)
        viewer.setText(Speed(hour_speed=5000))
        setContentView(viewer)
    }
}
