/*
 *
 *   Copyright (c) 2020  NESP Technology Corporation. All rights reserved.
 *
 *   This program is not free software; you can't redistribute it and/or modify it
 *   without the permit of team manager.
 *
 *   Unless required by applicable law or agreed to in writing.
 *
 *   If you have any questions or if you find a bug,
 *   please contact the author by email or ask for Issues.
 *
 *   Author:JinZhaolu <1756404649@qq.com>
 */

package com.nesp.sdk.android.app

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nesp.sdk.android.R


/**
 *
 * Author: <a href="mailto:jinzhaolu@numob.com">Jack Email:jinzhaolu@numob.com</a>
 * Time: Created 2020/6/24 5:11 PM
 * Description:
 **/
class BottomActionSheetDialog private constructor(
        private val ctx: Context
        , private val actions: List<Action>
        , private val onActionItemClickListener: OnActionItemClickListener? = null
        , private val autoDismiss: Boolean = true

) : Dialog(ctx) {

    class Builder(private val ctx: Context) {
        private var actions: List<Action>? = null
        private var mOnActionItemClickListener: OnActionItemClickListener? = null
        private var autoDismiss = true

        fun actions(actions: List<Action>): Builder {
            this.actions = actions
            return this
        }

        fun onActionItemClickListener(onActionItemClickListener: OnActionItemClickListener): Builder {
            this.mOnActionItemClickListener = onActionItemClickListener
            return this
        }

        fun autoDismiss(autoDismiss: Boolean): Builder {
            this.autoDismiss = autoDismiss
            return this
        }


        fun create(): BottomActionSheetDialog {
            return BottomActionSheetDialog(ctx
                    , actions!!
                    , mOnActionItemClickListener
                    , autoDismiss
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val window = window!!
        window.decorView.setPadding(0, 0, 0, 20)
        window.decorView.setBackgroundColor(Color.TRANSPARENT)
        window.setGravity(Gravity.BOTTOM)
        window.setWindowAnimations(R.style.bottom_dialog_animation)

        setContentView(R.layout.bottom_dialog_list_action_sheet)

        val recyclerView:RecyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.adapter = RvAdapter(object : OnActionItemClickListener {
            override fun onItemClick(v: View, positionForAction: Int) {
                onActionItemClickListener?.onItemClick(v, positionForAction)
                if (autoDismiss) dismiss()
            }
        }, actions)
        if (recyclerView.itemDecorationCount == 0) {
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
                    val childCount: Int = parent.childCount
                    val left: Int = parent.paddingLeft
                    val right: Int = parent.width - parent.paddingRight

                    for (i in 0 until childCount - 1) {
                        val view: View = parent.getChildAt(i)
                        val top = view.bottom.toFloat()
                        val bottom = view.bottom + 2f
                        c.drawRect(left.toFloat(), top, right.toFloat(), bottom, Paint().apply {
                            color = Color.parseColor("#eeeeee")
                        })
                    }
                }
            })
        }
        recyclerView.layoutManager = LinearLayoutManager(ctx, RecyclerView.VERTICAL, false)

        (findViewById(R.id.tvCancel) as TextView).setOnClickListener {
            dismiss()
        }

        val windowManager = (ctx as Activity).windowManager
        val display = windowManager.defaultDisplay
        val lp = window.attributes
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        lp.width = dm.widthPixels * 95 / 100
        window.attributes = lp
        setCanceledOnTouchOutside(true)

    }

    class RvAdapter(private val onActionItemClickListener: OnActionItemClickListener? = null, private val actions: List<Action>) : RecyclerView.Adapter<RvAdapter.RvViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
            return RvViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_action_sheet, parent, false))
        }

        override fun getItemCount(): Int {
            return actions.size
        }

        override fun onBindViewHolder(holder: RvViewHolder, position: Int) {
            val ctx = holder.itemView.context
            val action = actions[position]

            holder.tvName.text = action.text
            holder.tvName.setTextColor(ctx.resources.getColor(if (action.type == ActionType.NORMAL) R.color.black else if (action.type == ActionType.DELETE) R.color.colorIOSRed else R.color.black))
            holder.itemView.setOnClickListener {
                onActionItemClickListener?.onItemClick(it, position)
            }
        }

        class RvViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView = itemView.findViewById(R.id.tvName) as TextView
        }
    }

    interface OnActionItemClickListener {
        fun onItemClick(v: View, positionForAction: Int)
    }


    data class Action(
            var text: String = "",
            var type: ActionType = ActionType.NORMAL
    )

    enum class ActionType {
        NORMAL, DELETE
    }

}