package com.example.rangkul.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.rangkul.R
import com.example.rangkul.ui.createpost.SelectCategoryActivity
import com.example.rangkul.databinding.ActivityMainBinding
import com.example.rangkul.ui.post.PostOptionsBottomSheetFragment
import com.example.rangkul.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PostOptionsBottomSheetFragment.ItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private var justSignUp = false
    private var justLogIn = false
    private var justPublishedPost = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bottom Nav
        val navController = findNavController(R.id.fragment)
        binding.bottomView.setupWithNavController(navController)

        // Show message dialog
        justLogIn = intent.getStringExtra("LOGIN_SUCCESSFUL").toBoolean()
        justSignUp = intent.getStringExtra("SIGNUP_SUCCESSFUL").toBoolean()
        justPublishedPost = intent.getStringExtra("PUBLISH_SUCCESSFUL").toBoolean()
        showMessageDialog()

        // Navigate to Create Post
        binding.fabCreatePost.setOnClickListener {
            val intent = Intent(this, SelectCategoryActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showMessageDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (justLogIn) {
            dialog.setContentView(R.layout.dialog_login_successful)
            dialog.show()
        }

        if (justSignUp) {
            dialog.setContentView(R.layout.dialog_signup_successful)
            dialog.show()
        }

        if (justPublishedPost) {
            dialog.setContentView(R.layout.dialog_published_post_successful)
            dialog.show()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
        }, 2000)
    }

    override fun onBackPressed() {
        if (binding.bottomView.selectedItemId == R.id.homeFragment){
            // Close app (open home window) when onBackPressed, and prevent user navigate back to auth when onBackPress
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
        } else {
            // always back to homeFragment when back is clicked at search/notification/profile fragment
            binding.bottomView.selectedItemId = R.id.homeFragment
        }
    }

    // Handle bottomsheetdialogfragment
    override fun onItemClick(item: String?) {
        toast(item)
    }
}