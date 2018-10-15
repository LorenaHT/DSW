package modelo;

import java.time.LocalDateTime;

public class Intento {
	private LocalDateTime fechaHora;
	private int orden; //1,2,3,4....
	private int numeroJugado;
	private String mensaje;
	
	public Intento() {
		super();
	}
	
	public Intento(LocalDateTime fechaHora, int orden, int numeroJugado, String mensaje) {
		super();
		this.fechaHora = fechaHora;
		this.orden = orden;
		this.numeroJugado = numeroJugado;
		this.mensaje = mensaje;
	}

	public LocalDateTime getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(LocalDateTime fechaHora) {
		this.fechaHora = fechaHora;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int numeroCorrecto) {
		this.orden = numeroCorrecto;
	}
	public int getNumeroJugado() {
		return numeroJugado;
	}
	public void setNumeroJugado(int numeroJugado) {
		this.numeroJugado = numeroJugado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
