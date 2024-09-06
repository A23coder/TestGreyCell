package com.example.testgreycell

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testgreycell.data.UserData
import com.example.testgreycell.data.UserDatabase
import com.example.testgreycell.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val ageList = listOf(18, 19, 20, 21, 22, 23, 24, 25)
        val spinnerAge = ArrayAdapter(
            this@MainActivity,
            R.layout.simple_spinner_dropdown_item,
            ageList
        )
        binding.spinAge.adapter = spinnerAge
        database = UserDatabase.getDatabase(applicationContext)

        binding.showData.setOnClickListener {
            startActivity(Intent(this@MainActivity, MainActivity2::class.java))
        }

        binding.addDataFab.setOnClickListener {
            writeData()
        }

        binding.edtPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val phone = s.toString()
                if (phone.length in 8..10 && phone.isDigitsOnly()) {
                    binding.tvMode.text = "Valid Phone Number"
                } else {
                    binding.tvMode.text = "Phone number must be 8 to 10 digits."
                }
            }


            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun writeData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val name = binding.edtName.text.toString()
                val email = binding.edtEmail.text.toString()
                val phone = binding.edtPhone.text.toString()
                val age = binding.spinAge.selectedItem.toString()

                val validatedEmail = isValidEmail(email)

                if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty()) {
                    if (phone.length in 8..10) {
                        if (validatedEmail) {
                            GlobalScope.launch(Dispatchers.Main) {
                                try {
                                    val userData = UserData(
                                        0,
                                        name,
                                        email,
                                        phone,
                                        age
                                    )
                                    val result = database.userDao().insertData(userData)
                                    if (result != -1L) {
                                        Toast.makeText(
                                            this@MainActivity, "Data Successfully Added", Toast.LENGTH_SHORT
                                        ).show()
                                        binding.edtName.text.clear()
                                        binding.edtEmail.text.clear()
                                        binding.edtPhone.text.clear()
                                        binding.tvMode.text = "Successfully Data Added"
                                    } else {
                                        Toast.makeText(
                                            this@MainActivity, "Email or Phone already exists", Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Please provide a valid email",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Phone number must be between 8 to 10 digits",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity, "Please fill all the fields.", Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (_: Exception) {
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun CharSequence.isDigitsOnly(): Boolean {
        return this.matches(Regex("\\d+"))
    }
}
