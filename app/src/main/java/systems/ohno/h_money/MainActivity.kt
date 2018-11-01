package systems.ohno.h_money

import android.content.Intent
import android.app.Activity
import android.os.Bundle
import systems.ohno.h_money.model.Speed
import java.util.Calendar
import java.text.SimpleDateFormat
import android.widget.Button
import android.widget.ImageButton
import android.view.KeyEvent

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super<Activity>.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        val resetFlg:Int = prefs.getInt("resetFlg",0)
        val total:Int = prefs.getInt("totalMoney",0)
        val payDay:Int = prefs.getInt("payDay",1)
        var amount:Int = prefs.getInt("amount",0)

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
        val average:Int = getAverageSpeed(amount)

        val hour_speed:Float = getHourSpeed(total)
        val viewer  = topView(applicationContext)

        val remaingAmount:Int = getRemainingAmount(total)

        viewer.setText(Speed(hour_speed,remaingAmount,average))
        setContentView(viewer)

        val oneHundBtn: Button = findViewById(R.id.oneHundBtn)
        oneHundBtn.setOnClickListener {
            val total:Int = reSave(100)
            viewer.setText(Speed(getHourSpeed(total),getRemainingAmount(total),average))
        }


        val fiveHundBtn: Button = findViewById(R.id.fiveHundBtn)
        fiveHundBtn.setOnClickListener {
            val total:Int = reSave(500)
            viewer.setText(Speed(getHourSpeed(total),getRemainingAmount(total),average))
        }

        val thousandBtn: Button = findViewById(R.id.thousandBtn)
        thousandBtn.setOnClickListener {
            val total:Int = reSave(1000)
            viewer.setText(Speed(getHourSpeed(total),getRemainingAmount(total),average))
        }

        val fiveThousandBtn: Button = findViewById(R.id.fiveThousandBtn)
        fiveThousandBtn.setOnClickListener {
            val total:Int = reSave(5000)
            viewer.setText(Speed(getHourSpeed(total),getRemainingAmount(total),average))
        }

        val tenThousandBtn: Button = findViewById(R.id.tenThousandBtn)
        tenThousandBtn.setOnClickListener {
            val total:Int = reSave(10000)
            viewer.setText(Speed(getHourSpeed(total),getRemainingAmount(total),average))
        }

        val settingActivityBtn:ImageButton = findViewById(R.id.settingActivityBtn)
        settingActivityBtn.setOnClickListener {
            val settingIntent = Intent(this, SettingActivity::class.java)
            startActivity(settingIntent)
            overridePendingTransition(R.anim.in_right, R.anim.out_left)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack (true)
        }
        return false
    }

    fun reSave (money:Int):Int {
        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        val newTotal:Int = prefs.getInt("totalMoney",0) + money
        val editor = prefs.edit()
        editor.putInt("totalMoney",newTotal)
        editor.commit()
        return newTotal
    }

    /* 総利用額と差分から1hでの利用額を算出 */
    fun calHourSpeed(total:Int,miliss:Long): Float {
        val hour:Float = miliss.toFloat() / 3600000
        val speed:Float = total.toFloat() / hour
        return speed
    }

    /* 1hの速度を取得 */
    fun  getHourSpeed(total:Int): Float {
        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        val prefPayDay:Int = prefs.getInt("payDay",1)

        val nowDateCalendar = getNowDate()
        val beforeDateCalendar = getBeforeDate(prefPayDay)
        val diffMiliis:Long =getDiffMiliisByDate(nowDateCalendar,beforeDateCalendar)
        val hour:Float = calHourSpeed(total,diffMiliis)
        return hour
    }

    fun getAverageSpeed(amount:Int):Int {
        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        val prefPayDay:Int = prefs.getInt("payDay",1)
        val now = getNowDate()
        val before = getBeforeDate(prefPayDay)
        val diffMiliis = getDiffMiliisByDate(now,before)
        val speed:Float =  calHourSpeed(amount,diffMiliis)
        return Math.round(speed)
    }

    /*今日 - 先月の支払い日の差分を取得*/
    fun getDiffMiliisByDate(nowDate:String,beforeDate:String):Long {
        val nowCalender = Calendar.getInstance()
        val beforeCalender = Calendar.getInstance()

        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val parseNowDate = format.parse(nowDate)
        nowCalender.setTime(parseNowDate)

        val parseBeforeDate = format.parse(beforeDate)
        beforeCalender.setTime(parseBeforeDate)
        val diffMiliisTime =  nowCalender.getTimeInMillis() - beforeCalender.getTimeInMillis()
        return diffMiliisTime
    }

    /* 残りの金額を取得*/
    fun getRemainingAmount(total:Int):Int {
        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        val amount:Int = prefs.getInt("amount",0)
        return amount - total
    }

    /* 現在の日付をStringで取得 */
    fun getNowDate():String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val calendar = Calendar.getInstance()
        return format.format(calendar.getTime())
    }

    /* 先月支払日取得 */
    fun getBeforeDate(payDay:Int):String {
        val format = SimpleDateFormat("yyyy-MM-")
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        return format.format(calendar.getTime())+payDay.toString()+" 00:00:00"
    }
  }
