package com.example.finalproject2

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetailActivity : AppCompatActivity(), ImageAdapter.OnItemClickListener {

    private lateinit var imageView: ImageView
    private lateinit var recyclerViewThumbnails: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var imageList: List<String>
    private var imagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        imageView = findViewById(R.id.imageView)
        recyclerViewThumbnails = findViewById(R.id.recyclerViewThumbnails)

        imagePath = intent.getStringExtra("imagePath")
        imageList = intent.getStringArrayListExtra("imageList") ?: emptyList()

        loadImage(imagePath)

        recyclerViewThumbnails.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        imageAdapter = ImageAdapter(this, imageList, this)
        recyclerViewThumbnails.adapter = imageAdapter
    }

    override fun onItemClick(imagePath: String) {
        loadImage(imagePath)
    }

    private fun loadImage(imagePath: String?) {
        if (imagePath != null) {
            val bitmap = BitmapFactory.decodeFile(imagePath)
            imageView.setImageBitmap(bitmap)
        }
    }
}