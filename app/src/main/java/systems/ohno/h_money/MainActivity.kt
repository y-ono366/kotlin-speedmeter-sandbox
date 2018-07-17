package systems.ohno.h_money

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import systems.ohno.h_money.model.Speed
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("HMONEY_FILE", AppCompatActivity.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong("total",100000)
        editor.commit()
        var total = prefs.getLong("total",0)

        var viewer  = topView(applicationContext)
        var hour_speed = HourCalculation(total)
        viewer.setText(Speed(hour_speed))
        setContentView(viewer)
    }

    fun  HourCalculation(speed:Long): Long {
        var beforeDate = "2018-07-01"

        var diffDate =getDiffDay(beforeDate)

        var hourSpeed:(Long,Long)-> Long = { a,day ->
            a / day.toLong() / 24
        }
        var hour = hourSpeed(speed,diffDate)
        return hour
    }

    fun getDiffDay(beforeDate:String):Long {
        val nowDate = Calendar.getInstance()
        val calender = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(beforeDate)
        calender.setTime(date)
        val diffTime =  nowDate.getTimeInMillis() - calender.getTimeInMillis()
        val millis_of_day = 1000 * 60 * 60 * 24
        val diffDays = diffTime / millis_of_day
        return diffDays
    }
}
