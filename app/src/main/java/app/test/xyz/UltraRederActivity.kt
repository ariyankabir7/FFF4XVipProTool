package app.test.xyz

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.card.MaterialCardView
import app.test.xyz.R
import app.test.xyz.databinding.ActivityUltraRederBinding
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd

class UltraRederActivity : AppCompatActivity() , MaxAdListener {
    private lateinit var interstitialAd: MaxInterstitialAd
    lateinit var binding: ActivityUltraRederBinding
    private lateinit var cardViews: Array<CardView>
    private lateinit var selectedCardView: MaterialCardView
    private lateinit var clickitem: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityUltraRederBinding.inflate(layoutInflater)
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

        interstitialAd = MaxInterstitialAd(constant.int_ad, this)
        interstitialAd.setListener(this)
        // Load the first ad
        interstitialAd.loadAd()

        window.statusBarColor = resources.getColor(R.color.app_color)
        binding.back.setOnClickListener { finish() }
        // Initialize the array of CardViews
        cardViews = arrayOf(
            findViewById(R.id.mcv1),
            findViewById(R.id.mcv2),
            findViewById(R.id.mcv3),
            findViewById(R.id.mcv4),
            findViewById(R.id.mcv5),
            findViewById(R.id.mcv6)
        )

        // Set OnClickListener for each CardView
        for (i in cardViews.indices) {
            cardViews[i].setOnClickListener { onCardViewClicked(cardViews[i]) }
        }
        binding.title.text=intent.getStringExtra("title")

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

    private fun onCardViewClicked(clickedCardView: CardView) {
        // Set the background color of previously selected card view to black
        if (::selectedCardView.isInitialized) {
            selectedCardView.setCardBackgroundColor(Color.BLACK)
        }

        // Set the background color of the clicked card view to green
        clickedCardView.setCardBackgroundColor(Color.GREEN)
        clickitem = clickedCardView

        // Update the selected card view
        selectedCardView = clickedCardView as MaterialCardView
    }

    override fun onAdLoaded(p0: MaxAd) {
       interstitialAd.loadAd()
    }

    override fun onAdDisplayed(p0: MaxAd) {

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
            if (clickitem == findViewById(R.id.mcv1)) {
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
            } else if (clickitem == findViewById(R.id.mcv2)) {
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
            } else if (clickitem == findViewById(R.id.mcv3)) {
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
            } else if (clickitem == findViewById(R.id.mcv4)) {
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

    }

    override fun onAdLoadFailed(p0: String, p1: MaxError) {
       interstitialAd.loadAd()
    }

    override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
       interstitialAd.loadAd()
    }
}