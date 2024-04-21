package app.test.xyz

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import app.test.xyz.databinding.ActivityHomeBinding
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.applovin.mediation.MaxAd
import com.applovin.mediation.MaxAdListener
import com.applovin.mediation.MaxError
import com.applovin.mediation.ads.MaxInterstitialAd
import com.applovin.mediation.nativeAds.MaxNativeAdListener
import com.applovin.mediation.nativeAds.MaxNativeAdLoader
import com.applovin.mediation.nativeAds.MaxNativeAdView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import org.json.JSONArray
import org.json.JSONException

class HomeActivity : AppCompatActivity(), MaxAdListener {
    var activityC = "SENSI"
    private lateinit var interstitialAd: MaxInterstitialAd
    private var retryAttempt = 0.0
    lateinit var binding: ActivityHomeBinding
    private var VERSION = 2
    private val URL = "https://get-data.in/testctrl/get_settings.php?package=app.test.xyz"
    private lateinit var nativeAdLoader: MaxNativeAdLoader
    private var nativeAd: MaxAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
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
        loadNativeAd()


        window.statusBarColor = resources.getColor(R.color.app_color)

        binding.menu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        checkVersion()

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.shared -> {

                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    val appLink = "https://play.google.com/store/apps/details?id=$packageName"
                    val shareMessage =
                        "This is H4X Sensi Tool App :\n$appLink"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                    startActivity(Intent.createChooser(shareIntent, "Share app via"))
                    true
                }

