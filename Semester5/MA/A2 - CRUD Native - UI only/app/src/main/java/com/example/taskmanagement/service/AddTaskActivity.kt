package com.example.taskmanagement.service

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskmanagement.MainActivity
import com.example.taskmanagement.R
import com.example.taskmanagement.data.Task
import com.example.taskmanagement.utils.TaskUtils.setupAutoComplete
import com.example.taskmanagement.utils.TaskUtils.showDatePickerDialog
import com.example.taskmanagement.utils.TaskUtils.showTimePickerDialog

class AddTaskActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var autoCompletePriorityLevel: AutoCompleteTextView
    private lateinit var etDateDeadline: EditText
    private lateinit var etTimeDeadline: EditText
    private lateinit var etDescription: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        etTitle = findViewById(R.id.etTitle)
        autoCompletePriorityLevel = findViewById(R.id.autoCompletePriorityLevel)
        etDateDeadline = findViewById(R.id.etDateDeadline)
        etTimeDeadline = findViewById(R.id.etTimeDeadline)
        etDescription = findViewById(R.id.etDescription)

        val goBackArrow: ImageView = findViewById(R.id.ivBackButton)
        val ivSaveButton: ImageView = findViewById(R.id.ivSaveButton)

        goBackArrow.setOnClickListener { finish() }
        ivSaveButton.setOnClickListener { saveTask() }

        etDateDeadline.setOnClickListener {
            showDatePickerDialog(this, etDateDeadline)
        }
        etTimeDeadline.setOnClickListener {
            showTimePickerDialog(this, etTimeDeadline)
        }

        setupAutoComplete(this, autoCompletePriorityLevel)
    }

    private fun saveTask() {
        val title = etTitle.text.toString()
        val priorityLevel = autoCompletePriorityLevel.text.toString()
        val dateDeadline = etDateDeadline.text.toString()
        val timeDeadline = etTimeDeadline.text.toString()
        val description = etDescription.text.toString()

        if (title.isBlank()) {
            etTitle.error = "Required"
        } else {
            val newTask = Task(title, priorityLevel, dateDeadline, timeDeadline, description)
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("newTask", newTask)
            setResult(RESULT_OK, intent)
            finish()
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
        }
    }
}
