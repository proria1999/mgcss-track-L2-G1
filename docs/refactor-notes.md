
Problema: Código duplicado al buscar entidades y uso de excepciones genéricas.
Técnica a aplicar: Extract Method para la duplicación.
Riesgo: Si la forma de buscar una solicitud cambia, habría que modificarlo en 3 sitios distintos (mantenibilidad).
	private SolicitudEntity buscarSolicitudOError(Long solicitudId, String excepcion) {
		return jpaSolicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException(excepcion));
	}