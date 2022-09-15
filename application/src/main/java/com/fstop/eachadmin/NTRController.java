package com.fstop.eachadmin;

//import java.io.FileNotFoundException;
//
//
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import ch.qos.logback.classic.Logger;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import net.sf.jasperreports.engine.JRException;
//
//@Tag(name = "測試用")
//@RestController
//@RequestMapping("api/demo/common")
//public class NTRController {
//	private static final Logger logger = (Logger) LoggerFactory.getLogger(TestController.class);
//
//    @Autowired
//    private TestRptService commonService;
//
//    /**
//     * PDF 导出 2
//     * 基于 Jasper iReport
//     *
//     * @return
//     * @throws FileNotFoundException
//     * @throws JRException
//     */
//    @GetMapping(value = "/export/pdf/2", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<byte[]> exportPdf2() throws FileNotFoundException, JRException {
//        String fileName = "contract.pdf";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment", fileName);
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        
//        return new ResponseEntity<>(commonService.exportPdf2(), headers, HttpStatus.OK);
//    }
//    
//    @GetMapping(value = "/export/xls/2", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<byte[]> exportxls2() throws FileNotFoundException, JRException {
//        String fileName = "result.xls";
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentDispositionFormData("attachment", fileName);
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        
//        return new ResponseEntity<>(commonService.exportxls(), headers, HttpStatus.OK);
//    }
//    
//    
//    @Operation(summary = "API概述", description = "API功能說明")
//    @PostMapping(value = "/countdata")
//    public int countData(@RequestBody ExampleDto param){
//        return 123;
//    }
//    //=========================================================
////    @Operation(summary = "操作行API", description = "操作行API功能說明")
////    @PostMapping(value = "/operatedata")
////    public String operate(@RequestBody OperateDto param){
////        return "OK_Operation";
////    }
////    @Operation(summary = "總行代號API", description = "總行代號API功能說明")
////    @PostMapping(value = "/headofficedata")
////    public String headoffice(@RequestBody HeadDto param){
////        return "OK_HeadOffice";
////    }
////    @Operation(summary = "業務類別API", description = "業務類別API功能說明")
////    @PostMapping(value = "/businessdata")
////    public String business(@RequestBody BusinessDto param){
////        return "OK_Business";
////    }
////    @Operation(summary = "查詢API", description = "查詢API功能說明")
////    @PostMapping(value = "/searchdata")
////    public String search(@RequestBody SearchDto param){
////        return "OK_Search";
////    }
//    
////-----------------------------------------------------------------------
//    @Operation(summary = "操作行API", description = "操作行API功能說明")
//    @GetMapping(value = "/operatedata")
//    public String operate(@RequestBody Onblocktab_NotTradRes_Form param){
//        return "OK_Operation";
//    }
////    @Operation(summary = "總行代號API", description = "總行代號API功能說明")
////    @PostMapping(value = "/headofficedata")
////    public String headoffice(@RequestBody Onblocktab_NotTradRes_Form param){
////        return "OK_HeadOffice";
//    }
//    @Operation(summary = "業務類別API", description = "業務類別API功能說明")
//    @GetMapping(value = "/businessdata")
//    public String business(@RequestBody Onblocktab_NotTradRes_Form param){
//        return beanList;
//    }
//    @Operation(summary = "查詢API", description = "查詢API功能說明")
//    @GetMapping(value = "/searchdata")
//    public String search(@RequestBody Onblocktab_NotTradRes_Form param){
//        return search();
//    }
//    
//    //return json
//    //controller
//    @Operation(summary = "send_1406", description = "API功能說明")
//    @GetMapping(value = "/send_1406data")
//    public String send_1406(@RequestBody Onblocktab_NotTradRes_Form param){
//        return send_1406();
//    }
//    
//    
//    
//}