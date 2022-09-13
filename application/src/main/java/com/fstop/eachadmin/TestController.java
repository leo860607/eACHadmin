package com.fstop.eachadmin;

import java.io.FileNotFoundException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Logger;
import net.sf.jasperreports.engine.JRException;

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
}
