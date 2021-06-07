package com.example.emitter

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.emitter.adapters.UsersDataAdapter
import com.example.emitter.databinding.ActivityMainBinding
import com.example.emitter.pojo.UsersDataModelItem
import com.example.emitter.ui.UsersDataViewModel

class MainActivity : AppCompatActivity(), UsersDataAdapter.OnItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UsersDataViewModel
    private lateinit var usersAdapter: UsersDataAdapter
    private lateinit var usersDataList: List<UsersDataModelItem>
    private lateinit var name: String
    private lateinit var id: String
    private lateinit var email: String
    private lateinit var userDataName: String
    private lateinit var userDataId: String
    private lateinit var userDataEmail: String
    private lateinit var receivedName: TextView
    private lateinit var receivedId: TextView
    private lateinit var receivedEmail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(UsersDataViewModel::class.java)
        viewModel.users.observe(this, Observer { list ->
            usersDataList = list
            usersAdapter = UsersDataAdapter(usersDataList, this@MainActivity)
            binding.recViewUsers.apply {
                layoutManager =
                    LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                adapter = usersAdapter
            }
            usersAdapter.notifyDataSetChanged()

        })

        refreshContent()


    }

    private fun refreshContent() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.users.observe(this, Observer { usersList ->

                usersAdapter = UsersDataAdapter(usersList, this)
                binding.recViewUsers.apply {
                    layoutManager =
                        LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                    adapter = usersAdapter
                }
                usersAdapter.notifyDataSetChanged()

            })
            binding.refreshLayout.isRefreshing = false
        }
    }

    private val myReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if ("android.example.myBroadcastMessage" == intent!!.action) {
                userDataName = intent.getStringExtra("android.example.name")!!
                userDataId = intent.getStringExtra("android.example.id")!!
                userDataEmail = intent.getStringExtra("android.example.email")!!

                showDialogBox()
            }
        }

    }

    override fun onItemClick(position: Int) {
        name = usersDataList[position].name
        id = usersDataList[position].id.toString()
        email = usersDataList[position].email

        val confirmDialog = AlertDialog.Builder(this)
            .setMessage("Send Selected Data to MiddleMan App?")
            .setCancelable(false)
            .setPositiveButton("Confirm") { dialogInterface, i ->
                sendUsersDataToMiddleApp()
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->
                dialogInterface.cancel()
            }.create()

        confirmDialog.show()

    }

    private fun sendUsersDataToMiddleApp() {

        val intent = Intent("android.example.myBroadcastMessage").apply {
            putExtra("android.example.name", name)
            putExtra("android.example.id", id)
            putExtra("android.example.email", email)
            flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        }
        sendBroadcast(intent)
    }

    private fun showDialogBox() {

        val builder = AlertDialog.Builder(this@MainActivity)
        val view: View =
            LayoutInflater.from(applicationContext).inflate(R.layout.custom_dialog_box, null)
        receivedName = view.findViewById(R.id.name_2)
        receivedId = view.findViewById(R.id.id_2)
        receivedEmail = view.findViewById(R.id.email_2)

        receivedName.text = userDataName
        receivedId.text = userDataId
        receivedEmail.text = userDataEmail

        builder.setView(view).setCancelable(true)
        builder.show()
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter("android.example.myBroadcastMessage")
        registerReceiver(myReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(myReceiver)
    }
}