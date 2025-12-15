package cl.brbc.example.comanderaapp.modelo

// ItemMenu.kt

class ItemMenu(
    val nombre: String, // cazueal o pastel de choclo
    val precio: String //    valor de cada uno  En el diagrama es String, pero quizás debería ser Double o Int para cálculos. Usaré String como se indica.
) {
    // El constructor se define directamente en la cabecera de la clase en Kotlin.

    // Opcionalmente, puedes convertir el precio a un Double/Int privado para usarlo en cálculos.
    // private val precioNumerico: Double = precio.toDoubleOrNull() ?: 0.0
}

// ItemMesa.kt

class ItemMesa(
    val itemMenu: ItemMenu,
    var cantidad: Int // Uso 'var' por si la cantidad pudiera modificarse.
) {
    /**
     * Calcula el subtotal multiplicando la cantidad por el precio del ItemMenu.
     * **Nota importante:** Asumo que el 'precio' de ItemMenu es un String que se puede convertir a Double/Int.
     * Si no se puede convertir, devuelve 0.
     */
    fun calcularSubtotal(): Int {
        // Debemos asumir que el precio String de ItemMenu es un valor numérico entero (Int) para que el método devuelva Int.
        // Si el precio fuera Double (decimal), el método debería devolver Double.
        val precioUnitario = itemMenu.precio.toIntOrNull() ?: 0
        return cantidad * precioUnitario
    }
}


// CuentaMesa.kt

class CuentaMesa(
    val mesa: Int // Asumo que el parámetro del constructor 'mesa' es el número/ID de la mesa.
) {
    // El guion bajo en _items sugiere una propiedad privada.
    private val _items: MutableList<ItemMesa> = mutableListOf()

    // Propiedad 'aceptaPropina' con un valor por defecto.
    var aceptaPropina: Boolean = true // El diagrama indica 'Boolean = true'

    // Métodos para agregar artículos

    /**
     * Agrega un nuevo ItemMenu con una cantidad específica, creando un nuevo ItemMesa.
     */
    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        val nuevoItemMesa = ItemMesa(itemMenu, cantidad)
        _items.add(nuevoItemMesa)
    }

    /**
     * Agrega un objeto ItemMesa ya creado.
     */
    fun agregarItem(itemMesa: ItemMesa) {
        _items.add(itemMesa)
    }

    /**
     * Calcula el total de la cuenta sin incluir la propina.
     */
    fun calcularTotalSinPropina(): Int {
        return _items.sumOf { it.calcularSubtotal() }
    }

    /**
     * Calcula la propina (asumiendo un porcentaje, por ejemplo, 10%).
     * **Nota:** El diagrama solo indica 'calcularPropina() : Int'. Asumo una lógica de 10%.
     */
    fun calcularPropina(): Int {
        if (!aceptaPropina) {
            return 0
        }
        val totalSinPropina = calcularTotalSinPropina()
        // Calcula el 10% de propina. Se usa Int, por lo que podría haber truncamiento.
        return (totalSinPropina * 0.10).toInt()
    }

    /**
     * Calcula el total de la cuenta incluyendo la propina.
     */
    fun calcularTotalConPropina(): Int {
        val totalSin = calcularTotalSinPropina()
        val propina = calcularPropina()
        return totalSin + propina
    }
}


fun main() {
    // 1. Definir el menú del restaurante
    val pastelDeChoclo = ItemMenu("Pastel de Choclo", 12000)
    val cazuela = ItemMenu("Cazuela", 10000)

    println("--- Restaurante Chileno 'La Única Mesa' ---")

    // 2. Crear la cuenta para la única mesa (Mesa 1)
    val cuentaMesa1 = CuentaMesa(mesa = 1)

    // 3. Simular la toma de un pedido
    // El mesero agrega 2 Pasteles de Choclo y 1 Cazuela.

    cuentaMesa1.agregarItem(pastelDeChoclo, 2) // 2 x $12.000 = $24.000
    cuentaMesa1.agregarItem(cazuela, 1)        // 1 x $10.000 = $10.000

    // 4. Calcular el monto sin propina
    val totalSinPropina = cuentaMesa1.calcularTotalSinPropina()
    println("Total del pedido (Mesa ${cuentaMesa1.mesa}):")
    println("Monto sin propina: $$totalSinPropina.-")

    // 5. Aplicar la propina (por defecto es true)
    val propina = cuentaMesa1.calcularPropina()
    println("Propina (10%):     $$propina.-")

    // 6. Calcular el total final a pagar
    val totalConPropina = cuentaMesa1.calcularTotalConPropina()
    println("Total a pagar (con propina): $$totalConPropina.-")

    // EJEMPLO DE CLIENTE QUE NO ACEPTA PROPINA
    println("\n--- Cliente NO Acepta Propina ---")
    cuentaMesa1.aceptaPropina = false
    val totalSinPropinaRechazo = cuentaMesa1.calcularTotalSinPropina()
    val propinaRechazo = cuentaMesa1.calcularPropina() // Será 0
    val totalFinalRechazo = cuentaMesa1.calcularTotalConPropina()

    println("Monto sin propina: $$totalSinPropinaRechazo.-")
    println("Propina (Rechazada): $$propinaRechazo.-")
    println("Total a pagar: $$totalFinalRechazo.-")
}