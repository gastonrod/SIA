# TPE3 Algoritmos Genéticos

## Compilar

Para compilar el proyecto, alcanza con compilar todos los archivos Java del directorio src hacia adentro.

## Configuración

Hay tres archivos de configuración

* src/engine/config.properties: toda la configuración relativa al algoritmo genético (ver comentarios en el archivo)
* src/rpg/itemsFileLocation.properties: especifica la ruta donde encontrar los archivos tsv con los datos de los equipamientos
* src/rpg/statModifiers.properties: detalla los modificadores de atributos para el personaje

## Correr

Para correr el proyecto, alcanza con correr el método main de la clase Breeder del paquete engine.
Al correr, inicialmente se cargará toda la información de los archivos con los equipamientos.
Una vez que esté cargado, se puede correr sucesivas veces, alterando si se desea los archivos de parámetros del algoritmo y de modificadores de atributos, sin necesidad de recargar los equipamientos.
