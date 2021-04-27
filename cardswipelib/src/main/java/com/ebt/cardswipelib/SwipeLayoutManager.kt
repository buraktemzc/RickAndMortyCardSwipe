package com.ebt.cardswipelib

import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeLayoutManager(recyclerView: RecyclerView, itemTouchHelper: ItemTouchHelper) :
    RecyclerView.LayoutManager() {
    private var recyclerView: RecyclerView = DataUtil.checkIsNull(recyclerView)
    private var touchHelper: ItemTouchHelper = DataUtil.checkIsNull(itemTouchHelper)

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        val itemCount = itemCount
        if (itemCount > UnitConstant.DEFAULT_SHOW_ITEM) {
            for (position in UnitConstant.DEFAULT_SHOW_ITEM downTo 0) {
                val view = recycler.getViewForPosition(position)
                addView(view)
                measureChildWithMargins(view, 0, 0)
                val widthSpace = width - getDecoratedMeasuredWidth(view)
                val heightSpace = height - getDecoratedMeasuredHeight(view)
                layoutDecoratedWithMargins(
                    view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view)
                )

                when {
                    position == UnitConstant.DEFAULT_SHOW_ITEM -> {
                        view.scaleX = 1 - (position - 1) * UnitConstant.DEFAULT_SCALE
                        view.scaleY = 1 - (position - 1) * UnitConstant.DEFAULT_SCALE
                    }
                    position > 0 -> {
                        view.scaleX = 1 - position * UnitConstant.DEFAULT_SCALE
                        view.scaleY = 1 - position * UnitConstant.DEFAULT_SCALE
                    }
                    else -> {
                        view.setOnTouchListener(onTouchListener)
                    }
                }
            }
        } else {
            for (position in itemCount - 1 downTo 0) {
                val view = recycler.getViewForPosition(position)
                addView(view)
                measureChildWithMargins(view, 0, 0)
                val widthSpace = width - getDecoratedMeasuredWidth(view)
                val heightSpace = height - getDecoratedMeasuredHeight(view)
                layoutDecoratedWithMargins(
                    view, widthSpace / 2, heightSpace / 2,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 2 + getDecoratedMeasuredHeight(view)
                )
                if (position > 0) {
                    view.scaleX = 1 - position * UnitConstant.DEFAULT_SCALE
                    view.scaleY = 1 - position * UnitConstant.DEFAULT_SCALE
                } else {
                    view.setOnTouchListener(onTouchListener)
                }
            }
        }
    }

    private val onTouchListener = OnTouchListener { v, event ->
        val childViewHolder = this.recyclerView.getChildViewHolder(v)
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            touchHelper.startSwipe(childViewHolder)
        }
        false
    }
}