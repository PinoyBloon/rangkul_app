package com.example.rangkul.createpost

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rangkul.R
import com.example.rangkul.databinding.ActivitySelectCategoryBinding
import com.example.rangkul.databinding.ItemChipCategoryBinding
import com.google.android.material.chip.Chip

class SelectCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        setStatusBarColor()
        setupChip()

        binding.chipGroupCategory.setOnCheckedChangeListener { group, _ ->
            val ids = group.checkedChipIds
            for (id in ids) {
                val chip: Chip = group.findViewById(id!!)
                Toast.makeText(this@SelectCategoryActivity, chip.text, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun setupChip() {
        val categoryList =
            arrayListOf("Mental Health", "Body Shaming", "Depression", "Bullying", "Harassment", "Abuse",
            "Addiction", "Health", "Work", "Education", "Family", "Anxiety", "Personality", "Discrimination",
            "Racist", "Friends", "Sara", "Relationships", "Financial", "Traumatic", "Others")
        for (name in categoryList) {
            val chip = createChip(name)
            binding.chipGroupCategory.addView(chip)
        }
    }

    private fun createChip(label: String): Chip {
        val chip = ItemChipCategoryBinding.inflate(layoutInflater).root
        chip.text = label
        return chip
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back_green)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setStatusBarColor() {
        val window = this.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = this.resources.getColor(R.color.greenBackground)
        window.decorView.systemUiVisibility = 0 //Change icon color in status bar
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}