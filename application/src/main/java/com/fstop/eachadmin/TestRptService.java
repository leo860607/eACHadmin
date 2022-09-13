package com.fstop.eachadmin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.JRException;
import util.ReportUtil;

@Service
public class TestRptService {
	public byte[] exportPdf2() throws FileNotFoundException, JRException {
//        String templatePath = "classpath:templates/contract.jrxml";
		File file = null;
		try {
			file = new ClassPathResource("templates/contract.jrxml").getFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ContractEntity contract = new ContractEntity();
		contract.setContractCode("CON11123445567778888");
		contract.setContractName("马尔代夫海景房转让合同");
		contract.setContractOriginalCode("ORI555444333222111");
		contract.setOriginalCcyTaxIncludedAmt(new BigDecimal(123456789.12345666666).setScale(6, BigDecimal.ROUND_DOWN));
		contract.setLocalCcyTaxIncludedAmt(new BigDecimal(987654321.123456));
		contract.setContractType("租赁合同");
		contract.setContractDetailType("房屋租赁合同");
		contract.setSupplierName("太平洋租房股份有限公司");
		contract.setOperatorName("德玛西亚");
		contract.setOperatorOrgName("稀里糊涂银行总行");
		contract.setOperatorDeptName("马尔代夫总行财务部");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		contract.setEffectiveDate(df.format(new Date()));
		contract.setExpiredDate(df.format(new Date()));
		ObjectMapper oMapper = new ObjectMapper();
		return ReportUtil.exportPdfFromXml(file.getPath(), oMapper.convertValue(contract, Map.class));
	}
	
	public byte[] exportxls() throws FileNotFoundException, JRException {
//      String templatePath = "classpath:templates/contract.jrxml";
		File file = null;
		try {
			file = new ClassPathResource("templates/contract.jrxml").getFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ContractEntity contract = new ContractEntity();
		contract.setContractCode("CON11123445567778888");
		contract.setContractName("马尔代夫海景房转让合同");
		contract.setContractOriginalCode("ORI555444333222111");
		contract.setOriginalCcyTaxIncludedAmt(new BigDecimal(123456789.12345666666).setScale(6, BigDecimal.ROUND_DOWN));
		contract.setLocalCcyTaxIncludedAmt(new BigDecimal(987654321.123456));
		contract.setContractType("租赁合同");
		contract.setContractDetailType("房屋租赁合同");
		contract.setSupplierName("太平洋租房股份有限公司");
		contract.setOperatorName("德玛西亚");
		contract.setOperatorOrgName("稀里糊涂银行总行");
		contract.setOperatorDeptName("马尔代夫总行财务部");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		contract.setEffectiveDate(df.format(new Date()));
		contract.setExpiredDate(df.format(new Date()));
		ObjectMapper oMapper = new ObjectMapper();
		return ReportUtil.exportXlsFromXml(file.getPath(), oMapper.convertValue(contract, Map.class));
	}
}
