package com.ebt.rickandmortycardswipe.presentation.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ebt.cardswipelib.OnSwipeListener
import com.ebt.cardswipelib.SwipeItemTouchHelperCallback
import com.ebt.cardswipelib.SwipeLayoutManager
import com.ebt.cardswipelib.SwipeType
import com.ebt.rickandmortycardswipe.R
import com.ebt.rickandmortycardswipe.data.model.Character
import com.ebt.rickandmortycardswipe.data.util.Result
import com.ebt.rickandmortycardswipe.databinding.ActivityMainBinding
import com.ebt.rickandmortycardswipe.presentation.adapter.MainAdapter
import com.ebt.rickandmortycardswipe.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainAdapter
    private lateinit var swipeCallback: SwipeItemTouchHelperCallback<Character>
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initObservers()
        viewModel.getCharacters()
    }

    private fun initUI() {
        mainAdapter = MainAdapter()
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.adapter = mainAdapter
        swipeCallback = SwipeItemTouchHelperCallback<Character>(
            binding.recyclerView.adapter!!,
            arrayListOf()
        )

        swipeCallback.setOnSwipedListener(object : OnSwipeListener<Character> {
            override fun onAllItemsSwiped() {
                if (!viewModel.endOfCharacterSet) {
                    binding.recyclerView.postDelayed(Runnable {
                        viewModel.getCharacters()
                    }, 200)
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.end_of_character_set),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder?,
                t: Character,
                type: SwipeType
            ) {
                when (type) {
                    SwipeType.LEFT -> {
                        viewModel.incrementTotalDislike()
                    }
                    SwipeType.RIGHT -> {
                        viewModel.incrementTotalLike()
                    }
                }
            }
        })

        val touchHelper = ItemTouchHelper(swipeCallback)
        val cardLayoutManager = SwipeLayoutManager(binding.recyclerView, touchHelper)
        binding.recyclerView.layoutManager = cardLayoutManager
        touchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun initObservers() {
        viewModel.apiResult.observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    hideLoading()
                    val characters = it.data?.characters
                    characters?.let {
                        val dataList = ArrayList(characters)
                        swipeCallback.dataList = dataList
                        mainAdapter.differ.submitList(dataList)
                    }
                }
                is Result.Error -> {
                    hideLoading()
                }
                is Result.Loading -> {
                    showLoading()
                }
            }
        })

        viewModel.totalLike.observe(this, Observer {
            it.let {
                binding.totalLikeTv.text = getString(R.string.total_like, it)
            }
        })

        viewModel.totalDislike.observe(this, Observer {
            it.let {
                binding.totalDislikeTv.text = getString(R.string.total_dislike, it)
            }
        })
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.INVISIBLE
    }
}