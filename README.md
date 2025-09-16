### Escuela Colombiana de Ingeniería
### Arquitecturas de Software - ARSW 2025-2
### SpringBoot_REST_API_Blueprints_Part2

## Integrantes:
### Juan David Martínez Mendez
### Santiago Gualdrón Rincón

### Parte I

1. Integre al proyecto base suministrado los Beans desarrollados en el ejercicio anterior. Sólo copie las clases, NO los archivos de configuración. Rectifique que se tenga correctamente configurado el esquema de inyección de dependencias con las anotaciones @Service y @Autowired.


2. Modifique el bean de persistecia 'InMemoryBlueprintPersistence' para que por defecto se inicialice con al menos otros tres planos, y con dos asociados a un mismo autor.

<img width="855" height="429" alt="image" src="https://github.com/user-attachments/assets/33cbc4c3-28e2-4bb4-a3cd-88badc25e22c" />


3. Configure su aplicación para que ofrezca el recurso "/blueprints", de manera que cuando se le haga una petición GET, retorne -en formato jSON- el conjunto de todos los planos. Para esto:

	* Modifique la clase BlueprintAPIController teniendo en cuenta el siguiente ejemplo de controlador REST hecho con SpringMVC/SpringBoot:

	<img width="857" height="187" alt="image" src="https://github.com/user-attachments/assets/9a618aac-0355-4528-910b-bade9bfb8290" />

	* Haga que en esta misma clase se inyecte el bean de tipo BlueprintServices (al cual, a su vez, se le inyectarán sus dependencias de persisntecia y de filtrado de puntos).

	<img width="505" height="61" alt="image" src="https://github.com/user-attachments/assets/23744705-c4da-4d48-8eac-b91b7fddbb56" />

	<img width="488" height="127" alt="image" src="https://github.com/user-attachments/assets/5f1fa66b-bd0a-4cb5-a3e1-a776c2f3050c" />
	
 
4. Verifique el funcionamiento de a aplicación lanzando la aplicación con maven:

<img width="696" height="332" alt="image" src="https://github.com/user-attachments/assets/d8c45d80-da9f-4419-8a6f-0452c3286597" />

<img width="965" height="462" alt="image" src="https://github.com/user-attachments/assets/77716ad5-cb0f-42aa-84d2-b56222167b88" />


	Y luego enviando una petición GET a: http://localhost:8080/blueprints. Rectifique que, como respuesta, se obtenga un objeto jSON con una lista que contenga el detalle de los planos suministados por defecto, y que se haya aplicado el filtrado de puntos correspondiente.

<img width="899" height="946" alt="image" src="https://github.com/user-attachments/assets/75608533-8287-4fae-96a7-86d4695d0946" />


5. Modifique el controlador para que ahora, acepte peticiones GET al recurso /blueprints/{author}, el cual retorne usando una representación jSON todos los planos realizados por el autor cuyo nombre sea {author}. Si no existe dicho autor, se debe responder con el código de error HTTP 404. Para esto, revise en [la documentación de Spring](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html), sección 22.3.2, el uso de @PathVariable. De nuevo, verifique que al hacer una petición GET -por ejemplo- a recurso http://localhost:8080/blueprints/juan, se obtenga en formato jSON el conjunto de planos asociados al autor 'juan' (ajuste esto a los nombres de autor usados en el punto 2).

<img width="943" height="187" alt="image" src="https://github.com/user-attachments/assets/f3a254cd-06bc-4b93-9496-c77d2482417b" />

<img width="986" height="236" alt="image" src="https://github.com/user-attachments/assets/ba204005-5d33-48ea-b28d-a3fa2d4f5713" />

<img width="852" height="917" alt="image" src="https://github.com/user-attachments/assets/e491efb8-4c7a-4370-9c9d-8dffabcc1fa9" />

6. Modifique el controlador para que ahora, acepte peticiones GET al recurso /blueprints/{author}/{bpname}, el cual retorne usando una representación jSON sólo UN plano, en este caso el realizado por {author} y cuyo nombre sea {bpname}. De nuevo, si no existe dicho autor, se debe responder con el código de error HTTP 404. 

<img width="1032" height="198" alt="image" src="https://github.com/user-attachments/assets/7ff3d894-81d9-49b9-af44-1b3d60e4ba7c" />

<img width="846" height="885" alt="image" src="https://github.com/user-attachments/assets/f18f05be-9beb-43d1-98ac-0204fcb9586c" />

<img width="845" height="847" alt="image" src="https://github.com/user-attachments/assets/94e1d145-9cb4-4a05-8fa5-f3befa5dcdca" />

### Parte II

