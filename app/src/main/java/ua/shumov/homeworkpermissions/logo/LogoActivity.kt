package ua.shumov.homeworkpermissions.logo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_logo.*
import ua.shumov.homeworkpermissions.MainActivity
import ua.shumov.homeworkpermissions.R

class LogoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logo)

        ic_logo.startAnimation(
            AnimationUtils.loadAnimation(this,
                R.anim.splash_in
            ))
        Handler().postDelayed({
            ic_logo.startAnimation(
                AnimationUtils.loadAnimation(this,
                    R.anim.splash_out
                ))
            Handler().postDelayed({
                ic_logo.visibility = View.GONE
                startActivity(Intent(this@LogoActivity, MainActivity::class.java))
                finish()
            }, 500)
        }, 1500)
    }
}