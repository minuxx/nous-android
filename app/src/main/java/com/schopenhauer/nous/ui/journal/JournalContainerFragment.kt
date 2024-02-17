package com.schopenhauer.nous.ui.journal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.schopenhauer.nous.databinding.FragmentJournalContainerBinding

class JournalContainerFragment : Fragment() {
	private lateinit var binding: FragmentJournalContainerBinding

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		binding = FragmentJournalContainerBinding.inflate(inflater, container, false)
		return binding.root
	}
}