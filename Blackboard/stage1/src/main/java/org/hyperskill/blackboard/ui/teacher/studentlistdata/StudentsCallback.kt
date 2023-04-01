package org.hyperskill.blackboard.ui.teacher.studentlistdata

import org.hyperskill.blackboard.data.Student

interface StudentsCallback {
    fun onStudentsLoaded(studentsList: List<Student>)
}
