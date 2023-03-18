package org.hyperskill.blackboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.hyperskill.blackboard.data.Student
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
            getAllStudentsList()
        }
        return fragmentTeacherMenuBinding.root
    }

    private fun getAllStudentsList() {
        val jsonStr = viewModel.getList()
        if (jsonStr != null) {
            val student = Json.decodeFromString<List<Student>>(jsonStr)

            findNavController().navigate(R.id.action_teacherMenu_to_studentsListFragment)
        }


    }


}
