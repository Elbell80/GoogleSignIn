package com.ekbana.signinintegration

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    var GOOGLE_ACCOUNT = "google_account"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()

        initSignIn()

        populateFields()

        btnLogout.setOnClickListener {
            mGoogleSignInClient.signOut()
                .addOnCompleteListener(this) {
                    val intent = Intent(this@ProfileActivity, SignInActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                }
           // revokeAccess()
            onBackPressed()
        }


    }

    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess().addOnCompleteListener {
            //...
        }
    }

    private fun initSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun populateFields() {
        val googleSignInAccount = intent.getParcelableExtra<GoogleSignInAccount>(GOOGLE_ACCOUNT)

        val name = googleSignInAccount?.displayName
        val email = googleSignInAccount?.email
        val profileId = googleSignInAccount?.id

        txvEmail.text = email
        txvName.text = name
        txvProfileId.text = profileId.toString()
        Picasso.get().load(googleSignInAccount.photoUrl).centerInside().fit().into(imvProfilePicture)
    }
}
