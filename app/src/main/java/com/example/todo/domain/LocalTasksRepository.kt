package com.example.todo.domain


import com.example.todo.data.model.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class LocalTasksRepository {



//    override suspend fun updateDoneTodoItemsVisibility(visible: Boolean) {
//        isDoneVisible = visible
//        updateFlow()
//    }
//
//    private fun updateFlow() {
//        tasksFlow.update {
//            if (isDoneVisible)
//                tasks.toList()
//            else
//                tasks.filter { !it.isDone }
//        }
//        countFlow.update {
//            tasks.count(TodoItem::isDone)
//        }
//    }
}