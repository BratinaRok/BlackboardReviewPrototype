package org.hyperskill.blackboard.ui.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import org.hyperskill.blackboard.data.Grades
import org.hyperskill.blackboard.data.User
import org.hyperskill.blackboard.databinding.FragmentViewGradesBinding
import org.hyperskill.blackboard.model.ViewGradesViewModel

class ViewGradesFragment : Fragment() {
    private lateinit var fragmentViewGradesBinding: FragmentViewGradesBinding
    private val viewModel :ViewGradesViewModel by viewModels()

    private lateinit var gradesTF : TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentViewGradesBinding = FragmentViewGradesBinding.inflate(layoutInflater, container,false)
        viewModel.getGrades(User.username) { grades ->
            gradesTF = fragmentViewGradesBinding.gradesTF
            gradesTF.text = grades.toString()
        }
        return fragmentViewGradesBinding.root

    }

}