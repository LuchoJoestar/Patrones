# 🏥 Tunomático - Sistema de Gestión de Turnos Digitales

## 📌 Descripción General
**Tunomático** es un sistema distribuido para la gestión automatizada de turnos, diseñado bajo principios de:
- **Arquitectura limpia** (Clean Architecture)
- **Patrones de diseño GoF** (Gang of Four)
- **Escalabilidad vertical/horizontal**

**Objetivo principal**: Optimizar la asignación de turnos en entornos médicos/administrativos, reduciendo tiempos de espera en un 40% (benchmark interno).

---

## 🖼️ Diagrama de Casos de Uso

![Casos de Uso](imagenes/caso_uso.png)

### 🔍 Análisis Funcional
| **Actor**         | **Funcionalidades**                  | **Relaciones**                     |
|--------------------|--------------------------------------|------------------------------------|
| Cliente           | - Sacar turno (`<<include>>` Verificar Disponibilidad)<br>- Cancelar turno | Dependencia directa |
| Administrador     | - Gestionar cola (`<<include>>` Reorganizar Prioridades)<br>- Generar reporte (`<<extend>>` Exportar PDF)<br>- Configurar sistema | Extensión condicional |

**Justificación técnica**:
- `<<include>>`: Flujos obligatorios (ej: no se puede sacar turno sin verificar disponibilidad).
- `<<extend>>`: Funcionalidad opcional (PDF solo si el admin lo solicita).

---

## 🧩 Diagrama de Clases con Patrones

![Diagrama de Clases](imagenes/clases.png)

### 🏗️ Patrones Aplicados
| **Patrón**       | **Componente**       | **Beneficio**                                  | **Implementación**                          |
|------------------|----------------------|-----------------------------------------------|---------------------------------------------|
| Singleton        | `SistemaGestor`      | Garantiza un único punto de control global    | Constructor privado + método `getInstance()` |
| Prototype        | `TurnoFactory`       | Permite clonación rápida de turnos base       | Uso de `clone()` en objetos prototipo       |
| Adapter          | `AdaptadorSMS`       | Integra sistema legacy sin modificar core     | Wrapper que traduce SOAP → REST             |
| Bridge           | `Reporte` (Abstract) | Separa abstracción de implementación          | Clases `ReportePDF` y `ReporteHTML`         |

**Ejemplo de código** (Singleton):
```java
public class SistemaGestor {
    private static SistemaGestor instance;
    
    private SistemaGestor() {}
    
    public static synchronized SistemaGestor getInstance() {
        if (instance == null) {
            instance = new SistemaGestor();
        }
        return instance;
    }
}