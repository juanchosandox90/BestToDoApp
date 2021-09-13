package com.sandoval.besttodoapp.ui.fragments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sandoval.besttodoapp.R
import com.sandoval.besttodoapp.data.models.Priority
import com.sandoval.besttodoapp.data.models.ToDoData
import com.sandoval.besttodoapp.ui.fragments.ListFragmentDirections
import kotlinx.android.synthetic.main.row_layout.view.*

open class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var dataList = emptyList<ToDoData>()

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.titleText.text = dataList[position].title
        holder.itemView.descriptionText.text = dataList[position].description

        holder.itemView.rowBackground.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }

        when (dataList[position].priority) {
            Priority.HIGH -> holder.itemView.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.redPriority
                )
            )
            Priority.MEDIUM -> holder.itemView.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.yellowPriority)
            )
            Priority.LOW -> holder.itemView.priorityIndicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.greenPriority
                )
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(toDoData: List<ToDoData>) {
        this.dataList = toDoData
        notifyDataSetChanged()
    }
}