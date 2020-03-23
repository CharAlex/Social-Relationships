package com.alexchar_dev.socialrelationships.presentation.utils

import android.text.InputFilter
import android.text.Spanned

class UserNameInputFilter : InputFilter {
    override fun filter(
        p0: CharSequence?,
        p1: Int,
        p2: Int,
        p3: Spanned?,
        p4: Int,
        p5: Int
    ): CharSequence {
        return p0.toString().toLowerCase().replace(' ','_')
    }

}
class UserNameInputAllowedCharacters: InputFilter {
    override fun filter(
        p0: CharSequence,
        p1: Int,
        p2: Int,
        p3: Spanned?,
        p4: Int,
        p5: Int
    ): CharSequence? {
        for (i in p1 until p2) {
            if (!usernameCharacters.contains(p0[i])) {
                return ""
            }
        }
        return null
    }
}
val usernameCharacters = charArrayOf('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','.','_','1','2','3','4','5','6','7','8','9','0')