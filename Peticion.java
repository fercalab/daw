package extractor005;

public class Peticion {
	
	private boolean meta, guardar, todo;
	private int inicio, fin;
	private String ruta;
	
	public Peticion() {
		
		meta = true;
		guardar = true;
		todo = false;
		inicio = 1;
		fin = 4;
		ruta = "copias/";
	}
	
	 public boolean isMeta() {
		  return meta;
	  }

	  public void setMeta(boolean meta) {
		  this.meta = meta;
	  }

	  public boolean isGuardar() {
		  return guardar;
	  }

	  public void setGuardar(boolean guardar) {
		  this.guardar = guardar;
	  }

	  public boolean isTodo() {
		  return todo;
	  }

	  public void setTodo(boolean todo) {
		  this.todo = todo;
	  }

	  public int getInicio() {
		  return inicio;
	  }

	  public void setInicio(int inicio) {
		  this.inicio = inicio;
	  }

	  public int getFin() {
		  return fin;
	  }

	  public void setFin(int fin) {
		  this.fin = fin;
	  }

	  public String getRuta() {
		  return ruta;
	  }

	  public void setRuta(String ruta) {
		  this.ruta = ruta;
	  }

}
