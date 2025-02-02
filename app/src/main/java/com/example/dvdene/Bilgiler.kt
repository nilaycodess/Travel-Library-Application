package com.example.dvdene

class Bilgiler {
    var id: Int = 0
    var yeradi: String = "" // yeradi özelliği eklendi
    var image: ByteArray = ByteArray(1000)
    var aciklama: String = ""

    // Veri girişi için constructor
    constructor(image: ByteArray, aciklama: String, yeradi: String) {
        this.image = image
        this.aciklama = aciklama
        this.yeradi = yeradi
    }

    // Boş constructor (gerekirse)
    constructor()
}
