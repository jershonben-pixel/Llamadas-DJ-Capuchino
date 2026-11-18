package com.jershonzablov.djcapuchinollamadas

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    private lateinit var numberDisplay: TextView
    private val number = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberDisplay = findViewById(R.id.numberDisplay)

        val keys = mapOf(
            R.id.key1 to "1",
            R.id.key2 to "2",
            R.id.key3 to "3",
            R.id.key4 to "4",
            R.id.key5 to "5",
            R.id.key6 to "6",
            R.id.key7 to "7",
            R.id.key8 to "8",
            R.id.key9 to "9",
            R.id.keyStar to "*",
            R.id.key0 to "0",
            R.id.keyHash to "#"
        )
        for ((id, digit) in keys) {
            findViewById<Button>(id).setOnClickListener { appendDigit(digit) }
        }

        findViewById<ImageButton>(R.id.deleteButton).setOnClickListener {
            if (number.isNotEmpty()) {
                number.deleteCharAt(number.length - 1)
                updateDisplay()
            }
        }

        findViewById<Button>(R.id.callButton).setOnClickListener { makeCall() }

        findViewById<Button>(R.id.contactsButton).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, ContactsContract.Contacts.CONTENT_URI))
        }

        findViewById<Button>(R.id.recentsButton).setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, CallLog.Calls.CONTENT_URI))
        }
    }

    private fun appendDigit(digit: String) {
        if (number.length < 15) {
            number.append(digit)
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        numberDisplay.text = number.toString()
    }

    private fun makeCall() {
        if (number.isEmpty()) {
            Toast.makeText(this, "Escribe un número primero", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
    }
}
