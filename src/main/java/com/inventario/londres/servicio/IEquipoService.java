package com.inventario.londres.servicio;

import java.util.List;

import org.springframework.data.domain.*;

import com.inventario.londres.entidades.Equipo;

public interface IEquipoService {

	public List<Equipo> findAll();
	
	public Page<Equipo> findAll(Pageable pageable);
	
	public void save(Equipo equipo);
	
	public Equipo findOne(Long id);
	
	public void delete(Long id);
	
	public List<Equipo> findAllPorFechaAsc();
}
