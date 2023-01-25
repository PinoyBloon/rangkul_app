package com.example.rangkul.data.repository

import com.example.rangkul.data.model.CommentData
import com.example.rangkul.data.model.LikeData
import com.example.rangkul.data.model.PostData
import com.example.rangkul.data.model.UserData
import com.example.rangkul.utils.UiState

interface PostRepository {
    fun getPosts(type: String, result: (UiState<List<PostData>>) -> Unit)
    fun addPost(post: PostData, result: (UiState<String>) -> Unit)
    fun getComments(postId: String, result: (UiState<List<CommentData>>) -> Unit)
    fun addComment(comment: CommentData, postId: String, result: (UiState<String>) -> Unit)
    fun getIsPostLiked(postId: String, currentUserId: String, result: (UiState<Boolean>) -> Unit)
    fun addLike(like: LikeData, postId: String, currentUserId: String, result: (UiState<String>) -> Unit)
    fun getSessionData(result: (UserData?) -> Unit)
    fun getPostsCategory(category: String, result: (UiState<List<PostData>>) -> Unit)
    fun getPostsCurrentUser(uid: String, result: (UiState<List<PostData>>) -> Unit)

}