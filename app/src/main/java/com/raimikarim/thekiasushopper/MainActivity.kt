package com.raimikarim.thekiasushopper

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Html
import android.view.MenuItem
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val showHideKeyboard = OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) showKeyboard() else hideKeyboard(v)
        }
        item_a_price.onFocusChangeListener = showHideKeyboard
        item_b_price.onFocusChangeListener = showHideKeyboard
        item_a_qty.onFocusChangeListener = showHideKeyboard
        item_b_qty.onFocusChangeListener = showHideKeyboard

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        chatBox.text = Html.fromHtml(getText(R.string.auntie_intro).toString())
    }

    fun reset(v: View?) {
        removeCheckmarks()
        removeText()
        chatBox.text = Html.fromHtml(getText(R.string.auntie_intro).toString())
    }

    private fun removeCheckmarks() {
        item_a_positive.visibility = View.GONE
        item_b_positive.visibility = View.GONE
        item_a_negative.visibility = View.GONE
        item_b_negative.visibility = View.GONE
    }

    private fun removeText() {
        item_a_price.setText("")
        item_b_price.setText("")
        item_a_qty.setText("")
        item_b_qty.setText("")
    }

    fun evaluate(v: View?) {

        // 0. Remove
        removeCheckmarks()

        // 1. Parse EditText to String to Double?
        val priceA: Double? = item_a_price.text.toString().toDouble()
        val priceB: Double? = item_b_price.text.toString().toDouble()
        val qtyA: Double? = item_a_qty.text.toString().toDouble()
        val qtyB: Double? = item_b_qty.text.toString().toDouble()
        val nums: List<Double?> = listOf(priceA, priceB, qtyA, qtyB)

        // 2. Not a Double
        for (num in nums)
            if (num == null) {
                chatBox!!.text = Html.fromHtml(
                        getText(R.string.auntie_no_input).toString())
                return
            }

        // 3. Division by zero
        if (nums[2]!! == 0.0 || nums[3]!! == 0.0) {
            chatBox!!.text = Html.fromHtml(
                    getText(R.string.auntie_division_by_zero).toString())
            return
        }

        // 4. Evaluate prices
        val (rateA, rateB, rateAHigher) = evaluatePrices(priceA!!, qtyA!!, priceB!!, qtyB!!)
        when (rateAHigher) {
            1 -> {
                item_b_positive!!.visibility = View.VISIBLE
                item_a_negative!!.visibility = View.VISIBLE
                chatBox!!.text = Html.fromHtml("<b>Auntie: " +
                        "<font color=\"#379237\">Item B</font></b> more worth it." +
                        "<br>A: $" + rateA + "/unit. " +
                        "<br>B: $" + rateB + "/unit.")
            }
            0 -> {
                chatBox!!.text = Html.fromHtml(
                        getText(R.string.auntie_no_diff).toString())
            }
            else -> {
                item_a_positive!!.visibility = View.VISIBLE
                item_b_negative!!.visibility = View.VISIBLE
                chatBox!!.text = Html.fromHtml("<b>Auntie: " +
                        "<font color=\"#379237\">Item A</font></b> more worth it." +
                        "<br>A: $" + rateA + "/unit. " +
                        "<br>B: $" + rateB + "/unit.")
            }
        }
    }


    private fun showKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val appPackageName = packageName
        val id = item.itemId
        when (id) {
            R.id.about -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
            }
            R.id.how_to_use -> {
                val intent = Intent(this, HowToUseActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_share -> {
                val uri = "https://play.google.com/store/apps/details?id=$appPackageName"
                val intent = Intent()
                intent.setAction(Intent.ACTION_SEND).putExtra(Intent.EXTRA_TEXT, uri).type = "text/plain"
                startActivity(intent)
            }
            R.id.nav_rate -> {
                val intent = Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=com.raimikarim.thekiasushopper"))
                startActivity(intent)
            }
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}