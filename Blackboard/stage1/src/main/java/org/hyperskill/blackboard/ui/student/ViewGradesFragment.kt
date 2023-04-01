package org.hyperskill.blackboard.ui.student

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.hyperskill.blackboard.R
import org.hyperskill.blackboard.databinding.FragmentViewGradesBinding


class ViewGradesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var fragmentViewGradesBinding: FragmentViewGradesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return fragmentViewGradesBinding.root
    }
}