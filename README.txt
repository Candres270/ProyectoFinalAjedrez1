# Proyecto Final de POO: Juego de Ajedrez

## Descripción general
Este programa de ajedrez es una implementación con todas las funciones con una
interfaz gráfica, validación de movimientos, guardado de partidas y múltiples modos
de juego. Sigue principios orientados a objetos y proporciona un modo de juego y un
modo de visor para analizar las partidas guardadas. desarrollada con Java Swing siguiendo el patrón de arquitectura MVC.

## Características

- 🎮 Dos Modos de Juego:
  - Partida interactiva con temporizador
  - Visor de partidas PGN para estudio
- ⏱️ Reloj de ajedrez con 5 minutos por jugador
- 💾 Guardado de partidas en formato PGN
- 🎵 Efectos de sonido para movimientos y victorias
- 🎨 Interfaz moderna con efectos de gradiente y animaciones

## Requisitos del Sistema

- Java Runtime Environment (JRE) 11 o superior
- Resolución de pantalla mínima: 1200x800
- Dispositivo de salida de audio para efectos de sonido

## Estructura del Proyecto

```
src/
├── Controlador/
│   └── Controlador.java      # Lógica del juego y manejo de eventos
├── Modelo/
│   ├── GestorSonido.java    # Gestión de sonido
│   ├── JugadorAjedrez.java  # Gestión de jugadores
│   ├── Modelo.java          # Modelo central del juego
│   ├── Pieza.java          # Representación de piezas
│   ├── ReglaJuego.java     # Reglas y validación
│   ├── Tablero.java        # Gestión del tablero
│   ├── TableroEstado.java  # Capturas del estado del tablero
│   └── ValidadorMovimiento.java # Validación de movimientos
├── Vista/
│   ├── CasillaTablero.java  # Componente UI de casilla
│   ├── DialogoJugadores.java # Diálogo de entrada de jugadores
│   ├── RelojAjedrez.java   # Componente de reloj
│   └── Vista.java          # Implementación principal de UI
└── Main.java               # Punto de entrada de la aplicación
```


## Implementación del Guardado PGN

El sistema de guardado PGN se implementa mediante las siguientes clases y funcionalidades:

1. **Registro de Movimientos**:
   - Cada movimiento se registra en notación algebraica
   - Se mantiene un historial completo en `Tablero.java`
   - Se valida la sintaxis PGN antes del guardado

2. **Proceso de Guardado**:
   ```java
   // Ejemplo de formato guardado
   [Event "Partida Casual"]
   [Date "2024.01.20"]
   [White "Jugador1"]
   [Black "Jugador2"]
   1. e4 e5
   2. Nf3 Nc6
   ```

3. **Estructura de Archivos**:
   - Ubicación: `src/partidas/`
   - Formato: `partida_YYYYMMDD_HHMMSS.txt`
   - Incluye: Movimientos, marcas de tiempo, resultado

## Principios de POO Aplicados

### 1. Encapsulamiento
- Atributos privados en todas las clases
- Métodos de acceso controlado
- Protección de estado interno

### 2. Herencia
- `CasillaTablero` extiende `JButton`
- Jerarquía de componentes visuales

### 3. Polimorfismo
- Manejo uniforme de piezas
- Comportamiento específico por tipo de pieza

### 4. Abstracción
- Interfaces claras entre componentes
- Separación de responsabilidades

## Implementación de Reglas de Ajedrez

### Validación de Movimientos

1. **Sistema de Coordenadas**:
   ```java
   // Ejemplo de validación de coordenadas
   public boolean esMovimientoValido(int filaOrigen, int columnaOrigen,
                                   int filaDestino, int columnaDestino) {
       // Validación específica por tipo de pieza
   }
   ```

2. **Reglas por Pieza**:
   - Rey: Movimiento de una casilla en cualquier dirección
   - Dama: Combinación de torre y alfil
   - Torre: Movimientos horizontales y verticales
   - Alfil: Movimientos diagonales
   - Caballo: Movimiento en L
   - Peón: Avance frontal y captura diagonal

### Validaciones Especiales

1. **Jaque**:
   - Verificación constante de amenazas al rey
   - Prevención de movimientos que dejan al rey en jaque

2. **Jaque Mate**:
   - Análisis de todos los movimientos posibles
   - Verificación de escape del rey

3. **Movimientos Especiales**:
   - Enroque: Validación de condiciones específicas
   - Captura al paso: Seguimiento de peones
   - Promoción de peón: Manejo de transformación

## Controles

- Click izquierdo: Seleccionar y mover piezas
- Botón Guardar: Almacenar partida actual
- Botón Menú: Volver al menú principal
- Visualización del reloj: Tiempo restante por jugador

## Detalles Técnicos

- Arquitectura MVC
- Framework Java Swing UI
- Sistema personalizado de gestión de sonido
- Soporte formato PGN
- Validación de movimientos en tiempo real
- Gestión de estado para deshacer/rehacer

## Contribuir

Este proyecto es parte de un sistema educativo. Para modificaciones o mejoras, contacte a los administradores del sistema.

## Licencia

Todos los derechos reservados. Este software se proporciona con fines educativos.