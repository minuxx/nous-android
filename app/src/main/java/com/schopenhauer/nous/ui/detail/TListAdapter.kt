package com.schopenhauer.nous.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.schopenhauer.nous.databinding.ListItemBinding
import com.schopenhauer.nous.databinding.ListItemPageBinding
import com.schopenhauer.nous.domain.model.Item

class TListAdapter(
  private val itemClickListener: (Long) -> Unit
) : ListAdapter<Item, RecyclerView.ViewHolder>(diffUtil) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = when (viewType) {
      VIEW_TYPE_LIST_ITEM -> ListItemBinding.inflate(
        layoutInflater,
        parent,
        false
      )
      VIEW_TYPE_PAGE -> ListItemPageBinding.inflate(
        layoutInflater,
        parent,
        false
      )
      else -> throw IllegalArgumentException("Invalid view type")
    }

    return when (binding) {
      is ListItemBinding -> ItemViewHolder(binding) { position ->
        itemClickListener(currentList[position].id)
      }
      is ListItemPageBinding -> PageViewHolder(binding)
      else -> throw IllegalArgumentException("Invalid binding type")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    when (holder) {
      is ItemViewHolder -> holder.bind(currentList[position])
      is PageViewHolder -> holder.bind(currentList[position].page())
    }
  }

  override fun getItemViewType(position: Int): Int =
    if (currentList[position].page == null) VIEW_TYPE_LIST_ITEM
    else VIEW_TYPE_PAGE

  companion object {
    private const val VIEW_TYPE_LIST_ITEM = 0
    private const val VIEW_TYPE_PAGE = 1

    private val diffUtil = object : DiffUtil.ItemCallback<Item>() {
      override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
      }
    }
  }
}
