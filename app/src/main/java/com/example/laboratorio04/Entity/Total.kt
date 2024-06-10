package com.example.laboratorio04.Entity

class Total {

    //Atributos
    var precio : Double = 0.0
    var catidad: Int = 0
    var subTotal: Double = 0.0
    var Igv: Double = 0.0
    var envio: Double = 0.0
    var total: Double = 0.0

    constructor()
    constructor(precio: Double, catidad: Int, subTotal: Double, Igv: Double, envio: Double, total: Double) {
        this.precio = precio
        this.catidad = catidad
        this.subTotal = subTotal
        this.Igv = Igv
        this.envio = envio
        this.total = total
    }

}