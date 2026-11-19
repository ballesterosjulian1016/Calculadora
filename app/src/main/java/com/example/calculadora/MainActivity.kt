package com.example.calculadora

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculadora.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var primerNumero = 0.0
    private var operacion = ""
    private var nuevoNumero = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        this.setContentView(binding.root)

        binding.boton7.setOnClickListener {
            mostrarNumero("7")
        }
        binding.boton8.setOnClickListener {
            mostrarNumero("8")
        }
        binding.boton9.setOnClickListener {
            mostrarNumero("9")
        }

        binding.boton4.setOnClickListener {
            mostrarNumero("4")
        }
        binding.boton5.setOnClickListener {
            mostrarNumero("5")
        }
        binding.boton6.setOnClickListener {
            mostrarNumero("6")
        }
        binding.boton1.setOnClickListener {
            mostrarNumero("1")
        }
        binding.boton2.setOnClickListener {
            mostrarNumero("2")
        }
        binding.boton3.setOnClickListener {
            mostrarNumero("3")
        }
        binding.boton0.setOnClickListener {
            mostrarNumero("0")
        }
        binding.borrartodo.setOnClickListener {
            binding.tvResultado.text = "0"
        }
        binding.botonborrar.setOnClickListener {
            val numero = binding.tvResultado.text.toString()
            val nuevoNumero = numero.dropLast(1)
            if (nuevoNumero.isEmpty()) {
                binding.tvResultado.text = "0"

            } else {
                binding.tvResultado.text = nuevoNumero
            }
        }
        binding.botonmasmenos.setOnClickListener {
            val numero = binding.tvResultado.text.toString()
            if (numero == "0")
                binding.tvResultado.text = "0"
            else if (numero.startsWith("-")) {
                binding.tvResultado.text = numero.drop(1)
            } else {
                binding.tvResultado.text = "-" + numero
            }
        }
        binding.botoncoma.setOnClickListener {
            val numero = binding.tvResultado.text.toString()
            if (nuevoNumero){
                binding.tvResultado.text = "0,"
                nuevoNumero = false
            } else if (!numero.contains(",")){
                binding.tvResultado.append(",")
            }
        }
        binding.botonsuma.setOnClickListener {
            seleccionarOperacion("+")
        }
        binding.botonigual.setOnClickListener {
            if (operacion.isNotEmpty()) {
                val segundoNumero = binding.tvResultado.text.toString()
                    .replace(",", ".")
                    .toDoubleOrNull()
                if (segundoNumero != null) {
                    val resultado = when (operacion) {
                        "+" -> primerNumero + segundoNumero
                        "-" -> primerNumero - segundoNumero
                        "*" -> primerNumero * segundoNumero
                        "/" -> if (segundoNumero != 0.0) {
                            primerNumero / segundoNumero
                        } else {
                            Double.NaN
                        }

                        else -> Double.NaN
                    }

                    mostrarResultado(resultado)
                    operacion = ""
                    nuevoNumero = true
                }
            }
        }
        binding.botonresta.setOnClickListener {
           seleccionarOperacion("-")
        }
        binding.botonmultiplicacion.setOnClickListener {
            seleccionarOperacion("*")
        }
        binding.botondivision.setOnClickListener {
            seleccionarOperacion("/")
        }
        binding.botonporcentaje.setOnClickListener {
            val numero=binding.tvResultado.text.toString()
                .replace(",",".")
                .toDoubleOrNull()
            if (numero != null){
                val porcentaje = if (operacion == "+" || operacion == "-"){
                    primerNumero* numero/100
                } else{
                    numero/100
                }
                mostrarResultado(porcentaje)
            }
        }
        binding.botonraiz?.setOnClickListener {
               calcularCientifica { numero -> kotlin.math.sqrt(numero) }
        }
        binding.botoncuadrado?.setOnClickListener {
            calcularCientifica { numero -> numero * numero }
        }
        binding.botonpi?.setOnClickListener {
            calcularCientifica { Math.PI }
        }
        binding.botonseno?.setOnClickListener {
            calcularCientifica { numero -> kotlin.math.sin(Math.toRadians(numero)) }
        }
        binding.botoncoseno?.setOnClickListener {
            calcularCientifica { numero -> kotlin.math.cos(Math.toRadians(numero)) }
        }
        binding.botontangente?.setOnClickListener {
            calcularCientifica { numero -> kotlin.math.tan(Math.toRadians(numero)) }
        }

    }

    private fun mostrarNumero(numero: String) {
        if (nuevoNumero || binding.tvResultado.text.toString() == "0") {
            binding.tvResultado.text = numero
            nuevoNumero = false
        } else {
            binding.tvResultado.append(numero)
        }

    }

    private fun mostrarResultado(resultado: Double) {

        if (!resultado.isFinite()) {
            binding.tvResultado.text = "Error"
        } else if (resultado % 1.0 == 0.0) {
            binding.tvResultado.text = resultado.toLong().toString()
        } else {
            binding.tvResultado.text = resultado.toString()
                .replace(".", ",")
        }
    }

    private fun seleccionarOperacion(simbolo: String) {
        val numero = binding.tvResultado.text.toString()
            .replace(",", ".")
            .toDoubleOrNull()

        if (numero != null) {
            primerNumero = numero
            operacion = simbolo
            nuevoNumero = true
        }
    }
    private fun calcularCientifica(operacion: (Double) -> Double) {
        val numero = binding.tvResultado.text.toString()
            .replace(",", ".")
            .toDoubleOrNull() ?: return
        val resultado = operacion(numero)
        mostrarResultado(resultado)
    }


}



