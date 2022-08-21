package com.inventario.londres.entidades;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="equipo")
public class Equipo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String modelo;
	
	@NotEmpty
	private String marca;
	
	@NotEmpty
	private String ram;
	
	@NotEmpty
	private String procesador;
	
	@NotEmpty
	private String almacenamiento;
	
	@NotEmpty
	private String tipo;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaCompra;

	@OneToOne
	@JoinColumn(name="empleado")
	private Empleado empleado;
	
	

	public Equipo(Long id, @NotEmpty String modelo, @NotEmpty String marca, @NotEmpty String ram,
			@NotEmpty String procesador, @NotEmpty String almacenamiento, @NotEmpty String tipo,
			@NotNull Date fechaCompra, Empleado empleado) {
		super();
		this.id = id;
		this.modelo = modelo;
		this.marca = marca;
		this.ram = ram;
		this.procesador = procesador;
		this.almacenamiento = almacenamiento;
		this.tipo = tipo;
		this.fechaCompra = fechaCompra;
		this.empleado = empleado;
	}

	public Equipo() {
		super();
	}

	public Equipo(Long id) {
		super();
		this.id = id;
	}

	

	public Equipo(@NotEmpty String modelo, @NotEmpty String marca, @NotEmpty String ram, @NotEmpty String procesador,
			@NotEmpty String almacenamiento, @NotEmpty String tipo, @NotNull Date fechaCompra, Empleado empleado) {
		super();
		this.modelo = modelo;
		this.marca = marca;
		this.ram = ram;
		this.procesador = procesador;
		this.almacenamiento = almacenamiento;
		this.tipo = tipo;
		this.fechaCompra = fechaCompra;
		this.empleado = empleado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getProcesador() {
		return procesador;
	}

	public void setProcesador(String procesador) {
		this.procesador = procesador;
	}

	public String getAlmacenamiento() {
		return almacenamiento;
	}

	public void setAlmacenamiento(String almacenamiento) {
		this.almacenamiento = almacenamiento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(Date fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	
	
}
