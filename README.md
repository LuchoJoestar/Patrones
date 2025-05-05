# Informe Técnico: Sistema de Generación de Credenciales para Eventos  
**Nombre:** Luis Acevedo  
**Patrones de Diseño Aplicados:** Prototype y Singleton  

---

## 1. Introducción  
El sistema permite generar credenciales personalizadas para eventos mediante una plantilla base clonable, garantizando:  
✔ **Consistencia** en el diseño mediante Singleton  
✔ **Eficiencia** en creación de credenciales con Prototype  
✔ **Flexibilidad** para personalización de campos  

---

## 2. Estructura del Sistema  
### Diagrama de Clases  
```plantuml
@startuml
class Credencial {
  - nombre: String
  - cargo: String
  - rut: String
  - diseño: String
  + clone(): Credencial
  + agregarCampo()
}

class PlantillaCredencial {
  - colorFondo: String
  - logo: String
  + crearCredencial()
}

class GestorCredenciales {
  - instancia: GestorCredenciales
  + getInstancia()
  + crearCredencialBase()
}

Credencial ..|> Cloneable
PlantillaCredencial --> Credencial
GestorCredenciales --> PlantillaCredencial
GestorCredenciales --> Credencial
@enduml
