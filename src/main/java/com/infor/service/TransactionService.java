package com.infor.service;

import com.infor.dto.TransactionDTO;
import com.infor.models.AjaxResponseBody;
import com.infor.models.InforTransaction;

public interface TransactionService {
	public TransactionDTO checkIfRegisteredForParking(InforTransaction it);
}
