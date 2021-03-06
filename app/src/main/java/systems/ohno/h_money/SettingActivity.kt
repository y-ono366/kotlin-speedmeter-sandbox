package systems.ohno.h_money

import android.os.Bundle
import android.app.Activity
import android.widget.Button
import android.content.Intent
import android.text.InputType
import android.view.KeyEvent
import android.widget.EditText

class SettingActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        val payDay= findViewById(R.id.payDay) as EditText
        payDay.setInputType(InputType.TYPE_CLASS_NUMBER)
        val amount= findViewById(R.id.amount) as EditText
        amount.setInputType(InputType.TYPE_CLASS_NUMBER)

        val prefs = getSharedPreferences("HMONEY_FILE", Activity.MODE_PRIVATE)
        var prefAmount:Int = prefs.getInt("amount",0)
        var prefPayDay:Int = prefs.getInt("payDay",1)

        payDay.setText(prefPayDay.toString())
        amount.setText(prefAmount.toString())

        val saveBtn:Button = findViewById(R.id.saveBtn) as Button
        saveBtn.setOnClickListener {
            val day = Integer.parseInt(payDay.text.toString())
            val amount = Integer.parseInt(amount.text.toString())

            val editor = prefs.edit()
            editor.putInt("payDay",day)
            editor.putInt("amount",amount)
            editor.commit()
            val settingIntent = Intent(this, MainActivity::class.java)
            startActivity(settingIntent)
            overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left)
        }

        val resetBtn:Button = findViewById(R.id.resetBtn) as Button
        resetBtn.setOnClickListener {
            val editor = prefs.edit()
            editor.putInt("payDay",0)
            editor.putInt("amount",0)
            editor.putInt("totalMoney",0)
            editor.commit()
            val settingIntent = Intent(this, MainActivity::class.java)
            startActivity(settingIntent)
            overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left)
        }
    }
}
