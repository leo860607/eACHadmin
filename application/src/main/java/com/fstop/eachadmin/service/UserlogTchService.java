package com.fstop.eachadmin.service;

public class UserlogTchService {

	Each_Userlog_Form userlog_form = (Each_Userlog_Form) form ;
	
	String ac_key = StrUtils.isEmpty(userlog_form.getAc_key())?"":userlog_form.getAc_key();
	System.out.println("ADMUSERLOG_Action is start >> " + ac_key);
	
	Login_Form login_form = (Login_Form) WebServletUtils.getRequest().getSession().getAttribute("login_form");
	String fc_type = WebServletUtils.getRequest().getParameter("USER_TYPE");
	
	if(!ac_key.equals("back")){
		userlog_form.setFc_type(fc_type);
	}
	
	System.out.println("fc_type>>>"+fc_type);
	
	if(StrUtils.isNotEmpty(ac_key)){
		if(ac_key.equals("search")){
		}else if(ac_key.equals("update")){
		}else if(ac_key.equals("back")){
			BeanUtils.populate(userlog_form, JSONUtils.json2map(userlog_form.getSerchStrs()));
			System.out.println("userlog_form.getFc_type>>"+userlog_form.getFc_type());
			if(userlog_form.getFc_type().equals("A")){
				userlog_form.setUSER_TYPE((login_form.getUserData().getUSER_TYPE()));
				userlog_form.setUserIdList(userlog_bo.getUserIdListByComId(""));
				userlog_form.setUserCompanyList(userlog_bo.getUserCompanyList());
				userlog_form.setFuncList(userlog_bo.getFuncList());
			}else if(userlog_form.getFc_type().equals("B")) {
				System.out.println("Fc_type ="+userlog_form.getFc_type()+" ,ROLE_TYPE ="+userlog_form.getROLE_TYPE()+" ,USER_COMPANY ="+userlog_form.getUSER_COMPANY());
				userlog_form.setUSER_TYPE("B");
				
				if(userlog_form.getROLE_TYPE().equals("B")){
					setDropdownList4back(userlog_form , login_form);
				}else{
					setDropdownList4back2(userlog_form , login_form);
				}
				
			}
		}else if(ac_key.equals("add")){
		}else if(ac_key.equals("edit")){
			BeanUtils.populate(userlog_form, JSONUtils.json2map(userlog_form.getEdit_params()));
			System.out.println("pk>>"+userlog_form.getSERNO()+","+ userlog_form.getUSERID()+" , "+ userlog_form.getUSER_COMPANY());
			BeanUtils.copyProperties(userlog_form, userlog_bo.getDetail(userlog_form.getSERNO(), userlog_form.getUSERID(), userlog_form.getUSER_COMPANY()));
		}else if(ac_key.equals("save")){
		}else if(ac_key.equals("delete")){
		}
	}else{
		userlog_form.setUSER_COMPANY(login_form.getUserData().getUSER_COMPANY());
		setDropdownList(userlog_form , login_form);
	}
	
	if(StrUtils.isEmpty(target)){
		target = "search";
	}
	
	System.out.println("forward to >> " + target);
	return (mapping.findForward(target));
}
