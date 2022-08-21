package com.inventario.londres.repositorio;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.inventario.londres.entidades.Empleado;

public interface IEmpleadoRepository extends PagingAndSortingRepository<Empleado, Long>{
	
	Optional<Empleado> findByEmail(String email);
	
	boolean existsByEmail(String email);

}
