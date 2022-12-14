package com.glekhub.nau_web_music

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.glekhub.nau_web_music.databinding.ActivityMainBinding
import com.glekhub.nau_web_music.ui.MusicFragment
import com.glekhub.nau_web_music.ui.StartFragment
import com.glekhub.nau_web_music.viewmodel.MusicViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val viewModel: MusicViewModel by viewModels()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentFragment.observe(this@MainActivity) {
                    supportFragmentManager.commit {
                        if (it == 0) {
                            add<StartFragment>(R.id.fragmentContainer)
                        } else {
                            add<MusicFragment>(R.id.fragmentContainer)
                        }
                    }
                }
            }
        }
    }
}