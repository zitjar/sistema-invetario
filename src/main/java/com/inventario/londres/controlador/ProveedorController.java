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

import com.inventario.londres.entidades.Proveedor;
import com.inventario.londres.servicio.IProveedorService;
import com.inventario.londres.util.paginacion.PageRender;
import com.inventario.londres.util.reports.ProveedorExporterExcel;
import com.inventario.londres.util.reports.ProveedorExporterPdf;
import com.lowagie.text.DocumentException;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {
	
	@Autowired
	private IProveedorService proveedorService;
	
	@GetMapping("/ver/{id}")
	public String verDatos(@PathVariable(value="id")Long id, Map<String, Object> model, RedirectAttributes flash) {
		
		Proveedor proveedor = proveedorService.findOne(id);
		
		if(proveedor == null) {
			flash.addFlashAttribute("error","El proveedor no existe");
			return "redirect:/proveedores/listar";
		}
		model.put("proveedor", proveedor);
		model.put("titulo", "detalles de el proveedor "+proveedor.getNombre());
		return "proveedores/ver";
	}
	
	
	@GetMapping("/listar")
	public String listarProveedor(@RequestParam(name = "page", defaultValue = "0")int page,Model model) {
		Pageable pageRequest = PageRequest.of(page,5);
		Page<Proveedor> proveedor = proveedorService.findAll(pageRequest);
		PageRender<Proveedor> pageRender = new PageRender<>("/proveedores/listar",proveedor);
		
		model.addAttribute("titulo", "Listado de proveedores");
		model.addAttribute("proveedores",proveedor);
		model.addAttribute("page",pageRender);
		
		return "proveedores/listar";
	}
	
	@GetMapping("/form")
	public String mostrarFormularioDeregistroProveedor(Map<String, Object>model) {
		Proveedor proveedor = new Proveedor();
		model.put("proveedor",proveedor);
		model.put("titulo", "Anadir un nuevo proveedor");
		return "proveedores/form";
	}
	
	@PostMapping("/form")
	public String guardarProveedor(@Valid Proveedor proveedor, BindingResult result, Model model,  RedirectAttributes flash, SessionStatus status) {
		if(result.hasErrors()) {
			model.addAttribute("titulo", "registro de proveedor");
			return "proveedores/form";
		}
		
		String mensaje = (proveedor.getId() != null) ? "El proveedor ha sido editado con éxito" : "El proveedor ha sido registrado con éxito";
		
		proveedorService.save(proveedor);
		
		status.setComplete();
		
		flash.addFlashAttribute("success",mensaje);
		
		return "redirect:/proveedores/listar";
	}
	
	@GetMapping("/form/{id}")
	public String editarEmpleado(@PathVariable(value="id")long id, Map<String, Object> model, RedirectAttributes flash) {
		Proveedor proveedor = null;
		if(id>0) {
			proveedor = proveedorService.findOne(id);
			if(proveedor == null) {
				flash.addFlashAttribute("error","El proveedor no existe");
				return "redirect:/proveedores/listar";
			}
		}else {
			flash.addFlashAttribute("error", "El id del proveedor no puede ser 0");
			return "redirect:/proveedores/listar";
		}
		model.put("proveedor",proveedor);
		model.put("titulo", "Editar proveedor");
		return "proveedores/form";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarProveedor(@PathVariable(value="id")Long id, RedirectAttributes flash) {
		if(id>0) {
			proveedorService.delete(id);
			flash.addFlashAttribute("success","proveedor eliminado con éxito");
		}
		return "redirect:/proveedores/listar";
	}
	
	
	@GetMapping("/exportarPDF")
	public void exportarListaDeProveedorEnPDF(HttpServletResponse res) throws DocumentException, IOException {
		res.setContentType("application/pdf");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Proveedores_"+fechaActual+".pdf";
		
		res.setHeader(cabecera, valor);
		
		List<Proveedor> proveedor= proveedorService.findAll();
		
		ProveedorExporterPdf exporter = new ProveedorExporterPdf(proveedor);
		exporter.exportar(res);
	}
	
	@GetMapping("/exportarExcel")
	public void exportarListaDeEmpleadosEnExcel(HttpServletResponse res) throws DocumentException, IOException {
		res.setContentType("application/octet-stream");
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String fechaActual = dateFormatter.format(new Date());
		
		String cabecera = "Content-Disposition";
		String valor = "attachment; filename=Proveedores_"+fechaActual+".xlsx";
		
		res.setHeader(cabecera, valor);
		
		List<Proveedor> proveedor= proveedorService.findAll();
		
		ProveedorExporterExcel exporter = new ProveedorExporterExcel(proveedor);
		exporter.exportar(res);
	}

}
