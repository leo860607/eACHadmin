//package util;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.Closeable;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.StringWriter;
//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
////import java.nio.charset.StandardCharsets;
//import java.nio.charset.StandardCharsets;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Statement;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Properties;
//import java.util.TreeMap;
//import java.util.zip.ZipEntry;
//import java.util.zip.ZipFile;
//
//import javax.servlet.http.Cookie;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang.StringEscapeUtils;
//import org.apache.log4j.log;
//
//import com.google.gson.Gson;
//
//import net.lingala.zip4j.io.ZipOutputStream;
//import net.lingala.zip4j.model.ZipParameters;
//import net.lingala.zip4j.util.Zip4jConstants;
//import tw.org.twntch.bean.RADATA;
//import tw.org.twntch.db.dao.hibernate.CommonSpringDao;
//import tw.org.twntch.db.dao.hibernate.IKey_Dao;
//import tw.org.twntch.socket.HSMSocketClient;
//import tw.org.twntch.socket.HSMSuipRequestData;
//import tw.org.twntch.socket.HSMSuipResponseData;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class CodeUtils {
//    private log log = log.getlog(this.getClass().getName());
//    private CommonSpringDao commonSpringDao;
//    private IKey_Dao ikey_Dao;
//    // private SD_COMPANY_PROFILE_Dao sd_com_Dao;
//    // private SD_COMPANY_PROFILE_HIS_Dao sd_com_his_Dao;
//    // private FEE_CODE_NW_Dao fee_code_nw_Dao;
//    // private FEE_CODE_Dao fee_code_Dao;
//    static Gson gs = new Gson();
//
//    public static String toJson(Object obj) {
//        return gs.toJson(obj);
//    }
//
//    public static <T> T fromJson(String json, Class<T> to) {
//        return gs.fromJson(json, to);
//    }
//
//    // ????????????????????????txt???byte[]
//    public byte[] createTXT(List<Map<String, Object>> queryListMap, Map<String, Object> columnMap, String firstRow,
//                            String lastRow) {
//        byte[] byteTXT = null;
//
//        // ?????????
//        StringBuilder sb = new StringBuilder();
//        // ?????????????????????
//        List<String> columnNameList = (List<String>) columnMap.get("columnName");
//        // ????????????????????????????????????
//        List<Integer> columnLengthList = (List<Integer>) columnMap.get("columnLength");
//        // ???????????????????????????????????????????????????
//        List<String> columnTypeList = (List<String>) columnMap.get("columnType");
//
//        // ??????????????????
//        if (firstRow != null) {
//            sb.append(firstRow + "\r\n");
//        }
//        // ????????????
//        if (queryListMap.size() != 0) {
//            for (int x = 0; x < queryListMap.size(); x++) {
//                for (int y = 0; y < columnNameList.size(); y++) {
//                    // ?????????????????????
//                    String columnValue = String.valueOf(queryListMap.get(x).get(columnNameList.get(y)));
//                    // log.debug("columnValue="+columnValue);
//                    // ??????
//                    columnValue = padText(columnTypeList.get(y), columnLengthList.get(y), columnValue);
//                    // log.debug("columnValue after padText="+columnValue);
//                    // ?????????????????????
//                    sb.append(columnValue);
//                    // ??????????????????????????????????????????
//                    if (y == columnNameList.size() - 1) {
//                        sb.append("\r\n");
//                    }
//                }
//            }
//        }
//        // ??????????????????
//        if (lastRow != null) {
//            sb.append(lastRow);
//        }
//        try {
//            // ???SB??????Byte[]
//            // 20160310 edit by hugo req by UAT-2016310-02
//            // byteTXT = sb.toString().getBytes("UTF-8");
//            byteTXT = sb.toString().getBytes("BIG5");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            byteTXT = sb.toString().getBytes();
//        }
//        return byteTXT;
//    }
//
//    public byte[] createTXT_UTF8(List<Map<String, Object>> queryListMap, Map<String, Object> columnMap, String firstRow,
//                                 String lastRow) {
//        byte[] byteTXT = null;
//
//        // ?????????
//        StringBuilder sb = new StringBuilder();
//        // ?????????????????????
//        List<String> columnNameList = (List<String>) columnMap.get("columnName");
//        // ????????????????????????????????????
//        List<Integer> columnLengthList = (List<Integer>) columnMap.get("columnLength");
//        // ???????????????????????????????????????????????????
//        List<String> columnTypeList = (List<String>) columnMap.get("columnType");
//
//        // ??????????????????
//        if (firstRow != null) {
//            sb.append(firstRow + "\r\n");
//        }
//        // ????????????
//        if (queryListMap.size() != 0) {
//            for (int x = 0; x < queryListMap.size(); x++) {
//                for (int y = 0; y < columnNameList.size(); y++) {
//                    // ?????????????????????
//                    String columnValue = String.valueOf(queryListMap.get(x).get(columnNameList.get(y)));
//                    // log.debug("columnValue="+columnValue);
//                    // ??????
//                    columnValue = padText_UTF8(columnTypeList.get(y), columnLengthList.get(y), columnValue);
//                    // log.debug("columnValue after padText="+columnValue);
//                    // ?????????????????????
//                    sb.append(columnValue);
//                    // ??????????????????????????????????????????
//                    if (y == columnNameList.size() - 1) {
//                        sb.append("\r\n");
//                    }
//                }
//            }
//        }
//        // ??????????????????
//        if (lastRow != null) {
//            sb.append(lastRow);
//        }
//        try {
//            // ???SB??????Byte[]
//            // 20160310 edit by hugo req by UAT-2016310-02
//            byteTXT = sb.toString().getBytes("UTF-8");
//            // byteTXT = sb.toString().getBytes("BIG5");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            byteTXT = sb.toString().getBytes();
//        }
//        return byteTXT;
//    }
//
//    // ??????????????????txt???byte[]
//    public byte[] createTXTWithoutData(String firstRow, String lastRow) {
//        byte[] byteTXT = null;
//
//        // ?????????
//        StringBuilder sb = new StringBuilder();
//        // ??????????????????
//        if (firstRow != null) {
//            sb.append(firstRow + "\r\n");
//        }
//        // ??????????????????
//        if (lastRow != null) {
//            sb.append(lastRow);
//        }
//        try {
//            // ???SB??????Byte[]
//            byteTXT = sb.toString().getBytes("UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            byteTXT = sb.toString().getBytes();
//        }
//        return byteTXT;
//    }
//
//    // ???????????????zip???byte[]
//    public byte[] createZIP(List<byte[]> dataList, List<String> filenameList, String zipPassword) {
//        byte[] byteZIP = null;
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
//        try {
//            for (int i = 0; i < dataList.size(); i++) {
//                // zip?????????
//                ZipParameters zipParameters = new ZipParameters();
//                zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
//                zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
//                zipParameters.setSourceExternalStream(true);
//                // zip??????TXT?????????
//                log.debug("filenameList.get(i)=" + filenameList.get(i));
//                zipParameters.setFileNameInZip(filenameList.get(i));
//                // ????????????zipPassword?????????????????????
//                if (zipPassword == null) {
//                    zipParameters.setEncryptFiles(false);
//                }
//                // ?????????zipPassword??????????????????
//                else {
//                    zipParameters.setEncryptFiles(true);
//                    zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
//                    zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_128);
//                    // ???????????????
//                    zipParameters.setPassword(zipPassword);
//                }
//                // ??????????????????zip
//                zipOutputStream.putNextEntry(null, zipParameters);
//                zipOutputStream.write(dataList.get(i));
//                zipOutputStream.closeEntry();
//            }
//            // zip??????
//            zipOutputStream.finish();
//            zipOutputStream.close();
//            byteZIP = byteArrayOutputStream.toByteArray();
//            byteArrayOutputStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//        return byteZIP;
//    }
//
//    // ????????????????????????????????????
//    public void forDownload(InputStream inputStream, String filename, String cookieName, String cookie) {
//        try {
//            // URLEncoder.encode????????????????????????
//            WebServletUtils.getResponse().setHeader("Content-Disposition",
//                    "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
//            if (cookieName != null && cookie != null) {
//                // ???????????????cookie
//                WebServletUtils.getResponse().addCookie(new Cookie(cookieName, cookie));
//            }
//            // ???????????????????????????
//            WebServletUtils.getResponse().setContentType("application/octet-stream");
//            OutputStream outStream = WebServletUtils.getResponse().getOutputStream();
//            // ???????????????????????????????????????????????????
//            byte[] outputByte = new byte[4096];
//            int bytesRead = -1;
//            while ((bytesRead = inputStream.read(outputByte)) != -1) {
//                outStream.write(outputByte, 0, bytesRead);
//            }
//            inputStream.close();
//            WebServletUtils.getResponse().getOutputStream().flush();
//            WebServletUtils.getResponse().getOutputStream().close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//    }
//
//    // ???Spring????????????
//    public List<Map<String, Object>> queryListMap(String SQL, Object[] parameter) {
//        // ????????????
//        return commonSpringDao.list(SQL, parameter);
//    }
//
//    // ??????Zip??????????????????
//    public InputStream getFileInZIP(String zipPath, String fileName) {
//        InputStream inputStream = null;
//        try {
//            ZipFile zipFile = new ZipFile(zipPath);
//            ZipEntry entry = zipFile.getEntry(fileName);
//            inputStream = zipFile.getInputStream(entry);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//        return inputStream;
//    }
//
//    // ???Byte[]????????????????????????
//    public void putFileToPath(String targetPath, byte[] byteFile) throws Exception {
//        try {
//            // ??????????????????????????????
//            File file = new File(targetPath);
//            // ???????????????????????????
//            File folder = new File(file.getParent());
//            // ??????????????????????????????????????????????????????????????????
//            if (!folder.isDirectory()) {
//                // ???????????????????????????
//                folder.mkdirs();
//            }
//            FileOutputStream fileOutputStream = new FileOutputStream(targetPath);
//            fileOutputStream.write(byteFile);
//            fileOutputStream.flush();
//            fileOutputStream.close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            log.debug("putFileToPath.Exception>>" + ex);
//            throw ex;
//        }
//    }
//
//    // ???????????????????????????InputStream
//    public InputStream getFileFromPath(String targetPath) {
//        InputStream inputStream = null;
//        try {
//            File fileData = new File(targetPath);
//            inputStream = new FileInputStream(fileData);
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//        return inputStream;
//    }
//
//    // ????????????????????????????????????????????????(????????????????????????)
//    public void delete(File file) {
//        // ????????????????????????????????????
//        if (file.isFile()) {
//            file.delete();
//        }
//        // ??????????????????????????????????????????
//        else {
//            for (File f : file.listFiles()) {
//                delete(f);
//            }
//        }
//    }
//
//    // ???????????????Byte??????
//    // ???????????????BIG5???Byte(??????????????????)?????????BIG5??????????????????????????????????????????????????????????????????????????????????????????
//    // ?????????BIG5???Byte?????????????????????txt???????????????????????????????????????
//    public int findByteLength(String text) {
//        int byteLength = 0;
//        String newString = "";
//        try {
//            newString = new String(text.getBytes("BIG5"), "BIG5");
//            int hardWordNum = findHardWordNum(text, newString);
//            // log.debug("hardWordNum="+hardWordNum);
//            byteLength = text.getBytes("BIG5").length + hardWordNum;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//        return byteLength;
//    }
//
//    public int findByteLength_UTF8(String text) {
//        int byteLength = 0;
//        String newString = "";
//        try {
//            newString = new String(text.getBytes("UTF8"), "UTF8");
//            int hardWordNum = findHardWordNum(text, newString);
//            // log.debug("hardWordNum="+hardWordNum);
//            byteLength = text.getBytes("UTF8").length + hardWordNum;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//        return byteLength;
//    }
//
//    public int findHardWordNum(String text, String newString) {
//        int hardWordNum = 0;
//        int textQNum = 0;
//        int newStringQNum = 0;
//
//        for (int x = 0; x < text.length(); x++) {
//            if ("?".equals(text.substring(x, x + 1))) {
//                textQNum += 1;
//            }
//        }
//        for (int x = 0; x < newString.length(); x++) {
//            if ("?".equals(newString.substring(x, x + 1))) {
//                newStringQNum += 1;
//            }
//        }
//        hardWordNum = newStringQNum - textQNum;
//
//        return hardWordNum;
//    }
//
//    // ??????
//    public String padText(String textType, Integer expectLength, String textValue) {
//        // ??????textValue??????String.valueOf?????????null?????????'null'???????????????????????????
//        if (textValue == null || textValue.equalsIgnoreCase("null")) {
//            if (textType.contains("string")) {
//                textValue = "";
//            } else if (textType.contains("number")) {
//                textValue = "0";
//            } else if (textType.contains("decimal")) {
//                textValue = "0";
//            } else {
//                textValue = "";
//            }
//        }
//
//        // ??????????????????
//        if (textType.contains("string")) {
//            // ?????????????????????????????????????????????????????????????????????????????????
//            while (findByteLength(textValue) > expectLength) {
//                textValue = textValue.substring(0, textValue.length() - 1);
//            }
//            while (findByteLength(textValue) < expectLength) {
//                textValue = textValue + " ";
//            }
//            // log.debug("textValue after padding="+textValue);
//        }
//        // ???????????????
//        else if (textType.contains("number")) {
//            // ?????????????????????????????????????????????????????????????????????????????????
//            while (findByteLength(textValue) > expectLength) {
//                // ????????????????????????????????????
//                if ("-".equals(textValue.substring(0, 1))) {
//                    textValue = "-" + textValue.substring(2);
//                } else {
//                    textValue = textValue.substring(1);
//                }
//            }
//            while (findByteLength(textValue) < expectLength) {
//                // ????????????????????????????????????
//                if ("-".equals(textValue.substring(0, 1))) {
//                    textValue = "-0" + textValue.substring(1);
//                } else {
//                    textValue = "0" + textValue;
//                }
//            }
//            // log.debug("textValue after padding="+textValue);
//        }
//        // ????????????????????????????????????????????????0????????????????????????????????????0?????????textValue='55.04'???textType='decimal(3)'???3???????????????
//        // ??????6?????????????????????????????????'055040'
//        else if (textType.contains("decimal")) {
//            String decimalString = "";
//            // ????????????
//            if (textValue.indexOf(".") != -1) {
//                decimalString = textValue.substring(textValue.indexOf(".") + 1);
//                textValue = textValue.substring(0, textValue.indexOf("."));
//            }
//            // log.debug("decimalString before padding="+decimalString);
//            // log.debug("textValue before padding="+textValue);
//            // ?????????
//            int totalLength = Integer.valueOf(expectLength);
//            // ????????????
//            int decimalLength = Integer.valueOf(textType.substring(textType.indexOf("(") + 1, textType.indexOf(")")));
//
//            // log.debug("totalLength="+totalLength);
//            // log.debug("decimalLength="+decimalLength);
//
//            // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//            while (findByteLength(decimalString) > decimalLength) {
//                decimalString = decimalString.substring(0, decimalString.length() - 1);
//            }
//            // ?????????????????????????????????
//            while (findByteLength(decimalString) < decimalLength) {
//                decimalString += "0";
//            }
//            // log.debug("decimalString after padding="+decimalString);
//            // ?????????????????????????????????????????????????????????????????????????????????????????????
//            while (findByteLength(textValue) > (totalLength - decimalLength)) {
//                // ????????????????????????????????????
//                if ("-".equals(textValue.substring(0, 1))) {
//                    textValue = "-" + textValue.substring(2);
//                } else {
//                    textValue = textValue.substring(1);
//                }
//            }
//            // ?????????????????????????????????
//            while (findByteLength(textValue) < (totalLength - decimalLength)) {
//                // ????????????????????????????????????
//                if (!"".equals(textValue) && "-".equals(textValue.substring(0, 1))) {
//                    textValue = "-0" + textValue.substring(1);
//                } else {
//                    textValue = "0" + textValue;
//                }
//            }
//
//            // log.debug("textValue after padding="+textValue);
//            // ????????????
//            textValue += decimalString;
//        }
//        // ??????????????????
//        else {
//            while (findByteLength(textValue) < expectLength) {
//                textValue = textValue + " ";
//            }
//        }
//        return textValue;
//    }
//
//    // ??????
//    public String padText_UTF8(String textType, Integer expectLength, String textValue) {
//        // ??????textValue??????String.valueOf?????????null?????????'null'???????????????????????????
//        if (textValue == null || textValue.equalsIgnoreCase("null")) {
//            if (textType.contains("string")) {
//                textValue = "";
//            } else if (textType.contains("number")) {
//                textValue = "0";
//            } else if (textType.contains("decimal")) {
//                textValue = "0";
//            } else {
//                textValue = "";
//            }
//        }
//
//        // ??????????????????
//        if (textType.contains("string")) {
//            // ?????????????????????????????????????????????????????????????????????????????????
//            while (findByteLength_UTF8(textValue) > expectLength) {
//                textValue = textValue.substring(0, textValue.length() - 1);
//            }
//            while (findByteLength_UTF8(textValue) < expectLength) {
//                textValue = textValue + " ";
//            }
//            // log.debug("textValue after padding="+textValue);
//        }
//        // ???????????????
//        else if (textType.contains("number")) {
//            // ?????????????????????????????????????????????????????????????????????????????????
//            while (findByteLength_UTF8(textValue) > expectLength) {
//                // ????????????????????????????????????
//                if ("-".equals(textValue.substring(0, 1))) {
//                    textValue = "-" + textValue.substring(2);
//                } else {
//                    textValue = textValue.substring(1);
//                }
//            }
//            while (findByteLength_UTF8(textValue) < expectLength) {
//                // ????????????????????????????????????
//                if ("-".equals(textValue.substring(0, 1))) {
//                    textValue = "-0" + textValue.substring(1);
//                } else {
//                    textValue = "0" + textValue;
//                }
//            }
//            // log.debug("textValue after padding="+textValue);
//        }
//        // ????????????????????????????????????????????????0????????????????????????????????????0?????????textValue='55.04'???textType='decimal(3)'???3???????????????
//        // ??????6?????????????????????????????????'055040'
//        else if (textType.contains("decimal")) {
//            String decimalString = "";
//            // ????????????
//            if (textValue.indexOf(".") != -1) {
//                decimalString = textValue.substring(textValue.indexOf(".") + 1);
//                textValue = textValue.substring(0, textValue.indexOf("."));
//            }
//            // log.debug("decimalString before padding="+decimalString);
//            // log.debug("textValue before padding="+textValue);
//            // ?????????
//            int totalLength = Integer.valueOf(expectLength);
//            // ????????????
//            int decimalLength = Integer.valueOf(textType.substring(textType.indexOf("(") + 1, textType.indexOf(")")));
//
//            // log.debug("totalLength="+totalLength);
//            // log.debug("decimalLength="+decimalLength);
//
//            // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//            while (findByteLength_UTF8(decimalString) > decimalLength) {
//                decimalString = decimalString.substring(0, decimalString.length() - 1);
//            }
//            // ?????????????????????????????????
//            while (findByteLength_UTF8(decimalString) < decimalLength) {
//                decimalString += "0";
//            }
//            // log.debug("decimalString after padding="+decimalString);
//            // ?????????????????????????????????????????????????????????????????????????????????????????????
//            while (findByteLength_UTF8(textValue) > (totalLength - decimalLength)) {
//                // ????????????????????????????????????
//                if ("-".equals(textValue.substring(0, 1))) {
//                    textValue = "-" + textValue.substring(2);
//                } else {
//                    textValue = textValue.substring(1);
//                }
//            }
//            // ?????????????????????????????????
//            while (findByteLength_UTF8(textValue) < (totalLength - decimalLength)) {
//                // ????????????????????????????????????
//                if (!"".equals(textValue) && "-".equals(textValue.substring(0, 1))) {
//                    textValue = "-0" + textValue.substring(1);
//                } else {
//                    textValue = "0" + textValue;
//                }
//            }
//
//            // log.debug("textValue after padding="+textValue);
//            // ????????????
//            textValue += decimalString;
//        }
//        // ??????????????????
//        else {
//            while (findByteLength_UTF8(textValue) < expectLength) {
//                textValue = textValue + " ";
//            }
//        }
//        return textValue;
//    }
//
//    // ???message???????????????
//    public String appendMessage(String message, String text) {
//        if ("".equals(message)) {
//            message = text;
//        } else {
//            message = message + "???" + text;
//        }
//        return message;
//    }
//
//    // ???JDBC??????????????????Schema??????????????????
//    public ResultSet useJDBCGetResultSet(String className, String url, String username, String password, String SQL,
//                                         Map<String, Object> parameterMap) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        Statement statement = null;
//        ResultSet resultSet = null;
//        try {
//            // ??????class
//            Class.forName(className);
//            // ????????????
//            connection = DriverManager.getConnection(url, username, password);
//            // ?????????SQL???
//            if (parameterMap == null) {
//                statement = connection.createStatement();
//                resultSet = statement.executeQuery(SQL);
//            }
//            // ???PreparedStatement SQL???
//            else {
//                preparedStatement = connection.prepareStatement(SQL);
//                // ???????
//                List<Object> questionList = (List<Object>) parameterMap.get("question");
//                // ???????????????
//                List<String> columnTypeList = (List<String>) parameterMap.get("columnType");
//                // ?????????
//                for (int x = 0; x < questionList.size(); x++) {
//                    switch (columnTypeList.get(x)) {
//                        case "string":
//                            preparedStatement.setString(x + 1, (String) questionList.get(x));
//                            break;
//                        case "int":
//                            preparedStatement.setInt(x + 1, (Integer) questionList.get(x));
//                            break;
//                        default:
//                            preparedStatement.setObject(x + 1, questionList.get(x));
//                    }
//                }
//                resultSet = preparedStatement.executeQuery();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//        }
//        return resultSet;
//    }
//
//    // ??????properties????????????
//    public Map<String, String> getPropertyValue(String propertyName, String... keys) {
//        Map<String, String> valueMap = new HashMap<String, String>();
//        try {
//            System.out.println("propertyName>>" + propertyName);
//            System.out.println("getClassLoader>>" + this.getClass().getClassLoader());
//            System.out.println(
//                    "getResourceAsStream>>" + this.getClass().getClassLoader().getResourceAsStream(propertyName));
//            log.debug("propertyName>>" + propertyName);
//            log.debug("getClassLoader>>" + this.getClass().getClassLoader());
//            // ?????????classpath??????properties??????
//            Properties properties = new Properties();
//            // 20160107 edit by hugo ??????properties?????? ??????????????????
//            // properties.load(this.getClass().getClassLoader().getResourceAsStream(propertyName));
//            properties.load(
//                    new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(propertyName), "UTF-8"));
//            // ??????
//            for (String key : keys) {
//                valueMap.put(key, properties.getProperty(key));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            return null;
//        }
//        return valueMap;
//    }
//
//    // IKey??????
//    public Map<String, String> iKeyVerification(String subCn) {
//        log.debug("subCn = " + subCn);
//        Map<String, String> rtnMap = new HashMap<String, String>();
//        rtnMap.put("result", "FALSE");
//        String sno = null;
//        RADATA raData = null;
//        // ????????????
//        String[] statusCode = { "?????????????????????", "??????", "?????????", "?????????", "?????????", "?????????", "?????????", "??????" };
//
//        // ??????????????????
//        try {
//            String SQL = "select * from DB2ACHNRA.CertInfo where ci_SubCn=?";
//            Object[] paramObject = new Object[1];
//            paramObject[0] = subCn;
//            System.out.println("ikey_Dao1>>" + ikey_Dao);
//            ikey_Dao = (IKey_Dao) (ikey_Dao == null ? SpringAppCtxHelper.getBean("ikey_Dao") : ikey_Dao);
//            System.out.println("ikey_Dao2>>" + ikey_Dao);
//            List<Map<String, Object>> dataList = ikey_Dao.list(SQL, paramObject);
//            if (dataList != null && dataList.size() > 0) {
//                for (Map map : dataList) {
//                    raData = new RADATA();
//                    System.out.println("map>>>>" + map);
//                    BeanUtils.populate(raData, map);
//                }
//            } else {
//                rtnMap.put("msg", "?????????????????????????????????");
//                return rtnMap;
//            }
//            // ????????????
//            if (raData != null) {
//                log.debug("????????????: " + raData.getCI_COMPANYID() + " - " + raData.getCI_COMPANYNAME());
//                log.debug("????????????: " + raData.getCI_EMPLOYEEID());
//                log.debug("??????CN: " + raData.getCI_SUBCN());
//                log.debug("????????????: " + raData.getCI_USERCHNNAME());
//                log.debug("????????????: " + raData.getCI_USEREMAIL());
//                log.debug("????????????: " + raData.getCI_SIGNCERTSTATUS() + " - "
//                        + statusCode[Integer.parseInt(raData.getCI_SIGNCERTSTATUS())]);
//                log.debug("???????????? : " + raData.getCI_SIGNCERTNOTBEFORE() + "~" + raData.getCI_SIGNCERTNOTAFTER());
//            } else {
//                log.debug("raData>>" + raData);
//                rtnMap.put("msg", "????????????????????????????????????raData>>" + raData);
//                return rtnMap;
//            }
//
//            // //???????????????
//            if (!"1".equals(raData.getCI_SIGNCERTSTATUS())) {
//                log.debug("??????????????????????????????????????????????????? " + statusCode[Integer.parseInt(raData.getCI_SIGNCERTSTATUS())] + "???!");
//                rtnMap.put("msg",
//                        "??????????????????????????????????????????????????? " + statusCode[Integer.parseInt(raData.getCI_SIGNCERTSTATUS())] + "???!");
//            }
//            // ????????????
//            else {
//                // ???????????????????????????
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//                // ????????????
//                Date certVailDate = sdf.parse(raData.getCI_SIGNCERTNOTAFTER());
//
//                // ????????????????????????
//                if (certVailDate.before(new Date())) {
//                    log.debug("??????????????????????????????????????????" + sdf2.format(certVailDate) + "???????????????!");
//                    rtnMap.put("msg", "??????????????????????????????????????????" + sdf2.format(certVailDate) + "???????????????!");
//                }
//                // ????????????????????????
//                else {
//                    // ??????
//                    log.debug("SUCCESS!");
//                    // ???IKey????????????????????????????????????????????????
//                    rtnMap.put("ikeyValidateDate", sdf2.format(certVailDate));
//                    sno = raData.getCI_SIGNCERTSERIALNO();
//                    rtnMap.put("result", "TRUE");
//                    // ???IKey???????????????????????????????????????????????????PKCS7??????
//                    rtnMap.put("msg", sno);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            rtnMap.put("msg", "???????????????" + e.toString());
//        }
//
//        return rtnMap;
//    }
//
//    // Hard-Coded Password (CWE 259) ??????????????????
//    //
//    // public Map<String,String> iKeyVerification_bak(String subCn){
//    // log.debug("subCn = " + subCn);
//    // Map<String, String> rtnMap = new HashMap<String,String>();
//    // rtnMap.put("result", "FALSE");
//    // String sno = null;
//    // RADATA raData = null;
//    // //????????????
//    // String[] statusCode = {"?????????????????????","??????","?????????","?????????","?????????","?????????","?????????","??????"};
//    //
//    // //??????????????????
//    // try{
//    //// 20150422 edit by hugo ???????????????????????????????????????????????????????????????????????????????????????????????????JNDI
//    //// Map<String,String> valueMap =
//    // getPropertyValue("IKey.properties","className","url","username","password");
//    // Map<String,String> valueMap = SpringAppCtxHelper.getBean("ikey");
//    // //????????????IKey.properties???
//    // if(valueMap != null){
//    // log.debug(String.format("className=%s,url=%s,username=%s,password=%s",valueMap.get("className"),valueMap.get("url"),valueMap.get("username"),valueMap.get("password")));
//    // String SQL = "select * from DB2ACHNRA.CertInfo where ci_SubCn=?";
//    // Map<String,Object> parameterMap = new HashMap<String,Object>();
//    // List<Object> questionList = new ArrayList<Object>();
//    // List<String> columnTypeList = new ArrayList<String>();
//    // questionList.add(subCn);
//    // columnTypeList.add("string");
//    // parameterMap.put("question",questionList);
//    // parameterMap.put("columnType",columnTypeList);
//    // //???JDBC??????????????????Schema??????????????????
//    // ResultSet resultSet =
//    // useJDBCGetResultSet(valueMap.get("className"),valueMap.get("url"),valueMap.get("username"),valueMap.get("password"),SQL,parameterMap);
//    // //???????????????
//    // if(resultSet != null){
//    // raData = new RADATA();
//    // //??????????????????????????????Bean class
//    // while(resultSet.next()){
//    // raData.setCiIDENTIFIER(resultSet.getInt("CI_IDENTIFIER"));
//    // raData.setCiUSERIDNO(resultSet.getString("CI_USERIDNO"));
//    // raData.setCiUSERSEQNO(resultSet.getString("CI_USERSEQNO"));
//    // raData.setCiUSEROPTNO(resultSet.getString("CI_USEROPTNO"));
//    // raData.setCiCERTKEYUSAGE(resultSet.getString("CI_CERTKEYUSAGE"));
//    // raData.setCiCSR_P12(resultSet.getString("CI_CSR_P12"));
//    // raData.setCiCATYPE(resultSet.getString("CI_CATYPE"));
//    // raData.setCiSUBCN(resultSet.getString("CI_SUBCN"));
//    // raData.setCiSUBCOUNTRY(resultSet.getString("CI_SUBCOUNTRY"));
//    // raData.setCiUSEREMAIL(resultSet.getString("CI_USEREMAIL"));
//    // raData.setCiRA(resultSet.getString("CI_RA"));
//    // raData.setCiSUBDN(resultSet.getString("CI_SUBDN"));
//    // raData.setCiSIGNCERTSERIALNO(resultSet.getString("CI_SIGNCERTSERIALNO"));
//    // raData.setCiCERTSERIALNO(resultSet.getString("CI_CERTSERIALNO"));
//    // raData.setCiSIGNCERTSTATUS(resultSet.getString("CI_SIGNCERTSTATUS"));
//    // raData.setCiCERTSTATUS(resultSet.getString("CI_CERTSTATUS"));
//    // raData.setCiSIGNCERTNOTAFTER(resultSet.getString("CI_SIGNCERTNOTAFTER"));
//    // raData.setCiCERTNOTAFTER(resultSet.getString("CI_CERTNOTAFTER"));
//    // raData.setCiSIGNCERTNOTBEFORE(resultSet.getString("CI_SIGNCERTNOTBEFORE"));
//    // raData.setCiCERTNOTBEFORE(resultSet.getString("CI_CERTNOTBEFORE"));
//    // raData.setCiCHALLENGEPHRASE(resultSet.getString("CI_CHALLENGEPHRASE"));
//    // raData.setCiSIGNAPPLYID(resultSet.getString("CI_SIGNAPPLYID"));
//    // raData.setCiAPPLYID(resultSet.getString("CI_APPLYID"));
//    // raData.setCiSIGNCERT(resultSet.getString("CI_SIGNCERT"));
//    // raData.setCiCERT(resultSet.getString("CI_CERT"));
//    // raData.setCiREVOKEREASON(resultSet.getString("CI_REVOKEREASON"));
//    // raData.setCiREVOKEMEMO(resultSet.getString("CI_REVOKEMEMO"));
//    // raData.setCiENABLE(resultSet.getString("CI_ENABLE"));
//    // raData.setCiLASTUPDATETIME(resultSet.getString("CI_LASTUPDATETIME"));
//    // raData.setCiLASTUPDATESOURCE(resultSet.getString("CI_LASTUPDATESOURCE"));
//    // raData.setCiOPSTATUS(resultSet.getString("CI_OPSTATUS"));
//    // raData.setCiOPRRAO(resultSet.getString("CI_OPRRAO"));
//    // raData.setCiCNFRAO(resultSet.getString("CI_CNFRAO"));
//    // raData.setCiGROUPID(resultSet.getString("CI_GROUPID"));
//    // raData.setCiUSERCHNNAME(resultSet.getString("CI_USERCHNNAME"));
//    // raData.setCiCOMPANYNAME(resultSet.getString("CI_COMPANYNAME"));
//    // raData.setCiCOMPANYID(resultSet.getString("CI_COMPANYID"));
//    // raData.setCiEMPLOYEEID(resultSet.getString("CI_EMPLOYEEID"));
//    // raData.setCiUSERPHONE(resultSet.getString("CI_USERPHONE"));
//    // raData.setCiIKEYSNO(resultSet.getString("CI_IKEYSNO"));
//    // raData.setCiUSERPHONEEXT(resultSet.getString("CI_USERPHONEEXT"));
//    // raData.setCiLOGINPWD(resultSet.getString("CI_LOGINPWD"));
//    // raData.setCiLOGINERRORTIMES(resultSet.getString("CI_LOGINERRORTIMES"));
//    // raData.setCiSIGNCERTCHAIN(resultSet.getString("CI_SIGNCERTCHAIN"));
//    // }
//    // }
//    // //?????????????????????
//    // else{
//    // log.error("Cannot get resultset : \nclassName = " +
//    // valueMap.get("className") + "\nurl = " + valueMap.get("url") + "\nusername =
//    // " + valueMap.get("username") + "\npassword = " + valueMap.get("password"));
//    // rtnMap.put("msg", "???????????????????????????!");
//    // return rtnMap;
//    // }
//    // }
//    // //????????????IKey.properties???
//    // else{
//    // log.error("Cannot get IKey.properties!");
//    // rtnMap.put("msg", "??????????????????????????????????????????!");
//    // return rtnMap;
//    // }
//    //
//    // //????????????
//    // log.debug("????????????: " + raData.getCiCOMPANYID() + " - " +
//    // raData.getCiCOMPANYNAME() );
//    // log.debug("????????????: " + raData.getCiEMPLOYEEID() );
//    // log.debug("??????CN: " + raData.getCiSUBCN() );
//    // log.debug("????????????: " + raData.getCiUSERCHNNAME() );
//    // log.debug("????????????: " + raData.getCiUSEREMAIL() );
//    // log.debug("????????????: " + raData.getCiSIGNCERTSTATUS() + " - " +
//    // statusCode[Integer.parseInt(raData.getCiSIGNCERTSTATUS())] );
//    // log.debug("???????????? : " + raData.getCiSIGNCERTNOTBEFORE() + "~" +
//    // raData.getCiSIGNCERTNOTAFTER() );
//    // //???????????????
//    // if(!"1".equals(raData.getCiSIGNCERTSTATUS())){
//    // log.debug("???????????????????????????????????????????????????
//    // "+statusCode[Integer.parseInt(raData.getCiSIGNCERTSTATUS())]+"???!");
//    // rtnMap.put("msg", "???????????????????????????????????????????????????
//    // "+statusCode[Integer.parseInt(raData.getCiSIGNCERTSTATUS())]+"???!");
//    // }
//    // //????????????
//    // else{
//    // //???????????????????????????
//    // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
//    // SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//    // //????????????
//    // Date certVailDate = sdf.parse(raData.getCiSIGNCERTNOTAFTER());
//    //
//    // //????????????????????????
//    // if(certVailDate.before(new Date())){
//    // log.debug("??????????????????????????????????????????"+ sdf2.format(certVailDate) +"???????????????!");
//    // rtnMap.put("msg", "??????????????????????????????????????????"+ sdf2.format(certVailDate) +"???????????????!");
//    // }
//    // //????????????????????????
//    // else{
//    // //??????
//    // log.debug("SUCCESS!");
//    // //???IKey????????????????????????????????????????????????
//    // rtnMap.put("ikeyValidateDate",sdf2.format(certVailDate));
//    // sno = raData.getCiSIGNCERTSERIALNO();
//    // rtnMap.put("result", "TRUE");
//    // //???IKey???????????????????????????????????????????????????PKCS7??????
//    // rtnMap.put("msg", sno);
//    // }
//    // }
//    // }
//    // catch(Exception e){
//    // e.printStackTrace();
//    // log.error(e.getMessage());
//    // rtnMap.put("msg", "???????????????" + e.toString());
//    // }
//    //
//    // return rtnMap;
//    // }
//    // ??????HSM???Response
//    public HSMSuipResponseData getHSMResponse(String keyId, String macData, String hsmIP, int hsmPort, String keyFlag) {
//        HSMSocketClient hsmSocketClient = new HSMSocketClient();
//        HSMSuipRequestData hsmSuipRequestData = new HSMSuipRequestData();
//        HSMSuipResponseData hsmSuipResponseData;
//        // 20150129txmonsum
//        String data = "";
//        // "2015012900000000"; //System Date+???8???0???16???
//        // String icv = zDateHandler.getDateNum()+"00000000";
//        String icv = zDateHandler.getDateNum();
//        try {
//            // data = date+funcId;
//            data = macData;
//            hsmSuipRequestData.setIcv(icv);
//            hsmSuipRequestData.setKeyId(keyId);
//            // 20150129txmonsum
//            hsmSuipRequestData.setData(data);
//            hsmSuipRequestData.setKeyFlag(keyFlag);
//            hsmSuipResponseData = hsmSocketClient.send2HSM(hsmIP, hsmPort, hsmSuipRequestData);
//            log.debug("hsmSuipRequestData>>" + hsmSuipRequestData.toString());
//            if (hsmSuipResponseData != null) {
//                log.debug("hsmSuipResponseData>>" + hsmSuipResponseData.toString());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.error(e.getMessage());
//            return null;
//        }
//        return hsmSuipResponseData;
//    }
//
//    // ???byte[]??????Hexstring
//    public String bytesToHex(byte[] bytes) {
//        char[] hexArray = "0123456789ABCDEF".toCharArray();
//        char[] hexChars = new char[bytes.length * 2];
//        for (int j = 0; j < bytes.length; j++) {
//            int v = bytes[j] & 0xFF;
//            hexChars[j * 2] = hexArray[v >>> 4];
//            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
//        }
//        return new String(hexChars);
//    }
//
//    public CommonSpringDao l() {
//        return commonSpringDao;
//    }
//
//    public void setCommonSpringDao(CommonSpringDao commonSpringDao) {
//        this.commonSpringDao = commonSpringDao;
//    }
//
//    public IKey_Dao getIkey_Dao() {
//        return ikey_Dao;
//    }
//
//    public void setIkey_Dao(IKey_Dao ikey_Dao) {
//        this.ikey_Dao = ikey_Dao;
//    }
//
//    /**
//     * ????????????
//     *
//     * @throws UnsupportedEncodingException
//     */
//    public void StrTest() throws UnsupportedEncodingException {
//        Map<String, String> map = this.getPropertyValue("Configuration.properties", "agent_bat_str");
//        String json = map.get("agent_bat_str");
//        Map<String, String> strmap = new Gson().fromJson(json, Map.class);
//        Map<String, String> accmap = null;
//        for (String key : strmap.keySet()) {
//            System.out.println(strmap.get(key));
//        }
//        // accmap.put("str1", strmap.get("str1").replace("#1", "?????????")) ;
//        // System.out.println(accmap.get("str1"));
//        accmap = process(strmap, "str1", "#1", "?????????");
//        System.out.println("str1>>" + accmap.get("str1"));
//        accmap = process(accmap, "str1", "#2", "20:00:00");
//        System.out.println("str1>>" + accmap.get("str1"));
//        for (String key : accmap.keySet()) {
//            System.out.println(strmap.get(key));
//        }
//        accmap = process(accmap, "str1", "#3", "21:00:00");
//        accmap = process(accmap, "str1", "@?????????", "??????");
//        System.out.println("str1>>" + accmap.get("str1"));
//        accmap = process_all(accmap);
//        for (String key : accmap.keySet()) {
//            System.out.println(strmap.get(key));
//        }
//
//    }
//
//    public TreeMap<String, String> process(Map<String, String> strmap, String key, String oldChar, String newChar) {
//        TreeMap<String, String> map = new TreeMap<String, String>();
//        if (StrUtils.isNotEmpty(key)) {
//            strmap.put(key, strmap.get(key).replace(oldChar, "@" + newChar));
//        }
//        map.putAll(strmap);
//        return map;
//
//    }
//
//    public TreeMap<String, String> process_all(Map<String, String> strmap) {
//        TreeMap<String, String> map = new TreeMap<String, String>();
//        for (String tmpkey : strmap.keySet()) {
//            map.put(tmpkey,
//                    strmap.get(tmpkey).replace("@", "").replace("#1", "?????????").replace("#2", "").replace("#3", ""));
//        }
//        return map;
//
//    }
//
//    public static <T> T objectCovert(Class<T> to, Object from) {
//        String json = null;
//        if (from == null) {
//            return null;
//        }
//        json = gs.toJson(from);
//        T t = gs.fromJson(json, to);
//
//        return t;
//    }
//
//    public static String escape(String s) {
//
//        try {
//            s = StringEscapeUtils.escapeSql(s);
//            // s = StringEscapeUtils.escapeHtml(s);
//            // s = StringEscapeUtils.escapeXml(s);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        return s;
//    }
//
//    public static <T> List<T> escapeList(List<T> s) {
//        List<T> l = new LinkedList();
//        Class<Object> T;
//        String json = "";
//        String vStr = "";
//        try {
//            for (T each : s) {
//                json = new Gson().toJson(each);
//                vStr = StringEscapeUtils.escapeSql(json);
////				vStr = StringEscapeUtils.escapeHtml(json);
//                // System.out.println("JSON :"+json);
//                System.out.println("VSTR :" + vStr);
//                if (json.equals(vStr)) {
//                    l.add(each);
//                } else {
//                    System.out.println("validate failed");
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        return l;
//    }
//
//    public static Map<String, String> escapeStringMap(Map<String, String> map) {
//        Map<String, String> returnMap = new HashMap<String, String>();
//        String vStr = "";
//        for (String key : map.keySet()) {
//            vStr = StringEscapeUtils.escapeSql(map.get(key));
//            returnMap.put(key, vStr);
//        }
//        return returnMap;
//    }
//
//    public static File escapeFile(File file) {
//        FileInputStream is = null;
//        FileOutputStream os = null;
//        byte[] content = null;
//        String dataStr = "";
//        String vStr = "";
//
//        try {
//            is = new FileInputStream(file);
//
//            byte[] data = new byte[is.available()];
//            int pos = 0;
//            while (true) {
//                int amt = is.read(data, pos, data.length - pos);
//                if (amt <= 0) {
//                    break;
//                }
//                pos += amt;
//                int avail = is.available();
//                if (avail > data.length - pos) {
//                    byte[] newData = new byte[(pos + avail)];
//                    System.arraycopy(data, 0, newData, 0, pos);
//                    data = newData;
//                }
//            }
//
//            is.close();
//            dataStr = new String(data, StandardCharsets.UTF_8);
//            vStr = StringEscapeUtils.escapeSql(dataStr);
////			vStr = StringEscapeUtils.escapeHtml(dataStr);
//            content = vStr.getBytes();
//            os = new FileOutputStream(file, false);
//            os.write(content);
//            os.flush();
//            os.close();
//        } catch (FileNotFoundException e) {
//        } catch (IOException e) {
//        } finally {
//            close(is);
//            close(os);
//        }
//        return file;
//    }
//
//    public static File[] escapeFile2(File[] fileArrary) {
//        List<File> listOfFile = new ArrayList<File>();
//        for (File each : fileArrary) {
//            FileInputStream is = null;
//            FileOutputStream os = null;
//            byte[] content = null;
//            String dataStr = "";
//            String vStr = "";
//
//            try {
//                is = new FileInputStream(each);
//
//                byte[] data = new byte[is.available()];
//                int pos = 0;
//                while (true) {
//                    int amt = is.read(data, pos, data.length - pos);
//                    if (amt <= 0) {
//                        break;
//                    }
//                    pos += amt;
//                    int avail = is.available();
//                    if (avail > data.length - pos) {
//                        byte[] newData = new byte[(pos + avail)];
//                        System.arraycopy(data, 0, newData, 0, pos);
//                        data = newData;
//                    }
//                }
//
//                is.close();
//                dataStr = new String(data, StandardCharsets.UTF_8);
//                vStr = StringEscapeUtils.escapeSql(dataStr);
////				vStr = StringEscapeUtils.escapeHtml(dataStr);
//                content = vStr.getBytes();
//                os = new FileOutputStream(each, false);
//                os.write(content);
//                os.flush();
//                os.close();
//            } catch (FileNotFoundException e) {
//            } catch (IOException e) {
//            } finally {
//                close(is);
//                close(os);
//            }
//            listOfFile.add(each);
//        }
//        fileArrary = listOfFile.toArray(fileArrary);
//        return fileArrary;
//    }
//
//    public static InputStream escapeInpS(InputStream in) {
//        StringWriter writer = new StringWriter();
//        try {
//            IOUtils.copy(in, writer, "UTF-8");
//
//            String vStr = StringEscapeUtils.escapeSql(writer.toString());
////			vStr = StringEscapeUtils.escapeHtml(writer.toString());
//            in = new ByteArrayInputStream(vStr.getBytes());
//        } catch (IOException e) {
//
//        }
//
//        return in;
//    }
//
//    private static void close(Closeable closeable) {
//        if (closeable != null) {
//            try {
//                closeable.close();
//            } catch (IOException e) {
//
//            }
//        }
//    }
//
//    //	DB2 SQL Error: SQLCODE=-438, SQLSTATE=88888, SQLERRMC=null, DRIVER=4.14.111
//    public static String parseDB2Error(String errorStr ) {
//        Map<String,String> returnMap = new HashMap<String,String>();
//        String sqlstate="SQLSTATE=";
//        String retStr="";
//        Integer start = 0;
//        Integer end = 0;
//        start = errorStr.indexOf(sqlstate) ;
//        end = errorStr.indexOf(",", start);
//        if(start != -1){
//            retStr = errorStr.substring(start+sqlstate.length() , end).trim();
//        }
//
//        return retStr;
//    }
//
//    public static void main(String[] args) throws UnsupportedEncodingException {
//        CodeUtils c = new CodeUtils();
//        // System.out.println(c.iKeyVerification("0040000-01"));
//        // String gg ="??????????????????????????????????????????";
//        // System.out.println(new CodeUtils().findHardWordNum(gg,new
//        // String(gg.getBytes("BIG5"),"BIG5")));
//        c.StrTest();
//    }
//}
