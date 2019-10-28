package com.jonnyb.rndm.Model

import java.util.*

/**
 * Created by jonnyb on 10/11/17.
 */
data class Thought constructor(val username: String, val timestamp: Date, val thoughtTxt: String,
                               val numLikes: Int, val NumComments: Int, val documentId: String, val userId: String)