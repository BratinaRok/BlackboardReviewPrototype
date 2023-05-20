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
import org.hyperskill.blackboard.model.LogoutViewModel

class StudentMenuFragment : Fragment() {

    lateinit var fragmentStudentMenuBinding: FragmentStudentMenuBinding
    lateinit var viewGradesBtn: Button
    lateinit var logOutBtn: Button
    private val viewModel: LogoutViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStudentMenuBinding =
            FragmentStudentMenuBinding.inflate(layoutInflater, container, false)
        viewGradesBtn = fragmentStudentMenuBinding.studentMenuViewGradesBtn
        logOutBtn = fragmentStudentMenuBinding.studentMenuFragmentLogOutBtn

        viewGradesBtn.setOnClickListener {
            findNavController().navigate(R.id.action_studentMenuFragment_to_viewGradesFragment)
        }
        logOutBtn.setOnClickListener {
            viewModel.getLogout()
        }
        return fragmentStudentMenuBinding.root
    }


}
