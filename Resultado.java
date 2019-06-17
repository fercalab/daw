package extractor005;

import java.util.Map;

public class Resultado {

	private String contenido;
	private String ruta;
	private String nombre;
	private Map<String, String> listaMeta;
	private static Resultado instanciaResultado;
	
	private Resultado() {
		
	}
	public static Resultado instanciaResultado() {
		
		if(instanciaResultado == null) {
			instanciaResultado = new Resultado();
		}
		return instanciaResultado;
	}
	
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Map<String, String> getListaMeta() {
		return listaMeta;
	}
	public void setListaMeta(Map<String, String> listaMeta) {
		this.listaMeta = listaMeta;
	}
}