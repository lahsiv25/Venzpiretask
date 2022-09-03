package com.vishal.venzpiretask

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.vishal.venzpiretask.databinding.FragmentApinumberBinding
import com.vishal.venzpiretask.repository.Repository

class ApinumberFragment : Fragment() {

    private var _binding: FragmentApinumberBinding? = null
    private val binding get() = _binding!!
    var number = 0
    private lateinit var viewmodel:Viewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApinumberBinding.inflate(inflater, container, false)
        binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewmodel = ViewModelProvider(requireActivity(), viewModelFactory).get(Viewmodel::class.java)
        getRandomNumberValue("random","trivia", mutableMapOf())
        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
            getRandomNumberValue("random","trivia", mutableMapOf("max" to "10","min" to "5"))
        }
    }
    private fun getRandomNumberValue(number:String, type:String, query:MutableMap<String,String>) {
        var queries = (mutableMapOf("json" to "") + query).toMutableMap()
        viewmodel.getResult(number,type,queries)
        viewmodel.myresponse.observe(
            viewLifecycleOwner, Observer {
                if (it.isSuccessful){
                    binding.number.text = it.body()?.text
                }
            }
        )
    }
}