package com.ekbana.signinintegration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.tasks.Task
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener



class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        btnLogout.setOnClickListener(this)

    }

    override fun onClick(view: View?) {
        when (view) {
            btnLogout -> {
                mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this) {

                        // ...
                    }
                revokeAccess()
                startActivity(Intent(this, SignInActivity::class.java))

            }

        }



    }

    private fun revokeAccess(){
        mGoogleSignInClient.revokeAccess().addOnCompleteListener{
            //...
        }
    }
}
