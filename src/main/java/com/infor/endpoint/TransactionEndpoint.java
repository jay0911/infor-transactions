package com.infor.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infor.dto.TransactionDTO;
import com.infor.models.AjaxResponseBody;
import com.infor.models.InforTransaction;
import com.infor.service.TransactionService;

@RestController
public class TransactionEndpoint {
	
	@Autowired
	private TransactionService ts;
	
	@PostMapping("/checkregisteredforparking")
	public TransactionDTO checkIfRegisteredForParking(@RequestBody InforTransaction it){
		return ts.checkIfRegisteredForParking(it);
	}
	
	@PostMapping("/begintransaction")
	public AjaxResponseBody beginTransaction(@RequestBody InforTransaction it){
		AjaxResponseBody aj = new AjaxResponseBody();
		try{
			ts.beginTransaction(it);
			aj.setCode("200");
			aj.setMsg("timein success");
		}catch (Exception e) {
			// TODO: handle exception
			aj.setCode("400");
			aj.setMsg(e.getMessage());
		}
		return aj;
	}
	
	@PostMapping("/endtransaction")
	public AjaxResponseBody endTransaction(@RequestBody InforTransaction it){
		return null;
	}

}
