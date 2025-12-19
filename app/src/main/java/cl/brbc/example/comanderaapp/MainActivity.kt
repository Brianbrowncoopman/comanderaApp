package cl.brbc.example.comanderaapp
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cl.brbc.example.comanderaapp.modelo.CuentaMesa
import cl.brbc.example.comanderaapp.modelo.ItemMenu.Companion.cazuela
import cl.brbc.example.comanderaapp.modelo.ItemMenu.Companion.pastel_choclo
import cl.brbc.example.comanderaapp.modelo.ItemMenu.Companion.precio_cazuela
import cl.brbc.example.comanderaapp.modelo.ItemMenu.Companion.precio_pastel
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    /*variables*/
    private var switchCalcularPropina: Switch? = null
    private var etCantidadPastel: EditText? = null
    private var etCantidadCazuela: EditText? = null
    private var tvTotalUnidad1: TextView? = null
    private var tvTotalUnidad2: TextView? = null
    private var tvTotalComida: TextView? = null
    private var tvTotalPropina: TextView? = null
    private var tvTotalNumerico: TextView? = null

    private val cuentaMesa = CuentaMesa()

    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(
        Locale("es", "CL"))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*enableEdgeToEdge()*/
        setContentView(R.layout.activity_main)

        inicializarVistas()

        configurarListeners()

        actualizarTotalesUnidad(0, 0)
        actualizarTotalesCuenta()
    }

    private fun inicializarVistas() {
        switchCalcularPropina = findViewById(R.id.switch1)
        etCantidadPastel = findViewById(R.id.etncantidad1)
        etCantidadCazuela = findViewById(R.id.etncantidad2)

        tvTotalUnidad1 = findViewById(R.id.tvTotalUnidad1)
        tvTotalUnidad2 = findViewById(R.id.tvTotalUnidad2)
        tvTotalComida = findViewById(R.id.tvTotalComida)
        tvTotalPropina = findViewById(R.id.tvTotalPropina)
        tvTotalNumerico = findViewById(R.id.tvTotalNumerico)
    }

    private fun configurarListeners() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int){}
            override fun afterTextChanged(s: Editable?){
                calcularCuenta()
            }
        }

        etCantidadPastel?.addTextChangedListener(textWatcher)
        etCantidadCazuela?.addTextChangedListener(textWatcher)

        switchCalcularPropina?.setOnCheckedChangeListener { _, isChecked ->
            cuentaMesa.aceptaPropina = isChecked
            calcularCuenta()
        }
    }

    private fun obtenerCantidad(editText: EditText?): Int {

        val texto = editText?.text.toString()
        return texto.toIntOrNull() ?: 0
    }

    private fun calcularCuenta() {

        val cantidadPastel = obtenerCantidad(etCantidadPastel)
        val cantidadCazuela = obtenerCantidad(etCantidadCazuela)

        val subTotalPastel = cantidadPastel * precio_pastel
        val subTotalCazuela = cantidadCazuela * precio_cazuela

        actualizarTotalesUnidad(subTotalPastel, subTotalCazuela)

        cuentaMesa.getItems().clear()

        if (cantidadPastel > 0) {
            cuentaMesa.agregarItem(pastel_choclo , cantidadPastel)
        }
        if (cantidadCazuela > 0) {
            cuentaMesa.agregarItem(cazuela, cantidadCazuela)
        }

        actualizarTotalesCuenta()

    }

    private fun actualizarTotalesUnidad(totalPastel: Int, totalCazuela: Int) {
        tvTotalUnidad1?.text = currencyFormat.format(totalPastel)
        tvTotalUnidad2?.text = currencyFormat.format(totalCazuela)
    }

    private fun actualizarTotalesCuenta() {

        val totalComidaSinPropina = cuentaMesa.calcularTotalSinPropina()
        val totalPropina = cuentaMesa.calcularPropina()
        val totalFinal = cuentaMesa.calcularTotalConPropina()

        tvTotalComida?.text = currencyFormat.format(totalComidaSinPropina)
        tvTotalPropina?.text = currencyFormat.format(totalPropina)
        tvTotalNumerico?.text = currencyFormat.format(totalFinal)

    }

}