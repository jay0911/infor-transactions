package com.infor.iservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infor.dao.TransactionDao;
import com.infor.models.AjaxResponseBody;
import com.infor.models.InforTransaction;
import com.infor.service.TransactionService;

@Service
public class TransactionIService implements TransactionService{
	
	@Autowired
	private TransactionDao td;

	@Override
	public AjaxResponseBody checkIfRegisteredForParking(InforTransaction it) {
		// TODO Auto-generated method stub
		AjaxResponseBody aj = new AjaxResponseBody();
		if(td.checkIfRegisteredForParking(it)){
			aj.setCode("200");
			aj.setMsg("registered");
		}else{
			aj.setCode("400");
			aj.setMsg("unregistered");
		}
		return aj;
	}

}
