package com.example.dvdene

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dvdene.databinding.ItemPlaceBinding

class PlacesAdapter(private val placesList: List<Bilgiler>) :
    RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding =
            ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = placesList[position]

        holder.binding.apply {
            txtYerAdi.text = place.yeradi // Açıklama yerine yer adı eklenecek
            txtAciklama.text = place.aciklama
            val bitmap = BitmapFactory.decodeByteArray(place.image, 0, place.image.size)
            imagePlace.setImageBitmap(bitmap)
        }
    }

    override fun getItemCount(): Int = placesList.size
}
