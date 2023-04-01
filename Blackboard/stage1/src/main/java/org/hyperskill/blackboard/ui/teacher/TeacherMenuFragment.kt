package org.hyperskill.blackboard.ui.teacher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.hyperskill.blackboard.R
import org.hyperskill.blackboard.databinding.FragmentTeacherMenuBinding
import org.hyperskill.blackboard.model.StudentsListViewModel

class TeacherMenuFragment : Fragment() {

    lateinit var fragmentTeacherMenuBinding: FragmentTeacherMenuBinding
    lateinit var studentsButton: Button
    private val viewModel: StudentsListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentTeacherMenuBinding =
            FragmentTeacherMenuBinding.inflate(layoutInflater, container, false)
        studentsButton = fragmentTeacherMenuBinding.teacherMenuStudentsButton

        studentsButton.setOnClickListener {
            findNavController().navigate(R.id.action_teacherMenu_to_studentsListFragment)
        }
        return fragmentTeacherMenuBinding.root
    }


}
