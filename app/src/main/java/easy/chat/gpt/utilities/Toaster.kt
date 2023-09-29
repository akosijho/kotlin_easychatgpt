package easy.chat.gpt.utilities

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import easy.chat.gpt.R

object Toaster {
    fun showCustomToast(context: Context,) {
        // Inflate the custom layout for the Toast
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(R.layout.custom_taost, null)

        // Set the text message for the Toast
        val textViewMessage = layout.findViewById<TextView>(R.id.textViewMessage)
        textViewMessage.text = "Requesting..."

        // Create and configure the Toast
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout

        // Show the Toast
        toast.show()
    }
}