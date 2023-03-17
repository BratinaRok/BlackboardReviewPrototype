package org.hyperskill.blackboard.data

sealed class AuthToken {
    data class Token(val value: String) : AuthToken() {
        companion object {
            private var currentToken: AuthToken = None

            fun setCurrentToken(token: AuthToken) {
                currentToken = token
            }

            fun getCurrentToken(): String? {
                return when (currentToken) {
                    is Token -> (currentToken as Token).value
                    else -> null
                }
            }
        }
    }
    object None : AuthToken()
}

