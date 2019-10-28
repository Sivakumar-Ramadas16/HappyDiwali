package com.jonnyb.rndm.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jonnyb.rndm.Interfaces.CommentOptionsClickListener
import com.jonnyb.rndm.Model.Comment
import com.jonnyb.rndm.Model.Thought
import com.jonnyb.rndm.R
import com.jonnyb.rndm.Utilities.NUM_LIKES
import com.jonnyb.rndm.Utilities.THOUGHTS_REF
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jonnyb on 10/17/17.
 */

class CommentsAdapter(val comments: ArrayList<Comment>, val commentOptionsListener: CommentOptionsClickListener) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindComment(comments[position])
    }

    override fun getItemCount(): Int {
        return comments.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.comment_list_view, parent, false)
        return ViewHolder(view)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

        val username = itemView?.findViewById<TextView>(R.id.commentListUsername)
        val timestamp = itemView?.findViewById<TextView>(R.id.commentListTimestamp)
        val commentTxt = itemView?.findViewById<TextView>(R.id.commentListCommentTxt)
        val optionsImage = itemView?.findViewById<ImageView>(R.id.commentOptionsImage)

        fun bindComment(comment: Comment) {

            optionsImage?.visibility = View.INVISIBLE
            username?.text = comment.username
            commentTxt?.text = comment.commentTxt

            val dateFormatter = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
            val dateString = dateFormatter.format(comment.timestamp)
            timestamp?.text = dateString

            if (FirebaseAuth.getInstance().currentUser?.uid == comment.userId) {
                optionsImage?.visibility = View.VISIBLE
                optionsImage?.setOnClickListener {
                    commentOptionsListener.optionsMenuClicked(comment)
                }
            }
        }
    }

















}