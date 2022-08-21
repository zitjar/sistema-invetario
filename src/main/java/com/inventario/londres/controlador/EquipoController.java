package com.inventario.londres.controlador;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventario.londres.entidades.Empleado;
import com.inventario.londres.entidades.Equipo;
import com.inventario.londres.servicio.IEmpleadoService;
import com.inventario.londres.servicio.IEquipoService;
import com.inventario.londres.util.paginacion.PageRender;
import com.inventario.londres.util.reports.EquiposExporterExcel;
import com.inventario.londres.util.reports.EquiposExporterPdf;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/equipos")
public class EquipoController {

	@Autowired
	private IEquipoService equipoService;
	
	@Autowired
	private IEmpleadoService empleadoService;
	
	@GetMapping("/ver/{id}")
	public String verDatos(@PathVariable(value="id")Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Equipo equipo = equipoService.findOne(id);
		
		if(equipo == null) {
			flash.addFlashAttribute("error","El equipo no existe");
			return "redirect:/equipos/listar";
		}
		model.put("equipo", equipo);
		model.put("titulo", "detalles de equipo "+equipo.getModelo());
		return "equipos/ver";
	}
	
	
	@GetMapping("/listar")
	public String listarEquipos(@RequestParam(name = "page", defaultValue = "0")int page,Model model) {
		Pageable pageRequest = PageRequest.of(page,5);
		Page<Equipo> equipos = equipoService.findAll(pageRequest);
		PageRender<Equipo> pageRender = new PageRender<>("/empleados/listar",equipos);
		
		model.addAttribute("titulo", "Listado de equipo");
		model.addAttribute("equipos",equipos);
		model.addAttribute("page",pageRender);
		
		return "equipos/listar";
	}
	
	@GetMapping("/form")
	public String mostrarFormularioDeregistroEquipo(Map<String, Object>model) {
		Equipo equipo = new Equipo();
		List<Empleado> empleados = empleadoService.findAll();
		model.put("equipo",equipo);
		model.put("empleados", empleados);
		model.put("titulo", "Anadir un nuevo equipo");
		return "equipos/form";
	}
	
	@PostMapping("/form")
	public String guardarEquipo(@Valid Equipo equipo, BindingResult result, Model model,  RedirectAttributes flash, SessionStatus status, Map<String, Object>modelo) {
		List<Empleado> empleados = empleadoService.findAll();
		modelo.put("empleados",empleados);
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", "registro de equipo");
			return "equipos/form";
		}
		
		String mensaje = (equipo.getId() != null) ? "El equipo ha sido editado con éxito" : "El equipo ha sido registrado con éxito";
		
		equipoService.save(equipo);
		
		status.setComplete();
		
		flash.addFlashAttribute("success",mensaje);
		
		return "redirect:/equipos/listar";
	}
	
	@GetMapping("/form/{id}")
	public String editarEquipo(@PathVariable(value="id")long id, Map<String, Object> model, RedirectAttributes flash, Map<String, Object>modelo) {
		Equipo equipo = null;
		List<Empleado> empleados = empleadoService.findAll();
		modelo.put("empleados",empleados);
		if(id>0) {
			equipo = equipoService.findOne(id);
			if(equipo == null) {
				flash.addFlashAttribute("error","El equipo no existe");
				return "redirect:/equipos/listar";
			}
		}else {
			flash.addFlashAttribute("error", "El id del equipo no puede ser 0");
			return "redirect:/equipos/listar";
		}
		model.put("equipo",equipo);
		model.put("titulo", "Editar equipo");
		return "equipos/form";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarEquipo(@PathVariable(value="id")Long id, RedirectAttributes flash) {
		if(id>0) {
			equipoService.delete(id);
			flash.addFlashAttribute("success","Equipo eliminado con éxito");
		}
		return "redirect:/equipos/listar";
	}
	
	
	@GetMapping("/exportarPDF")
	public void exportarListaDeEquipoEnPDF(HttpServletResponse res) throws DocumentException, IOException {
		res.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_"+fechaActual+".pdf";
		
		res.setHeader(cabecera, valor);
		
		List<Equipo> equipos= equipoService.findAll();
		
		EquiposExporterPdf exporter = new EquiposExporterPdf(equipos);
		exporter.exportar(res);
	}
	
	@GetMapping("/exportarExcel")
	public void exportarListaDeEquipoEnExcel(HttpServletResponse res) throws DocumentException, IOException {
		res.setContentType("application/octet-stream");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_"+fechaActual+".xlsx";
		
		res.setHeader(cabecera, valor);
		
		List<Equipo> equipos= equipoService.findAll();
		
		EquiposExporterExcel exporter = new EquiposExporterExcel(equipos);
		exporter.exportar(res);
	}
	
}