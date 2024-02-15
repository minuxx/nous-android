package com.schopenhauer.nous.ui.journals

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.schopenhauer.nous.databinding.ListItemJournalBinding
import com.schopenhauer.nous.domain.model.Journal

class JournalAdapter(
	private val itemClickListener: (Int) -> Unit
) : ListAdapter<Journal, JournalAdapter.JournalViewHolder>(diffUtil) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
		val binding = ListItemJournalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return JournalViewHolder(binding) { position -> itemClickListener(currentList[position].id) }
	}

	override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
		holder.bind(currentList[position])
	}

	override fun getItemCount(): Int = currentList.size

	class JournalViewHolder(
		private val binding: ListItemJournalBinding,
		onItemClicked: (Int) -> Unit
	) : RecyclerView.ViewHolder(binding.root) {

		init {
			binding.root.setOnClickListener { onItemClicked(bindingAdapterPosition) }
		}

		fun bind(journal: Journal) {
			binding.datetimeTv.text = journal.dateTime
		}
	}

	companion object {
		private val diffUtil = object : DiffUtil.ItemCallback<Journal>() {
			override fun areItemsTheSame(oldItem: Journal, newItem: Journal): Boolean {
				return oldItem.id == newItem.id
			}

			override fun areContentsTheSame(oldItem: Journal, newItem: Journal): Boolean {
				return oldItem == newItem
			}
		}
	}
}