# RickAndMortyCardSwipe

In this project, I used Clean Architecture with MVVM using Dagger-Hilt, Coroutines, Retrofit and Glide. On the other hand, I used JUnit, MockWebServer and Truth
libraries to verify the accuracy of the requests. For swipe card side, cardswipelib implementation has been added to this project.

#### The app has below packages:
1. **data**: This package contains models, datasources and endpoint interface for rickandmortyapi.
2. **domain**: This package contains usecases and repositories for sending requests to fill models and serve data to presentation layer.
3. **presentation**: All UI components and ViewModels are located in this package.

### Basic example of using card swipe library:

```
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

```
