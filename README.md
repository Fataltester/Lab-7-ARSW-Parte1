### Escuela Colombiana de Ingeniería
### Arquiecturas de Software
## Integrantes:
### Juan David Martínez Mendez
### Santiago Gualdrón Rincón

## Construción de un cliente 'grueso' con un API REST, HTML5, Javascript y CSS3. Parte II.
### Procesos de desarrollo de software - PDSW

![](img/mock2.png)

1. Agregue al canvas de la página un manejador de eventos que permita capturar los 'clicks' realizados, bien sea a través del mouse, o a través de una pantalla táctil. Para esto, tenga en cuenta [este ejemplo de uso de los eventos de tipo 'PointerEvent'](https://mobiforge.com/design-development/html5-pointer-events-api-combining-touch-mouse-and-pen) (aún no soportado por todos los navegadores) para este fin. Recuerde que a diferencia del ejemplo anterior (donde el código JS está incrustado en la vista), se espera tener la inicialización de los manejadores de eventos correctamente modularizado, tal [como se muestra en este codepen](https://codepen.io/hcadavid/pen/BwWbrw).

	<img width="718" height="354" alt="image" src="https://github.com/user-attachments/assets/f7164b2e-b161-40ae-86eb-2ecba5392f47" />


2. Agregue lo que haga falta en sus módulos para que cuando se capturen nuevos puntos en el canvas abierto (si no se ha seleccionado un canvas NO se debe hacer nada):
	1. Se agregue el punto al final de la secuencia de puntos del canvas actual (sólo en la memoria de la aplicación, AÚN NO EN EL API!).

	<img width="726" height="332" alt="image" src="https://github.com/user-attachments/assets/ae52593f-0d9c-4ed4-8f2c-de3c0b9a4ae5" />
	
	2. Se repinte el dibujo.
	<img width="686" height="99" alt="image" src="https://github.com/user-attachments/assets/cfabf8a3-c50b-4e66-ab85-536575a5c17f" />


3. Agregue el botón Save/Update. Respetando la arquitectura de módulos actual del cliente, haga que al oprimirse el botón:

	<img width="969" height="775" alt="image" src="https://github.com/user-attachments/assets/9bbe247c-bede-485b-bbe4-11b7a006ede6" />


4. Agregue el botón 'Create new blueprint', de manera que cuando se oprima: 
	* Se borre el canvas actual.
	* Se solicite el nombre del nuevo 'blueprint' (usted decide la manera de hacerlo).

	<img width="1485" height="561" alt="image" src="https://github.com/user-attachments/assets/47f656c7-ebb5-4133-b5c3-72c8f8efc7ae" />

	<img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/4b36ee25-d833-48e4-adfc-51d2e233a448" />

	<img width="1600" height="676" alt="image" src="https://github.com/user-attachments/assets/7d59097f-699f-4cff-9f4d-e2f5cc8be0e9" />

	Esta opción debe cambiar la manera como funciona la opción 'save/update', pues en este caso, al oprimirse la primera vez debe (igualmente, usando promesas):

	1. Hacer POST al recurso /blueprints, para crear el nuevo plano.

		<img width="1083" height="696" alt="image" src="https://github.com/user-attachments/assets/0d518c05-179f-458b-a6f2-eb23ef09d5f3" />

	2. Hacer GET a este mismo recurso, para actualizar el listado de planos y el puntaje del usuario.

		<img width="1128" height="782" alt="image" src="https://github.com/user-attachments/assets/e645efba-67d7-46e4-a2da-82699d5534af" />

5. Agregue el botón 'DELETE', de manera que (también con promesas):
	* Borre el canvas.

		<img width="1639" height="631" alt="image" src="https://github.com/user-attachments/assets/c718734e-cca6-4a85-a5ef-ea18c1e15676" />

		<img width="1639" height="637" alt="image" src="https://github.com/user-attachments/assets/a8a442ac-474f-43fd-94e0-6bb593cbc2c8" />

	* Haga DELETE del recurso correspondiente.

		<img width="989" height="699" alt="image" src="https://github.com/user-attachments/assets/bce0ac2c-f598-4575-a979-6c3cdb6f0774" />

	* Haga GET de los planos ahora disponibles.

		<img width="1146" height="954" alt="image" src="https://github.com/user-attachments/assets/3f8371f3-6ce2-420c-a146-25b6bac3741b" />

### Criterios de evaluación

1. Funcional
	* La aplicación carga y dibuja correctamente los planos.
	* La aplicación actualiza la lista de planos cuando se crea y almacena (a través del API) uno nuevo.
	* La aplicación permite modificar planos existentes.
	* La aplicación calcula correctamente los puntos totales.
2. Diseño
	* Los callback usados al momento de cargar los planos y calcular los puntos de un autor NO hace uso de ciclos, sino de operaciones map/reduce.
	* Las operaciones de actualización y borrado hacen uso de promesas para garantizar que el cálculo del puntaje se realice sólo hasta cando se hayan actualizados los datos en el backend. Si se usan callbacks anidados se evalúa como R.
	
