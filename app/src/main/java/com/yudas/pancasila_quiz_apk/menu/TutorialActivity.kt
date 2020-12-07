package com.yudas.pancasila_quiz_apk.menu

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

import com.yudas.pancasila_quiz_apk.MainActivity
import com.yudas.pancasila_quiz_apk.R
import kotlinx.android.synthetic.main.activity_welcome.*


class TutorialActivity : AppCompatActivity() {
    private val mContext = this@TutorialActivity

    private var myViewPagerAdapter: MyViewPagerAdapter? = null
    private var dots: Array<TextView?>? = null
    private var layouts: IntArray? = null


    //  viewpager change listener
    internal var viewPagerPageChangeListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageSelected(position: Int) {
            addBottomDots(position)
            layouts?.let { println("asulay"+it.size) }
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts!!.size - 1) {
                // last page. make button text to GOT IT
                btn_start!!.visibility = View.VISIBLE
                btn_skip!!.visibility = View.GONE

            }

            else {
                // still pages are left
                btn_start!!.visibility = View.GONE
                btn_skip!!.visibility = View.VISIBLE
            }
        }

        override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {

        }

        override fun onPageScrollStateChanged(arg0: Int) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tutorial)

        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = intArrayOf(R.layout.tutor_slide1, R.layout.tutor_slide2, R.layout.tutor_slide3,R.layout.tutor_slide4
        ,R.layout.tutor_slide5,R.layout.tutor_slide6,R.layout.tutor_slide7,R.layout.tutor_slide8)

        // adding bottom dots
        addBottomDots(0)

        // making notification bar transparent
        changeStatusBarColor()

        myViewPagerAdapter = MyViewPagerAdapter()
        view_pager!!.adapter = myViewPagerAdapter
        view_pager!!.addOnPageChangeListener(viewPagerPageChangeListener)

        btn_skip!!.setOnClickListener { HomeScreen() }
        btn_start!!.setOnClickListener { HomeScreen() }



    }

    private fun HomeScreen(){
        val intent = Intent(this@TutorialActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun addBottomDots(currentPage: Int) {
        dots = arrayOfNulls(layouts!!.size)

        val colorsActive = resources.getIntArray(R.array.array_dot_active)
        val colorsInactive = resources.getIntArray(R.array.array_dot_inactive)

        layoutDots!!.removeAllViews()
        for (i in dots!!.indices) {
            dots!![i] = TextView(this)
            dots!![i]?.textSize  = 35f
            dots!![i]?.setText(Html.fromHtml("&#8226;"))
            dots!![i]?.setTextColor(colorsInactive[currentPage])
            layoutDots!!.addView(dots!![i])
        }

        if (dots!!.size > 0)
            dots!![currentPage]?.setTextColor(colorsActive[currentPage])
    }



    /**
     * Making notification bar transparent
     */
    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    /**
     * View pager adapter
     */
    inner class MyViewPagerAdapter : PagerAdapter() {
        private var layoutInflater: LayoutInflater? = null

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            layoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            val view = layoutInflater!!.inflate(layouts!![position], container, false)
            container.addView(view)
            println("ASU"+position)
            return view
        }

        override fun getCount(): Int {
            return layouts!!.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj
        }


        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            val view = `object` as View
            container.removeView(view)
        }
    }
}
