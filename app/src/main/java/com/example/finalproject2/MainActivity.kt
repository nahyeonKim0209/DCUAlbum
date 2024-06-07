package com.example.finalproject2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), ImageAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private var imageList: MutableList<String> = mutableListOf()

    private val READ_MEDIA_IMAGES_PERMISSION_CODE = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        imageAdapter = ImageAdapter(this, imageList, this)
        recyclerView.adapter = imageAdapter

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
            READ_MEDIA_IMAGES_PERMISSION_CODE
        )
    }

    override fun onItemClick(imagePath: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("imagePath", imagePath)
        intent.putStringArrayListExtra("imageList", ArrayList(imageList))
        startActivity(intent)
    }

    private fun loadImagesFromMediaStorage() {
        val projection = arrayOf(android.provider.MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )

        cursor?.use { cursor ->
            while (cursor.moveToNext()) {
                val imagePath = cursor.getString(cursor.getColumnIndexOrThrow(android.provider.MediaStore.Images.Media.DATA))
                imageList.add(imagePath)
                Log.d("MainActivity", "Image Path: $imagePath")
            }
        }
        imageAdapter.notifyDataSetChanged()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_MEDIA_IMAGES_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImagesFromMediaStorage()
            } else {
                Log.d("MainActivity", "접근 거부")
            }
        }
    }
}
