package com.example.rangkul.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rangkul.data.model.LikeData
import com.example.rangkul.data.model.PostData
import com.example.rangkul.data.model.UserData
import com.example.rangkul.databinding.ActivityCategoryContentBinding
import com.example.rangkul.ui.comment.CommentActivity
import com.example.rangkul.ui.home.PostAdapter
import com.example.rangkul.ui.home.PostViewModel
import com.example.rangkul.utils.UiState
import com.example.rangkul.utils.hide
import com.example.rangkul.utils.show
import com.example.rangkul.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CategoryContentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryContentBinding
    private var selectedCategory = ""
    private val viewModelPost: PostViewModel by viewModels()
    private val viewModelCategoryContent: CategoryContentViewModel by viewModels()
    private val adapterPost by lazy {
        PostAdapter(
            onCommentClicked = { pos, item ->
                val intent = Intent(this, CommentActivity::class.java)
                intent.putExtra("OBJECT_POST", item)
                startActivity(intent)
            },
            onLikeClicked = { pos, item ->
                addLike(item)
            },
            onOptionClicked = { pos, item ->

            },
            onBadgeClicked = { pos, item ->

            },
            getIsPostLikedData = { pos, item ->
                isPostLiked(item)
            }
        )
    }
    private val adapterContent by lazy {
        CategoryContentAdapter(
            onItemClick = { pos, item ->
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()

        // Get selected category data
        selectedCategory = intent.getStringExtra("CATEGORY").toString()
        binding.tvSelectedCategory.text = selectedCategory

        // Handle chip button clicked
        binding.chipPost.setOnClickListener {
            binding.rvPost.adapter = adapterPost
            viewModelPost.getPostsCategory(selectedCategory)
        }
        binding.chipArticle.setOnClickListener {
            binding.rvPost.adapter = adapterContent
            viewModelCategoryContent.getContents(selectedCategory, "Article")
        }
        binding.chipVideo.setOnClickListener {
            binding.rvPost.adapter = adapterContent
            viewModelCategoryContent.getContents(selectedCategory, "Video")
        }

        // Configure Post RecyclerView
        binding.rvPost.adapter = adapterPost
        binding.rvPost.layoutManager = LinearLayoutManager(this)
        binding.rvPost.setHasFixedSize(true)
        binding.rvPost.isNestedScrollingEnabled = true

        // Get post list based on the selected category
        viewModelPost.getPostsCategory(selectedCategory)
        viewModelPost.postCategory.observe(this) {state ->
            when(state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }

                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }

                is UiState.Success -> {
                    binding.progressBar.hide()
                    adapterPost.updateList(state.data.toMutableList())
                }
            }
        }

        // Get Article list based on the selected category
        viewModelCategoryContent.getContents.observe(this) {state ->
            when(state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }

                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }

                is UiState.Success -> {
                    binding.progressBar.hide()
                    adapterContent.updateList(state.data.toMutableList())
                }
            }
        }
    }

    private fun isPostLiked(item: PostData): Boolean {

        viewModelPost.getIsPostLiked(item.postId, currentUserData().userId)
        var isLiked = false

        viewModelPost.getIsPostLiked.observe(this) {state ->
            when(state) {
                is UiState.Loading -> {
//                    binding.progressBar.show()
                }

                is UiState.Failure -> {
                    toast(state.error)
                }

                is UiState.Success -> {
                    isLiked = state.data
                }
            }
        }

        return isLiked
    }

    private fun addLike(item: PostData) {
        // Add Like
        viewModelPost.addLike(
            LikeData(
                likedBy = "",
                likedAt = Date(),
            ), item.postId, currentUserData().userId
        )

        viewModelPost.addLike.observe(this) {state ->
            when(state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }

                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }

                is UiState.Success -> {
                    binding.progressBar.hide()
                    toast(state.data)
                }
            }
        }
    }

    private fun currentUserData(): UserData {
        var user = UserData()
        viewModelPost.getSessionData {
            if (it != null) {
                user = it
            }
        }
        return user
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(com.example.rangkul.R.drawable.ic_back_grey)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}