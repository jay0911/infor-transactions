package com.infor.iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infor.dao.TransactionDao;
import com.infor.dto.TransactionDTO;
import com.infor.models.AjaxResponseBody;
import com.infor.models.InforParking;
import com.infor.models.InforTransaction;
import com.infor.service.TransactionService;

@Service
public class TransactionIService implements TransactionService{
	
	@Autowired
	private TransactionDao td;

	@Override
	public TransactionDTO checkIfRegisteredForParking(InforTransaction it) {
		// TODO Auto-generated method stub
		List<InforParking> inforParkings = td.getParkingDetails(it);
		TransactionDTO dto = new TransactionDTO();
		AjaxResponseBody aj = new AjaxResponseBody();
		if(inforParkings.size() > 0){
			aj.setCode("200");
			aj.setMsg("registered");
		}else{
			aj.setCode("400");
			aj.setMsg("unregistered");
		}
		dto.setAjaxResponseBody(aj);
		dto.setInforParking(inforParkings.get(0));
		return dto;
	}

}
