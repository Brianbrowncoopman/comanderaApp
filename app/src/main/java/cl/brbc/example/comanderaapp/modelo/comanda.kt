package cl.brbc.example.comanderaapp.modelo

class ItemMenu(val nombre: String, val precio: Int) {
    companion object {
        const val precio_pastel = 12000
        const val precio_cazuela = 10000

        val pastel_choclo = ItemMenu("Pastel de choclo" , precio_pastel)
        val cazuela = ItemMenu("Cazuela", precio_cazuela)
    }
}

/*
 * 2. Clase ItemMesa (Representa el pedido de la mesa)  arrastra con itemMenu(el nombre y el precio)
 * y guarda en cantidad la cantidad de ese plato
 */
class ItemMesa(val itemMenu: ItemMenu, val cantidad: Int) {

    /*
     * Funcion para calcular el subtotal (multiplica itemMenu.precio(solo el precio de itemMenu) contra la cantidad)
     * y retorna el suctotal (como entero)
     */
    fun calcularSubtotal(): Int {
        return itemMenu.precio * cantidad
    }
}

/*
 * -----------------------------------------------------------
 * 3. Clase CuentaMesa (Gestiona el total de la cuenta) guarda en variable privada el numero de la mesa
 * _item es una lista (array) que almacena los pedidos
 * -----------------------------------------------------------
 */
class CuentaMesa(private val mesa: Int = 1) {

    // +_items : MutableList<ItemMesa>
    private val _items: MutableList<ItemMesa> = mutableListOf()

    // +aceptaPropina : Boolean = true
    var aceptaPropina: Boolean = true


    /*Funcion para agregarItem (arrastra el nombre y el precio con itemMenu y la cantidad de itemMenu con cantidad)*/
    fun agregarItem(itemMenu: ItemMenu, cantidad: Int) {
        if (cantidad > 0) {   /*evalua si la cantidad es mayor a 0 */
            val itemMesa = ItemMesa(itemMenu, cantidad) /*en val itemMesa se almacena el nombre,
                                                         *  el valor y la cantidad(2 parametros(1 de ellos tiene 2 parametros))*/
            _items.add(itemMesa) /**si cantidad es mayor a 0  alm,acena itemMesa en el array _items*/
        }
    }


    fun agregarItem(itemMesa: ItemMesa) {
        _items.add(itemMesa)
    }


    fun calcularTotalSinPropina(): Int {
        return _items.sumOf { it.calcularSubtotal() }
    }


    fun calcularPropina(): Int {
        if (aceptaPropina) {
            val totalSinPropina = calcularTotalSinPropina()
            // 10% de propina
            return totalSinPropina / 10
        }
        return 0
    }


    fun calcularTotalConPropina(): Int {
        return calcularTotalSinPropina() + calcularPropina()
    }


    fun getItems(): MutableList<ItemMesa> = _items
}

