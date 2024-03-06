package com.example.taskmanagement.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanagement.R
import com.example.taskmanagement.data.Task
import com.example.taskmanagement.service.UpdateTaskActivity

class TaskAdapter (private val tasks: MutableList<Task>, private val context: Context,
                   private val startForResultUpdate: ActivityResultLauncher<Intent>)
    : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle : TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription : TextView = itemView.findViewById(R.id.tvDescription)
        val tvPriorityLevel : TextView = itemView.findViewById(R.id.tvPriorityLevel)
        val tvDeadline : TextView = itemView.findViewById(R.id.tvDeadline)
        val ivDeleteButton: ImageView = itemView.findViewById(R.id.ivDeleteButton)
        val ivUpdateButton: ImageView = itemView.findViewById(R.id.ivUpdateButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_task,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currTask = tasks[position]

        holder.tvTitle.text = currTask.title
        holder.tvPriorityLevel.text = "Priority level: " + currTask.priorityLevel
        holder.tvDeadline.text = "Due: " + currTask.dateDeadline + " " + currTask.timeDeadline
        holder.tvDescription.text = currTask.description

        holder.ivDeleteButton.setOnClickListener{
            showConfirmationDialog(currTask.id)
        }

        holder.ivUpdateButton.setOnClickListener{
            val intent = Intent(context, UpdateTaskActivity::class.java)
            intent.putExtra("taskDetails", currTask)
            startForResultUpdate.launch(intent)
        }
    }

    override fun getItemCount(): Int = tasks.size

    fun addTask(task: Task) {
        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    private fun deleteTask(id: Int) {
        val index = tasks.indexOfFirst { it.id == id }
        tasks.removeAt(index)
        notifyItemRemoved(index)
    }

    fun updateTask(task: Task) {
        val index = tasks.indexOfFirst { it.id == task.id }
        tasks[index] = task
        notifyItemChanged(index)
    }

    private fun showConfirmationDialog(id: Int) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        alertDialogBuilder.setTitle("Confirmation")
        alertDialogBuilder.setMessage("Are you sure you want to delete this task?")

        alertDialogBuilder.setPositiveButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialogBuilder.setNegativeButton("Yes") { _, _ ->
            deleteTask(id)
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}