package com.example.testgreycell

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testgreycell.data.UserData
import com.example.testgreycell.data.UserDatabase
import com.example.testgreycell.databinding.ActivityMain2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var appDb: UserDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)


        appDb = UserDatabase.getDatabase(this@MainActivity2)

        CoroutineScope(Dispatchers.Main).launch {
            getData()
        }

        setContentView(binding.root)

    }

    private suspend fun getData() {
        val userDao = appDb.userDao()
        val userList: List<UserData> = withContext(Dispatchers.IO) {
            userDao.getUserData()
        }
        binding.recyclerView.adapter = CustomAdapter(userList, this@MainActivity2)
    }
}