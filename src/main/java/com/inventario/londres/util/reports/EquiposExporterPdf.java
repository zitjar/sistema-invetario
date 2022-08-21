package com.inventario.londres.util.reports;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.inventario.londres.entidades.Equipo;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class EquiposExporterPdf {

	private List<Equipo> listaEquipos;

	public EquiposExporterPdf(List<Equipo> listaEquipos) {
		super();
		this.listaEquipos = listaEquipos;
	}

	private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();
		celda.setBackgroundColor(Color.red);
		celda.setPadding(5);
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.white);

		celda.setPhrase(new Phrase("Id del equipo", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Modelo", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Marca", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Ram", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Procesador", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Almacenamiento", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("tipo", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Fecha de compra", fuente));
		tabla.addCell(celda);
		
	}

	private void escribirDatosDeLaTabla(PdfPTable tabla) {
		for (Equipo equipo : listaEquipos) {
			tabla.addCell(String.valueOf(equipo.getId()));
			tabla.addCell(equipo.getModelo());
			tabla.addCell(equipo.getMarca());
			tabla.addCell(equipo.getRam());
			tabla.addCell(equipo.getProcesador());
			tabla.addCell(equipo.getAlmacenamiento());
			tabla.addCell(equipo.getTipo());
			tabla.addCell(equipo.getFechaCompra().toString());

		}
	}

	public void exportar(HttpServletResponse res) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, res.getOutputStream());

		documento.open();

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.red);
		fuente.setSize(18);

		Paragraph titulo = new Paragraph("Lista de empleado", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);

		PdfPTable tabla = new PdfPTable(8);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] { 3.5f, 2.8f, 2.9f, 1.5f, 2.9f, 3f, 3f,3.5f });
		tabla.setWidthPercentage(110);

		escribirCabeceraDeLaTabla(tabla);
		escribirDatosDeLaTabla(tabla);

		documento.add(tabla);
		documento.close();

	}

}
