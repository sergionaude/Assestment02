package com.example.assestment02

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

fun fragmentNavigation(supportFragmentManager: FragmentManager, fragment: Fragment){
    supportFragmentManager.beginTransaction()
        .replace(R.id.Contenedor, fragment)
        .addToBackStack(fragment.id.toString())
        .commit()
}
