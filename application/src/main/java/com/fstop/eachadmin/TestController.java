package com.fstop.eachadmin;

import java.io.FileNotFoundException;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.sf.jasperreports.engine.JRException;

@Tag(name = "測試用")
@RestController
@RequestMapping("api/demo/common")
public class TestController {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(TestController.class);

    @Autowired
    private TestRptService commonService;

    /**
     * PDF 导出 2
     * 基于 Jasper iReport
     *
     * @return
     * @throws FileNotFoundException
     * @throws JRException
     */
    @GetMapping(value = "/export/pdf/2", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> exportPdf2() throws FileNotFoundException, JRException {
        String fileName = "contract.pdf";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        
        return new ResponseEntity<>(commonService.exportPdf2(), headers, HttpStatus.OK);
    }
    
    @GetMapping(value = "/export/xls/2", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> exportxls2() throws FileNotFoundException, JRException {
        String fileName = "result.xls";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        
        return new ResponseEntity<>(commonService.exportxls(), headers, HttpStatus.OK);
    }
    
    
//    @Operation(summary = "API概述", description = "API功能說明")
//    @PostMapping(value = "/countdata")
//    public int countData(@RequestBody ExampleDto param){
//        return 123;
//    }
    
//操作行
    @Operation(summary = "操作行", description = "操作行功能說明")
    @PostMapping(value = "/getOpbkIdList")
    public List getOpbkIdListtData(@RequestBody ExampleDto param){
        return commonService.getOpbkIdList();
    }
    
    
//業務行
    @Operation(summary = "業務行概述", description = "業務行功能說明")
    @PostMapping(value = "/getBsTypeIdList")
    public int getBsTypeIdListData(@RequestBody ExampleDto param){
        return 123;
    }
    
    //業務行
    @Operation(summary = "業務行", description = "業務類別的API,用來呈現下拉式選單的選項")
    @PostMapping(value = "/bsTypeIdList")
    public List bsTypeIdList(@RequestBody BsFormDto param){
        return commonService.getBsTypeIdList();
    }
    
//    //查詢(暫未完成Dto)
//    @Operation(summary = "查詢表單產出", description = "查詢按鈕(label),點選後依據篩選條件將需要的表單產出")
//    @PostMapping(value = "/pageSearch")
//    public List pageSearch(@RequestBody FormDto param){
//        return commonService.getBsTypeIdList();
//    }
    
    //return json
    //請求傳送未完成交易結果(暫未完成Dto)
    @Operation(summary = "send_1406", description = "查詢資料內,「請求傳送未完成交易結果」按鈕")
    @PostMapping(value = "/send_1406")
    public String send_1406(@RequestBody FormDto param){
//        return commonService.send_1406();
    	return "123";
    }

	public static void main(String[] args) {
		Onblocktab_Form
	}
    
    
    
}
