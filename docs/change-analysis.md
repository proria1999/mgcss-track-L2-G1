1. ¿Qué métodos del dominio se ven afectados? 
SolicitudService.cambiarEstado(Long, EstadoSolicitud): Se debe modificar o flexibilizar la validación de negocio actual que lanza una excepción al intentar cambiar el estado de una solicitud cuando se encuentra "CERRADA".

2. ¿Qué reglas actuales cambian? 
Reapertura permitida: La regla estricta de cierre final se elimina; ahora una solicitud en estado "CERRADA" puede volver a transicionar al estado "EN_PROCESO".
Trazabilidad obligatoria: Se añade una nueva regla donde el sistema debe registrar y mantener un histórico ordenado con todos los cambios de estado.

3. ¿Qué tests deberían romperse? 
El test unitario  noCambiarEstadoSiYaEstaCerrada() de la clase "SolicitudServiceTest" fallará inmediatamente, ya que está programado explícitamente para esperar una "IllegalStateException" al intentar modificar una solicitud cerrada.

4. ¿Qué parte del modelo debe extenderse? 
Extensión de "SolicitudEntity": La entidad core debe ampliarse para incluir una lista interna o colección que albergue las instancias de los cambios de estado.

5. ¿Qué impacto tiene en persistencia?
El esquema de la base de datos se verá alterado
Mapeos JPA: Añadiremos anotaciones @ElementCollection en SolicitudEntity para que Hibernate gestione el guardado automático del historial al salvar la solicitud

6. Justificar el diseño estructura de histórico 
Hemos añadido el historial de estado en SolicitudEntity ya que en esa clase tenemos implementado el método que cambia el estado. Por lo que hemos decidido que al llamar a ese método se guarde en un historial de forma implícita y asi al cambiar de estado también se está guardando en el historial. En el constructor también hemos llamado a este método para asignar el estado inicial y guardarlo en el historial.