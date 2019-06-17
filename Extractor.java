package extractor005;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class Extractor {
	
	private File file;
	private static Extractor instancia;
	
	private Extractor() {
		
	}
	
	public static Extractor getInstancia() {
		
		if(instancia == null) {
			instancia = new Extractor();
		}
		return instancia;
	}
	
	public void procesaPeticion(String archivo) {
		
		Peticion miPeticion = new Peticion();
		file = new File(archivo);
		Resultado resultado = Resultado.instanciaResultado();
		String nombre = file.getName();
		resultado.setNombre(nombre);
		int inicio = miPeticion.getInicio();
		boolean meta = miPeticion.isMeta();
		boolean todo = miPeticion.isTodo();
		
		if(todo) { 
			extraerTodo(file, resultado, meta);
		}else {
			if(inicio!=0) {
			int fin = miPeticion.getFin();
			extraerNPaginas(file, inicio, fin, resultado, meta);
			}
		}
			
		if(meta && !todo && inicio == 0) extraerMeta(file, resultado);
		
		boolean guardar = miPeticion.isGuardar();
		if(guardar) {
			String ruta = miPeticion.getRuta();
			guardarResultado(resultado, ruta, meta);
		}
	}

public void extraerTodo(File file, Resultado resultado, boolean meta) {
		
		try {
			PDDocument document = PDDocument.load(file);
			PDFTextStripper pdfStripper = new PDFTextStripper();
	        String texto = pdfStripper.getText(document);
	         
   	         resultado.setContenido(texto);
	         System.out.println(texto);
	         if(meta) {
	        	 int numPaginas = document.getNumberOfPages();
	 			if(numPaginas > 3) document.getPage(0);
	 			File tempFile = File.createTempFile("mificherotemporal", ".pdf", null);
	 			document.save(tempFile);
	 			
	 			  BodyContentHandler handler = new BodyContentHandler();
	 		      Metadata metadata = new Metadata();
	 		      FileInputStream inputstream = new FileInputStream(tempFile);
	 		      ParseContext pcontext = new ParseContext();
	 		      PDFParser pdfparser = new PDFParser(); 
	 		      try {
	 				pdfparser.parse(inputstream, handler, metadata, pcontext);
	 			} catch (SAXException e) {
	 				e.printStackTrace();
	 			} catch (TikaException e) {
	 				e.printStackTrace();
	 			}
	 		      
	 		      String[] metadataNames = metadata.names();
	 		      Map<String, String> map = new HashMap<String, String>();
	 		      
	 		      for(String name : metadataNames) {
	 		    	 String metadato =  metadata.get(name);
	 		    	 map.put(name, metadato);
	 		         System.out.println(name+ " : " + metadato);  
	 		      }
	 		      resultado.setListaMeta(map);
	 		      tempFile.deleteOnExit();
	         }
	         
	         document.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void extraerNPaginas(File file, int inicio, int fin, Resultado resultado, boolean meta) {
		
		if(fin == 0)fin = 3;
		if(inicio != -1) {
			try {
				PDDocument document = PDDocument.load(file);
				if(fin < inicio) fin = inicio+(fin-1);
				PDFTextStripper pdfStripper = new PDFTextStripper();
				pdfStripper.setStartPage(inicio);
				pdfStripper.setEndPage(fin);
		        String texto = pdfStripper.getText(document);
		        int numPaginas = document.getNumberOfPages();
		        
	   	         resultado.setContenido(texto); 
	   	         System.out.println(texto);
	   	      if(meta) {
		        	
		 			if(numPaginas > 3) document.getPage(0);
		 			File tempFile = File.createTempFile("mificherotemporal", ".pdf", null);
		 			document.save(tempFile);
		 			
		 			  BodyContentHandler handler = new BodyContentHandler();
		 		      Metadata metadata = new Metadata();
		 		      FileInputStream inputstream = new FileInputStream(tempFile);
		 		      ParseContext pcontext = new ParseContext();
		 		      PDFParser pdfparser = new PDFParser(); 
		 		      try {
		 				pdfparser.parse(inputstream, handler, metadata, pcontext);
		 			} catch (SAXException e) {
		 				e.printStackTrace();
		 			} catch (TikaException e) {
		 				e.printStackTrace();
		 			}
		 		      
		 		      String[] metadataNames = metadata.names();
		 		      Map<String, String> map = new HashMap<String, String>();
		 		      
		 		      for(String name : metadataNames) {
		 		    	 String metadato =  metadata.get(name);
		 		    	 map.put(name, metadato);
		 		         System.out.println(name+ " : " + metadato);  
		 		      }
		 		      resultado.setListaMeta(map);
		 		      tempFile.deleteOnExit();
		         }
		         document.close();	
				
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}else {
			try {
				PDDocument document = PDDocument.load(file);
				PDFTextStripper pdfStripper = new PDFTextStripper();
				int numPaginas = document.getNumberOfPages();
				int comienza = numPaginas-(fin-1);
				pdfStripper.setStartPage(comienza);
				pdfStripper.setEndPage(numPaginas);
		        String texto = pdfStripper.getText(document);
		        
	   	         resultado.setContenido(texto); 
	   	         System.out.println(texto);
	   	      if(meta) {
		 			if(numPaginas > 3) document.getPage(0);
		 			File tempFile = File.createTempFile("mificherotemporal", ".pdf", null);
		 			document.save(tempFile);
		 			
		 			  BodyContentHandler handler = new BodyContentHandler();
		 		      Metadata metadata = new Metadata();
		 		      FileInputStream inputstream = new FileInputStream(tempFile);
		 		      ParseContext pcontext = new ParseContext();
		 		      PDFParser pdfparser = new PDFParser(); 
		 		      try {
		 				pdfparser.parse(inputstream, handler, metadata, pcontext);
		 			} catch (SAXException e) {
		 				e.printStackTrace();
		 			} catch (TikaException e) {
		 				e.printStackTrace();
		 			}
		 		      
		 		      String[] metadataNames = metadata.names();
		 		      Map<String, String> map = new HashMap<String, String>();
		 		      
		 		      for(String name : metadataNames) {
		 		    	 String metadato =  metadata.get(name);
		 		    	 map.put(name, metadato);
		 		         System.out.println(name+ " : " + metadato);  
		 		      }
		 		      resultado.setListaMeta(map);
		 		      tempFile.deleteOnExit();
		         }
		         document.close();
		         
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	public void extraerMeta(File file, Resultado resultado) {
		try {
			PDDocument document = PDDocument.load(file);
			int numPaginas = document.getNumberOfPages();
			if(numPaginas > 3) document.getPage(0);
			File tempFile = File.createTempFile("mificherotemporal", ".pdf", null);
			document.save(tempFile);
			
			  BodyContentHandler handler = new BodyContentHandler();
		      Metadata metadata = new Metadata();
		      FileInputStream inputstream = new FileInputStream(tempFile);
		      ParseContext pcontext = new ParseContext();
		      PDFParser pdfparser = new PDFParser(); 
		      try {
				pdfparser.parse(inputstream, handler, metadata, pcontext);
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (TikaException e) {
				e.printStackTrace();
			}
		      
		      String[] metadataNames = metadata.names();
		      Map<String, String> map = new HashMap<String, String>();
		      
		      for(String name : metadataNames) {
		    	 String metadato =  metadata.get(name);
		    	 map.put(name, metadato);
		         System.out.println(name+ " : " + metadato);  
		      }
		      resultado.setListaMeta(map);
		      tempFile.deleteOnExit();
		      document.close();
		      
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void guardarResultado(Resultado resultado, String ruta, boolean meta) {
		
		String contenido = resultado.getContenido();
		String metadatos = "";
		String nombre = resultado.getNombre();
		String nombreCopia = nombre+"_COPIA";
		String destino = ruta+nombreCopia+".txt";
		if(meta) {
			Map<String, String> map = resultado.getListaMeta();
			
			Iterator<String> it = map.keySet().iterator();
			while(it.hasNext()) {
				String key = (String) it.next();
				String valor = map.get(key);
				metadatos += key+" : "+valor+"\n";
			}
			String text = "METADATOS : \n"+metadatos+"\n TEXTO EXTRAIDO : \n"+contenido;
			try {
				FileWriter copia = new FileWriter(destino);
				copia.write(text);
				copia.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			String text = "TEXTO EXTRAIDO : \n"+contenido;
			try {
				FileWriter copia = new FileWriter(destino);
				copia.write(text);
				copia.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}		
	}
}
