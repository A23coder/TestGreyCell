package com.example.testgreycell

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testgreycell.data.UserData
import com.google.android.material.bottomsheet.BottomSheetDialog

class CustomAdapter(val dataList: List<UserData>, val context: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val email: TextView = itemView.findViewById(R.id.txtEmailData)
        val btn: Button = itemView.findViewById(R.id.txtBtnShow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]

        holder.email.text = item.email

        holder.btn.setOnClickListener {
            val dialog = BottomSheetDialog(context)
            val view = LayoutInflater.from(context).inflate(R.layout.bottomsheet, null)

            val name = view.findViewById<TextView>(R.id.lblname)
            val email = view.findViewById<TextView>(R.id.lblemail)
            val phone = view.findViewById<TextView>(R.id.lblphone)
            val age = view.findViewById<TextView>(R.id.lblage)

            name.text = "Name-" + item.name
            email.text = "Email-" + item.email
            phone.text = "Phone-" + item.phone
            age.text = "Age-" + item.age


            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}