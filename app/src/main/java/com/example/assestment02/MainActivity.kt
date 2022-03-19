package com.example.assestment02

import android.os.Bundle
import android.support.annotation.Nullable
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.assestment02.views.ClasicFragment
import com.example.assestment02.views.RockFragment
import com.example.assestment02.views.SongFragment
import com.google.android.material.tabs.TabLayout
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide();

/*        if (savedInstanceState == null) {
            fragmentNavigation(supportFragmentManager, SongFragment.newInstance())
        }
*/
        /*
        super.onCreate(savedInstanceState)
           var tab: TabLayout = findViewById(R.id.tabs)
            tab.addTab(tab.newTab().setText("ROCK"))
            tab.addTab(tab.newTab().setText("POP"),true)
            tab.addTab(tab.newTab().setText("CLASICA"))
*/
                val tab_toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
                val tab_viewpager = findViewById<ViewPager>(R.id.view_pager)
                val tab_tablayout = findViewById<TabLayout>(R.id.tabLayout)

                setSupportActionBar(tab_toolbar)
                setupViewPager(tab_viewpager)
                tab_tablayout.setupWithViewPager(tab_viewpager)
            }
            private fun setupViewPager(viewpager: ViewPager) {
                val adapter = ViewPagerAdapter(supportFragmentManager)
                adapter.addFragment(SongFragment(), "Pop")
                adapter.addFragment(RockFragment(), "Rock")
                adapter.addFragment(ClasicFragment(), "Clasico")
                viewpager.setAdapter(adapter)
            }

            class ViewPagerAdapter(supportFragmentManager: FragmentManager) :
                FragmentPagerAdapter(supportFragmentManager) {
                private var fragmentList1: ArrayList<Fragment> = ArrayList()
                private var fragmentTitleList1: ArrayList<String> = ArrayList()

                init {
                }

                override fun getItem(position: Int): Fragment {
                    return fragmentList1.get(position)
                }
                @Nullable
                override fun getPageTitle(position: Int): CharSequence {
                    return fragmentTitleList1.get(position)
                }
                override fun getCount(): Int {
                    return fragmentList1.size
                }

                fun addFragment(fragment: Fragment, title: String) {
                    fragmentList1.add(fragment)
                    fragmentTitleList1.add(title)
                }
            }
        }


