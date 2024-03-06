package com.example.taskmanagement

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.adapter.TaskAdapter
import com.example.taskmanagement.data.Task
import com.example.taskmanagement.service.AddTaskActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val tasksList = mutableListOf(Task("Mobile Apps"))

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val startForResultUpdate: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data != null) {
                    val newTask =  result.data!!.getParcelableExtra("updatedTask", Task::class.java)
                    if (newTask != null) {
                        taskAdapter.updateTask(newTask)
                    }
                }
            }
        })

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val taskAdapter = TaskAdapter(tasksList, this, startForResultUpdate)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvTaskItems : RecyclerView = findViewById(R.id.rvTaskItems)
        rvTaskItems.adapter = taskAdapter
        rvTaskItems.layoutManager = LinearLayoutManager(this)

        val btnAddTask : FloatingActionButton = findViewById(R.id.btnAddTask)
        btnAddTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startForResultAdd.launch(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val startForResultAdd: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), ActivityResultCallback<ActivityResult> { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data != null) {
                    val newTask =  result.data!!.getParcelableExtra("newTask", Task::class.java)
                    if (newTask != null) {
                        taskAdapter.addTask(newTask)
                    }
                }
            }
        })
}
