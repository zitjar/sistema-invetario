package com.inventario.londres.repositorio;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.inventario.londres.entidades.Equipo;

public interface IEquipoRepository extends PagingAndSortingRepository<Equipo, Long>{

}
