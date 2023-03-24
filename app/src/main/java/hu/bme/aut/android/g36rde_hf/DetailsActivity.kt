package hu.bme.aut.android.g36rde_hf

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.android.g36rde_hf.databinding.ActivityDetailsBinding


class DetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvDetailsDate.text = intent.extras!!.getString("date")
        binding.tvDetailsDetails.movementMethod = ScrollingMovementMethod()
        binding.tvDetailsDetails.text = intent.extras!!.getString("desc")
        binding.tvDetailsAuthor.text = intent.extras!!.getString("auth")
        binding.tvDetailsTitle.text = intent.extras!!.getString("titl")
        binding.tvDetailsSubtitle.text = intent.extras!!.getString("subt")
    }
}
