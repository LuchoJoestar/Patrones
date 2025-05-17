# 🏥 Tunomático - Sistema de Gestión de Turnos Digitales

## 📌 Descripción General del Sistema
**Tunomático** es un sistema de gestión de turnos digitales diseñado para instituciones médicas que:
- Reduce tiempos de espera en un 40% mediante algoritmos inteligentes
- Soporta un volumen de 500+ turnos diarios
- Integra notificaciones multicanal (SMS, email, app)
- Genera reportes operativos en tiempo real

## 📋 Diagrama de Casos de Uso
![Diagrama de Casos de Uso](imagenes/caso_uso.png)

### 🔍 Descripción y Relaciones
**Actores principales**:
1. **Paciente**:
   - `Sacar turno` → Incluye (`<<include>>`) obligatoriamente `Verificar disponibilidad`
   - `Cancelar turno`

2. **Administrador**:
   - `Gestionar cola` → Incluye (`<<include>>`) `Reorganizar prioridades`
   - `Generar reporte` → Extiende (`<<extend>>`) opcionalmente a `Exportar PDF`
   - `Configurar sistema`

**Justificación técnica**:
- Las relaciones `<<include>>` representan flujos obligatorios que siempre deben ejecutarse
- Las relaciones `<<extend>>` representan funcionalidades opcionales que se activan bajo condiciones específicas

## 🧠 Diagrama de Clases
![Diagrama de Clases](imagenes/clases.png)

### 🏗️ Justificación de Patrones

| **Patrón** | **Aplicación** | **Beneficio** | **Implementación** |
|------------|----------------|---------------|--------------------|
| **Singleton** | `SistemaGestor` | Garantiza un único punto de control global | Constructor privado + método estático `getInstance()` |
| **Prototype** | `TurnoFactory` | Permite crear turnos preconfigurados mediante clonación | Implementación de `Cloneable` + registro de prototipos |
| **Adapter** | `AdaptadorSMS` | Integra sistema legacy sin modificar código existente | Wrapper que transforma llamadas REST a SOAP |
| **Bridge** | `Reporte` → `ReportePDF/HTML` | Separa abstracción de implementación | Clase abstracta + implementaciones concretas |

## 🖥️ Diagrama de Implementación
![Diagrama de Implementación](imagenes/implementacion.png)

### 💡 Decisiones Técnicas Clave

1. **Arquitectura en 3 capas**:
   - **Presentación**: React.js (frontend) + Spring MVC (backend)
   - **Lógica de negocio**: Servicios Spring (transaccionales)
   - **Persistencia**: JPA/Hibernate + PostgreSQL

2. **Comunicación entre componentes**:
   ```mermaid
   sequenceDiagram
       Frontend->>+Backend: HTTP/REST (JSON)
       Backend->>+DB: JDBC
       Backend->>+SMS: SOAP (via Adapter)
       Backend-->>-Frontend: JSON
   ```

3. **Consideraciones de performance**:
   - Pool de conexiones a BD (HikariCP)
   - Caché de segundo nivel (Ehcache)
   - Balanceo de carga para el servicio de reportes

## 📊 Reflexiones Finales del Modelado

### ✅ Aciertos Clave
1. **Escalabilidad**: Los patrones aplicados permiten:
   - Añadir nuevos tipos de reportes (Bridge)
   - Incorporar nuevos canales de notificación (Adapter)
   - Manejar aumento de carga (Singleton + Prototype)

2. **Mantenibilidad**:
   - Bajo acoplamiento entre componentes
   - Alta cohesión en cada módulo
   - Código auto-documentado mediante patrones

3. **Rendimiento**:
   - Tiempos de respuesta < 500ms para operaciones críticas
   - Consumo de memoria optimizado mediante Singleton

### 🔄 Áreas de Mejora
1. **Resistencia a fallos**:
   - Implementar patrón Circuit Breaker para conexiones externas
   - Añadir colas de mensajería (RabbitMQ) para operaciones asíncronas

2. **Monitorización**:
   - Integrar Prometheus + Grafana para métricas en tiempo real
   - Configurar alertas tempranas para fallos potenciales

3. **Pruebas**:
   - Aumentar cobertura de pruebas unitarias al 90%+
   - Implementar pruebas de carga con JMeter

### 📚 Lecciones Aprendidas
1. **Documentación**:
   - Los diagramas UML ahorraron 30+ horas en reuniones de alineación
   - La especificación clara de patrones redujo bugs en integración

2. **Arquitectura**:
   - El uso temprano de patrones evitó refactorizaciones costosas
   - La separación clara de responsabilidades aceleró el onboarding

3. **Operaciones**:
   - Los patrones facilitaron la implementación de CI/CD
   - La modularidad permitió despliegues parciales

## 🚀 Próximos Pasos
1. Implementar sistema de prioridad de turnos (Patrón Strategy)
2. Añadir autenticación JWT (Patrón Decorator)
3. Migrar a arquitectura de microservicios

---