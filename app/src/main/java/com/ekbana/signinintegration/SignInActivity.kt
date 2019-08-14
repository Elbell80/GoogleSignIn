package com.ekbana.signinintegration

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(), GoogleApiClient.OnConnectionFailedListener {

    private var googleApiClient: GoogleApiClient? = null
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()

        btnSignIn.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_SIGN_IN) {
                Log.d("abc", "codeCorrect")
                try {
                    val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                    val account = task.getResult(ApiException::class.java)
                    onLoggedIn(account)

                } catch (e: ApiException) {
                    Log.d("SignIN", "signInResult:failed code=" + e.statusCode)

                }
            }
        }
    }

    private fun onLoggedIn(googleSignInAccount: GoogleSignInAccount?) {

        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra(ProfileActivity().GOOGLE_ACCOUNT, googleSignInAccount)
        startActivity(intent)
        finish()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d("abc", "ConnectionFailed")
    }

    override fun onStart() {
        super.onStart()
        val alreadyLoggedInAccount = GoogleSignIn.getLastSignedInAccount(this)
        if (alreadyLoggedInAccount != null) {
            onLoggedIn(alreadyLoggedInAccount)

        } else {
            Log.d("SIGNIN", "Not Logged In")
        }
    }
}
