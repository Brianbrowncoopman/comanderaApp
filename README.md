Informe de entrega de Evaluación de Proyecto ComanderaApp 

 

Programación II 

Profesora: Carolina Tapia 

Bimestre 5 

Iplacex 

Diciembre 2025 

Brian Brown Coopman 

Github : https://github.com/Brianbrowncoopman/comanderaApp 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

 

Desarrollo del proyecto  

 

Construcción de las clases que solucionan el problema del restaurant 

Para resolver la lógica del cálculo de la cuenta se creó un paquete independiente llamado modelo.kt, dentro de la carpeta Modelo separado de la MainActivity. En este paquete se definieron las clases que representan los elementos principales del problema: los platos del menú, los pedidos de la mesa y la cuenta total. Esta organización permite aplicar programación orientada a objetos y mantener el código más ordenado y fácil de entender.  

La clase ItemMenu modela un plato del restaurante. Contiene dos propiedades inmutables (val): el nombre del plato y su precio unitario como entero en pesos chilenos. Además, en el bloque companion object se definieron constantes para los valores fijos de cada plato (precio_pastel = 12000 y precio_cazuela = 10000) y se crearon dos objetos estáticos, pastel_choclo y cazuela, que representan los únicos platos disponibles en el menú. De esta forma, el resto de la aplicación puede reutilizar estas mismas instancias sin repetir nombres ni precios, cumpliendo con el menú descrito en el enunciado. 
<img width="751" height="366" alt="image" src="https://github.com/user-attachments/assets/3774d2ae-7dfb-4707-b227-1cb0c1e10f4e" />

 

 

 

 

La clase ItemMesa representa un pedido concreto realizado por la mesa sobre un plato específico. Cada ItemMesa almacena una referencia a un ItemMenu y una cantidad entera que indica cuántas unidades de ese plato se pidieron. Dentro de la clase se implementa la función calcularSubtotal(), que retorna el resultado de multiplicar el precio del plato por la cantidad solicitada. Esta función encapsula el cálculo del subtotal y evita que la Activity tenga que repetir esta lógica en distintos lugares. 
<img width="738" height="281" alt="image" src="https://github.com/user-attachments/assets/e9eeb597-91f2-474b-8149-29b3a914958f" />

 

 

 

 

 

 

 

 

 

 

 

La clase CuentaMesa es la encargada de gestionar la cuenta completa de la mesa. Se define con un parámetro mesa (por defecto 1) para identificar el número de mesa. Internamente mantiene una lista mutable privada _items de tipo MutableList<ItemMesa>, donde se almacenan todos los pedidos ingresados. También posee una variable booleana mutable aceptaPropina, inicializada en true, que indica si se debe considerar o no el 10 % de propina. Para manipular la lista se implementaron dos versiones de agregarItem: una que recibe un ItemMenu y una cantidad, y otra que recibe directamente un ItemMesa. La primera versión solo agrega el ítem cuando la cantidad es mayor que cero, creando el objeto ItemMesa y agregándolo a la lista. Sobre esta colección se implementan los métodos calcularTotalSinPropina(), calcularPropina() y calcularTotalConPropina(), que se describen y utilizan más adelante en la Activity. Finalmente, se expone un método getItems() que devuelve la lista, permitiendo a la interfaz reconstruir o limpiar los pedidos cuando sea necesario. 
<img width="727" height="639" alt="image" src="https://github.com/user-attachments/assets/f6edf2e0-a2fd-4772-b1aa-8291b244543a" />
<img width="741" height="295" alt="image" src="https://github.com/user-attachments/assets/1bf90ffe-c3e0-48d9-8c0f-16496126c568" />

 

 

 

 

 

 

 

 

Construcción de la pantalla con ConstraintLayout 

La interfaz de usuario principal se creó en el archivo activity_main.xml usando ConstraintLayout como vista raíz. Esto permite posicionar cada componente mediante restricciones horizontales y verticales hacia otros elementos o hacia el contenedor padre, cumpliendo con el requisito de organizar la pantalla con este tipo de layout. Gracias a este enfoque, la aplicación se adapta mejor a distintos tamaños de pantalla y mantiene un diseño ordenado.  

En la parte superior de la pantalla se construyó un encabezado visual. Se utiliza una vista View a modo de barra de fondo y, encima de ella, un LinearLayout que contiene tres TextView: uno para el nombre de la aplicación, otro para el nombre del curso y otro para la institución. Esta sección sirve de presentación y deja claro el contexto académico de la app. A continuación se añadió un ImageView central con el logo del restaurante o de la aplicación, lo que da identidad gráfica a la pantalla. 

