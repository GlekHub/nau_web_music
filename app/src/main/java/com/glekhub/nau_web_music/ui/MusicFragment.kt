package com.glekhub.nau_web_music.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.glekhub.nau_web_music.Constants.AMOUNT
import com.glekhub.nau_web_music.databinding.FragmentMusicBinding
import com.glekhub.nau_web_music.viewmodel.MusicViewModel
import com.glekhub.nau_web_music.viewmodel.MusicViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MusicFragment : Fragment() {

    private var _binding: FragmentMusicBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MusicViewModel by activityViewModels {
        MusicViewModelFactory()
    }

    private var answer: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservables()

        binding.apply {
            trueButton.setOnClickListener {
                falseText.visibility = View.GONE
                trueText.visibility = View.VISIBLE
                if (viewModel.currentQuestionCount.value != AMOUNT - 1) {
                    if (answer == "True") {
                        refreshState()
                        trueText.text = "Correct"
                    } else {
                        trueText.text = "Incorrect"
                    }
                    refreshCount()
                } else showFinalScoreDialog()
            }
            falseButton.setOnClickListener {
                trueText.visibility = View.GONE
                falseText.visibility = View.VISIBLE
                if (viewModel.currentQuestionCount.value != AMOUNT - 1) {
                    if (answer == "False") {
                        refreshState()
                        falseText.text = "Correct"
                    } else {
                        falseText.text = "Incorrect"
                    }
                    refreshCount()
                } else showFinalScoreDialog()
            }
        }
    }

    private fun setObservables() {

        binding.apply {
            viewModel.currentQuestionCount.observe(viewLifecycleOwner) { count ->
                viewModel.resultLiveData.observe(viewLifecycleOwner) { response ->
                    if (response == null) {
                        questionText.text = "No connection"
                        retryButton.visibility = View.VISIBLE
                        retryButton.setOnClickListener {
                            viewModel.getResult()
                        }
                        return@observe
                    }
                    retryButton.visibility = View.GONE
                    questionText.text = response[count].question
                        .replace("&#039;", "`")
                        .replace("&quot;", "\"").trim()
                    answer = response[count].correct_answer
                }
            }
            viewModel.currentQuestionCount.observe(viewLifecycleOwner) {
                totalText.text = it.toString()
            }
            viewModel.currentState.observe(viewLifecycleOwner) {
                scoreText.text = it.toString()
            }
        }
    }

    private fun showFinalScoreDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Congratulations!")
            .setMessage(
                "Total: " + (viewModel.currentQuestionCount.value?.plus(1)).toString() +
                        "\nYou scored: " + (viewModel.currentState.value?.plus(1)).toString()
            )
            .setCancelable(false)
            .setNegativeButton("Exit") { _, _ ->
                exitGame()
            }
            .setPositiveButton("Play again") { _, _ ->
                restartGame()
            }
            .show()
    }

    private fun refreshCount() {
        viewModel.refreshCount()
    }

    private fun refreshState() {
        viewModel.refreshState()
    }

    private fun restartGame() {
        binding.trueText.visibility = View.GONE
        binding.falseText.visibility = View.GONE
        viewModel.restart()
    }

    private fun exitGame() {
        activity?.finish()
    }
}