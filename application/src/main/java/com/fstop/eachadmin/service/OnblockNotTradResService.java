package com.fstop.eachadmin.service;

import java.util.List;

import java.util.Map;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fstop.eachadmin.repository.BUSINESS_TYPE_Dao;
import com.fstop.infra.dao.OnPendingTabRepository;
import com.fstop.infra.entity.BusinessType;
import com.fstop.infra.entity.OnPendingTab;
import com.fstop.infra.entity.OnPendingTabPk;

import util.messageConverter;
import util.DateTimeUtils;
import util.StrUtils;
import util.socketPackage;
import util.zDateHandler;
import util.socketPackage.Body;
import util.socketPackage.Header;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstop.eachadmin.dto.SendRq;
import com.fstop.eachadmin.dto.SendRs;


@Service
public class OnblockNotTradResService {
	
	@Autowired
	private BUSINESS_TYPE_Dao business_type_Dao ;
	
	@Autowired
	OnPendingTabRepository OnPendingTabR;

	
	// businessLabel
	public List<Map<String,String>> getBsTypeIdList () {
		
		// List<BUSINESS_TYPE> list = business_type_Dao.getAll();
		// List<BUSINESS_TYPE> list = business_type_Dao.find("FROM tw.org.twntch.po.BUSINESS_TYPE ORDER BY BUSINESS_TYPE_ID");
		// TODO:
		// dao 還沒有改寫好, 暫時先用
		List<BUSINESS_TYPE> list = business_type_Dao.testFunction();
		List<Map<String,String>> beanList = new LinkedList<Map<String,String>>();
		
		Map<String,String> bean = null;
		
		for (BUSINESS_TYPE po :list) {
			po.getBUSINESS_TYPE_ID();
			String k = (String) po.getBUSINESS_TYPE_ID() + " - " + po.getBUSINESS_TYPE_NAME();
			String v = (String) po.getBUSINESS_TYPE_ID();
			bean = new HashMap<String,String>();
			bean.put(k, v);
			beanList.add(bean);
		}
		System.out.println("beanList>>" + beanList);
		return beanList;
	}
	
