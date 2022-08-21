package com.inventario.londres.util.reports;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.inventario.londres.entidades.Proveedor;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class ProveedorExporterPdf {

	private List<Proveedor> listaProveedores;

	public ProveedorExporterPdf(List<Proveedor> listaProveedores) {
		super();
		this.listaProveedores = listaProveedores;
	}

	private void escribirCabeceraDeLaTabla(PdfPTable tabla) {
		PdfPCell celda = new PdfPCell();
		celda.setBackgroundColor(Color.red);
		celda.setPadding(5);
		Font fuente = FontFactory.getFont(FontFactory.HELVETICA);
		fuente.setColor(Color.white);

		celda.setPhrase(new Phrase("Num Proveedor", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Nombre", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("RFC", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Dirección", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Telefono", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Correo", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("Representante Legal", fuente));
		tabla.addCell(celda);

		celda.setPhrase(new Phrase("productos", fuente));
		tabla.addCell(celda);
	}

	private void escribirDatosDeLaTabla(PdfPTable tabla) {
		for (Proveedor proveedor : listaProveedores) {
			tabla.addCell(String.valueOf(proveedor.getId()));
			tabla.addCell(proveedor.getNombre());
			tabla.addCell(proveedor.getRfc());
			tabla.addCell(proveedor.getDireccion());
			tabla.addCell(proveedor.getTelefono());
			tabla.addCell(proveedor.getEmail());
			tabla.addCell(proveedor.getRepresentanteLegal());
			tabla.addCell(proveedor.getProductos());
		}
	}

	public void exportar(HttpServletResponse res) throws DocumentException, IOException {
		Document documento = new Document(PageSize.A4);
		PdfWriter.getInstance(documento, res.getOutputStream());

		documento.open();

		Font fuente = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fuente.setColor(Color.red);
		fuente.setSize(18);

		Paragraph titulo = new Paragraph("Lista de Proveedores", fuente);
		titulo.setAlignment(Paragraph.ALIGN_CENTER);
		documento.add(titulo);

		PdfPTable tabla = new PdfPTable(8);
		tabla.setWidthPercentage(100);
		tabla.setSpacingBefore(15);
		tabla.setWidths(new float[] { 3.5f, 2.3f, 2.3f, 5f, 2.9f, 1.5f, 2.5f, 2.5f });
		tabla.setWidthPercentage(110);

		escribirCabeceraDeLaTabla(tabla);
		escribirDatosDeLaTabla(tabla);

		documento.add(tabla);
		documento.close();

	}

}
