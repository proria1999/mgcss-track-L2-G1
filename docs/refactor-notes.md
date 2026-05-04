---SolicitudService---
Problema: Código duplicado al buscar entidades y uso de excepciones genéricas.
Técnica a aplicar: Extract Method para la duplicación.
Riesgo: Si la forma de buscar una solicitud cambia, habría que modificarlo en 3 sitios distintos (mantenibilidad).
Método añadido para solucionar el riesgo
	private SolicitudEntity buscarSolicitudOError(Long solicitudId, String excepcion) {
		return jpaSolicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException(excepcion));
	}
	
--- SolicitudServiceTest --- 
Problema identificado: Código muerto/comentado en la clase de pruebas SolicitudServiceTest.java.  
Métrica asociada: Code Smell (Maintainability).  
Riesgo potencial: El código comentado genera "ruido", dificulta la lectura del archivo y puede causar confusión a otros desarrolladores sobre si esa prueba es necesaria o está obsoleta. Además, si el código comentado es una versión antigua de un test que ya existe (como el de la línea 35), aumenta la carga cognitiva innecesariamente
Hemos eliminado el código muerto.

