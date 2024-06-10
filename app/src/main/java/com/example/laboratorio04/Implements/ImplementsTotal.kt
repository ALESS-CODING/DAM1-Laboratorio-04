package com.example.laboratorio04.Implements

import android.util.Log
import com.example.laboratorio04.Entity.Total
import com.example.laboratorio04.Interface.ITotal

class ImplementsTotal : ITotal {
    override fun CalculaSubTotal(total: Total): Double {
        try {
            return  total.precio * total.catidad
        }catch (e: Exception) {
            Log.e("Error al calcular SubTotal:", e.toString())
            return 0.0
        }
    }

    override fun CalculaIGV(total: Total): Double {
        try {
            return total.subTotal * 0.18
        } catch (e: Exception){
            Log.e("Error al generar IGV : ", e.message.toString())
            return 0.0
        }
    }

    override fun CalculaEnvio(total: Total): Double {
        try {
            return total.subTotal * 0.05
        } catch (e: Exception){
            Log.e("Error al calculra el envio : ", e.message.toString())
            return 0.0
        }
    }

    override fun CalculaTotal(total: Total): Double {
        try {
            return total.subTotal + total.Igv + total.envio
        } catch (e: Exception){
            Log.e("Error al calculra total : ", e.message.toString())
            return 0.0
        }
    }

    override fun CargaProducto(): List<String> {
        return listOf("Seleccione un producto", "TV LG", "Laptop Lenovo",
            "Memoria USB 8mb", "Iphone 15 pro max")
    }
}