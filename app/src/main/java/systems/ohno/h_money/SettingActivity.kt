package systems.ohno.h_money

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.content.Intent

import kotlinx.android.synthetic.main.activity_setting.*
import org.w3c.dom.Text
import android.text.InputType
import android.widget.EditText

class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setContentView(R.layout.activity_setting)

        val payDay= findViewById(R.id.payDay) as EditText
        payDay.setInputType(InputType.TYPE_CLASS_NUMBER)
        val amount= findViewById(R.id.amount) as EditText
        amount.setInputType(InputType.TYPE_CLASS_NUMBER)

        val prefs = getSharedPreferences("HMONEY_FILE", AppCompatActivity.MODE_PRIVATE)
        var prefAmount = prefs.getInt("amount",0)
        var prefPayDay = prefs.getInt("payDay",0)

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
        }

    }

}
