package com.schopenhauer.nous.ui.journal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.schopenhauer.nous.databinding.ListItemTaskBinding
import com.schopenhauer.nous.domain.model.Task

class TaskAdapter(
	private val isDeletable: Boolean,
	private val itemClickListener: ((Long) -> Unit)? = null
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(diffUtil) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
		val binding = ListItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return TaskViewHolder(binding, isDeletable) { position -> itemClickListener?.invoke(currentList[position].id) }
	}

	override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
		holder.bind(currentList[position])
	}

	override fun getItemCount(): Int = currentList.size

	class TaskViewHolder(
		private val binding: ListItemTaskBinding,
		isDeletable: Boolean,
		onItemClicked: ((Int) -> Unit)? = null
	) : RecyclerView.ViewHolder(binding.root) {

		init {
			if (isDeletable) {
				binding.deleteIb.visibility = View.VISIBLE
				binding.deleteIb.setOnClickListener { onItemClicked?.invoke(bindingAdapterPosition) }
			}
		}

		fun bind(task: Task) {
			binding.contentTv.text = task.content
		}
	}

	companion object {
		private val diffUtil = object : DiffUtil.ItemCallback<Task>() {
			override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
				return oldItem.id == newItem.id
			}

			override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
				return oldItem == newItem
			}
		}
	}
}