	public void testFunction () {
		System.out.println("哭哭");
	}
	
	
	
	

	
	
    
//===========================================================================
	
	
    //details----請求傳送未完成交易結果(1406)
    public SendRs send_1406(SendRq param){
		/* 查詢未完成交易處理結果 
			<?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
			<msg> 
			    <header> 
			        <SystemHeader>eACH01</SystemHeader> 
			        <MsgType>0100</MsgType> 
			        <PrsCode>1406</PrsCode> 
			        <Stan>XXXXXXX</Stan> 
			        <InBank>0000000</InBank> 
			        <OutBank>9990000</OutBank> 
			        <DateTime>YYYYMMDDHHMMSS</DateTime> 
			        <RspCode>0000</RspCode> 
			    </header> 
			    <body> 
			         <OTxDate></OTXDate> 
			         <OSTAN></OSTAN> 
			    </body> 
			</msg> 

		 */
		String json = "{}";
		//String stan = StrUtils.isNotEmpty(param.get("STAN"))?param.get("STAN"):"" ;
//		String stan = StrUtils.isNotEmpty(param.get("STAN"))?param.get("STAN"):"" ;//innput
		String stan = StrUtils.isNotEmpty(param.getStan())?param.getStan():"" ;//innput
//		String txdate = StrUtils.isNotEmpty(param.get("TXDATE"))?param.get("TXDATE"):"" ;//innput
		String txdate = StrUtils.isNotEmpty(param.getTxdate())?param.getTxdate():"" ;//innput
		txdate = DateTimeUtils.convertDate(2, txdate, "yyyy-MM-dd", "yyyyMMdd");
		String resultCode = "";
		Map rtnMap = new HashMap();
		try {
			//先檢查onpendingtab中是否有該筆資料存在
			OnPendingTabPk id = new OnPendingTabPk(txdate, stan);
			Optional<OnPendingTab> po = OnPendingTabR.findById(id);
			if(po == null){
				rtnMap.put("result", "FALSE");//outtput
				rtnMap.put("msg", "失敗，資料尚未轉移，PK={STAN:"+stan+",TXDATE:"+txdate+"}");//outtput
			}else{
//				20150529 add by hugo req by UAT-20150526-06
				if(po.get().getBIZDATE() !=null){//表示已有處理結果
					rtnMap.put("result", "FALSE");//outtput
					rtnMap.put("msg", "已有未完成交易處理結果，營業日="+po.get().getBIZDATE());//outtput
				//TODO
				//	json = JSONUtils.map2json(rtnMap);
//					return json;
				}
				Header msgHeader = new Header();
				msgHeader.setSystemHeader("eACH01");
				msgHeader.setMsgType("0100");
				msgHeader.setPrsCode("1406");
				msgHeader.setStan("");//此案例未使用
				msgHeader.setInBank("0000000");
				msgHeader.setOutBank("9990000");	//20150109 FANNY說 票交發動的電文，「OUTBANK」必須固定為「9990000」
				msgHeader.setDateTime(zDateHandler.getDateNum()+zDateHandler.getTheTime().replaceAll(":", ""));
				msgHeader.setRspCode("0000");
				socketPackage msg = new socketPackage();
				msg.setHeader(msgHeader);
				Body body = new Body();
				body.setOSTAN(stan);//bsformdto
				body.setOTxDate(txdate);//otxdate11//txdate bsformdto
//				body.setResultCode(resultCode);
				msg.setBody(body);//11
				String telegram = messageConverter.marshalling(msg);
				//TODO
//				rtnMap = socketClient.send(telegram);//socket先註解
			}
		} catch ( JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnMap.put("result", "FALSE");//outtput
			rtnMap.put("msg", "失敗，電文發送異常");//outtput
		}catch(Exception ee){
			ee.printStackTrace();
			rtnMap.put("result", "FALSE");//outtput
			rtnMap.put("msg", "失敗，系統異常");//outtput
		}
		//TODO
		//object mapper把JS轉RS型別
		
//		json = JSONUtils.map2json(rtnMap);//json先不搬
//		return json;
		ObjectMapper mapper = new ObjectMapper();
		SendRs result = mapper.convertValue(rtnMap, SendRs.class);
		return result;
	}
//
//=============================================================================
    
    
	//===========================================================
	//details----請求傳送確認訊息(1400)
    public SendRs send_1400(SendRq param){
		/* 請求傳送確認訊息			
<?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
<msg> 
    <header> 
        <SystemHeader>eACH01</SystemHeader> 
        <MsgType>0100</MsgType> 
        <PrsCode>1400</PrsCode> 
        <Stan>XXXXXXX</Stan> 
        <InBank>0000000</InBank> 
        <OutBank>9990000</OutBank> 
        <DateTime>YYYYMMDDHHMMSS</DateTime> 
        <RspCode>0000</RspCode> 
    </header> 
    <body> 
         <OTxDate></OTxDate> 
         <OSTAN></OSTAN> 
    </body> 
</msg> 



		 */
		String json = "{}";
//		String stan = StrUtils.isNotEmpty(param.get("STAN"))?param.get("STAN"):"" ;//innput
//		String txdate = StrUtils.isNotEmpty(param.get("TXDATE"))?param.get("TXDATE"):"" ;//innput
		String stan = StrUtils.isNotEmpty(param.getStan())?param.getStan():"" ;//innput
		String txdate = StrUtils.isNotEmpty(param.getTxdate())?param.getTxdate():"" ;//innput
		txdate = DateTimeUtils.convertDate(2, txdate, "yyyy-MM-dd", "yyyyMMdd");
		String resultCode = "";
		Map rtnMap = new HashMap();
		try {
			//先檢查onpendingtab中是否有該筆資料存在
			OnPendingTabPk id = new OnPendingTabPk(txdate, stan);
			Optional<OnPendingTab> po = OnPendingTabR.findById(id);
			if(po != null){
				rtnMap.put("result", "FALSE");//outtput
				rtnMap.put("msg", "失敗，資料已轉移，PK={STAN:"+stan+",TXDATE:"+txdate+"}");//outtput
			}else{
				Header msgHeader = new Header();
				msgHeader.setSystemHeader("eACH01");
				msgHeader.setMsgType("0100");
				msgHeader.setPrsCode("1400");
				msgHeader.setStan("");//此案例未使用
				msgHeader.setInBank("0000000");
				msgHeader.setOutBank("9990000");	//20150109 FANNY說 票交發動的電文，「OUTBANK」必須固定為「9990000」
				msgHeader.setDateTime(zDateHandler.getDateNum()+zDateHandler.getTheTime().replaceAll(":", ""));
				msgHeader.setRspCode("0000");
				socketPackage msg = new socketPackage();
				msg.setHeader(msgHeader);
				Body body = new Body();
				body.setOSTAN(stan);
				body.setOTxDate(txdate);
//				body.setResultCode(resultCode);
				msg.setBody(body);
				String telegram = messageConverter.marshalling(msg);
				//TODO
//				rtnMap = socketClient.send(telegram);
			}
		} catch ( JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnMap.put("result", "FALSE");//outtput
			rtnMap.put("msg", "失敗，電文發送異常");//outtput
		}catch(Exception ee){
			ee.printStackTrace();
			rtnMap.put("result", "FALSE");//outtput
			rtnMap.put("msg", "失敗，系統異常");//outtput
		}
		
		//TODO
		//object mapper 轉RS型別
		ObjectMapper mapper = new ObjectMapper();
		SendRs response = mapper.convertValue(rtnMap, SendRs.class);
		
//		json = JSONUtils.map2json(rtnMap);
//		return json;
		return response;
	}
	
//===============================================================================

}




