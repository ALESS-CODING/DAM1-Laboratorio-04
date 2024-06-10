package com.example.laboratorio04.Interface

import com.example.laboratorio04.Entity.Total

interface ITotal {

    //Operaciones
    fun CalculaSubTotal (total: Total): Double
    fun CalculaIGV (total: Total): Double
    fun CalculaEnvio (total: Total): Double
    fun CalculaTotal (total: Total): Double

    //funcion que devuelve arreglo de tipo texto
    fun CargaProducto() : List<String>

}