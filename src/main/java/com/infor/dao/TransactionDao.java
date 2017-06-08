package com.infor.dao;

import java.util.List;

import com.infor.models.InforParking;
import com.infor.models.InforTransaction;
import com.infor.models.InforUser;

public interface TransactionDao {
	public void beginTransaction(InforTransaction inforTransaction);
	public void endTransaction(InforTransaction inforTransaction);
	public boolean checkIfRegisteredForParking(InforTransaction inforTransaction);
	public boolean checkIfHavingTimeOut(InforTransaction inforTransaction);
	public List<InforParking> getParkingDetails(InforTransaction inforTransaction);
	public List<InforUser> getTandemParkingDetails(InforTransaction inforTransaction);
}
