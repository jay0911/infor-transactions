package com.infor.idao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.infor.dao.TransactionDao;
import com.infor.models.InforParking;
import com.infor.models.InforTransaction;

@Repository
@Transactional
public class TransactionIDao extends HibernateDaoSupport implements TransactionDao{
	
	private static final String UPDATE_TRANSACTION = "update InforTransaction set timeout=:timeout where userid=:userid";
	private static final String FETCH_TRANSACTION = "from InforTransaction";
	private static final String FETCH_PARKING = "from InforParking where userid=:userid";

	@Override
	public void beginTransaction(InforTransaction inforTransaction) {
		// TODO Auto-generated method stub
		getSessionFactory().save(inforTransaction);
	}

	@Override
	public void endTransaction(InforTransaction inforTransaction) {
		// TODO Auto-generated method stub
		Query q= getSessionFactory().createQuery(UPDATE_TRANSACTION);
		q.setParameter("timeout", inforTransaction.getTimeout());
		q.setParameter("userid", inforTransaction.getUserid());
		q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkIfRegisteredForParking(InforTransaction inforTransaction) {
		// TODO Auto-generated method stub
		boolean isRegistered = false;	
		List<InforTransaction> transaction = customSelectQuery(FETCH_TRANSACTION.concat(" where userid=:userid"))
				.setParameter("userid", inforTransaction.getUserid())
				.list();		
		if(transaction.size() > 0){
			isRegistered = true;
		}		
		return isRegistered;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean checkIfHavingTimeOut(InforTransaction inforTransaction) {
		// TODO Auto-generated method stub
		boolean isHavingTimeOut = false;	
		List<InforTransaction> transaction = customSelectQuery(FETCH_TRANSACTION.concat(" where userid=:userid and timeout==\"\""))
				.setParameter("userid", inforTransaction.getUserid())
				.list();		
		if(transaction.size() > 0){
			isHavingTimeOut = true;
		}		
		return isHavingTimeOut;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InforParking> getParkingDetails(InforTransaction inforTransaction) {
		// TODO Auto-generated method stub
		return customSelectQuery(FETCH_PARKING)
				.list();
	}

}
