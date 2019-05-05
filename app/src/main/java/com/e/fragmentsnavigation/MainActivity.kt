package com.e.fragmentsnavigation

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.e.fragmentsnavigation.fragments.FragmentA
import com.e.fragmentsnavigation.fragments.FragmentB


class MainActivity : AppCompatActivity(), FragmentA.OnFragmentInteractionListener {

    private var clickCounter = 0;
    private val isPortrait : Boolean
            get() = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT
    private val isLandscape
            get() = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE

    private var currentPortraitFragment = NO_FRAGMENT

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.let {
            clickCounter = it.getInt(COUNTER_ARG)
            currentPortraitFragment = it.getString(CURRENT_PORTRAIT_FRAGMENT_NAME)
        }
        paintFragments()


    }
    override fun onFragmentInteraction() {
        clickCounter++;
        repaintFragments()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(COUNTER_ARG, clickCounter)
        outState?.putString(CURRENT_PORTRAIT_FRAGMENT_NAME, currentPortraitFragment)
    }

    private fun paintFragments() {
        val fragmentManager = supportFragmentManager
        if (isPortrait) {
            val transaction = fragmentManager.beginTransaction()
            when (currentPortraitFragment) {
                NO_FRAGMENT -> {
                    transaction.replace(R.id.fragment_container, FragmentA())
                    currentPortraitFragment = FRAGMENT_A
                }
                FRAGMENT_A -> {
                    transaction.replace(R.id.fragment_container, FragmentA())
                }
                FRAGMENT_B -> {
                    val fragmentB = FragmentB.newInstance(clickCounter)
                    fragmentB.arguments?.putInt(COUNTER_ARG, clickCounter)
                    transaction.replace(R.id.fragment_container, fragmentB)
                }
            }
            transaction.addToBackStack(null)
            transaction.commit()
        }
        if (isLandscape) {
            val transactionA = fragmentManager.beginTransaction()
            val fragmentA = FragmentA()
            transactionA.replace(R.id.fragment_a_container, fragmentA)
            transactionA.commit()

            val fragmentB = FragmentB.newInstance(clickCounter)
            fragmentB.arguments?.putInt(COUNTER_ARG, clickCounter)
            val transactionB = fragmentManager.beginTransaction()
            transactionB.replace(R.id.fragment_b_container, fragmentB)
            transactionB.commit()
        }
    }

    private fun repaintFragments(){
        val fragmentManager = supportFragmentManager
        if(isPortrait){
            val transaction = fragmentManager.beginTransaction()
            when(currentPortraitFragment) {
                NO_FRAGMENT->{
                    transaction.replace(R.id.fragment_container, FragmentA())
                    currentPortraitFragment = FRAGMENT_A
                }
                FRAGMENT_A -> {
                    val fragmentB = FragmentB.newInstance(clickCounter)
                    fragmentB.arguments?.putInt(COUNTER_ARG, clickCounter)
                    transaction.replace(R.id.fragment_container, fragmentB)
                    currentPortraitFragment = FRAGMENT_B
                }
                FRAGMENT_B -> {
                    transaction.replace(R.id.fragment_container, FragmentA())
                    currentPortraitFragment = FRAGMENT_A
                }
            }
            transaction.addToBackStack(null)
            transaction.commit()
        }
        if(isLandscape){
            val transactionA = fragmentManager.beginTransaction()
            val fragmentA = FragmentA()
            transactionA.replace(R.id.fragment_a_container,fragmentA)
            transactionA.commit()

            val fragmentB = FragmentB.newInstance(clickCounter)
            fragmentB.arguments?.putInt(COUNTER_ARG, clickCounter)
            val transactionB = fragmentManager.beginTransaction()
            transactionB.replace(R.id.fragment_b_container, fragmentB)
            transactionB.commit()
        }
    }

    override fun onBackPressed() {
        if (isPortrait && currentPortraitFragment == FRAGMENT_B){
            currentPortraitFragment = FRAGMENT_A
        }
        super.onBackPressed()
    }

    private companion object {
        private const val COUNTER_ARG = "COUNT"
        private const val CURRENT_PORTRAIT_FRAGMENT_NAME = "CURRENT_PORTRAIT_FRAGMENT_NAME"
        private const val FRAGMENT_A = "FragmentA"
        private const val FRAGMENT_B = "FragmentB"
        private const val NO_FRAGMENT = "NoFragment"
    }

}
