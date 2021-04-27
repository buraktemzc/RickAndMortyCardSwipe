package com.ebt.cardswipelib

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class SwipeItemTouchHelperCallback<T>(
    private var adapter: RecyclerView.Adapter<*>,
    var dataList: ArrayList<T>
) :
    ItemTouchHelper.Callback() {
    private var swipeListener: OnSwipeListener<T>? = null

    init {
        this.adapter = DataUtil.checkIsNull(adapter)
        this.dataList = DataUtil.checkIsNull(dataList)
    }

    fun setOnSwipedListener(mListener: OnSwipeListener<T>) {
        this.swipeListener = mListener
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = 0
        var swipeFlags = 0
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is SwipeLayoutManager) {
            swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        viewHolder.itemView.setOnTouchListener(null)

        val layoutPosition = viewHolder.layoutPosition
        val remove: T = dataList.removeAt(layoutPosition)

        adapter.notifyItemRemoved(layoutPosition)

        if (swipeListener != null) {
            swipeListener!!.onSwiped(
                viewHolder,
                remove,
                if (direction == ItemTouchHelper.LEFT) SwipeType.LEFT else SwipeType.RIGHT
            )
        }

        if (adapter.itemCount == 0) {
            swipeListener?.onAllItemsSwiped()
        }
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun onChildDraw(
        c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
        dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            var ratio = dX / getThreshold(recyclerView, viewHolder)
            if (ratio > 1) {
                ratio = 1f
            } else if (ratio < -1) {
                ratio = -1f
            }

            val childCount = recyclerView.childCount
            if (childCount > UnitConstant.DEFAULT_SHOW_ITEM) {
                for (position in 1 until childCount - 1) {
                    val index = childCount - position - 1
                    val view = recyclerView.getChildAt(position)
                    view.scaleX =
                        1 - index * UnitConstant.DEFAULT_SCALE + abs(ratio) * UnitConstant.DEFAULT_SCALE
                    view.scaleY =
                        1 - index * UnitConstant.DEFAULT_SCALE + abs(ratio) * UnitConstant.DEFAULT_SCALE
                }
            } else {
                for (position in 0 until childCount - 1) {
                    val index = childCount - position - 1
                    val view = recyclerView.getChildAt(position)
                    view.scaleX =
                        1 - index * UnitConstant.DEFAULT_SCALE + abs(ratio) * UnitConstant.DEFAULT_SCALE
                    view.scaleY =
                        1 - index * UnitConstant.DEFAULT_SCALE + abs(ratio) * UnitConstant.DEFAULT_SCALE
                }
            }
        }
    }

    private fun getThreshold(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Float {
        return recyclerView.width * getSwipeThreshold(viewHolder)
    }
}