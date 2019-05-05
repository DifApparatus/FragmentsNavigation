package com.e.fragmentsnavigation.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.e.fragmentsnavigation.R

class FragmentB : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_b, container, false)
        val textView = view?.findViewById<TextView>(R.id.clickQuantityTextView)
        arguments?.let {
            textView?.text = it.getInt(COUNTER_ARG).toString()
        }
        return view
    }

    companion object {

        private const val COUNTER_ARG = "COUNT"

        fun newInstance(count:Int) =
            FragmentB().apply {
                arguments = Bundle().apply {
                    putInt(COUNTER_ARG, count)
                }
            }
    }
}
