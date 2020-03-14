package com.fsck.k9.ui.managefolders

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.fsck.k9.Account
import com.fsck.k9.activity.K9Activity
import com.fsck.k9.ui.R
import com.fsck.k9.ui.findNavController

class ManageFoldersActivity : K9Activity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayout(R.layout.activity_manage_folders)

        val accountUuid = intent.getStringExtra(EXTRA_ACCOUNT) ?: error("Missing Intent extra '$EXTRA_ACCOUNT'")

        initializeNavController(accountUuid)
    }

    private fun initializeNavController(accountUuid: String) {
        navController = findNavController(R.id.nav_host_fragment)

        val fragmentArguments = bundleOf(
            ManageFoldersFragment.EXTRA_ACCOUNT to accountUuid
        )
        navController.setGraph(R.navigation.navigation_manage_folders, fragmentArguments)

        val appBarConfiguration = AppBarConfiguration(topLevelDestinationIds = emptySet())
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp() || navigateUpBySimulatedBackButtonPress()
    }

    private fun navigateUpBySimulatedBackButtonPress(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private const val EXTRA_ACCOUNT = "account"

        @JvmStatic
        fun launch(activity: Activity, account: Account) {
            val intent = Intent(activity, ManageFoldersActivity::class.java).apply {
                putExtra(EXTRA_ACCOUNT, account.uuid)
            }
            activity.startActivity(intent)
        }
    }
}