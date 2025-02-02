package com.example.dvdene

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dvdene.databinding.ActivityMain2Binding
import java.io.ByteArrayOutputStream

class MainActivity2 : AppCompatActivity() {

    private var selectedImage: ByteArray? = null // Görsel için global değişken
    private lateinit var selectImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseHelper = DatabaseHelper(this)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // ActivityResultLauncher tanımlama
        selectImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                    val imageUri: Uri? = result.data?.data
                    binding.imageView3.setImageURI(imageUri) // Görseli göster
                    val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    val outputStream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    selectedImage = outputStream.toByteArray() // Fotoğraf ByteArray olarak kaydedilir
                    Toast.makeText(this, "Fotoğraf seçildi!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Fotoğraf seçimi iptal edildi.", Toast.LENGTH_SHORT).show()
                }
            }

        // Görsel seçmek için tıklama olayı
        binding.imageView3.setOnClickListener {
            pickImageFromGallery()
        }

        // Kaydetme işlemi
        binding.btnkaydet.setOnClickListener {
            val yeradi = binding.txtyeradi.text.toString()
            val aciklama = binding.txtaciklama.text.toString()
            if (selectedImage != null) {
                val result = databaseHelper.insertPlace(yeradi, selectedImage!!, aciklama)
                if (result > 0) {
                    Toast.makeText(this, "Kayıt başarılı!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Kayıt başarısız!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Lütfen bir fotoğraf seçin!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.anasayfadon.setOnClickListener{
            val ıntent=Intent(this,MainActivity::class.java)
            startActivity(ıntent)
        }

        // Sistem çubuğu içeriği
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        selectImageLauncher.launch(intent)
    }
}
