package org.hyperskill.blackboard.data

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val id: Int,
    val name: String,
    val lastName: String
)
