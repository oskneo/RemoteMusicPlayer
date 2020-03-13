package com.gza21.remotemusicplayer.utils

class TextUtils {
    companion object {
        fun isEmpty(text: CharSequence?): Boolean {
            if (text == null) {
                return true
            }
            return text.isEmpty()
        }

        fun isNotEmpty(text: CharSequence?): Boolean {
            return !isEmpty(text)
        }
    }
}