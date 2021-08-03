package com.lolhistory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lolhistory.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        binding.btnInputSummoner.setOnClickListener { v ->
            Log.d("TESTLOG", "setOnClickListener " + binding.etInputSummoner.text.toString())
            viewModel.searchSummoner(binding.etInputSummoner.text.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}