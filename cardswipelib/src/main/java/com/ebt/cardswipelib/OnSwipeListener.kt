package com.ebt.cardswipelib

import androidx.recyclerview.widget.RecyclerView

interface OnSwipeListener<T> {
    fun onSwiped(viewHolder: RecyclerView.ViewHolder?, t: T, type: SwipeType)
    fun onAllItemsSwiped()
}