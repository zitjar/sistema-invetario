package com.inventario.londres.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventario.londres.entidades.Equipo;
import com.inventario.londres.repositorio.IEquipoRepository;

@Service
public class EquipoSeviceImpl implements IEquipoService{
	
	@Autowired
	private IEquipoRepository equipoRepository;

	@Override
	@Transactional(readOnly=true)
	public List<Equipo> findAll() {
		return (List<Equipo>) equipoRepository.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Equipo> findAll(Pageable pageable) {
		return equipoRepository.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Equipo empleado) {
		equipoRepository.save(empleado);
	}

	@Override
	@Transactional(readOnly=true)
	public Equipo findOne(Long id) {
		return equipoRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		equipoRepository.deleteById(id);
	}

	@Override
	public List<Equipo> findAllPorFechaAsc() {
		return (List<Equipo>) equipoRepository.findAll(Sort.by("fechaCompra").ascending());
	}

}
