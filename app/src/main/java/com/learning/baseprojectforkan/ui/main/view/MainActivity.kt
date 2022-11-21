package com.learning.baseprojectforkan.ui.main.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.learning.baseprojectforkan.R
import com.learning.baseprojectforkan.common.data.remote.model.UserModel
import com.learning.baseprojectforkan.common.datastore.DataStoreViewModel
import com.learning.baseprojectforkan.common.dialogs.ChooseAlertDialog
import com.learning.baseprojectforkan.common.dialogs.CustomAlertDialog
import com.learning.baseprojectforkan.common.dialogs.PromptDialog
import com.learning.baseprojectforkan.common.extensions.toastyError
import com.learning.baseprojectforkan.common.utils.Status
import com.learning.baseprojectforkan.databinding.ActivityMainBinding
import com.learning.baseprojectforkan.ui.main.adapter.MainAdapter
import com.learning.baseprojectforkan.ui.main.viewmodel.MainViewModel
import com.orhanobut.logger.Logger
import com.shashank.sony.fancytoastlib.FancyToast
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var dataStoreViewModel: DataStoreViewModel

    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /**     HERE IS THE DATA STORE PREFERENCE CODE * /

        dataStoreViewModel = ViewModelProvider(this)[DataStoreViewModel::class.java]
        dataStoreViewModel.readFromDataStore.observe(this) { myName ->
        binding.tvReadValue.text = myName
        }

        binding.apply {
        btnSave.setOnClickListener {
        val myName = etSaveValue.text.toString()
        dataStoreViewModel.saveToDataStore(myName)
        }
        }
         */


        setupListener()
        setupUI()
        setupObserver()


    }


    private fun setupListener() {
        binding.apply {
            btnSuccess.setOnClickListener {
                CustomAlertDialog.showSuccess(this@MainActivity, getString(R.string.success))
                //PRETTY_LOGGER
                Logger.i("FORKAN Log test")

            }
            btnError.setOnClickListener {
                CustomAlertDialog.showError(this@MainActivity, getString(R.string.err))
            }
            btnWarning.setOnClickListener {
                showLogoutDialog()
            }
            btnHelp.setOnClickListener {
                CustomAlertDialog.showHelp(this@MainActivity, getString(R.string.help))
            }
            btnInfo.setOnClickListener {
                CustomAlertDialog.showInfo(this@MainActivity, getString(R.string.info))
            }
        }
    }

    private fun setupUI() {
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        binding.rvUser.addItemDecoration(
            DividerItemDecoration(
                binding.rvUser.context,
                (binding.rvUser.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvUser.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.users.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    // progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    binding.rvUser.visibility = View.VISIBLE

                }
                Status.LOADING -> {
                    // progressBar.visibility = View.VISIBLE
                    binding.rvUser.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    // progressBar.visibility = View.GONE
                    toastyError(it.message)
                }
            }
        })
    }

    private fun renderList(users: List<UserModel.UserModelItem>) {
        adapter.submitData(users)
        adapter.notifyDataSetChanged()
    }

    private fun showLogoutDialog() {
        ChooseAlertDialog(this)
            .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
            .setAnimationEnable(true)
            .setTitleText(getString(R.string.sign_out))
            .setContentText(getString(R.string.alert_confirm_logout))
            .setNegativeListener(getString(R.string.yes)) { dialog ->
                dialog.dismiss()
                /**System default toast*/
//                  Toast.makeText(this@MainActivity, "Logout Successfully!", Toast.LENGTH_SHORT).show()
                /**FancyToast library*/
                FancyToast.makeText(
                    this, "Logout Successfully!", FancyToast.LENGTH_LONG,
                    FancyToast.INFO, false
                ).show()
                /**Toasty library*/
//                Toasty.success(this@MainActivity, "Logout Successfully!", Toast.LENGTH_SHORT, true)
//                    .show();
            }
            .setPositiveListener(getString(R.string.no)) { dialog ->
                dialog.dismiss()
                /**System default toast*/
//                Toast.makeText(this@MainActivity, "Logout Canceled!", Toast.LENGTH_SHORT).show()
                /**FancyToast library*/
                FancyToast.makeText(
                    this, "Logout Canceled!", FancyToast.LENGTH_LONG,
                    FancyToast.ERROR, false
                ).show()

                /**Toasty library*/
//                Toasty.warning(this@MainActivity, "Logout Canceled!", Toast.LENGTH_SHORT, true)
//                    .show();

            }.show()
    }


}