# Tunomático - Sistema de Gestión de Turnos Digitales

## Descripción General
Tunomático es un sistema orientado a la gestión eficiente de turnos digitales, dirigido tanto a clientes como a administradores. Implementa principios sólidos de diseño orientado a objetos, así como patrones de diseño reconocidos.

---

## Diagrama de Casos de Uso

![Casos de Uso](imagenes/caso_uso.png)

Este diagrama ilustra las principales funcionalidades del sistema. El actor **Cliente** puede sacar y cancelar turnos, mientras que el **Administrador** gestiona la cola, genera reportes y configura el sistema. Se aplican relaciones `<<include>>` para funcionalidades obligatorias y `<<extend>>` para funcionalidades opcionales, como exportar reportes.

---

## Diagrama de Clases

![Diagrama de Clases](imagenes/clases.png)

El diseño aplica múltiples patrones de diseño:

- **Singleton**: `SistemaGestor`, que mantiene una única instancia centralizada.
- **Prototype**: `TurnoFactory`, que permite crear turnos a través de clonación.
- **Adapter**: `AdaptadorSMS`, que conecta con un sistema de notificación antiguo.
- **Bridge**: `Reporte` y sus implementaciones concretas `ReportePDF` y `ReporteHTML`.

Cada clase tiene visibilidad adecuada, atributos y relaciones claras con cardinalidad definida.

---

## Diagrama de Implementación

![Implementación](imagenes/implementacion.png)

El sistema se implementa en un entorno distribuido:

- Cliente Web y Administrador Web acceden a través de navegadores.
- Un servidor central aloja los componentes lógicos.
- La base de datos almacena los turnos y configuraciones del sistema.
- Se explicitan patrones aplicados en el entorno físico.

---

## Reflexiones Finales

Este trabajo refleja el proceso completo de modelado arquitectónico, desde los requerimientos funcionales hasta la arquitectura técnica. Se destaca la importancia de usar patrones de diseño como base para la escalabilidad, mantenibilidad y claridad del sistema. Además, el uso de UML en sus diferentes niveles facilita la documentación y comprensión del mismo por múltiples actores técnicos y no técnicos.

