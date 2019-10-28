package com.jonnyb.rndm.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jonnyb.rndm.Interfaces.ThoughtOptionsClickListener
import com.jonnyb.rndm.Model.Thought
import com.jonnyb.rndm.R
import com.jonnyb.rndm.Utilities.NUM_LIKES
import com.jonnyb.rndm.Utilities.THOUGHTS_REF
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by jonnyb on 10/11/17.
 */
class ThoughtsAdapter(val thoughts: ArrayList<Thought>, val thoughtOptionsListener: ThoughtOptionsClickListener,  val itemClick: (Thought) -> Unit) : RecyclerView.Adapter<ThoughtsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindThought(thoughts[position])
    }

    override fun getItemCount(): Int {
        return thoughts.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.thought_list_view, parent, false)
        return ViewHolder(view, itemClick)
    }

    inner class ViewHolder(itemView: View?, val itemClick: (Thought) -> Unit) : RecyclerView.ViewHolder(itemView) {

        val username = itemView?.findViewById<TextView>(R.id.listViewUsername)
        val timestamp = itemView?.findViewById<TextView>(R.id.listViewTimestamp)
        val thoughtTxt = itemView?.findViewById<TextView>(R.id.listViewThoughtTxt)
        val numLikes = itemView?.findViewById<TextView>(R.id.listVIewNumLikesLbl)
        val likesImage = itemView?.findViewById<ImageView>(R.id.listViewLikesImage)
        val numComments = itemView?.findViewById<TextView>(R.id.numCommentsLbl)
        val optionsImage = itemView?.findViewById<ImageView>(R.id.thoughtOptionsImage)

        fun bindThought(thought: Thought) {

            optionsImage?.visibility = View.INVISIBLE
            username?.text = thought.username
            thoughtTxt?.text = thought.thoughtTxt
            numLikes?.text = thought.numLikes.toString()
            numComments?.text = thought.NumComments.toString()

            val dateFormatter = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
            val dateString = dateFormatter.format(thought.timestamp)
            timestamp?.text = dateString
            itemView.setOnClickListener { itemClick(thought) }

            likesImage?.setOnClickListener {
                FirebaseFirestore.getInstance().collection(THOUGHTS_REF).document(thought.documentId)
                        .update(NUM_LIKES, thought.numLikes + 1)
            }

            if (FirebaseAuth.getInstance().currentUser?.uid == thought.userId) {
                optionsImage?.visibility = View.VISIBLE
                optionsImage?.setOnClickListener {
                    thoughtOptionsListener.thoughtOptionsMenuClicked(thought)
                }
            }

        }

    }


















}