Debajo del logo se encuentran las secciones correspondientes a los dos platos ofrecidos: “Pastel de Choclo” y “Cazuela”. Cada plato se presenta con un TextView para el nombre, un ImageView con la foto del plato y un EditText para que el mesero ingrese la cantidad deseada. Al lado de cada campo de cantidad se ubica un TextView donde se muestra el subtotal de ese plato (cantidad por precio unitario). En la parte inferior se agregan los totales generales: un TextView para el monto total de comida sin propina, otro para el valor de la propina y un último TextView para el total final. Entre estas etiquetas se incluyen también otros TextView que actúan como rótulos (“Comida”, “Propina” y “Total”) para que la información sea más clara. Además, se incorporó un Switch que permite activar o desactivar fácilmente la propina del 10 %. Todo esto se completa con un pie de página (LinearLayout con TextView) que muestra mi nombre y la versión de la aplicación. 
<img width="639" height="494" alt="image" src="https://github.com/user-attachments/assets/e51913a3-724b-432a-85ac-1689bd9f37cb" />

 

 

Construcción de la Activity 

La lógica de interacción y presentación se concentra en la clase MainActivity. En esta clase se declaran las variables que referencian a los componentes de la interfaz: el Switch para calcular la propina, los EditText de cantidades de pastel y cazuela, y los TextView que muestran los subtotales y totales. También se instancia un objeto CuentaMesa, que se encargará de almacenar los pedidos actuales y de realizar los cálculos de montos con y sin propina. 

En el método onCreate se establece el layout principal con setContentView y se llama a inicializarVistas(), donde se enlazan las variables de la Activity con las vistas definidas en el XML mediante findViewById.  
<img width="625" height="397" alt="image" src="https://github.com/user-attachments/assets/56fbd6f5-bcc8-46cf-8d69-9cf5444aa27c" />

 

 

A continuación se ejecuta configurarListeners(), que registra los eventos necesarios para reaccionar a las acciones del usuario. Finalmente se inicializan los textos de los totales llamando a actualizarTotalesUnidad(0, 0) y actualizarTotalesCuenta(), de modo que la pantalla comience mostrando todos los montos en cero. 
<img width="732" height="375" alt="image" src="https://github.com/user-attachments/assets/f5b24f14-5aa0-4d6e-96f0-ae450a88b187" />

 

La Activity implementa varias funciones privadas para mantener la claridad del código. obtenerCantidad(editText: EditText?) lee el texto del campo correspondiente y lo intenta convertir a entero con toIntOrNull(), devolviendo cero en caso de que el usuario deje la caja vacía o ingrese un valor no numérico. Esto evita que la aplicación falle por una excepción de formato. 
<img width="725" height="444" alt="image" src="https://github.com/user-attachments/assets/31d41e0d-bbfa-49d9-9895-71a07c360077" />

 

 

 

 

 

Por otro lado, actualizarTotalesUnidad(totalPastel: Int, totalCazuela: Int) recibe los subtotales individuales y los escribe en sus TextView formateados como moneda.  
<img width="744" height="119" alt="image" src="https://github.com/user-attachments/assets/9eb88ebf-18cd-4d31-b3ee-bcddff501489" />

 

Por último, actualizarTotalesCuenta() consulta directamente a cuentaMesa los valores de calcularTotalSinPropina(), calcularPropina() y calcularTotalConPropina(), y los muestra en pantalla también en formato de pesos chilenos. 
<img width="743" height="284" alt="image" src="https://github.com/user-attachments/assets/c1c0e5a1-3e0f-4c53-b213-096f122abc68" />

 

 

 

Integración de la Activity con las clases modelo 

La integración entre MainActivity y las clases del paquete comanda.kt se realiza principalmente en la función calcularCuenta(). Cada vez que el usuario cambia las cantidades de los platos o el estado del Switch de propina, esta función se encarga de leer las cantidades actuales usando obtenerCantidad y de calcular el subtotal de cada plato multiplicando la cantidad por las constantes precio_pastel y precio_cazuela definidas en ItemMenu. Esos subtotales se pasan a actualizarTotalesUnidad, lo que actualiza de inmediato los campos tvTotalUnidad1 y tvTotalUnidad2 de la interfaz. 

