package com.schopenhauer.nous.ui.detail

import android.graphics.PorterDuff
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.schopenhauer.nous.R
import com.schopenhauer.nous.databinding.ListItemBinding
import com.schopenhauer.nous.databinding.ListItemPageBinding
import com.schopenhauer.nous.domain.model.Item

class ItemViewHolder(
  private val binding: ListItemBinding,
  onItemClicked: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

  init {
    binding.bookmarkBtn.setOnClickListener { onItemClicked(bindingAdapterPosition) }
  }

  fun bind(item: Item) {
    Glide.with(binding.thumbnailIv)
      .load(item.thumbnailUrl)
      .skipMemoryCache(false)
      .diskCacheStrategy(DiskCacheStrategy.NONE)
      .centerCrop()
      .into(binding.thumbnailIv)

    val colorResId = if (item.isBookmarked) R.color.colorComplementary else R.color.colorComplementary
    val color = ContextCompat.getColor(binding.root.context, colorResId)
    binding.bookmarkBtn.setColorFilter(color, PorterDuff.Mode.SRC_IN)
  }
}

class PageViewHolder(
  private val binding: ListItemPageBinding
) : RecyclerView.ViewHolder(binding.root) {

  fun bind(page: String) {
    binding.pageTv.text = page
  }
}
