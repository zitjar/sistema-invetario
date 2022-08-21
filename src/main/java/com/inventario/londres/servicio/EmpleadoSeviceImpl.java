package com.inventario.londres.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventario.londres.entidades.Empleado;
import com.inventario.londres.repositorio.IEmpleadoRepository;

@Service
public class EmpleadoSeviceImpl implements IEmpleadoService{
	
	@Autowired
	private IEmpleadoRepository empleadoRepository;

	@Override
	@Transactional(readOnly=true)
	public List<Empleado> findAll() {
		return (List<Empleado>) empleadoRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Empleado> findAll(Pageable pageable) {
		return empleadoRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Empleado empleado) {
		empleadoRepository.save(empleado);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Empleado findOne(Long id) {
		return empleadoRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		empleadoRepository.deleteById(id);
		
	}
	
	public Optional<Empleado> getById(Long id){
		return empleadoRepository.findById(id);
	}
	
	public Optional<Empleado> getByEmail(String email){
		return empleadoRepository.findByEmail(email);
	}
	
	public boolean existsById(Long id) {
		return empleadoRepository.existsById(id);
	}
	
	public boolean existsByEmail(String email) {
		return empleadoRepository.existsByEmail(email);
	}

}
