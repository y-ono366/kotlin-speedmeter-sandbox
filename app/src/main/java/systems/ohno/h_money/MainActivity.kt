package systems.ohno.h_money

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import systems.ohno.h_money.model.Speed
import java.util.Calendar
import java.text.SimpleDateFormat
import android.widget.Button

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        var resetFlg:Int = prefs.getInt("resetFlg",0)
        var total:Int = prefs.getInt("totalMoney",0)
        var payDay:Int = prefs.getInt("payDay",1)

        val nowDate = Calendar.getInstance()
        val format = SimpleDateFormat("dd")
        val nowDay = format.format(nowDate.getTime())
        if(payDay < nowDay.toInt() && resetFlg == 1) {
            val editor = prefs.edit()
            editor.putInt("resetFlg",0)
            editor.commit()
        }
        if(payDay == nowDay.toInt() && resetFlg == 0) {
            val editor = prefs.edit()
            editor.putInt("resetFlg",1)
            editor.putInt("totalMoney",0)
            editor.commit()
        }

        var hour_speed:Int = getHourSpeed(total)
        var viewer  = topView(applicationContext)

        viewer.setText(Speed(hour_speed))
        setContentView(viewer)

        val oneHundBtn: Button = findViewById(R.id.oneHundBtn)
        oneHundBtn.setOnClickListener {
            viewer.setText(Speed(getHourSpeed(reSave(100))))
        }


        val fiveHundBtn: Button = findViewById(R.id.fiveHundBtn)
        fiveHundBtn.setOnClickListener {
            viewer.setText(Speed(getHourSpeed(reSave(500))))
        }

        val thousandBtn: Button = findViewById(R.id.thousandBtn)
        thousandBtn.setOnClickListener {
            viewer.setText(Speed(getHourSpeed(reSave(1000))))
        }

        val fiveThousandBtn: Button = findViewById(R.id.fiveThousandBtn)
        fiveThousandBtn.setOnClickListener {
            viewer.setText(Speed(getHourSpeed(reSave(5000))))
        }

        val tenThousandBtn: Button = findViewById(R.id.tenThousandBtn)
        tenThousandBtn.setOnClickListener {
            viewer.setText(Speed(getHourSpeed(reSave(10000))))
        }

        val settingActivityBtn:Button = findViewById(R.id.settingActivityBtn)
        settingActivityBtn.setOnClickListener {
            val settingIntent = Intent(this, SettingActivity::class.java)
            startActivity(settingIntent)
        }

    }

    fun reSave (money:Int):Int {
        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        var newTotal:Int = prefs.getInt("totalMoney",0) + money
        val editor = prefs.edit()
        editor.putInt("totalMoney",newTotal)
        editor.commit()
        return newTotal
    }

    fun calHourSpeed(total:Int,miliss:Long): Float {
        var hour:Float = miliss.toFloat() / 3600000
        var speed:Float = total.toFloat() / hour
        return speed
    }

    fun getDiffMiliis(beforeDate:String):Long {
        val nowDate = Calendar.getInstance()
        val calender = Calendar.getInstance()
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(beforeDate)
        calender.setTime(date)
        val diffMiliisTime =  nowDate.getTimeInMillis() - calender.getTimeInMillis()
        return diffMiliisTime
    }

    fun  getHourSpeed(total:Int): Int {
        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        var prefPayDay:Int = prefs.getInt("payDay",1)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        val format = SimpleDateFormat("yyyy-MM-")
        val beforePayDate = format.format(calendar.getTime())+prefPayDay.toString()

        var diffMiliis:Long =getDiffMiliis(beforePayDate)
        var hour:Float = calHourSpeed(total,diffMiliis)
        return Math.round(hour)
    }
}
