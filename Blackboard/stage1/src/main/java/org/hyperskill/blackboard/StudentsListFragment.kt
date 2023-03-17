package org.hyperskill.blackboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.hyperskill.blackboard.databinding.FragmentStudentsListBinding

class StudentsListFragment : Fragment() {

    lateinit var fragmentStudentsListFragment: FragmentStudentsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentStudentsListFragment =
            FragmentStudentsListBinding.inflate(layoutInflater, container, false)

        return fragmentStudentsListFragment.root
    }
}

