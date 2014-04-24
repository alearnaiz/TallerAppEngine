Taller App Engine
===============

API de grupos de música y su valoración a partir de votos positivos y negativos

Instalación
===========

Este proyecto usa AppEngine, por lo que para hacerlo funcionar teneis que tener el plugin de Google instalado además de el de Git para Eclipse

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
* PUT: Actualiza el grupo cuyo nombre coincida con {nombreGrupo}, 404 en caso de no encontrarlo o pasarle mal el JSON
* DELETE: Borra el grupo cuyo nombre coincida con {nombreGrupo}, 404 en caso de no encontrarlo
