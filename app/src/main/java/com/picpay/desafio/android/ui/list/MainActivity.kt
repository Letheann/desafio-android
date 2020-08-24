package com.picpay.desafio.android.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coredata.models.User
import com.picpay.desafio.android.R
import com.picpay.desafio.android.helper.extensions.hide
import com.picpay.desafio.android.helper.extensions.listen
import com.picpay.desafio.android.helper.extensions.show
import com.picpay.desafio.android.helper.extensions.toast
import com.example.coredata.models.request.ErrorRequest
import com.example.coredata.models.request.SuccessRequest
import com.picpay.desafio.android.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val adapter by lazy {
        UserListAdapter()
    }
    private val viewModel by viewModel<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        load()
    }

    private fun load() {
        viewModel.getUsers()
    }

    private fun init() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        user_list_progress_bar.show()
        initObservers()
    }

    private fun initObservers() {
        viewModel.viewState().listen(this) {
            when (it) {
                is SuccessRequest -> successRequest(it.data)
                is ErrorRequest -> errorRequest()
            }
        }
    }

    private fun successRequest(users: List<com.example.coredata.models.User>?) {
        user_list_progress_bar.hide()
        if (users != null) {
            adapter.users = users
        }
    }


    private fun errorRequest() {
        user_list_progress_bar.show()
        recyclerView.hide()
        toast(getString(R.string.error))
    }

}
