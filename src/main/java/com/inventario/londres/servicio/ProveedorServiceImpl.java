package com.inventario.londres.servicio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventario.londres.entidades.Proveedor;
import com.inventario.londres.repositorio.IProveedorRepository;

@Service
public class ProveedorServiceImpl implements IProveedorService{
	
	@Autowired
	private IProveedorRepository proveedorRepo;

	@Override
	@Transactional(readOnly=true)
	public List<Proveedor> findAll() {
		return (List<Proveedor>) proveedorRepo.findAll();
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Proveedor> findAll(Pageable pageable) {
		return proveedorRepo.findAll(pageable);
	}

	@Override
	@Transactional
	public void save(Proveedor proveedor) {
		proveedorRepo.save(proveedor);
		
	}

	@Override
	@Transactional(readOnly=true)
	public Proveedor findOne(Long id) {
		return proveedorRepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		proveedorRepo.deleteById(id);
		
	}

}
