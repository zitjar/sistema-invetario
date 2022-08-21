package com.inventario.londres.servicio;

import java.util.List;

import org.springframework.data.domain.*;

import com.inventario.londres.entidades.Proveedor;

public interface IProveedorService {

	public List<Proveedor> findAll();
	
	public Page<Proveedor> findAll(Pageable pageable);
	
	public void save(Proveedor proveedor);
	
	public Proveedor findOne(Long id);
	
	public void delete(Long id);
}
