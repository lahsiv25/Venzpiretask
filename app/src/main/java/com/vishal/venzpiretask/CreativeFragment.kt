package com.vishal.venzpiretask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vishal.venzpiretask.databinding.FragmentCreativeBinding
import com.vishal.venzpiretask.databinding.FragmentTimeBinding
import com.vishal.venzpiretask.repository.Repository


class CreativeFragment : Fragment() {
    private var _binding:FragmentCreativeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel:Viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreativeBinding.inflate(inflater, container, false)
        binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toggleSectionText(binding.btToggleText)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewmodel = ViewModelProvider(requireActivity(), viewModelFactory).get(Viewmodel::class.java)

        binding.btToggleText.setOnClickListener {
            toggleSectionText(binding.btToggleText)
        }
        binding.addQuery.setOnClickListener {
            toggleSectionText(binding.btToggleText)
        }
        binding.numberradiogrp.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener{ group , checkedId ->
                when(checkedId){
                    binding.random.id -> {
                        binding.numberedittext.isEnabled = false
                        binding.numberedittext.hint = ""
                        showtoast("gives you random number")

                    }
                    binding.custom.id -> {
                        binding.numberedittext.isEnabled = true
                        binding.numberedittext.hint = "Number"
                        showtoast("enter the desired munber")
                    }
                    binding.year.id -> {
                        binding.numberedittext.isEnabled = true
                        binding.numberedittext.hint = "year"
                        showtoast("Enter your favourite year")
                    }
                }
            }
        )
        binding.queryoneradiogrp.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    binding.none1.id -> {
                        binding.queryoneedittextfirst.isEnabled = false
                        binding.queryoneedittextsecond.isEnabled = false
                        binding.queryoneedittextfirst.hint = ""
                        binding.queryoneedittextsecond.hint = ""
                        showtoast("empty query")
                    }
                    binding.floor.id -> {
                        binding.queryoneedittextfirst.isEnabled = false
                        binding.queryoneedittextsecond.isEnabled = false
                        binding.queryoneedittextfirst.hint = ""
                        binding.queryoneedittextsecond.hint = ""
                        showtoast("gives the lower neighbour value")
                    }
                    binding.ceil.id -> {
                        binding.queryoneedittextfirst.isEnabled = false
                        binding.queryoneedittextsecond.isEnabled = false
                        binding.queryoneedittextfirst.hint = ""
                        binding.queryoneedittextsecond.hint = ""
                        showtoast("gives the higher neighbour value")
                    }
                    binding.minmax.id -> {
                        binding.queryoneedittextfirst.isEnabled = true
                        binding.queryoneedittextsecond.isEnabled = true
                        binding.queryoneedittextfirst.hint = "Min"
                        binding.queryoneedittextsecond.hint = "Max"
                        showtoast("gives a value between min/max")
                    }
                }
            }
        )
        binding.querytworadiogrp.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    binding.none2.id -> {
                        binding.querytwoedittextfirst.isEnabled = false
                        binding.querytwoedittextsecond.isEnabled = false
                        binding.querytwoedittextfirst.hint = ""
                        binding.queryoneedittextsecond.hint = ""
                        showtoast("empty query")
                    }
                    binding.floor2.id -> {
                        binding.querytwoedittextfirst.isEnabled = true
                        binding.querytwoedittextsecond.isEnabled = false
                        binding.querytwoedittextfirst.hint = "Number"
                        binding.queryoneedittextsecond.hint = ""
                        showtoast("gives the lower neighbour value")

                    }
                    binding.ceil2.id -> {
                        binding.querytwoedittextfirst.isEnabled = true
                        binding.querytwoedittextsecond.isEnabled = false
                        binding.querytwoedittextfirst.hint = "Number"
                        binding.queryoneedittextsecond.hint = ""
                        showtoast("gives the higher neighbour value")
                    }
                    binding.minmax2.id -> {
                        binding.querytwoedittextfirst.isEnabled = true
                        binding.querytwoedittextsecond.isEnabled = true
                        binding.querytwoedittextfirst.hint = "Min"
                        binding.querytwoedittextsecond.hint = "Max"
                        showtoast("gives a value between min/max")
                    }
                }
            }
        )
        binding.submitQuery.setOnClickListener{
            var finalnumber = view.findViewById<RadioButton>(binding.numberradiogrp.checkedRadioButtonId)
            var finaltype = view.findViewById<RadioButton>(binding.typeradiogrp.checkedRadioButtonId)
            var finalqueryone = view.findViewById<RadioButton>(binding.queryoneradiogrp.checkedRadioButtonId)
            var finalquerytwo = view.findViewById<RadioButton>(binding.querytworadiogrp.checkedRadioButtonId)
            var number = "random"
            var type = "trivia"
            var queries = mutableMapOf<String,String>()

            when(finalnumber){
                binding.custom -> {
                    val num = binding.numberedittext.text
                    if(num.isNotEmpty()){
                        number = num.toString().toInt().toString()
                    }
                    number = num.toString()
                }

                binding.year -> {
                    var start = binding.numberedittext.text
                    if (start.isNotEmpty()){
                        number = start.toString().toInt().toString()
                    }
                }
            }
            when(finaltype){
                binding.math -> type = "math"
                binding.yeart -> type = "year"
            }
            when(finalqueryone){
                binding.floor -> queries["notfound"] = "floor"
                binding.ceil -> queries["notfound"] = "ceil"
                binding.minmax -> {
                    val min = binding.queryoneedittextfirst.text
                    val max = binding.queryoneedittextsecond.text
                    if(min.isNotEmpty() && max.isNotEmpty() ){
                        queries["min"] = "${min.toString().toInt()}"
                        queries["max"] = "${max.toString().toInt()}"
                    }
                }
            }
            when(finalquerytwo){
                binding.floor2 -> queries["notfound"] = "floor"
                binding.ceil2 -> queries["notfound"] = "ceil"
                binding.minmax2 -> {
                    val min2 = binding.querytwoedittextfirst.text
                    val max2 = binding.querytwoedittextsecond.text
                    if(min2.isNotEmpty() && max2.isNotEmpty() ){
                        queries["min"] = "${min2.toString().toInt()}"
                        queries["max"] = "${max2.toString().toInt()}"
                    }
                }
            }
            if(number.isNotEmpty()){
                getcreativeResult(number,type,queries)
            }

        }
    }

    private fun toggleArrow(view: View): Boolean {
        return if (view.rotation == 0f) {
            view.animate().setDuration(200).rotation(180f)
            true
        } else {
            view.animate().setDuration(200).rotation(0f)
            false
        }
    }

    private fun toggleSectionText(view: View) {
        val show = toggleArrow(view)
        if (show) {
            expand(binding.lytExpandText, object : AnimListener {
                override fun onFinish() {
                    nestedScrollTo(binding.nestedContent, binding.lytExpandText)
                }
            })
        } else {
            collapse(binding.lytExpandText)
        }
    }

    interface AnimListener {
        fun onFinish()
    }

    private fun expand(v: View, animListener: AnimListener) {
        val a = expandAction(v)
        a.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                animListener.onFinish()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        v.startAnimation(a)
    }

    private fun expandAction(v: View): Animation {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetedHeight = v.measuredHeight
        v.layoutParams.height = 0
        v.visibility = View.VISIBLE
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                v.layoutParams.height =
                    if (interpolatedTime == 1f) ViewGroup.LayoutParams.WRAP_CONTENT else (targetedHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.duration = (targetedHeight / v.context.resources.displayMetrics.density).toLong()
        v.startAnimation(a)
        return a
    }

    private fun nestedScrollTo(nested: NestedScrollView, targetView: View) {
        nested.post { nested.scrollTo(500, targetView.bottom) }
    }

    private fun collapse(v: View) {
        val initialHeight = v.measuredHeight
        val a: Animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        a.duration = (initialHeight / v.context.resources.displayMetrics.density).toLong()
        v.startAnimation(a)
    }
    private fun getcreativeResult(number:String, type:String, query:MutableMap<String,String>) {
        var queries = (mutableMapOf("json" to "") + query).toMutableMap()
        viewmodel.getResult(number,type,queries)
        viewmodel.myresponse.observe(
            viewLifecycleOwner, Observer{
                if (it.isSuccessful){
                    val text1 = it.body()?.text.toString()
                    binding.result.text = text1
                }
            }
        )
    }
    fun showtoast(message:String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}