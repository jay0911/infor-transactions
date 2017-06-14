package com.infor.iservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infor.dao.TransactionDao;
import com.infor.dto.TransactionDTO;
import com.infor.models.AjaxResponseBody;
import com.infor.models.Email;
import com.infor.models.InforParking;
import com.infor.models.InforTransaction;
import com.infor.service.TransactionService;

@Service
public class TransactionIService implements TransactionService{
	
	@Autowired
	private TransactionDao td;
	
	@Autowired
	private Email mail;

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
			dto.setInforCars(td.getOwnedCars(it));
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
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		inforTransaction.setTimein(dateFormat.format(date));
		inforTransaction.setTimeout("-");
		td.beginTransaction(inforTransaction);
	}

	@Override
	public void endTransaction(InforTransaction inforTransaction) {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		inforTransaction.setTimeout(dateFormat.format(date));
		td.endTransaction(inforTransaction);
	}

	@Override
	public void sendMail(Email email) {	
		StringBuilder sb = new StringBuilder();
		createMessage(sb,email);	
		// TODO Auto-generated method stub
		mail.setSenderAddress(email.getSenderAddress());
		mail.setToAddress(email.getToAddress());
		mail.setSubject(email.getSubject());
		mail.setMessage(sb.toString());
		mail.send();
	}
	
	private void createMessage(StringBuilder sb,Email email){
		sb.append(email.getMessage());
		sb.append(System.getProperty("line.separator"));
		sb.append("- "+email.getSenderAddress());
	}

}
