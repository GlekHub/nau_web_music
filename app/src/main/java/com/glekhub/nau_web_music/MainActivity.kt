package com.glekhub.nau_web_music

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.glekhub.nau_web_music.databinding.ActivityMainBinding
import com.glekhub.nau_web_music.ui.MusicFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        supportFragmentManager.commit {
            add<MusicFragment>(R.id.fragmentContainer)
        }
    }
}