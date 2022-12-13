package com.glekhub.nau_web_music.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.glekhub.nau_web_music.R
import com.glekhub.nau_web_music.databinding.FragmentStartBinding
import com.glekhub.nau_web_music.viewmodel.MusicViewModel
import com.glekhub.nau_web_music.viewmodel.MusicViewModelFactory

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MusicViewModel by activityViewModels {
        MusicViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            startButton.setOnClickListener {
                viewModel.setDifficulty(startSwitch.isChecked)

                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                fragmentManager.beginTransaction().replace(R.id.fragmentContainer, MusicFragment())
                    .commit()
            }
        }
    }
}