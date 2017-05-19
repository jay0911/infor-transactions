package com.infor.service;

import com.infor.models.AjaxResponseBody;
import com.infor.models.InforTransaction;

public interface TransactionService {
	public AjaxResponseBody checkIfRegisteredForParking(InforTransaction it);
}
