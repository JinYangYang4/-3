package com.example.musicapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActionbarAdapter1(private val selectlist: ArrayList<String>) :
    RecyclerView.Adapter<ActionbarAdapter1.ViewHolder1>() {

    inner class ViewHolder1(view: View) : RecyclerView.ViewHolder(view) {
        val actionBarTitleName: TextView = view.findViewById(R.id.action_bar_title_name)
    }
    private var selectedPosition = -1

    // 新增：定义点击事件接口
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    private var onItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    fun setSelectedPosition(position: Int) {
        val oldPosition = selectedPosition
        selectedPosition = position
        if (oldPosition != -1) {
            notifyItemChanged(oldPosition)
        }
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.action_bar_item, parent, false)
        return ViewHolder1(view)
    }

    override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
        val selectBar = selectlist[position]
        holder.actionBarTitleName.text = selectBar
        // 新增：item 点击事件
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(position)
        }
        /*根据选中位置更新文本颜色
        if (position == selectedPosition) {
            holder.actionBarTitleName.setTextColor(Color.GREEN)

        } else {
            holder.actionBarTitleName.setTextColor(Color.BLACK)
        }*/
    }

    override fun getItemCount(): Int = selectlist.size
}