package com.inventario.londres.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.inventario.londres.entidades.Equipo;
import com.inventario.londres.servicio.IEquipoService;

@Controller
public class IndexController {
	
	@Autowired
	private IEquipoService equipoService;

	@GetMapping({"/","","/index"})
	public String index() {
		return "index";
	}
	
	@GetMapping("equipos-antiguos")
	public String listarEquipoMasAntiguos(Map<String, Object> model) {
		List<Equipo> equipos = equipoService.findAllPorFechaAsc();
		model.put("titulo", "Equipos con más tiempo");
		model.put("equipos",equipos);
		
		return "equipos-antiguos";
	}

}