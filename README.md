Taller App Engine
===============

API REST de grupos de música y su valoración a partir de votos positivos y negativos

Instalación
===========

Este proyecto usa App Engine con el Datastore que provee Google, por lo que para hacerlo funcionar teneis que tener el plugin de Google instalado además de el de Git para Eclipse

1-Importar el proyecto como un proyecto Git

2-Añadir la URL https://github.com/alearnaiz/TallerAppEngine.git o usar el proyecto clonado si ya os lo habías bajado antes

3-Cuando termine de bajarse seleccionar que el proyecto ya existe. Si todo esta bien, ya tendréis el proyecto montado

Uso
===

Para comprobar que funciona tenéis que arrancarlo y usar alguna herramienta o haceros un cliente que haga peticiones al servidor (App Engine). Yo aconsejo usar la app "Advance Rest Client" para Chrome.

Los datos que tratamos serán en `JSON` Y las URLs que hay son:

*/api/grupos*
* GET: Obtiene todos los grupos que hay creados
* POST: Crea un grupo, para ello pasarle un JSON en el cuerpo del mensaje del tipo `{"nombre":"Queen", "votosPositivos":50000, "votosNegativos":1}`, 404 en caso de pasarle mal el JSON
* DELETE: Borra todos los grupos almacenados.

*/api/grupos/{nombreGrupo}*
* GET: Obtiene el grupo cuyo nombre coincida con {nombreGrupo}, 404 en caso de no encontrarlo
* PUT: Actualiza el grupo cuyo nombre coincida con {nombreGrupo}, para ello pasarle un JSON en el cuerpo del mensaje del tipo `{"nombre":"Queen", "votosPositivos":300, "votosNegativos":2}`, 404 en caso de no encontrarlo o pasarle mal el JSON
* DELETE: Borra el grupo cuyo nombre coincida con {nombreGrupo}, 404 en caso de no encontrarlo



A parte de la API REST cuenta con un servicio POST para la carga inicial de datos a través de Twitter, en la que pasándole nombres de grupos te saca de cada uno los votos positivos y votos negativos a raíz de los últimos tweets que contengan la palabra del grupo y palabras que nosotros consideremos positivas ("bueno", "buen", "gusta", "genial", "estupendo", "nice", "genial", "maravilloso", "maravillosa", "buena", "precioso", "preciosa") a las que le daremos un voto positivo como a tweets que contengan palabras que nosotros consideremos negativo ("malo", "mala", "feo", "fea", "horrible", "terrible", "inutil", "feisimo", "feisima", "nefasta", "nefasto", "odio") a las que le daremos un voto negativo.

*/api/Carga*

* POST: Crea un conjunto de grupos, para ello pasarle un array de JSON en el cuerpo del mensaje del tipo `[{"nombre": "sabina"}, {"nombre": "mecano"}].`, 404 en caso de pasarle mal el JSON

Cliente
=======

Se ha creado un archivo `index.html` que hace uso de jQuery, con el que puedes crear datos para la API y consumirlos. No sé muy bien la razón pero solo funciona en IE, de todos modos con la app que pusimos anteriormente va sin problema.
