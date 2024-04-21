package app.test.xyz

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.test.xyz.R
import app.test.xyz.databinding.ActivityLockMasterBinding
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd

class LockMasterActivity : AppCompatActivity(), MaxAdListener {
    private lateinit var interstitialAd: MaxInterstitialAd
    var g = 0
    var r = 0
    var f = 0
    var m = 0
    var n = 0

    lateinit var binding: ActivityLockMasterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLockMasterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val insetsController = ViewCompat.getWindowInsetsController(v)
            insetsController?.isAppearanceLightStatusBars = false
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = resources.getColor(R.color.app_color)

        binding.back.setOnClickListener {
            finish()
        }
        binding.title.text=intent.getStringExtra("title")

        interstitialAd = MaxInterstitialAd(constant.int_ad, this)
        interstitialAd.setListener(this)
        // Load the first ad
        interstitialAd.loadAd()




        binding.p1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                g = progress // Store the updated progress value in g
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.p2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                r = progress // Store the updated progress value in r
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        binding.p3.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                f = progress // Store the updated progress value in f
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.p4.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                m = progress // Store the updated progress value in f
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        binding.p5.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                n = progress // Store the updated progress value in f
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })



        binding.checkbtn.setOnClickListener {



            if (interstitialAd.isReady) {
                interstitialAd.showAd()
            } else {
                // If ad is not ready, start activity directly
                Toast.makeText(this, "Try Again !", Toast.LENGTH_SHORT).show()
                interstitialAd.loadAd()
            }



        }

    }
    private fun setSwitchTintColor(switchButton: Switch) {
        switchButton.setOnCheckedChangeListener { buttonView, isChecked ->
            // Change the color when the switch is toggled
            if (isChecked) {
                switchButton.thumbDrawable.setTint(getResources().getColor(R.color.light_green))
            } else {
                switchButton.thumbDrawable.setTint(getResources().getColor(R.color.off_switch))
            }
        }
    }

    override fun onAdLoaded(p0: MaxAd) {
       interstitialAd.isReady
    }

    override fun onAdDisplayed(p0: MaxAd) {
        TODO("Not yet implemented")
    }

    override fun onAdHidden(p0: MaxAd) {
        // Inflate custom layout for AlertDialog
        val customLayout = LayoutInflater.from(this)
            .inflate(R.layout.custom_alert_layout, null)
        val progressBar: ProgressBar =
            customLayout.findViewById(R.id.progressBar)
        val textView: TextView = customLayout.findViewById(R.id.textView)

        // Create AlertDialog with custom layout
        val builder = AlertDialog.Builder(this)
        builder.setView(customLayout)
        builder.setCancelable(false) // Prevent dismissing AlertDialog by tapping outside or pressing back button

        // Show AlertDialog
        val alertDialog = builder.create()
        alertDialog.show()

        // Simulating some background task delay
        Handler().postDelayed({
            // Dismiss AlertDialog
            alertDialog.dismiss()

            // Show toast indicating successful operation completion
            //Toast.makeText(this, "Settings Apply successfully!", Toast.LENGTH_SHORT).show()
            //finish()
            if (g <= 60) {
                val customLayout = LayoutInflater.from(this)
                    .inflate(R.layout.bad_popup, null)

                // Create AlertDialog with custom layout
                val builder = AlertDialog.Builder(this, R.style.TransparentDialogTheme)
                builder.setView(customLayout)
                builder.setCancelable(true) // Prevent dismissing AlertDialog by tapping outside or pressing back button

                val popupAlert = builder.create()
                popupAlert.show()

                val okaybtn = customLayout.findViewById<LinearLayout>(R.id.okbtn)
                okaybtn.setOnClickListener {
                    popupAlert.dismiss()
                }
            } else if (r <= 70) {
                val customLayout = LayoutInflater.from(this)
                    .inflate(R.layout.good_popup, null)

                // Create AlertDialog with custom layout
                val builder = AlertDialog.Builder(this, R.style.TransparentDialogTheme)
                builder.setView(customLayout)
                builder.setCancelable(true) // Prevent dismissing AlertDialog by tapping outside or pressing back button

                val popupAlert = builder.create()
                popupAlert.show()

                val okaybtn = customLayout.findViewById<LinearLayout>(R.id.okbtn)
                okaybtn.setOnClickListener {
                    popupAlert.dismiss()
                }
            } else if (f <= 80) {
                val customLayout = LayoutInflater.from(this)
                    .inflate(R.layout.nice_popup, null)

                // Create AlertDialog with custom layout
                val builder = AlertDialog.Builder(this, R.style.TransparentDialogTheme)
                builder.setView(customLayout)
                builder.setCancelable(true) // Prevent dismissing AlertDialog by tapping outside or pressing back button

                val popupAlert = builder.create()
                popupAlert.show()

                val okaybtn = customLayout.findViewById<LinearLayout>(R.id.okbtn)
                okaybtn.setOnClickListener {
                    popupAlert.dismiss()
                }
            } else if (m <= 85) {
                val customLayout = LayoutInflater.from(this)
                    .inflate(R.layout.best_popup, null)

                // Create AlertDialog with custom layout
                val builder = AlertDialog.Builder(this, R.style.TransparentDialogTheme)
                builder.setView(customLayout)
                builder.setCancelable(true) // Prevent dismissing AlertDialog by tapping outside or pressing back button

                val popupAlert = builder.create()
                popupAlert.show()

                val okaybtn = customLayout.findViewById<LinearLayout>(R.id.okbtn)
                okaybtn.setOnClickListener {
                    popupAlert.dismiss()
                }
            } else if (n <= 90) {
                val customLayout = LayoutInflater.from(this)
                    .inflate(R.layout.best_popup, null)

                // Create AlertDialog with custom layout
                val builder = AlertDialog.Builder(this, R.style.TransparentDialogTheme)
                builder.setView(customLayout)
                builder.setCancelable(true) // Prevent dismissing AlertDialog by tapping outside or pressing back button

                val popupAlert = builder.create()
                popupAlert.show()

                val okaybtn = customLayout.findViewById<LinearLayout>(R.id.okbtn)
                okaybtn.setOnClickListener {
                    popupAlert.dismiss()
                }
            } else {
                val customLayout = LayoutInflater.from(this)
                    .inflate(R.layout.awesome_popup, null)


                // Create AlertDialog with custom layout
                val builder = AlertDialog.Builder(this, R.style.TransparentDialogTheme)
                builder.setView(customLayout)
                builder.setCancelable(true)
                val popupAlert = builder.create()
                popupAlert.show()

                val okaybtn = customLayout.findViewById<LinearLayout>(R.id.okbtn)
                okaybtn.setOnClickListener {
                    popupAlert.dismiss()
                }
            }
        }, 2000) // Delay of 5 seconds
    }

    override fun onAdClicked(p0: MaxAd) {
        TODO("Not yet implemented")
    }

    override fun onAdLoadFailed(p0: String, p1: MaxError) {
       interstitialAd.loadAd()
    }

    override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
        interstitialAd.loadAd()
    }
}