Después de actualizar los subtotales visuales, la Activity sincroniza la cuenta de la mesa con los nuevos datos. Primero se limpia la lista interna de CuentaMesa a través de cuentaMesa.getItems().clear(). Luego, si la cantidad de pastel es mayor que cero, se llama a cuentaMesa.agregarItem(pastel_choclo, cantidadPastel), y de forma análoga se agrega el ítem de cazuela cuando su cantidad es mayor que cero. Esto hace que la lista _items de CuentaMesa represente exactamente los pedidos actuales. Por último, se invoca a actualizarTotalesCuenta(), que pide a CuentaMesa los totales sin propina, la propina y el total con propina usando las funciones ya descritas y refresca la parte inferior de la pantalla. Gracias a este diseño, toda la lógica de cálculo se mantiene concentrada en las clases modelo y la Activity actúa como intermediaria entre la capa visual y los datos. 

 

Gestión de eventos 

La gestión de eventos de usuario se centró en dos tipos principales: cambios de texto en los campos de cantidad y cambios de estado en el Switch de propina. Para los EditText de pastel y cazuela se creó un objeto anónimo que implementa la interfaz TextWatcher. En particular, se usa el método afterTextChanged, que se ejecuta cada vez que el usuario termina de modificar el contenido del campo. Dentro de este método se llama a calcularCuenta(), logrando que al escribir o borrar cantidades se recalculen automáticamente los subtotales y los totales de la cuenta sin necesidad de presionar botones adicionales. Este patrón es una práctica común en Android para reaccionar a cambios de texto en tiempo real.  

Por otro lado, el Switch que decide si se incluye propina se maneja mediante setOnCheckedChangeListener. Cuando el usuario activa o desactiva el Switch, el listener recibe el nuevo estado booleano y lo asigna a la propiedad cuentaMesa.aceptaPropina. A continuación se vuelve a ejecutar calcularCuenta(), que en su parte final llama a actualizarTotalesCuenta() para que el monto de la propina y el total final reflejen inmediatamente el cambio. De esta manera se cumple con el requisito de que los totales se actualicen dinámicamente tanto al variar las cantidades como al cambiar la decisión de incluir la propina del 10 %.  
<img width="662" height="485" alt="image" src="https://github.com/user-attachments/assets/acd9b2e7-743f-4bd0-b8e1-ae642071a61c" />
<img width="730" height="462" alt="image" src="https://github.com/user-attachments/assets/6107c81a-0a06-449c-baa7-f142b700789f" />

 

 

 

 

 

 

 

 

 

 

Formateo de valores monetarios como pesos chilenos 

Para presentar los montos como pesos chilenos se utilizó la clase NumberFormat. En MainActivity se declaró la variable currencyFormat de tipo NumberFormat, inicializada mediante NumberFormat.getCurrencyInstance(Locale("es", "CL")). Con esta configuración, todos los valores enteros de la cuenta se convierten a cadenas de texto en formato de moneda, usando el símbolo $ y el separador de miles correspondiente a Chile. Este objeto se emplea tanto en la función actualizarTotalesUnidad como en actualizarTotalesCuenta, garantizando que los subtotales de cada plato, el total de comida, el monto de propina y el total final se muestren de forma consistente y clara para el mesero.  
<img width="756" height="153" alt="image" src="https://github.com/user-attachments/assets/6f754875-0782-4ec4-85bf-b287495bd51d" />

 

 

 

 

Evidencia de la solución funcionando 

Como parte del informe adjuntare imagenes desde mi teléfono móvil con la aplicación funcionando. 

 

App en estado inicial 
<img width="327" height="326" alt="image" src="https://github.com/user-attachments/assets/a15c8622-ec8b-4ad8-9b4a-70f7e5b01d16" />

 

 

Carga de 12 unidades en Pastel de Choclo 
<img width="288" height="346" alt="image" src="https://github.com/user-attachments/assets/c97179a6-93e9-4b3f-9ab5-0785727cbfc7" />

 

Carga de 34 unidades en Cazuela 
<img width="283" height="387" alt="image" src="https://github.com/user-attachments/assets/750e0cc3-fe5a-4bdd-85e3-3fd4cb7c9603" />

 

 

 

 

Totales en donde se muestra la propina incluida por defecto 
<img width="313" height="454" alt="image" src="https://github.com/user-attachments/assets/c2f1c531-fc3c-43f8-9c3c-254d9059c062" />

 

 

Totales en donde a travez del swish  se deshabilita la propina 
<img width="305" height="435" alt="image" src="https://github.com/user-attachments/assets/297bf415-d925-4fcc-a308-42bfc0294e14" />

