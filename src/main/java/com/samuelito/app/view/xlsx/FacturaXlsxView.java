package com.samuelito.app.view.xlsx;

import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.samuelito.app.domain.Factura;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachmente; filename=\"facurita.xlsx\"");
		Factura factura = (Factura) model.get("factura");
		Sheet sheet = workbook.createSheet("Factura Spring xD");
		MessageSourceAccessor mensajes = getMessageSourceAccessor();
		
		//Datos del cliente
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(mensajes.getMessage("text.factura.ver.datos.cliente"));
		
		//Nombre cliente
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombreCompleto());
		
		//Email
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		sheet.createRow(4).createCell(0).setCellValue("text.factura.ver.datos.factura");
		sheet.createRow(5).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.folio") + ": " + factura.getIdFactura());
		sheet.createRow(6).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.descripcion") + ": " + factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.fecha") + ": " + factura.getCreatedAt());
		
		CellStyle tblHeaderStyle = workbook.createCellStyle();
		tblHeaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		tblHeaderStyle.setBorderTop(BorderStyle.MEDIUM);
		tblHeaderStyle.setBorderRight(BorderStyle.MEDIUM);
		tblHeaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		tblHeaderStyle.setFillForegroundColor(IndexedColors.GOLD.getIndex());
		tblHeaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tblBodyStyle = workbook.createCellStyle();
		tblBodyStyle.setBorderBottom(BorderStyle.THIN);
		tblBodyStyle.setBorderTop(BorderStyle.THIN);
		tblBodyStyle.setBorderRight(BorderStyle.THIN);
		tblBodyStyle.setBorderLeft(BorderStyle.THIN);
		
		Row tblHeader = sheet.createRow(9);
		
		tblHeader.createCell(0).setCellValue(mensajes.getMessage("text.cliente.factura.producto"));
		tblHeader.createCell(1).setCellValue(mensajes.getMessage("text.cliente.factura.precio"));
		tblHeader.createCell(2).setCellValue(mensajes.getMessage("text.cliente.factura.cantidad"));
		tblHeader.createCell(3).setCellValue(mensajes.getMessage("text.cliente.factura.total"));
		
		tblHeader.getCell(0).setCellStyle(tblHeaderStyle);
		tblHeader.getCell(1).setCellStyle(tblHeaderStyle);
		tblHeader.getCell(2).setCellStyle(tblHeaderStyle);
		tblHeader.getCell(3).setCellStyle(tblHeaderStyle);
		
		int rownum = 10;
		
		for (var i : factura.getItems()) {
			Row tblBodyRow = sheet.createRow(rownum++);
			cell = tblBodyRow.createCell(0);
			cell.setCellValue(i.getProducto().getNombre());
			cell.setCellStyle(tblBodyStyle);
			
			cell = tblBodyRow.createCell(1);
			cell.setCellValue(i.getProducto().getPrecio());
			cell.setCellStyle(tblBodyStyle);
			
			cell = tblBodyRow.createCell(2);
			cell.setCellValue(i.getCantidad());
			cell.setCellStyle(tblBodyStyle);
			
			cell = tblBodyRow.createCell(3);
			cell.setCellValue(i.calcularImporte());
			cell.setCellStyle(tblBodyStyle);
		}
		
		Row filaTotal = sheet.createRow(rownum++);
		
		filaTotal.createCell(2).setCellValue("Gran total");
		filaTotal.createCell(3).setCellValue(factura.getTotal());
		
		
	}

}
