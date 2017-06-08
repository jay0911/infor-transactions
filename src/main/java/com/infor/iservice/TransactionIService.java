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
			dto.setInforParking(inforParkings.get(0));
			if(td.checkIfHavingTimeOut(it)){
				aj.setCode("200");
				aj.setMsg("account to timeout");
			}else{
				aj.setCode("201");
				aj.setMsg("account to timein");
			}
			if(inforParkings.get(0).getIsparkingtandem().equals("Yes")){
				InforTransaction transact = new InforTransaction();
				transact.setUserid(inforParkings.get(0).getUserid());
				transact.setParkingid(inforParkings.get(0).getParkingid());
				dto.setTandemParkingDetails(td.getTandemParkingDetails(transact).get(0));
			}
		}else{
			aj.setCode("400");
			aj.setMsg("unregistered");
		}
		dto.setAjaxResponseBody(aj);
		return dto;
	}

	@Override
	public void beginTransaction(InforTransaction inforTransaction) {
		// TODO Auto-generated method stub
		td.beginTransaction(inforTransaction);
	}

	@Override
	public void endTransaction(InforTransaction inforTransaction) {
		// TODO Auto-generated method stub
		td.endTransaction(inforTransaction);
	}

}
