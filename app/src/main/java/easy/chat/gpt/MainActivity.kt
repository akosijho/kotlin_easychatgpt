package easy.chat.gpt

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val etQuestion = findViewById<EditText>(R.id.etQuestion)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val txtResult = findViewById<TextView>(R.id.txtResponse)

        btnSubmit.setOnClickListener{
            val question = etQuestion.text.toString()
            Toast.makeText(this,question,Toast.LENGTH_SHORT).show()
        }
    }
}