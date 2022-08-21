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
import com.inventario.londres.servicio.IEmpleadoService;
import com.inventario.londres.util.paginacion.PageRender;
import com.inventario.londres.util.reports.EmpleadoExporterExcel;
import com.inventario.londres.util.reports.EmpleadoExporterPdf;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/empleados")
public class EmpleadoController {

	@Autowired
	private IEmpleadoService empleadoService;
	
	@GetMapping("/ver/{id}")
	public String verDatos(@PathVariable(value="id")Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Empleado empleado = empleadoService.findOne(id);
		
		if(empleado == null) {
			flash.addFlashAttribute("error","El empleado no existe");
			return "redirect:/empleados/listar";
		}
		model.put("empleado", empleado);
		model.put("titulo", "detalles de empleado "+empleado.getNombre());
		return "empleados/ver";
	}
	
	
	@GetMapping("/listar")
	public String listarEmpleados(@RequestParam(name = "page", defaultValue = "0")int page,Model model) {
		Pageable pageRequest = PageRequest.of(page,5);
		Page<Empleado> empleados = empleadoService.findAll(pageRequest);
		PageRender<Empleado> pageRender = new PageRender<>("/empleados/listar",empleados);
		
		model.addAttribute("titulo", "Listado de empleado");
		model.addAttribute("empleados",empleados);
		model.addAttribute("page",pageRender);
		
		return "empleados/listar";
	}
	
	@GetMapping("/form")
	public String mostrarFormularioDeregistroEmpleado(Map<String, Object>model) {
		Empleado empleado = new Empleado();
		model.put("empleado",empleado);
		model.put("titulo", "Anadir un nuevo empleado");
		return "empleados/form";
	}
	
	@PostMapping("/form")
	public String guardarEmpleado(@Valid Empleado empleado, BindingResult result, Model model,  RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "registro de empleado");
			return "empleados/form";
		}
		
		String mensaje = (empleado.getId() != null) ? "El empleado ha sido editado con éxito" : "El empleado ha sido registrado con éxito";
		
		empleadoService.save(empleado);
		
		status.setComplete();
		
		flash.addFlashAttribute("success",mensaje);
		
		return "redirect:/empleados/listar";
	}
	
	@GetMapping("/form/{id}")
	public String editarEmpleado(@PathVariable(value="id")long id, Map<String, Object> model, RedirectAttributes flash) {
		Empleado empleado = null;
		if(id>0) {
			empleado = empleadoService.findOne(id);
			if(empleado == null) {
				flash.addFlashAttribute("error","El empleado no existe");
				return "redirect:/empleados/listar";
			}
		}else {
			flash.addFlashAttribute("error", "El id del empleado no puede ser 0");
			return "redirect:/empleados/listar";
		}
		model.put("empleado",empleado);
		model.put("titulo", "Editar empleado");
		return "empleados/form";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarEmpleado(@PathVariable(value="id")Long id, RedirectAttributes flash) {
		if(id>0) {
			empleadoService.delete(id);
			flash.addFlashAttribute("success","Empleado eliminado con éxito");
		}
		return "redirect:/empleados/listar";
	}
	
	
	@GetMapping("/exportarPDF")
	public void exportarListaDeEmpleadosEnPDF(HttpServletResponse res) throws DocumentException, IOException {
		res.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_"+fechaActual+".pdf";
		
		res.setHeader(cabecera, valor);
		
		List<Empleado> empleados= empleadoService.findAll();
		
		EmpleadoExporterPdf exporter = new EmpleadoExporterPdf(empleados);
		exporter.exportar(res);
	}
	
	@GetMapping("/exportarExcel")
	public void exportarListaDeEmpleadosEnExcel(HttpServletResponse res) throws DocumentException, IOException {
		res.setContentType("application/octet-stream");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Empleados_"+fechaActual+".xlsx";
		
		res.setHeader(cabecera, valor);
		
		List<Empleado> empleados= empleadoService.findAll();
		
		EmpleadoExporterExcel exporter = new EmpleadoExporterExcel(empleados);
		exporter.exportar(res);
	}
	
}