package com.example.monthandyearpicker.floor

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monthandFloorpicker.floor.FloorsAdapter
import com.example.monthandyearpicker.R
import com.example.monthandyearpicker.Utils

internal class FloorPickerView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {
    
    private var rvFloors: RecyclerView
    var floorsAdapter: FloorsAdapter
        private set

    private var tvSelectedFloor: TextView

    var onFloorSelected: ((Int) -> Unit)? = null

    init {
        inflate(context, R.layout.view_floor_picker, this)
        rvFloors = findViewById(R.id.rv_floor)
        tvSelectedFloor = findViewById(R.id.tv_selected_floor)
        tvSelectedFloor.isSelected = true

        floorsAdapter = FloorsAdapter {
            tvSelectedFloor.text = Utils.numberToOrdinal(it)
            tvSelectedFloor.isSelected = true
            onFloorSelected?.invoke(it)
        }
        rvFloors.adapter = floorsAdapter
        rvFloors.layoutManager = LinearLayoutManager(context)
        rvFloors.visibility = VISIBLE

        tvSelectedFloor.setOnClickListener {
            if (!rvFloors.isVisible) {
                rvFloors.visibility = VISIBLE
                tvSelectedFloor.isSelected = true
            }
        }
    }

    fun setMaxFloor(maxFloor: Int) {
        floorsAdapter.maxFloor = maxFloor
    }

    fun setMinFloor(minFloor: Int) {
        floorsAdapter.minFloor = minFloor
    }

    fun setSelectedFloor(floor: Int) {
        floorsAdapter.setSelectedFloor(floor)
        rvFloors.post {
            rvFloors.scrollToPosition(floorsAdapter.getPositionForFloor(floor))
        }
        tvSelectedFloor.text = Utils.numberToOrdinal(floor)
    }
    
}