package com.jonnyb.rndm.Activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.firebase.firestore.FirebaseFirestore
import com.jonnyb.rndm.R
import com.jonnyb.rndm.Utilities.*
import kotlinx.android.synthetic.main.activity_update_comment.*

class UpdateCommentActivity : AppCompatActivity() {

    lateinit var thoughtDocId: String
    lateinit var commentDocId: String
    lateinit var commentTxt: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_comment)

        thoughtDocId = intent.getStringExtra(THOUGHT_DOC_ID_EXTRA)
        commentDocId = intent.getStringExtra(COMMENT_DOC_ID_EXTRA)
        commentTxt = intent.getStringExtra(COMMENT_TXT_EXTRA)

        updateCommentTxt.setText(commentTxt)
    }

    fun updateCommentClicked(view: View) {
        FirebaseFirestore.getInstance().collection(THOUGHTS_REF).document(thoughtDocId)
                .collection(COMMENTS_REF).document(commentDocId)
                .update(COMMENT_TXT, updateCommentTxt.text.toString())
                .addOnSuccessListener {
                    hideKeyboard()
                    finish()
                }
                .addOnFailureListener { exception ->
                    Log.e("Exception", "could not update comment: ${exception.localizedMessage}")
                }
    }

    private fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (inputManager.isAcceptingText) {
            inputManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }














}
