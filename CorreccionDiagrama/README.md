### Diagrama de clases

### ❌ **Error 1: Herencia incorrecta entre `Reserva` y `NotificadorCorreo`**

#### ❌ Problema:

En el diagrama, `NotificadorCorreo` aparece heredando de `Reserva`, lo cual **no tiene sentido semántico**.

#### ✅ Corrección en PlantUML:

```plantuml
class Reserva {
  - fecha : Date
  - estado : String
}

class NotificadorCorreo {
  + notificar()
}

Reserva <--x-- NotificadorCorreo
```

#### ✅ Justificación:

`NotificadorCorreo` **no es un tipo de** `Reserva`. Son clases con responsabilidades diferentes: una representa una entidad del sistema (una reserva hecha) y la otra un componente funcional (envío de correos). Esta herencia es incorrecta desde el punto de vista de la lógica de dominio y del principio de sustitución de Liskov (LSP).

---

### ❌ **Error 2: `Usuario` reserva directamente una `Sala`**

#### ❌ Problema:

`Usuario` tiene una relación directa con `Sala` (flecha con etiqueta `reserva`), lo que **viola el principio de bajo acoplamiento**.

#### ✅ Corrección en PlantUML:

```plantuml
Usuario --> SistemaReservas : solicitaReserva()

SistemaReservas --> Sala : reservar()
```

#### ✅ Justificación:

El `Usuario` debe interactuar con el `SistemaReservas`, y este sistema gestiona la lógica de asignar una sala, validar disponibilidad, etc. Así se respeta el **principio de responsabilidad única** y se desacopla al usuario de la implementación de reservas.

---

### ❌ **Error 3: `GestorNotificaciones` hereda de `NotificadorSMS`**

#### ❌ Problema:

`GestorNotificaciones` aparece como una subclase de `NotificadorSMS`, lo que **rompe la lógica de herencia**.

#### ✅ Corrección en PlantUML:

```plantuml
class GestorNotificaciones {
  + enviarCorreo()
  + enviarSMS()
}

class NotificadorCorreo {
  + notificar()
}

class NotificadorSMS {
  + notificar()
}

GestorNotificaciones --> NotificadorCorreo
GestorNotificaciones --> NotificadorSMS
```

#### ✅ Justificación:

`GestorNotificaciones` no es un tipo de `NotificadorSMS`; más bien **los utiliza**. Esto es un caso claro donde debe aplicarse **composición sobre herencia**. Usar herencia aquí viola los principios SOLID (especialmente LSP y OCP) y dificulta la extensión del sistema si se agregan nuevos tipos de notificaciones.

---
