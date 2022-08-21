package com.inventario.londres.util.reports;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.inventario.londres.entidades.Equipo;

public class EquiposExporterExcel {

	private XSSFWorkbook libro;
	private XSSFSheet hoja;

	private List<Equipo> listaEquipos;

	public EquiposExporterExcel(List<Equipo> listaEquipos) {
		this.listaEquipos = listaEquipos;
		libro = new XSSFWorkbook();
		hoja = libro.createSheet("Equipos");
	}
	
	private void escribirCabeceraDeLaTabla() {
		Row fila = hoja.createRow(0);
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setBold(true);
		fuente.setFontHeight(16);
		estilo.setFont(fuente);
		
		Cell celda = fila.createCell(0);
		celda.setCellValue("Id Equipo");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(1);
		celda.setCellValue("Marca");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(2);
		celda.setCellValue("Modelo");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(3);
		celda.setCellValue("Ram");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(4);
		celda.setCellValue("Procesador");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(5);
		celda.setCellValue("Almacenamiento");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(6);
		celda.setCellValue("Tipo de equipo");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(7);
		celda.setCellValue("Asignado A");
		celda.setCellStyle(estilo);
		
		celda = fila.createCell(8);
		celda.setCellValue("Fecha de compra");
		celda.setCellStyle(estilo);
	}
	
	private void escribirDatosDeLaTabla() {
		int numeroFilas = 1;
		
		CellStyle estilo = libro.createCellStyle();
		XSSFFont fuente = libro.createFont();
		fuente.setFontHeight(14);
		estilo.setFont(fuente);
		
		for(Equipo equipo: listaEquipos) {
			Row fila = hoja.createRow(numeroFilas ++);
			
			Cell celda = fila.createCell(0);
			celda.setCellValue(equipo.getId());
			hoja.autoSizeColumn(0);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(1);
			celda.setCellValue(equipo.getMarca());
			hoja.autoSizeColumn(1);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(2);
			celda.setCellValue(equipo.getModelo());
			hoja.autoSizeColumn(2);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(3);
			celda.setCellValue(equipo.getRam());
			hoja.autoSizeColumn(3);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(4);
			celda.setCellValue(equipo.getProcesador());
			hoja.autoSizeColumn(4);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(5);
			celda.setCellValue(equipo.getAlmacenamiento());
			hoja.autoSizeColumn(5);
			celda.setCellStyle(estilo);
			
			celda = fila.createCell(6);
			celda.setCellValue(equipo.getTipo());
			hoja.autoSizeColumn(6);
			celda.setCellStyle(estilo);
			
			if(equipo.getEmpleado() != null) {
			celda = fila.createCell(7);
			celda.setCellValue(equipo.getEmpleado().getNombre().concat(" "+equipo.getEmpleado().getApellido()));
			hoja.autoSizeColumn(7);
			celda.setCellStyle(estilo);
			}
			
			celda = fila.createCell(8);
			celda.setCellValue(equipo.getFechaCompra().toString());
			hoja.autoSizeColumn(8);
			celda.setCellStyle(estilo);
		}
	}
	
	public void exportar(HttpServletResponse res) throws IOException {
		escribirCabeceraDeLaTabla();
		escribirDatosDeLaTabla();
		
		ServletOutputStream outPutStream = res.getOutputStream();
		libro.write(outPutStream);
		
		libro.close();
	}

}
