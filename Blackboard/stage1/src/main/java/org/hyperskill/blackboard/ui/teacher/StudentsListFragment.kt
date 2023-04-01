package org.hyperskill.blackboard.ui.teacher

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.hyperskill.blackboard.R
import org.hyperskill.blackboard.data.Student
import org.hyperskill.blackboard.ui.teacher.studentlistdata.StudentAdapter
import org.hyperskill.blackboard.ui.teacher.studentlistdata.StudentsCallback
import org.hyperskill.blackboard.databinding.FragmentStudentsListBinding
import org.hyperskill.blackboard.model.StudentsListViewModel

class StudentsListFragment : Fragment(), StudentsCallback {

    lateinit var fragmentStudentsListFragment: FragmentStudentsListBinding
    private val viewModel: StudentsListViewModel by viewModels()
    var studentsList: List<Student> = emptyList()

    override fun onStudentsLoaded(studentsList: List<Student>) {
        Handler(Looper.getMainLooper()).post {
            val recyclerView =
                fragmentStudentsListFragment.root.findViewById<RecyclerView>(R.id.recyclerView)
            recyclerView?.layoutManager = LinearLayoutManager(this.context)
            recyclerView?.adapter = StudentAdapter(studentsList)
            recyclerView?.requestLayout()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStudentsListFragment =
            FragmentStudentsListBinding.inflate(layoutInflater, container, false)
        viewModel.getList(this)

        return fragmentStudentsListFragment.root
    }
}

