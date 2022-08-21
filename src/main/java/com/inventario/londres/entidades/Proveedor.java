package com.inventario.londres.entidades;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name="proveedor")
public class Proveedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String rfc;
	
	@NotEmpty
	private String direccion;
	
	@NotNull
	private String telefono;
	
	@NotEmpty
	private String email;
	
	@NotEmpty
	private String representanteLegal;
	
	private String productos;

	public Proveedor(@NotEmpty String nombre, @NotEmpty String rfc, @NotEmpty String direccion,
			@NotNull String telefono, @NotEmpty String email, @NotEmpty String representanteLegal, String productos) {
		super();
		this.nombre = nombre;
		this.rfc = rfc;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.representanteLegal = representanteLegal;
		this.productos = productos;
	}

	public Proveedor(Long id) {
		super();
		this.id = id;
	}

	public Proveedor(Long id, @NotEmpty String nombre, @NotEmpty String rfc, @NotEmpty String direccion,
			@NotNull String telefono, @NotEmpty String email, @NotEmpty String representanteLegal, String productos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.rfc = rfc;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.representanteLegal = representanteLegal;
		this.productos = productos;
	}

	public Proveedor() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRepresentanteLegal() {
		return representanteLegal;
	}

	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}

	public String getProductos() {
		return productos;
	}

	public void setProductos(String productos) {
		this.productos = productos;
	}

	
}
