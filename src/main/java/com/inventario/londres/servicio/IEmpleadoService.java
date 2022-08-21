package com.inventario.londres.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.*;

import com.inventario.londres.entidades.Empleado;

public interface IEmpleadoService {

	public List<Empleado> findAll();
	
	public Page<Empleado> findAll(Pageable pageable);
	
	public void save(Empleado empleado);
	
	public Empleado findOne(Long id);
	
	public void delete(Long id);
	
	public Optional<Empleado> getById(Long id);
	
	public Optional<Empleado> getByEmail(String email);
	
	public boolean existsById(Long id);
	
	public boolean existsByEmail(String email);
}
