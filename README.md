# TP2

## Correr

Para correr el programa, alcanza con compilarlo y correr el método main de la clase Solver del paquete engine (todo el código fuente se encuentra en el directorio src).

## Algoritmos

Para elegir el algoritmo de búsqueda, debe indicarse en el archivo config.properties del root del proyecto la propiedad METHOD.

## Debug

Es posible habilitar un modo de debug. Para ello se indica TRUE en la propiedad DEBUG del archivo de configuración arriba mencionado. Esto hace que al correr el programa se establezca una interacción estilo terminal a través de la entrada y salida estándar. Aquí se aceptan varios comandos. El comando 'h' despliega la lista de comandos posibles.

## Interoperabilidad

Para correr otros problemas, hace falta incorporar el .jar correspondiente a proyecto y cambiar la instancación del problema y la heurística en la línea 21 del archivo Solver.java del paquete engine.
