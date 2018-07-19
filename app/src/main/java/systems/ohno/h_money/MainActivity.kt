package systems.ohno.h_money

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import systems.ohno.h_money.model.Speed
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.text.SimpleDateFormat
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("HMONEY_FILE", AppCompatActivity.MODE_PRIVATE)

        var total = prefs.getLong("total",0)
        var hour_speed = HourCalculation(total)
        var viewer  = topView(applicationContext)

        viewer.setText(Speed(hour_speed))
        setContentView(viewer)

        val oneHundBtn: Button = findViewById(R.id.oneHundBtn)
        oneHundBtn.setOnClickListener {
            viewer.setText(Speed(HourCalculation(resave(100))))
        }


        val fiveHundBtn: Button = findViewById(R.id.fiveHundBtn)
        fiveHundBtn.setOnClickListener {
            viewer.setText(Speed(HourCalculation(resave(500))))
        }

        val thousandBtn: Button = findViewById(R.id.thousandBtn)
        thousandBtn.setOnClickListener {
            viewer.setText(Speed(HourCalculation(resave(1000))))
        }

        val fiveThousandBtn: Button = findViewById(R.id.fiveThousandBtn)
        fiveThousandBtn.setOnClickListener {
            viewer.setText(Speed(HourCalculation(resave(5000))))
        }

    }

    fun resave (money:Long):Long {
        val prefs = getSharedPreferences("HMONEY_FILE", AppCompatActivity.MODE_PRIVATE)
        var newTotal = prefs.getLong("total",0) + money
        val editor = prefs.edit()
        editor.putLong("total",newTotal)
        editor.commit()
        return newTotal
    }

    fun  HourCalculation(speed:Long): Long {
        var beforeDate = "2018-07-15"

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
