package com.tony.lab.coffeebotdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.mobile.auth.core.IdentityHandler
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobile.config.AWSConfiguration

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG: String = this::class.java.simpleName
    }
    private var credentialsProvider: AWSCredentialsProvider? = null
    private var awsConfiguration: AWSConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AWSMobileClient.getInstance().initialize(this) {
            Log.e(TAG, "AWSMobileClient is initialized")
            credentialsProvider = AWSMobileClient.getInstance().credentialsProvider
            awsConfiguration = AWSMobileClient.getInstance().configuration

            IdentityManager.getDefaultIdentityManager().getUserID(object : IdentityHandler {
                override fun handleError(exception: Exception?) {
                    Log.e(TAG, "Retrieving identity: ${exception?.message}")
                }

                override fun onIdentityId(identityId: String?) {
                    Log.e(TAG, "Identity = $identityId")
                    val cachedIdentityId = IdentityManager.getDefaultIdentityManager().cachedUserID
                    // Do something with the identity here
                }
            })
        }.execute()
    }
}
