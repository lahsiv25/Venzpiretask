package com.vishal.venzpiretask

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.DatePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vishal.venzpiretask.databinding.FragmentDateBinding
import com.vishal.venzpiretask.databinding.FragmentTimeBinding
import com.vishal.venzpiretask.repository.Repository
import java.util.*

class DateFragment : Fragment() {

    private var _binding:FragmentDateBinding? = null
    private val binding get() = _binding!!
    private var mYear = 0
    private var mDay = 0
    private var mMonth = 0
    private var date:String = "1/1"
    private var years:String = "2022"
    private lateinit var viewmodel:Viewmodel
    var text = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDateBinding.inflate(inflater, container, false)
        binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewmodel = ViewModelProvider(requireActivity(), viewModelFactory).get(Viewmodel::class.java)
        datePicker()
        binding.swipeRefresh.setOnRefreshListener {
            text = ""
            binding.number.text = text
            binding.swipeRefresh.isRefreshing = false
            datePicker()
        }

    }
    fun datePicker(){
        val c = Calendar.getInstance()
        mYear = c[Calendar.YEAR]
        mMonth = c[Calendar.MONTH]
        mDay = c[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                binding.dateText.text =
                    dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year
                //date = (monthOfYear + 1).toString() +"/"+  dayOfMonth.toString()
                years = year.toString()
                getDateandYear(years,"year", mutableMapOf())
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
        datePickerDialog.setCancelable(false)
    }
    private fun getDateandYear(number:String, type:String, query:MutableMap<String,String>) {
        var queries = (mutableMapOf("json" to "") + query).toMutableMap()
        viewmodel.getResult(number,type,queries)
        viewmodel.myresponse.observe(
            viewLifecycleOwner,Observer{
                if (it.isSuccessful){
                    val text1 = it.body()?.text.toString()
                    Log.d("hello",text1)
                    binding.number.text = text1
                    val animation = AnimationUtils.loadAnimation(requireContext(),R.anim.fadein)
                    binding.tt1.text= years
                    binding.tt1.startAnimation(animation)
                    binding.tt2.text= years
                    binding.tt2.startAnimation(animation)
                    binding.tt3.text= years
                    binding.tt3.startAnimation(animation)
                    binding.tt4.text= years
                    binding.tt4.startAnimation(animation)
                    binding.tt5.text= years
                    binding.tt5.startAnimation(animation)
                    binding.tt6.text= years
                    binding.tt6.startAnimation(animation)
                }
            }
        )
    }
}