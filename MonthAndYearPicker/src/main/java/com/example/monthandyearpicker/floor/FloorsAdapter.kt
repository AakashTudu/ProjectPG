package com.example.monthandFloorpicker.floor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.monthandyearpicker.R
import com.example.monthandyearpicker.Utils

internal class FloorsAdapter(private val onFloorSelectedListener: ((Int) -> Unit)? = null) : RecyclerView.Adapter<FloorsAdapter.ViewHolder>() {

    var maxFloor = 100
    var minFloor = 1
    var selectedFloor = 1
        private set

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_year, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindViewHolder(getFloorForPosition(position))
    }

    override fun getItemCount(): Int {
        return maxFloor - minFloor + 1
    }

    fun getPositionForFloor(Floor: Int): Int {
        return Floor - minFloor
    }

    fun setSelectedFloor(Floor: Int) {
        selectedFloor = Floor
        notifyItemChanged(selectedFloor)
    }

    private fun getFloorForPosition(position: Int): Int {
        return minFloor + position
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBindViewHolder(item: Int) {
            (itemView as TextView).apply {
                isSelected = selectedFloor == item
                text = Utils.numberToOrdinalWord(item)
                textSize = if (selectedFloor == item) {
                    25f
                } else {
                    20f
                }
            }
            itemView.setOnClickListener {
                if (selectedFloor != item) {
                    selectedFloor = item
                    notifyDataSetChanged()
                    onFloorSelectedListener?.invoke(item)
                }
            }
        }
    }
}