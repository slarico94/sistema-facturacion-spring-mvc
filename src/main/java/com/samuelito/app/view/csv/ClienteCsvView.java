package com.samuelito.app.view.csv;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.samuelito.app.domain.Cliente;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("listar.csv")
public class ClienteCsvView extends AbstractView {

	public ClienteCsvView() {
		setContentType("text/csv");
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "attachmente; filename=\"factura.csv\"");
		response.setContentType(getContentType());
		Page<Cliente> clientes = (Page<Cliente>) model.get("clientes");

		try (ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE)) {

			String[] header = { "idCliente", "nombre", "apellido", "email", "createdAt" };

			for (Cliente cliente : clientes) {
				beanWriter.write(cliente, header);
			}
		}

	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

}
