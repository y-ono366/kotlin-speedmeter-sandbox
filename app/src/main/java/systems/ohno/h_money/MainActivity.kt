package systems.ohno.h_money

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import systems.ohno.h_money.model.Speed
import java.util.Calendar
import java.text.SimpleDateFormat
import android.widget.Button
import android.widget.ImageButton

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        val resetFlg:Int = prefs.getInt("resetFlg",0)
        val total:Int = prefs.getInt("totalMoney",0)
        val payDay:Int = prefs.getInt("payDay",1)

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

        val hour_speed:Int = getHourSpeed(total)
        val viewer  = topView(applicationContext)

        val remaingAmount:Int = getRemainingAmount(total)

        viewer.setText(Speed(hour_speed,remaingAmount))
        setContentView(viewer)

        val oneHundBtn: Button = findViewById(R.id.oneHundBtn)
        oneHundBtn.setOnClickListener {
            val total:Int = reSave(100)
            viewer.setText(Speed(getHourSpeed(total),getRemainingAmount(total)))
        }


        val fiveHundBtn: Button = findViewById(R.id.fiveHundBtn)
        fiveHundBtn.setOnClickListener {
            val total:Int = reSave(500)
            viewer.setText(Speed(getHourSpeed(total),getRemainingAmount(total)))
        }

        val thousandBtn: Button = findViewById(R.id.thousandBtn)
        thousandBtn.setOnClickListener {
            val total:Int = reSave(1000)
            viewer.setText(Speed(getHourSpeed(total),getRemainingAmount(total)))
        }

        val fiveThousandBtn: Button = findViewById(R.id.fiveThousandBtn)
        fiveThousandBtn.setOnClickListener {
            val total:Int = reSave(5000)
            viewer.setText(Speed(getHourSpeed(total),getRemainingAmount(total)))
        }

        val tenThousandBtn: Button = findViewById(R.id.tenThousandBtn)
        tenThousandBtn.setOnClickListener {
            val total:Int = reSave(10000)
            viewer.setText(Speed(getHourSpeed(total),getRemainingAmount(total)))
        }

        val settingActivityBtn:ImageButton = findViewById(R.id.settingActivityBtn)
        settingActivityBtn.setOnClickListener {
            val settingIntent = Intent(this, SettingActivity::class.java)
            startActivity(settingIntent)
        }

    }

    fun reSave (money:Int):Int {
        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        val newTotal:Int = prefs.getInt("totalMoney",0) + money
        val editor = prefs.edit()
        editor.putInt("totalMoney",newTotal)
        editor.commit()
        return newTotal
    }

    fun calHourSpeed(total:Int,miliss:Long): Float {
        val hour:Float = miliss.toFloat() / 3600000
        val speed:Float = total.toFloat() / hour
        return speed
    }

    fun  getHourSpeed(total:Int): Int {
        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        val prefPayDay:Int = prefs.getInt("payDay",1)

        val nowDateCalendar = getNowDate(prefPayDay)
        val beforeDateCalendar = getBeforeDate(prefPayDay)
        val diffMiliis:Long =getDiffMiliisByDate(nowDateCalendar,beforeDateCalendar)
        val hour:Float = calHourSpeed(total,diffMiliis)
        return Math.round(hour)
    }

    fun getAverageSpeed(total:Int):Int {
        val now:String = "2018-08-10"
        val before:String = "2018-07-10"
        val diffMiliis = getDiffMiliisByDate(now,before)
        val speed:Float =  calHourSpeed(total,diffMiliis)
        return Math.round(speed)
    }

    fun getDiffMiliisByDate(nowDate:String,beforeDate:String):Long {
        val nowCalender = Calendar.getInstance()
        val beforeCalender = Calendar.getInstance()

        val format = SimpleDateFormat("yyyy-MM-dd")
        val parseNowDate = format.parse(nowDate)
        nowCalender.setTime(parseNowDate)

        val parseBeforeDate = format.parse(beforeDate)
        beforeCalender.setTime(parseBeforeDate)
        val diffMiliisTime =  nowCalender.getTimeInMillis() - beforeCalender.getTimeInMillis()
        return diffMiliisTime
    }

    fun getRemainingAmount(total:Int):Int {
        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        val amount:Int = prefs.getInt("amount",0)
        return amount - total
    }

    fun getNowDate(payDay:Int):String {
        val format = SimpleDateFormat("yyyy-MM-")
        val calendar = Calendar.getInstance()
        return format.format(calendar.getTime())+payDay.toString()
    }

    fun getBeforeDate(payDay:Int):String {
        val format = SimpleDateFormat("yyyy-MM-")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        return format.format(calendar.getTime())+payDay.toString()
    }
  }
