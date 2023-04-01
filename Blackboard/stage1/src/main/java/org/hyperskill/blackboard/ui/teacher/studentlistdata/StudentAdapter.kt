package org.hyperskill.blackboard.ui.teacher.studentlistdata

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.hyperskill.blackboard.R
import org.hyperskill.blackboard.data.Student

class StudentAdapter(private val studentList: List<Student>) :
    RecyclerView.Adapter<StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val currentStudent = studentList[position]
        holder.nameTextView.text = currentStudent.name
        holder.lastNameTextView.text = currentStudent.lastName
    }

    override fun getItemCount() = studentList.size

}
