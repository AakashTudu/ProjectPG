package com.example.monthandyearpicker.floor

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import androidx.annotation.IntRange
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.withStyledAttributes
import com.example.monthandyearpicker.R
import java.util.*

class FloorPickerDialog private constructor(
    context: Context,
    @StyleRes
    themeResId: Int,
    private val onFloorSetListener: OnFloorSetListener?,
    positiveButtonText: CharSequence,
    negativeButtonText: CharSequence
) : AlertDialog(context, themeResId), DialogInterface.OnClickListener {
    
    private val floorPickerView: FloorPickerView = FloorPickerView(ContextThemeWrapper(context, themeResId))
    init {
        floorPickerView.context.withStyledAttributes(themeResId, R.styleable.MonthYearPickerDialog) {
            val backgroundColor = getColor(R.styleable.MonthYearPickerDialog_monthBackgroundColorSelected, androidx.appcompat.R.attr.colorBackgroundFloating)
            window!!.setBackgroundDrawable(ColorDrawable(backgroundColor))
        }

        setView(floorPickerView)

        setButton(BUTTON_POSITIVE, positiveButtonText, this)
        setButton(BUTTON_NEGATIVE, negativeButtonText, this)
    }
    
    

    override fun onClick(p0: DialogInterface, which: Int) {
        if (which == BUTTON_POSITIVE) {
            floorPickerView.clearFocus()
            onFloorSetListener?.onFloorSet(floorPickerView.floorsAdapter.selectedFloor)
        }
    }

    class Builder(
        private val context: Context,
        @StyleRes
        private val themeResId: Int,
        private val OnFloorSetListener: OnFloorSetListener? = null,
        private var selectedFloor: Int = 1
    ) {

        private var positiveButtonText: CharSequence = context.getText(android.R.string.ok)
        private var negativeButtonText: CharSequence = context.getText(android.R.string.cancel)
        private var isAnnualMode = true
        private var minFloor = 1
        private var maxFloor = 100
        private var onFloorSelectedListener: OnFloorSelectedListener? = null
        

        /**
         * Sets maximum available Floor.
         *
         * @return This [Builder][Builder] object to allow chaining of setter methods.
         */
        fun setMaxFloor(Floor: Int): Builder {
            maxFloor = Floor
            return this
        }

        /**
         * Sets minimum available Floor.
         *
         * @return This [Builder][Builder] object to allow chaining of setter methods.
         */
        fun setMinFloor(Floor: Int): Builder {
            minFloor = Floor
            return this
        }

        /**
         * Sets text to display in the negative button.
         *
         * @return This [Builder][Builder] object to allow chaining of setter methods.
         */
        fun setNegativeButton(@StringRes textId: Int): Builder {
            negativeButtonText = context.getText(textId)
            return this
        }

        /**
         * Sets text to display in the negative button.
         *
         * @return This [Builder][Builder] object to allow chaining of setter methods.
         */
        fun setNegativeButton(text: CharSequence): Builder {
            negativeButtonText = text
            return this
        }

        /**
         * Sets callback that will be invoked when user has selected month.
         *
         * @param onFloorSelectedListener the [callback][FloorPickerDialog.OnFloorSelectedListener] that will run.
         *
         * @return This [Builder][Builder] object to allow chaining of setter methods.
         */
        fun setOnFloorSelectedListener(onFloorSelectedListener: OnFloorSelectedListener): Builder {
            this.onFloorSelectedListener = onFloorSelectedListener
            return this
        }

        /**
         * Sets text to display in the positive button.
         *
         * @return This [Builder][Builder] object to allow chaining of setter methods.
         */
        fun setPositiveButton(@StringRes textId: Int): Builder {
            positiveButtonText = context.getText(textId)
            return this
        }

        /**
         * Sets text to display in the positive button.
         *
         * @return This [Builder][Builder] object to allow chaining of setter methods.
         */
        fun setPositiveButton(text: CharSequence): Builder {
            positiveButtonText = text
            return this
        }

        /**
         * Creates [FloorPickerDialog] after checking the entered parameters.
         *
         * @return [FloorPickerDialog].
         */
        fun build(): FloorPickerDialog {

            require(minFloor <= maxFloor) {
                "Minimum Floor ($minFloor) is larger than maximum Floor ($maxFloor)"
            }
            require(selectedFloor in minFloor..maxFloor) {
                "Selected Floor ($selectedFloor) is not in range from minimum ($minFloor) to maximum ($maxFloor) Floor"
            }

            val floorPickerDialog = FloorPickerDialog(
                context,
                themeResId,
                OnFloorSetListener,
                positiveButtonText,
                negativeButtonText
            )
            floorPickerDialog.floorPickerView.apply {
                onFloorSelected = { onFloorSelectedListener?.onFloorSelected(it) }
                setMinFloor(minFloor)
                setMaxFloor(maxFloor)
                setSelectedFloor(selectedFloor)
            }
            return floorPickerDialog
        }
    }

    fun interface OnFloorSetListener {
        /**
         * Called when user has selected month and Floor.
         *
         * @param floor selected Floor.
         */
        fun onFloorSet(floor: Int)
    }

    /**
     * Interface definition for a callback to be invoked when user has selected Floor.
     */
    fun interface OnFloorSelectedListener {
        /**
         * Called when user has selected Floor.
         *
         * @param floor selected Floor.
         */
        fun onFloorSelected(floor: Int)
    }
}