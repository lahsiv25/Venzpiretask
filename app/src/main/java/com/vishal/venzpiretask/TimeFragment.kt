package com.vishal.venzpiretask

import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TimePicker
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.vishal.venzpiretask.databinding.FragmentApinumberBinding
import com.vishal.venzpiretask.databinding.FragmentTimeBinding
import com.vishal.venzpiretask.repository.Repository
import java.util.*

class TimeFragment : Fragment() {

    private var _binding:FragmentTimeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewmodel:Viewmodel
    var text = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimeBinding.inflate(inflater, container, false)
        binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewmodel = ViewModelProvider(requireActivity(), viewModelFactory).get(Viewmodel::class.java)
        timePicker()
        binding.swipeRefresh.setOnRefreshListener {
            text = ""
            binding.result.text = text
            binding.swipeRefresh.isRefreshing = false
            timePicker()
        }
    }

    fun timePicker(){
        val c = Calendar.getInstance()
        var mHour = c[Calendar.HOUR_OF_DAY]
        var mMinute = c[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _: TimePicker?, hourOfDay: Int, minute: Int ->
                binding.timeText.text = "$hourOfDay:$minute"
                getTime("$hourOfDay$minute","trivia", mutableMapOf())
            }, mHour, mMinute, false
        )
        timePickerDialog.show()
        timePickerDialog.setCancelable(false)
    }
    private fun getTime(number:String, type:String, query:MutableMap<String,String>) {
        var queries = (mutableMapOf("json" to "") + query).toMutableMap()
        viewmodel.getResult(number,type,queries)
        viewmodel.myresponse.observe(
            viewLifecycleOwner,Observer{
                if (it.isSuccessful){
                    val text1 = it.body()?.text.toString()
                    binding.result.text = text1
                }
            }
        )
    }
}