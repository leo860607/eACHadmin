package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@Slf4j
public class ReportUtil {


	/**
	 * 导出 PDF
	 *
	 * @param templatePath jrxml 模板路径(base classpath)
	 * @param paramMap     数据对象
	 * @return
	 * @throws FileNotFoundException
	 * @throws JRException
	 */
	public static byte[] exportPdfFromXml(String templatePath, Map<String, Object> paramMap)
			throws FileNotFoundException, JRException {
		log.info(paramMap.toString());
		JasperReport jasperReport = getJasperReportFromXml(templatePath);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JREmptyDataSource());
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	public static byte[] exportXlsFromXml(String templatePath, Map<String, Object> paramMap)
			throws FileNotFoundException, JRException {
		log.info(paramMap.toString());
		JasperReport jasperReport = getJasperReportFromXml(templatePath);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JREmptyDataSource());
		SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
		configuration.setOnePagePerSheet(true);
		configuration.setDetectCellType(true); // Detect cell types (date and etc.)
		configuration.setWhitePageBackground(false); // No white background!
		configuration.setFontSizeFixEnabled(false);

		// No spaces between rows and columns
		configuration.setRemoveEmptySpaceBetweenRows(true);
		configuration.setRemoveEmptySpaceBetweenColumns(true);
		// If you want to name sheets then uncomment this line
		// configuration.setSheetNames(new String[] { "Data" });
		JRAbstractExporter exporter = new JRXlsExporter();
		exporter.setConfiguration(configuration);
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

		ByteArrayOutputStream excelStream = new ByteArrayOutputStream();
		OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(excelStream);
		exporter.setExporterOutput(exporterOutput);
		exporter.exportReport();
		return excelStream.toByteArray();
	}

	/**
	 * 导出 PDF
	 *
	 * @param templatePath jasper 模板路径(base classpath)
	 * @param paramMap     数据对象
	 * @return
	 * @throws FileNotFoundException
	 * @throws JRException
	 */
	public static byte[] exportPdfFromJasper(String templatePath, Map<String, Object> paramMap)
			throws FileNotFoundException, JRException {
		JasperReport jasperReport = getJasperReportFromJasper(templatePath);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, paramMap, new JREmptyDataSource());
		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	/**
	 * 获取导出对象,从 xml 中
	 *
	 * @param templatePath 模板路径(base classpath)
	 * @return
	 * @throws FileNotFoundException
	 * @throws JRException
	 */
	private static JasperReport getJasperReportFromXml(String templatePath) throws FileNotFoundException, JRException {
		return JasperCompileManager.compileReport(templatePath);
	}

	/**
	 * 获取导出对象,从 jasper 中 (jasper 为 jrxml 编译后生成的文件)
	 *
	 * @param templatePath 模板路径(base classpath)
	 * @return
	 * @throws FileNotFoundException
	 * @throws JRException
	 */
	private static JasperReport getJasperReportFromJasper(String templatePath)
			throws FileNotFoundException, JRException {
		File file = null;
		ClassLoader classloader = null;
		try {
			file = new File(classloader.getResource(templatePath).getFile());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (JasperReport) JRLoader.loadObject(file);
	}

}
