package org.hyperskill.blackboard.data

import kotlinx.serialization.Serializable;

@Serializable
data class Grades(
    val subject: String,
    val grade: Int
)