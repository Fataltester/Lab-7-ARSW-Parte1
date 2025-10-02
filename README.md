### Escuela Colombiana de Ingeniería
### Arquiecturas de Software
## Integrantes:
### Juan David Martínez Mendez
### Santiago Gualdrón Rincón

## Construción de un cliente 'grueso' con un API REST, HTML5, Javascript y CSS3. Parte I.

## Ajustes Backend

1. Trabaje sobre la base del proyecto anterior (en el que se hizo el API REST).
2. Incluya dentro de las dependencias de Maven los 'webjars' de jQuery y Bootstrap (esto permite tener localmente dichas librerías de JavaScript al momento de construír el proyecto):

<img width="513" height="269" alt="image" src="https://github.com/user-attachments/assets/92ab4ac5-d93b-4352-ad85-fb869851da32" />


## Front-End - Vistas

1. Cree el directorio donde residirá la aplicación JavaScript. Como se está usando SpringBoot, la ruta para poner en el mismo contenido estático (páginas Web estáticas, aplicaciones HTML5/JS, etc) es:  

<img width="670" height="867" alt="image" src="https://github.com/user-attachments/assets/63080ddc-9660-440c-a570-a60171cadd6c" />


2. Cree, en el directorio anterior, la página index.html, sólo con lo básico: título, campo para la captura del autor, botón de 'Get blueprints', campo <div> donde se mostrará el nombre del autor seleccionado, [la tabla HTML](https://www.w3schools.com/html/html_tables.asp) donde se mostrará el listado de planos (con sólo los encabezados), y un campo <div> donde se mostrará el total de puntos de los planos del autor. Recuerde asociarle identificadores a dichos componentes para facilitar su búsqueda mediante selectores.

Se presentan todos los componentes de la página html acorde a lo solicitado:

<img width="756" height="754" alt="image" src="https://github.com/user-attachments/assets/85ae73d6-ca8c-44ca-a84b-54cf27775e78" />


3. En el elemento \<head\> de la página, agregue las referencia a las librerías de jQuery, Bootstrap y a la hoja de estilos de Bootstrap. 

<img width="1221" height="452" alt="image" src="https://github.com/user-attachments/assets/fd746cc7-9271-4796-9460-b9744eedc63a" />


4. Suba la aplicación (mvn spring-boot:run), y rectifique:

<img width="696" height="860" alt="image" src="https://github.com/user-attachments/assets/e616e6ef-ac9e-4c3e-af92-660823b3ea1b" />


Al abrir la consola de desarrollador del navegador, NO deben aparecer mensajes de error 404 (es decir, que las librerías de JavaScript se cargaron correctamente).

<img width="550" height="343" alt="image" src="https://github.com/user-attachments/assets/a0d41305-40a4-4578-8e7e-516444f132fa" />

## Front-End - Lógica

1. Ahora, va a crear un Módulo JavaScript que, a manera de controlador, mantenga los estados y ofrezca las operaciones requeridas por la vista. Para esto tenga en cuenta el [patrón Módulo de JavaScript](https://toddmotto.com/mastering-the-module-pattern/), y cree un módulo en la ruta static/js/app.js .

Desarrollamos el módulo app el cual implementa el patrón módulo y realiza las siguientes tareas:
* set al nombre del autor
* carga los planos del autor en caso de error envía alertas
* transofrma los objetos blueprint a pares {name, points}
* Limpia la tabla y el encabezado
* Agrega filas a la tabla acorde a los resultados
* Calcula el total de puntos
* maneja el evento open para cada plano
* se encarga de obtener,dibujar y limpiar los planos en el canva además de cambiar el nombre cada que se actualiza a un nuevo plano.
  
2. Copie el módulo provisto (apimock.js) en la misma ruta del módulo antes creado. En éste agréguele más planos (con más puntos) a los autores 'quemados' en el código.

<img width="1326" height="117" alt="image" src="https://github.com/user-attachments/assets/420c8b83-eae1-4227-bd4b-afe40a9d433a" />

3. Agregue la importación de los dos nuevos módulos a la página HTML (después de las importaciones de las librerías de jQuery y Bootstrap):

 <img width="542" height="73" alt="image" src="https://github.com/user-attachments/assets/561727be-f315-4d28-8a54-caaea1708cdc" />

4. Haga que el módulo antes creado mantenga de forma privada:
    * El nombre del autor seleccionado.
    * El listado de nombre y tamaño de los planos del autor seleccionado. Es decir, una lista objetos, donde cada objeto tendrá dos propiedades: nombre de plano, y número de puntos del plano.

<img width="442" height="103" alt="image" src="https://github.com/user-attachments/assets/1e305098-5405-44dc-8ddf-642a7d2afee9" />

Junto con una operación pública que permita cambiar el nombre del autor actualmente seleccionado.

<img width="582" height="143" alt="image" src="https://github.com/user-attachments/assets/c7e44ce1-e6c1-43d0-9ecc-4b1540063169" />

5. Agregue al módulo 'app.js' una operación pública que permita actualizar el listado de los planos, a partir del nombre de su autor (dado como parámetro). Para hacer esto, dicha operación debe invocar la operación 'getBlueprintsByAuthor' del módulo 'apimock' provisto, enviándole como _callback_ una función que:

    * Tome el listado de los planos, y le aplique una función 'map' que convierta sus elementos a objetos con sólo el nombre y el número de puntos.

<img width="901" height="124" alt="image" src="https://github.com/user-attachments/assets/259329a4-8f28-4fc1-aaaf-b9e57a01e662" />


    * Sobre el listado resultante, haga otro 'map', que tome cada uno de estos elementos, y a través de jQuery agregue un elemento \<tr\> (con los respectvos \<td\>) a la tabla creada en el punto 4. Tenga en cuenta los [selectores de jQuery](https://www.w3schools.com/JQuery/jquery_ref_selectors.asp) y [los tutoriales disponibles en línea](https://www.tutorialrepublic.com/codelab.php?topic=faq&file=jquery-append-and-remove-table-row-dynamically). Por ahora no agregue botones a las filas generadas.

<img width="904" height="250" alt="image" src="https://github.com/user-attachments/assets/865eefa5-a513-4726-a771-7541fb2834ab" />


    * Sobre cualquiera de los dos listados (el original, o el transformado mediante 'map'), aplique un 'reduce' que calcule el número de puntos. Con este valor, use jQuery para actualizar el campo correspondiente dentro del DOM.

<img width="936" height="108" alt="image" src="https://github.com/user-attachments/assets/246a0836-131c-49ab-a809-da691eb6de7e" />


6. Asocie la operación antes creada (la de app.js) al evento 'on-click' del botón de consulta de la página.

<img width="718" height="170" alt="image" src="https://github.com/user-attachments/assets/ff308a1a-c434-4003-8015-76ad2bb96ce3" />


7. Verifique el funcionamiento de la aplicación. Inicie el servidor, abra la aplicación HTML5/JavaScript, y rectifique que al ingresar un usuario existente, se cargue el listado del mismo.

<img width="550" height="351" alt="image" src="https://github.com/user-attachments/assets/e8ff0328-3e10-4f89-bc19-f08f45fe879b" />


## Para la próxima semana

8. A la página, agregue un [elemento de tipo Canvas](https://www.w3schools.com/html/html5_canvas.asp), con su respectivo identificador. Haga que sus dimensiones no sean demasiado grandes para dejar espacio para los otros componentes, pero lo suficiente para poder 'dibujar' los planos.

<img width="1135" height="143" alt="image" src="https://github.com/user-attachments/assets/35061a51-c84e-4569-83fb-c555ee4c55c7" />

9. Al módulo app.js agregue una operación que, dado el nombre de un autor, y el nombre de uno de sus planos dados como parámetros, haciendo uso del método getBlueprintsByNameAndAuthor de apimock.js y de una función _callback_:
    * Consulte los puntos del plano correspondiente, y con los mismos dibuje consectivamente segmentos de recta, haciendo uso [de los elementos HTML5 (Canvas, 2DContext, etc) disponibles](https://www.w3schools.com/html/tryit.asp?filename=tryhtml5_canvas_tut_path)* Actualice con jQuery el campo <div> donde se muestra el nombre del plano que se está dibujando (si dicho campo no existe, agruéguelo al DOM).

<img width="1110" height="713" alt="image" src="https://github.com/user-attachments/assets/e95b1097-225a-43e6-9691-7ca899fed529" />


10. Verifique que la aplicación ahora, además de mostrar el listado de los planos de un autor, permita seleccionar uno de éstos y graficarlo. Para esto, haga que en las filas generadas para el punto 5 incluyan en la última columna un botón con su evento de clic asociado a la operación hecha anteriormente (enviándo como parámetro los nombres correspondientes).

<img width="1262" height="250" alt="image" src="https://github.com/user-attachments/assets/1bc3f4d8-1dc9-4cc2-bf4d-58d97dd01075" />

<img width="529" height="338" alt="image" src="https://github.com/user-attachments/assets/fd922f4a-891f-4685-9411-9b986b71a2ac" />


11. Verifique que la aplicación ahora permita: consultar los planos de un auto y graficar aquel que se seleccione.

<img width="653" height="851" alt="image" src="https://github.com/user-attachments/assets/6c602446-c645-41f0-92bc-80e404329c5c" />

12. Una vez funcione la aplicación (sólo front-end), haga un módulo (llámelo 'apiclient') que tenga las mismas operaciones del 'apimock', pero que para las mismas use datos reales consultados del API REST. Para lo anterior revise [cómo hacer peticiones GET con jQuery](https://api.jquery.com/jquery.get/), y cómo se maneja el esquema de _callbacks_ en este contexto.

Para el apiclient realizamos las mismas operaciones del apimock que es obtener los blueprints por autor o por nombre y autor

<img width="1303" height="494" alt="image" src="https://github.com/user-attachments/assets/84605dc4-54cc-4d0d-9d26-d24d36cdb6e3" />

<img width="1294" height="502" alt="image" src="https://github.com/user-attachments/assets/97f6d5bf-8ee6-454c-906a-7b17515f70ba" />


13. Modifique el código de app.js de manera que sea posible cambiar entre el 'apimock' y el 'apiclient' con sólo una línea de código.

Para esto únicamente se agraga una variable que contenga el apimock o el apiclient dependiendo lo que se requira y sobre esa variable se realizan las consultas.

<img width="889" height="75" alt="image" src="https://github.com/user-attachments/assets/5472d2ce-16ca-4d32-be2a-9c84aea63e39" />

Ejemplo de funcionamiento 

Se tienen los siguiente blueprints en el backend

<img width="1791" height="135" alt="image" src="https://github.com/user-attachments/assets/36d33869-9fed-458d-8953-736e1d6a4eef" />

<img width="868" height="842" alt="image" src="https://github.com/user-attachments/assets/6d812c77-99f1-45e0-9009-a143eb2ef2ac" />


14. Revise la [documentación y ejemplos de los estilos de Bootstrap](https://v4-alpha.getbootstrap.com/examples/) (ya incluidos en el ejercicio), agregue los elementos necesarios a la página para que sea más vistosa, y más cercana al mock dado al inicio del enunciado.

Para los estilos realizamos un archivo css y también estilos de bootstrap para acercarnos al mockup propuesto, este fue el resultado

<img width="1907" height="833" alt="image" src="https://github.com/user-attachments/assets/78bfdcea-7e1e-4de5-8126-9409a4ef33db" />

