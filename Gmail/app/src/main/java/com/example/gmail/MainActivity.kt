package com.example.gmail

import android.content.res.ColorStateList
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.gmail.databinding.ActivityMainBinding
import com.example.gmail.databinding.GmailItemBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var main: ActivityMainBinding

    data class Email(val sender: String, val subject: String, val timestamp: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main = ActivityMainBinding.inflate(layoutInflater)
        val view = main.root
        setContentView(view)

        val data = generateRandomEmailDataList(25)

        val adapter = EmailAdapter(this, data)
        val listView = main.listView
        listView.adapter = adapter
    }

    inner class EmailAdapter(context: AppCompatActivity, objects: List<Email>) :
        ArrayAdapter<Email>(context, 0, objects) {

        @RequiresApi(Build.VERSION_CODES.M)
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val binding: GmailItemBinding

            if (convertView == null) {
                binding = GmailItemBinding.inflate(LayoutInflater.from(context), parent, false)
                binding.root.tag = binding
            } else {
                binding = convertView.tag as GmailItemBinding
            }

            val email = getItem(position)

            binding.sender.text = email?.sender
            binding.firstSender.text = email?.sender?.take(1)
            binding.timestamp.text = email?.timestamp
            binding.content.text = email?.subject


            val randomColor = ContextCompat.getColor(context, generateRandomColor())
            val icon = binding.icon
            icon.foregroundTintList = ColorStateList.valueOf(randomColor)

            return binding.root
        }
    }

    private fun generateRandomEmailData(): Email {
        val random = Random()

        val sender = generateRandomSender()
        val subject = generateRandomSubject(200);

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, random.nextInt(12) + 1)
        calendar.set(Calendar.MINUTE, random.nextInt(60))

        val timestamp = SimpleDateFormat("hh:mm a", Locale.US).format(calendar.time)

        return Email(sender, subject, timestamp)
    }

    private fun generateRandomEmailDataList(count: Int): List<Email> {
        val emailList = mutableListOf<Email>()
        repeat(count) {
            emailList.add(generateRandomEmailData())
        }
        return emailList
    }

    private fun generateRandomSubject(subjectLength : Int): String {
        val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val random = Random()

        val subject = StringBuilder(subjectLength)
        repeat(subjectLength) {
            val randomIndex = random.nextInt(letters.length)
            val randomChar = letters[randomIndex]
            subject.append(randomChar)
        }

        return subject.toString()
    }

    private fun generateRandomSender(): String {
        val random = Random()

        return senderNames[random.nextInt(senderNames.size)]
    }

    private fun generateRandomColor(): Int {
        val random = Random()

        return colorResourceIds[random.nextInt(colorResourceIds.size)]
    }


    private val senderNames = arrayOf(
        "Alice", "Bob", "Charlie", "David", "Eve",
        "Frank", "Grace", "Hannah", "Isaac", "Jane"
    )

    private val colorResourceIds = arrayOf(
        R.color.blue,
        R.color.olive,
        R.color.purple,
        R.color.brown,
        R.color.navy,
        R.color.teal
    )

}