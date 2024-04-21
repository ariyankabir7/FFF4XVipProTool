package app.test.xyz

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.test.xyz.R
import app.test.xyz.databinding.ActivitySightCraftBinding
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd

class SightCraftActivity : AppCompatActivity(), MaxAdListener {
    private lateinit var interstitialAd: MaxInterstitialAd
    lateinit var binding: ActivitySightCraftBinding
    var s = ""
    var r = ""
    var f = ""
    var c = ""
    var sh = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySightCraftBinding.inflate(layoutInflater)
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

        binding.back.setOnClickListener { finish() }

        interstitialAd = MaxInterstitialAd(constant.int_ad, this)
        interstitialAd.setListener(this)
        // Load the first ad
        interstitialAd.loadAd()



        val resolution =
            listOf("960x540", "1024x576", "1280(HD)", "1366x768", "1440(HD+)", "1600x900")
        val fps = listOf("30FPS", "60FPS", "90FPS", "120FPS", "150FPS", "180FPS")
        val smooth = listOf("Normal", "High", "Ultra")
        val cpu = listOf("Disable", "Enable")
        val shadow = listOf("Disable", "Enable")


        binding.spinner1.setSelection(0) // Select the first item by default

// Define an adapter for the spinner
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.resolution_array,
            R.layout.spinner_dropdown_item
        )
        // Specify the layout to use when the list of choices appearss
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set the adapter to the spinner
        binding.spinner1.adapter = adapter
// Set the selection based on the index of the item in the graphics list
        binding.spinner1.setSelection(0)
        binding.spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                r = resolution[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle no selection if needed
            }
        }
        binding.spinner2.setSelection(0) // Select the first item by default


        val adapter1 = ArrayAdapter.createFromResource(
            this,
            R.array.smooth_array,
            R.layout.spinner_dropdown_item
        )
        // Specify the layout to use when the list of choices appearss
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set the adapter to the spinner
        binding.spinner2.adapter = adapter1


// Set the selection based on the index of the item in the graphics list
        binding.spinner2.setSelection(0)

        binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sh = smooth[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle no selection if needed
            }
        }
        binding.spinner3.setSelection(0) // Select the first item by default

// Define an adapter for the spinner

        val adapter3 = ArrayAdapter.createFromResource(
            this,
            R.array.quality_array,
            R.layout.spinner_dropdown_item
        )
        // Specify the layout to use when the list of choices appearss
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set the adapter to the spinner
        binding.spinner3.adapter = adapter3
// Set the selection based on the index of the item in the graphics list
        binding.spinner3.setSelection(0)
        binding.spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                c = cpu[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle no selection if needed
            }
        }
        binding.spinner3.setSelection(0) // Select the first item by default

// Define an adapter for the spinner

        val adapter4 =
            ArrayAdapter.createFromResource(this, R.array.fps_array, R.layout.spinner_dropdown_item)
        // Specify the layout to use when the list of choices appearss
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set the adapter to the spinner
        binding.spinner4.adapter = adapter4
// Set the selection based on the index of the item in the graphics list
        binding.spinner4.setSelection(0)
        binding.spinner4.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                c = fps[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle no selection if needed
            }
        }
        binding.spinner4.setSelection(0) // Select the first item by default

        binding.checkbtn.setOnClickListener {

            if (interstitialAd.isReady) {
                interstitialAd.showAd()
            } else {
                // If ad is not ready, start activity directly
                Toast.makeText(this, "Please Wait !", Toast.LENGTH_SHORT).show()
                interstitialAd.loadAd()
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
        if (interstitialAd.isReady) {
            interstitialAd.showAd()
        } else {
            // If ad is not ready, start activity directly
            Toast.makeText(this, "Try Again !", Toast.LENGTH_SHORT).show()
            interstitialAd.loadAd()
        }
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