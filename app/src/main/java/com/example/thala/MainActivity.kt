package com.example.thala

import android.app.Dialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView

class MainActivity : AppCompatActivity() {
    private lateinit var dialogBox: Dialog
    lateinit var click: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val click = findViewById<Button>(R.id.click)
        dialogBox = Dialog(this)
        dialogBox.setContentView(R.layout.dialb)

        click.setOnClickListener {
            handleButtonClick()
        }

        val retry = dialogBox.findViewById<Button>(R.id.btnRetry)
        retry.setOnClickListener {
            dialogBox.dismiss()
        }
    }

    private fun handleButtonClick() {
        val textView = findViewById<EditText>(R.id.et)
        val inputText = textView.text.toString().trim()
        val reasonTextView = dialogBox.findViewById<TextView>(R.id.tvReason)

        if (inputText.isNotEmpty()) {
            val inputNumber = inputText.toIntOrNull()
            var reason = ""

            val playVideo1 = when {
                inputText.length == 7 -> {
                    reason = "✅ Length is 7"
                    true
                }
                inputNumber != null && inputNumber % 7 == 0 -> {
                    reason = "✅ $inputNumber is a multiple of 7"
                    true
                }
                inputText.sumOfDigits() == 7 -> {
                    reason = "✅ Sum of digits is 7"
                    true
                }
                else -> false
            }

            dialogBox.show()
            reasonTextView.text = reason
            reasonTextView.visibility = if (playVideo1) View.VISIBLE else View.GONE

            val videoPath = if (playVideo1) {
                "android.resource://$packageName/${R.raw.vedio1}"
            } else {
                "android.resource://$packageName/${R.raw.moye}"
            }
            dialogVideo(videoPath)
        } else {
            Toast.makeText(this, "!Logic Break!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun String.sumOfDigits(): Int {
        return this.filter { it.isDigit() }.sumOf { it.toString().toInt() }
    }

    private fun dialogVideo(videoPath: String) {
        val videoView = dialogBox.findViewById<VideoView>(R.id.videoView)
        val videoUri = Uri.parse(videoPath)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        videoView.setVideoURI(videoUri)
        videoView.start()
    }
}
