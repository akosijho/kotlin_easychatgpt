package easy.chat.gpt

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etQuestion = findViewById<EditText>(R.id.etQuestion)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val txtResult = findViewById<TextView>(R.id.txtResponse)

        btnSubmit.setOnClickListener {
            val question = etQuestion.text.toString()
            val ai: ApplicationInfo = applicationContext.packageManager
                .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
            val value = ai.metaData["OPENAI_KEY"]

            val key = value.toString()
            Toast.makeText(this, "Waiting response...", Toast.LENGTH_SHORT).show()
            getResponse(question, key) { response ->
                runOnUiThread {
                    txtResult.text = response
                }

            }
        }
    }

    private fun getResponse(question: String, key: String, callback: (String) -> Unit) {
        val url = "https://api.openai.com/v1/chat/completions"
        val requestBody = """
    {
    "model": "gpt-3.5-turbo",
    "messages": [
      {
        "role": "system",
        "content": "$question"
      }
    ]
    }
        """.trimIndent()

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $key")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("error", "API failed", e)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (body != null) {
                    Log.v("data", body)
                } else {
                    Log.v("data", "empty")
                }
                val jsonObject = JSONObject(body.toString())
                val jsonArray:JSONArray = jsonObject.getJSONArray("choices")

                val tempResult = jsonArray.getJSONObject(0).getJSONObject("message")
                val textResult = tempResult.getString("content")
                callback(textResult)
            }
        })
    }

}