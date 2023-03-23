package org.hyperskill.blackboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.hyperskill.blackboard.data.AuthToken
import org.hyperskill.blackboard.databinding.FragmentLoginBinding
import org.hyperskill.blackboard.model.LoginViewModel


class LoginFragment : Fragment() {
    lateinit var fragmentLoginBinding: FragmentLoginBinding
    lateinit var logInButton: Button
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var token: AuthToken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        fragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        logInButton = fragmentLoginBinding.loginBtn

        logInButton.setOnClickListener {
            val username = fragmentLoginBinding.loginUsernameEt.text.toString()
            val password = fragmentLoginBinding.loginPassEt.text.toString()
            viewModel.login(username, password)

            viewModel.loginResult.observe(viewLifecycleOwner) { response ->
                if (response.success) {
                    Toast.makeText(
                        context,
                        "Login successful",
                        Toast.LENGTH_SHORT
                    ).show()

                    if (response.token != null) {
                        token = AuthToken.Token(response.token)
                        AuthToken.Token.setCurrentToken(token)
                    }
                    if (response.role.equals("teacher")) {
                        findNavController().navigate(R.id.action_loginFragment_to_teacherMenu)
                    } else if (response.role.equals("student")) {

                    }
                } else {
                    Toast.makeText(context, "Wrong credentials", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return fragmentLoginBinding.root
    }

}

