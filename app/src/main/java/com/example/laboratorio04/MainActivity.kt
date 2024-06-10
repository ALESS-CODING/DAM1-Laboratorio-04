package com.example.laboratorio04

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.laboratorio04.Entity.Total
import com.example.laboratorio04.Implements.ImplementsTotal
import com.example.laboratorio04.Interface.ITotal
import com.example.laboratorio04.utilidad.Util

class MainActivity : AppCompatActivity() {

    //Declaramos los Controles
    private lateinit var cmbProducto : Spinner
    private lateinit var txtPrecio : EditText
    private lateinit var txtCantidad : EditText
    private lateinit var txtSubTotal : EditText
    private lateinit var rgTipoDocumento : RadioGroup
    private lateinit var rbBoleta : RadioButton
    private lateinit var rbFactura : RadioButton
    private lateinit var txtIGV : EditText
    private lateinit var chkDelivery : CheckBox
    private lateinit var txtPrecioEnvio : EditText
    private lateinit var txtTotal : EditText

    private lateinit var btnCalcular: Button
    private lateinit var btnLimpiar: Button
    private lateinit var btnSalir: Button

    //Creamos Objetos
    val objTotal: Total = Total()
    val iTotal: ITotal = ImplementsTotal()
    val util: Util = Util()

    //Declaracion de variables
    var precio : Double = 0.0
    var catidad: Int = 0
    var subTotal: Double = 0.0
    var igv: Double = 0.0
    var envio: Double = 0.0
    var total: Double = 0.0

    //Adaptador para combo
    var adapter: ArrayAdapter<String>? = null;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //Comenzamos a crear los controles
        cmbProducto = findViewById(R.id.cmbProducto)
        txtPrecio = findViewById(R.id.txtPrecio)
        txtCantidad = findViewById(R.id.txtCantidad)
        txtSubTotal = findViewById(R.id.txtSubTotal)
        rgTipoDocumento = findViewById(R.id.rgTipoDocumento)
        rbBoleta = findViewById(R.id.rbBoleta)
        rbFactura = findViewById(R.id.rbFactura)
        txtIGV = findViewById(R.id.txtIGV)
        chkDelivery = findViewById(R.id.chkDelivery)
        txtPrecioEnvio = findViewById(R.id.txtPrecioEnvio)
        txtTotal = findViewById(R.id.txtTotalPagar)

        btnCalcular = findViewById(R.id.btnCalcular)
        btnLimpiar = findViewById(R.id.btnLimpiar)
        btnSalir = findViewById(R.id.btnSalir)

        //Bloquendo las cajas
        txtPrecio.isEnabled = false
        txtSubTotal.isEnabled = false
        txtIGV.isEnabled = false
        txtPrecioEnvio.isEnabled = false
        txtTotal.isEnabled = false

        //Creamos adaptador para cargar productos
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, iTotal.CargaProducto())

        //Asignamos  adapter al spiner
        cmbProducto.adapter = adapter

        //Agregado eventos a los controles
        //obtenemos el valor del precio
        cmbProducto.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var fila = position
                when (fila){
                    1 -> precio = 1500.00
                    2 -> precio = 45.00
                    3 -> precio = 1200.00
                    4 -> precio = 1800.00
                    else -> 0.0
                }

                txtPrecio.setText(precio.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        btnCalcular.setOnClickListener (View.OnClickListener {
            if(cmbProducto.selectedItemPosition == 0){
                util.MensajeAlerta(this, "Validacion de datos", "Seleccione un producto ",
                    false, "Aceptar")
                cmbProducto.requestFocus()
            }else if(txtCantidad.getText().isBlank()) {
                util.MensajeAlerta(this, "Validacion de datos", "Ingrese la cantidad ",
                    false, "Aceptar")
                txtCantidad.requestFocus()
            }else if (!rbBoleta.isChecked && !rbFactura.isChecked){
                util.MensajeAlerta(this, "Validacion de datos", "Seleccione tipo de Comprobante ",
                    false, "Aceptar")
                rbBoleta.requestFocus()
            } else {
                //Capturando valores
                catidad = txtCantidad.getText().toString().toInt()

                //Ebnviando los valores
                objTotal.precio = precio
                objTotal.catidad = catidad

                //Realñizamos operaciones
                subTotal = iTotal.CalculaSubTotal(objTotal)
                //enviamos a la clase
                objTotal.subTotal = subTotal

                //Calculamos IGV
                if(rbFactura.isChecked){
                    igv = iTotal.CalculaIGV(objTotal)
                }else igv = 0.0

                objTotal.Igv = igv

                //Calculamos el evio
                if(chkDelivery.isChecked){
                    envio = iTotal.CalculaEnvio(objTotal)
                }else envio = 0.0

                objTotal.envio = envio

                //Calculamos totales
                total = iTotal.CalculaTotal(objTotal)

                objTotal.total = total

                txtTotal.setText(objTotal.total.toString())
                txtSubTotal.setText(objTotal.subTotal.toString())
                txtIGV.setText(objTotal.Igv.toString())

            }

        })

        btnLimpiar.setOnClickListener(View.OnClickListener {
            cmbProducto.setSelection(0)
            txtCantidad.setText("")
            txtPrecio.setText("")
            rgTipoDocumento.clearCheck()
            txtSubTotal.setText("")
            txtTotal.setText("")
            txtPrecioEnvio.setText("")
            chkDelivery.isChecked = false
            cmbProducto.requestFocus()
        })

        btnSalir.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}