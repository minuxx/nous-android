package com.schopenhauer.nous.ui.news.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.schopenhauer.nous.databinding.ListItemNewsBinding
import com.schopenhauer.nous.domain.model.News

class NewsAdapter(
	private val itemClickListener: (String) -> Unit
) : ListAdapter<News, NewsAdapter.NewsViewHolder>(diffUtil) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
		val binding = ListItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return NewsViewHolder(binding) { position -> itemClickListener(currentList[position].id) }
	}

	override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
		holder.bind(currentList[position])
	}

	override fun getItemCount(): Int = currentList.size

	class NewsViewHolder(
		private val binding: ListItemNewsBinding,
		onItemClicked: (Int) -> Unit
	) : RecyclerView.ViewHolder(binding.root) {

		init {
			binding.root.setOnClickListener { onItemClicked(bindingAdapterPosition) }
		}

		fun bind(news: News) {
			with(binding) {
				titleTv.text = news.title
				descriptionTv.text = news.description
				dateTv.text = news.date
			}
		}
	}

	companion object {
		private val diffUtil = object : DiffUtil.ItemCallback<News>() {
			override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
				return oldItem.id == newItem.id
			}

			override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
				return oldItem == newItem
			}
		}
	}
}
