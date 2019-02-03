package com.example.demo6;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;


@RestController
@Transactional
public class PelajarController {
	
	public static final Logger logger = LoggerFactory.getLogger(PelajarController.class);
	
	@Autowired
	private PelajarRepository pelajarData;
	
	@GetMapping("/pelajarPdf")
	public @ResponseBody void pelajarPDF (HttpServletResponse response) {
		try
		{
			InputStream jasperStream = this.getClass().getResourceAsStream("/reports/test_A4.jrxml");
			JasperDesign design = JRXmlLoader.load(jasperStream);
			JasperReport report = JasperCompileManager.compileReport(design);
			
			Map<String,Object> parameterMap = new HashMap();
			
			List<Pelajar> pelajar = pelajarData.findAll();
			
			JRDataSource jRDataSource = new JRBeanCollectionDataSource(pelajar);
					
			parameterMap.put("datasource", jRDataSource);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(report,  parameterMap, jRDataSource);
			response.setContentType("application/x.pdf");
			response.setHeader("Content.Disposition",  "inline; filename=pelajar.pdf");
			
			
			final OutputStream outputStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			
		} catch (JRException ex)
		{
			logger.info("Tidak bisa membaca file jrxml");
		} catch (IOException ex)
		{
			logger.info("Tidak bisa mengambil outputstream dari response");;
		}	
	}
}
