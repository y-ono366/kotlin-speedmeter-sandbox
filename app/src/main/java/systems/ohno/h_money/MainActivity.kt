package systems.ohno.h_money

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import systems.ohno.h_money.topView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        topView().render()
    }
}
