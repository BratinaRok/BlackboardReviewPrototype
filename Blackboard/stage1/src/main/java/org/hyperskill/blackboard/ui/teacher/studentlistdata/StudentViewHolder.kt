package org.hyperskill.blackboard.ui.teacher.studentlistdata

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.hyperskill.blackboard.R

class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameTextView: TextView = itemView.findViewById(R.id.nameTWRecycler)
    val lastNameTextView: TextView = itemView.findViewById(R.id.lastNameTWRecycler)
}
