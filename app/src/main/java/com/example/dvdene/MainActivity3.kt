package com.example.dvdene

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dvdene.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    lateinit var binding: ActivityMain3Binding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        // Verileri çek ve RecyclerView'a bağla
        val placesList = databaseHelper.getAllPlaces()
        val adapter = PlacesAdapter(placesList)

        binding.recyclerViewPlaces.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPlaces.adapter = adapter
    }
}
