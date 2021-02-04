package motor;


import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;



@ManagedBean(name="mecanismoMotor", eager=true)
@SessionScoped
public class mecanismoMotor implements Serializable{ 
	
	private static final long serialVersionUID = 1L;
	
	/********************************************************/
	/* 	Declaración de atributos del mecanismo del motor	*/
	/********************************************************/
	// Declaración de los estados del motor
	private enum tipoEstado{
		APAGADO, ENCENDIDO, ACELERANDO
	}
	
	
	private tipoEstado estado; 			// Estado actual del motor
	
	private String textoEtiqueta;			// Texto de la etiqueta del estado actual del motor
	private String colorEtiqueta;			// Color de la etiqueta del estado actual del motor
	
	private String textoBotonEncender;	// Texto botón de encender/apagar el motor
	private String colorBotonEncender;	// Color botón de encender/apagar el motor
	
	private String colorBotonAcelerar;	// Color botón de acelerar el motor
	
	/********************************************************/
	/* 				Constructor mecanismoMotor				*/
	/********************************************************/
	public mecanismoMotor() {
		estado = tipoEstado.APAGADO;
		
		textoEtiqueta = "Apagado";
		colorEtiqueta = "red";
		
		textoBotonEncender = "Encender";
		colorBotonEncender = "green";
		
		colorBotonAcelerar = "grey";
	}
	
	/********************************************************/
	/* 		Métodos que definen las acciones del motor 		*/
	/********************************************************/
	
	// Método que enciende el motor
	private void encender() {
		estado = tipoEstado.ENCENDIDO;
		
		textoEtiqueta = "Encendido";
		colorEtiqueta = "green";
		
		textoBotonEncender = "Apagar";
		colorBotonEncender = "red";
		
		colorBotonAcelerar = "blue";
	}
	
	// Método que apaga el motor
	private void apagar() {
		estado = tipoEstado.APAGADO;
		
		textoEtiqueta = "Apagado";
		colorEtiqueta = "red";
		
		textoBotonEncender = "Encender";
		colorBotonEncender = "green";
		
		colorBotonAcelerar = "grey";
	}
	
	// Método que acelera el motor
	private void acelerar() {
		estado = tipoEstado.ACELERANDO;
		
		textoEtiqueta = "Acelerando";
		colorEtiqueta = "blue";
	}
	
	/********************************************************/
	/* 		Métodos manejadores de eventos de los botones 	*/
	/********************************************************/
	
	// Método que obtiene el evento del botón de encender/apagar 
	// el motor y lo enciende/apaga según corresponda
	public void pulsarBotonEncender(ActionEvent e) {
		if(estado == tipoEstado.APAGADO) // Si el motor está apagado lo enciende
			encender();
		else							 // Si el motor está encendido o acelerando lo apaga
			apagar();
			
		System.out.println(estado);
	}
	
	// Método que obtiene el evento del botón de acelerar el motor
	// y acelera si está encendido
	public void pulsarBotonAcelerar(ActionEvent e) {
		if(estado == tipoEstado.ENCENDIDO) // Si está encendido acelera
			acelerar();
		
		System.out.println(estado);
	}
	
	/********************************************************/
	/* 					Métodos GET/SET						*/
	/********************************************************/
	
	// SET/GET Texto etiqueta
	public void setTextoEtiqueta(String textoEtiqueta) {
		this.textoEtiqueta = textoEtiqueta;
	}
	
	public String getTextoEtiqueta() {
		return textoEtiqueta;
	}
	
	// SET/GET Color etiqueta
	public void setColorEtiqueta(String colorEtiqueta) {
		this.colorEtiqueta = colorEtiqueta;
	}
	
	public String getColorEtiqueta() {
		return colorEtiqueta;
	}
	
	// SET/GET Texto boton encender
	public void setTextoBotonEncender(String textoBotonEncender) {
		this.textoBotonEncender = textoBotonEncender;
	}
	
	public String getTextoBotonEncender() {
		return textoBotonEncender;
	}
	
	// SET/GET Color boton encender
	public void setColorBotonEncender(String colorBotonEncender) {
		this.colorBotonEncender = colorBotonEncender;
	}
	
	public String getColorBotonEncender() {
		return colorBotonEncender;
	}
	
	// SET/GET Color boton acelerar
	public void setColorBotonAcelerar(String colorBotonAcelerar) {
		this.colorBotonAcelerar = colorBotonAcelerar;
	}
	
	public String getColorBotonAcelerar() {
		return colorBotonAcelerar;
	}
}