1.  Agregue el manejo de peticiones POST (creación de nuevos planos), de manera que un cliente http pueda registrar una nueva orden haciendo una petición POST al recurso ‘planos’, y enviando como contenido de la petición todo el detalle de dicho recurso a través de un documento jSON. Para esto, tenga en cuenta el siguiente ejemplo, que considera -por consistencia con el protocolo HTTP- el manejo de códigos de estados HTTP (en caso de éxito o error):

	```	java
	@RequestMapping(method = RequestMethod.POST)	
	public ResponseEntity<?> manejadorPostRecursoXX(@RequestBody TipoXX o){
        try {
            //registrar dato
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (XXException ex) {
            Logger.getLogger(XXController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla",HttpStatus.FORBIDDEN);            
        }        
 	
	}
	```	

<img width="1034" height="292" alt="image" src="https://github.com/user-attachments/assets/9ca75d68-c4b7-4995-97a1-f89c6546d996" />

2.  Para probar que el recurso ‘planos’ acepta e interpreta
    correctamente las peticiones POST, use el comando curl de Unix. Este
    comando tiene como parámetro el tipo de contenido manejado (en este
    caso jSON), y el ‘cuerpo del mensaje’ que irá con la petición, lo
    cual en este caso debe ser un documento jSON equivalente a la clase
    Cliente (donde en lugar de {ObjetoJSON}, se usará un objeto jSON correspondiente a una nueva orden:

	```	
	$ curl -i -X POST -HContent-Type:application/json -HAccept:application/json http://localhost:8080/blueprints/ -d '{"author": "sergio","points": [{"x": 98,"y": 200},{"x": 150,"y": 170 } ], "name": "bp5"}'
	```

	Con lo anterior, registre un nuevo plano (para 'diseñar' un objeto jSON, puede usar [esta herramienta](http://www.jsoneditoronline.org/)):
	
 	<img width="724" height="103" alt="image" src="https://github.com/user-attachments/assets/34e3afdb-28fe-41a4-99c7-2dea1ff1cb69" />

	Nota: puede basarse en el formato jSON mostrado en el navegador al consultar una orden con el método GET.
	
 	<img width="725" height="220" alt="image" src="https://github.com/user-attachments/assets/54589254-4293-40d6-b48a-2d95077f0745" />

	(NOTA: el ultimo formato JSON, es el dado en el POST anterior)
	Mostrandolo en la plataforma de POSTMAN:

	<img width="1367" height="859" alt="image" src="https://github.com/user-attachments/assets/dd0c836a-8c07-4e8a-baaa-c3b67630e0dc" />


4. Teniendo en cuenta el autor y numbre del plano registrado, verifique que el mismo se pueda obtener mediante una petición GET al recurso '/blueprints/{author}/{bpname}' correspondiente.

	<img width="1367" height="521" alt="image" src="https://github.com/user-attachments/assets/67631151-4b72-4cab-97f3-4c64be1144c3" />

5. Agregue soporte al verbo PUT para los recursos de la forma '/blueprints/{author}/{bpname}', de manera que sea posible actualizar un plano determinado.

	<img width="1105" height="341" alt="image" src="https://github.com/user-attachments/assets/24ce762e-8fc6-4c79-b6b8-b1f9ce5fadc6" />

	<img width="1367" height="644" alt="image" src="https://github.com/user-attachments/assets/dfe9425b-86b0-45da-925a-14d330addbea" />

	<img width="1360" height="519" alt="image" src="https://github.com/user-attachments/assets/77a43d8c-9290-4555-a68d-a1e695986493" />

### Parte III

El componente BlueprintsRESTAPI funcionará en un entorno concurrente. Es decir, atederá múltiples peticiones simultáneamente (con el stack de aplicaciones usado, dichas peticiones se atenderán por defecto a través múltiples de hilos). Dado lo anterior, debe hacer una revisión de su API (una vez funcione), e identificar:

* Qué condiciones de carrera se podrían presentar?
* Cuales son las respectivas regiones críticas?

Ajuste el código para suprimir las condiciones de carrera. Tengan en cuenta que simplemente sincronizar el acceso a las operaciones de persistencia/consulta DEGRADARÁ SIGNIFICATIVAMENTE el desempeño de API, por lo cual se deben buscar estrategias alternativas.

Escriba su análisis y la solución aplicada en el archivo ANALISIS_CONCURRENCIA.txt, cabe aclarar que las preguntas y justificación de la solución están planteadas en el .txt.

1. <img width="995" height="44" alt="image" src="https://github.com/user-attachments/assets/fe9539fb-c148-44b9-8d85-9f0dcc1189c5" />

2. <img width="1010" height="162" alt="image" src="https://github.com/user-attachments/assets/b52e0125-d278-4e79-ade9-76ab50a60244" />

