package com.alexchar_dev.socialrelationships.domain.entity

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class FriendRequest(val avatar: String = "", val seen: Boolean = false, @ServerTimestamp val timestamp :java.util.Date = Date(), val uid: String = "", val username: String = "")