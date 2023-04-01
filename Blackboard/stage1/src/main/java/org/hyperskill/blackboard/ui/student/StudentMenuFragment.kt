package org.hyperskill.blackboard.ui.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.hyperskill.blackboard.R
import org.hyperskill.blackboard.databinding.FragmentStudentMenuBinding
import org.hyperskill.blackboard.model.StudentsListViewModel

class StudentMenuFragment : Fragment() {

    lateinit var fragmentStudentMenuBinding: FragmentStudentMenuBinding
    lateinit var viewGradesBtn: Button
    private val viewModel: StudentsListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStudentMenuBinding =
            FragmentStudentMenuBinding.inflate(layoutInflater, container, false)
        viewGradesBtn = fragmentStudentMenuBinding.studentMenuViewGradesBtn

        viewGradesBtn.setOnClickListener {
            findNavController().navigate(R.id.action_studentMenuFragment_to_viewGradesFragment)
        }
        return fragmentStudentMenuBinding.root
    }


}
