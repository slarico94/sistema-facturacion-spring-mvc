package com.samuelito.app.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.samuelito.app.domain.Factura;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView {
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LocaleResolver localeResolver;

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Factura factura = (Factura) model.get("factura");
		Locale locale = localeResolver.resolveLocale(request);
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		PdfPTable tablaDatosCliente = new PdfPTable(1);
		tablaDatosCliente.setSpacingAfter(20);
		
		PdfPCell cell = null;
		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente", null, locale)));
		cell.setBackgroundColor(new Color(184, 218, 255));
		cell.setPadding(8f);
		
		tablaDatosCliente.addCell(cell);
		tablaDatosCliente.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		tablaDatosCliente.addCell(factura.getCliente().getEmail());
		
		PdfPTable tableDatosFactura = new PdfPTable(1);
		tableDatosFactura.setSpacingAfter(20);
		
		cell = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura", null, locale)));
		cell.setBackgroundColor(new Color(195, 230, 203));
		cell.setPadding(8f);
		
		tableDatosFactura.addCell(cell);
		tableDatosFactura.addCell(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getIdFactura());
		tableDatosFactura.addCell(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
		tableDatosFactura.addCell(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreatedAt());
		
		PdfPTable tblDetalle = new PdfPTable(4);
		tblDetalle.setWidths(new float[] {3.5f, 1, 1, 1});
		tblDetalle.addCell(mensajes.getMessage("text.cliente.factura.producto"));
		tblDetalle.addCell(mensajes.getMessage("text.cliente.factura.precio"));
		tblDetalle.addCell(mensajes.getMessage("text.cliente.factura.cantidad"));
		tblDetalle.addCell(mensajes.getMessage("text.cliente.factura.total"));
		
		for (var i : factura.getItems()) {
			tblDetalle.addCell(i.getProducto().getNombre());
			tblDetalle.addCell(i.getProducto().getPrecio().toString());
			cell = new PdfPCell(new Phrase(i.getCantidad().toString()));
			cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			tblDetalle.addCell(cell);
			tblDetalle.addCell(i.calcularImporte().toString());
		}
		
		cell = new PdfPCell(new Phrase("Total: "));
		cell.setColspan(3);
		cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		tblDetalle.addCell(cell);
		tblDetalle.addCell(factura.getTotal().toString());
		
		document.add(tablaDatosCliente);
		document.add(tableDatosFactura);
		document.add(tblDetalle);
	}

}
