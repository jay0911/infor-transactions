package com.infor.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infor.models.AjaxResponseBody;
import com.infor.models.InforTransaction;
import com.infor.service.TransactionService;

@RestController
public class TransactionEndpoint {
	
	@Autowired
	private TransactionService ts;
	
	@PostMapping("/checkregisteredforparking")
	public AjaxResponseBody checkIfRegisteredForParking(@RequestBody InforTransaction it){
		return ts.checkIfRegisteredForParking(it);
	}

}