                R.id.rate -> {

                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                        )
                    )
                    true
                }

                R.id.privacy -> {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.google.com")
                        )
                    )
                    true
                }

                R.id.exit -> {
                    showPpopupDialog()
                    true
                }

                else -> {
                    false
                }
            }
        }
        binding.ivRateus.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
        binding.ivShareus.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            val appLink = "https://play.google.com/store/apps/details?id=$packageName"
            val shareMessage =
                "This is H4X Sensi Tool App :\n$appLink"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
            startActivity(Intent.createChooser(shareIntent, "Share app via"))
        }
        binding.ivPrivacy.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.google.com")
                )
            )
        }

        binding.ivVortex.setOnClickListener {
            var activityC = "Vortex"
            handleImageViewClick()
        }
        binding.ivSpeedSpike.setOnClickListener {
            activityC="SpeedSpike"
            handleImageViewClick()
        }
        binding.ivSensiRush.setOnClickListener {
            activityC="SensiRushActivity"
            handleImageViewClick()
        }
        binding.ivLockMaster.setOnClickListener {
            activityC="LockMasterActivity"
            handleImageViewClick()
        }
        binding.ivUltraReder.setOnClickListener {

            activityC="UltraRederActivity"
            handleImageViewClick()
        }
        binding.ivHeadshot.setOnClickListener {

            activityC="HeadShotActivity"
            handleImageViewClick()
        }
        binding.ivSightCraft.setOnClickListener {
            activityC="SightCraftActivity"
            handleImageViewClick()
        }
    }


    private fun showPpopupDialog() {
        AlertDialog.Builder(this, R.style.TransparentDialogTheme).setView(R.layout.back_popup)
            .setCancelable(true).create().apply {
                show()

                findViewById<MaterialButton>(R.id.buttonCancel)?.setOnClickListener {
                    dismiss()
                }
                findViewById<MaterialButton>(R.id.buttonConfirm)?.setOnClickListener {
                    dismiss()
                    super.onBackPressed()
                    finish()
                }
            }

    }

    private fun checkVersion() {
        val requestQueue = Volley.newRequestQueue(this)
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, URL, null,
            { response ->
                parseResponse(response)
            },
            { error ->
                handleErrorResponse(error)
            })

        requestQueue.add(jsonArrayRequest)
    }

    private fun parseResponse(response: JSONArray) {
        try {
            if (response.length() > 0) {
                val jsonObject = response.getJSONObject(0)
                val version = jsonObject.getString("version").toInt()
                val open_url = jsonObject.getString("updurl")
                val actrl = jsonObject.getString("actrl")
                val aimg = jsonObject.getString("aimg")
                val aurl = jsonObject.getString("aurl")

                val bctrl = jsonObject.getString("bctrl")
                val bimg = jsonObject.getString("bimg")
                val burl = jsonObject.getString("burl")

                if (version > VERSION) {
                    showPopupDialog1(open_url)
                } else {

                    val sharedPreferences = getSharedPreferences("TinyDB", MODE_PRIVATE)

                    val editor = sharedPreferences.edit()
                    editor.putString("actrl", actrl)
                    editor.putString("aimg", aimg)
                    editor.putString("aurl", aurl)
                    editor.putString("bctrl", bctrl)
                    editor.putString("bimg", bimg)
                    editor.putString("burl", burl)
                    editor.apply()
                    if (bctrl != "0") {
                        showAppOpenAd(bctrl, bimg, burl)
                    }
                    if (actrl != "0") {
                        showBannerAd(actrl, aimg, aurl)
                    }

                }
            }
        } catch (_: JSONException) {

        }
    }

    private fun showAppOpenAd(bctrl: String, bimg: String, burl: String) {
        AlertDialog.Builder(this, R.style.TransparentDialogTheme).setView(R.layout.ads_open_popup)
            .setCancelable(false).create().apply {
                show()

                findViewById<MaterialCardView>(R.id.materialCardView)?.setOnClickListener {
                    dismiss()
                }
                Glide.with(findViewById<ImageView>(R.id.ad)!!.context)
                    .load(Uri.parse(bimg))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .fitCenter()
                    .into(findViewById<ImageView>(R.id.ad)!!)


                findViewById<MaterialCardView>(R.id.cv_ad)?.setOnClickListener {
                    if (bctrl == "1") {
                        val builder = CustomTabsIntent.Builder()
                        val customTabsIntent = builder.build()
                        customTabsIntent.launchUrl(this@HomeActivity, Uri.parse(burl))

                    } else if (bctrl == "2") {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(burl))
                        startActivity(intent)
                    }
                }
            }
    }

    private fun showBannerAd(actrl: String, aimg: String, aurl: String) {
        binding.nativeads.visibility = View.VISIBLE
        Glide.with(this)
            .load(Uri.parse(aimg))
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .fitCenter()
            .into(binding.bannerAd)
        binding.bannerAd.setOnClickListener {

            if (actrl == "1") {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(this, Uri.parse(aurl))

            } else if (actrl == "2") {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(aurl))
                startActivity(intent)
            }
        }
    }

    private fun showPopupDialog1(open_url: String) {

        AlertDialog.Builder(
            this, R.style.updateDialogTheme
        ).setView(R.layout.update_popup)
            .setCancelable(false).create().apply {
                show()

                findViewById<MaterialCardView>(R.id.okaybtn)?.setOnClickListener {
                    openPlayStore(open_url)
                }

            }
    }

    private fun openPlayStore(open_url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(open_url)
        startActivity(intent)
    }

    private fun handleErrorResponse(error: VolleyError) {
        Log.e("Volley Error", "Error fetching data from server", error)
    }

    override fun onBackPressed() {
        showPpopupDialog()

    }

    fun handleImageViewClick() {
        if (interstitialAd.isReady) {
            interstitialAd.showAd()
        } else {
            // If ad is not ready, start activity directly
            Toast.makeText(this, "Try Again !", Toast.LENGTH_SHORT).show()
            interstitialAd.loadAd()
        }

    }

    private fun startNewActivity(activityClass: Class<*>) {

    }

    override fun onAdLoaded(p0: MaxAd) {
        interstitialAd.isReady
    }

    override fun onAdDisplayed(p0: MaxAd) {

    }

    override fun onAdHidden(p0: MaxAd) {
        if (activityC == "SensiRushActivity"){
            val intent = Intent(this@HomeActivity, SensiRushActivity::class.java)
            startActivity(intent)
        }else   if (activityC == "Vortex"){
            val intent = Intent(this@HomeActivity, VortexActivity::class.java)
            startActivity(intent)
        }else   if (activityC == "UltraRederActivity"){
            val intent = Intent(this@HomeActivity, UltraRederActivity::class.java)
            startActivity(intent)
        }else   if (activityC == "HeadShotActivity"){
            val intent = Intent(this@HomeActivity, HeadShotActivity::class.java)
            startActivity(intent)
        }else if (activityC == "SightCraftActivity"){
            val intent = Intent(this@HomeActivity, SightCraftActivity::class.java)
            startActivity(intent)
        }else   if (activityC == "LockMasterActivity"){
            val intent = Intent(this@HomeActivity, LockMasterActivity::class.java)
            startActivity(intent)
        }else   if (activityC == "SpeedSpike"){
            val intent = Intent(this@HomeActivity,RushCraftSpikeActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onAdClicked(p0: MaxAd) {

    }

    override fun onAdLoadFailed(p0: String, p1: MaxError) {
        interstitialAd.loadAd()
    }

    override fun onAdDisplayFailed(p0: MaxAd, p1: MaxError) {
        interstitialAd.loadAd()
    }

    private fun loadNativeAd() {
        val nativeAdContainer = findViewById<FrameLayout>(R.id.native_ad_layout)
        nativeAdLoader = MaxNativeAdLoader(constant.nat_id, this)
        nativeAdLoader.setNativeAdListener(object : MaxNativeAdListener() {
            override fun onNativeAdLoaded(nativeAdView: MaxNativeAdView?, ad: MaxAd) {
                if (nativeAd != null) {
                    nativeAdLoader.destroy(nativeAd)
                }
                nativeAd = ad
                nativeAdContainer.removeAllViews()
                nativeAdContainer.addView(nativeAdView)
            }

            override fun onNativeAdLoadFailed(adUnitId: String, error: MaxError) {
                // Handle ad loading failure
            }

            override fun onNativeAdClicked(ad: MaxAd) {
                // Handle ad click
            }
        })
        nativeAdLoader.loadAd()
    }
}