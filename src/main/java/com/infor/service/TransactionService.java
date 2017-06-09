package com.infor.service;

import com.infor.dto.TransactionDTO;
import com.infor.models.Email;
import com.infor.models.InforTransaction;

public interface TransactionService {
	public TransactionDTO checkIfRegisteredForParking(InforTransaction it);
	public void beginTransaction(InforTransaction inforTransaction);
	public void endTransaction(InforTransaction inforTransaction);
	public void sendMail(Email email);
}
