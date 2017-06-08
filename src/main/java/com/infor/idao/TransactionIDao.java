package com.infor.idao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import com.infor.dao.TransactionDao;
import com.infor.models.InforParking;
import com.infor.models.InforTransaction;
import com.infor.models.InforUser;

@Repository
@Transactional
public class TransactionIDao extends HibernateDaoSupport implements TransactionDao{
	
	private static final String UPDATE_TRANSACTION = "update InforTransaction set timeout=:timeout where userid=:userid";
	private static final String FETCH_TRANSACTION = "from InforTransaction";
	private static final String FETCH_PARKING = "from InforParking where userid=:userid";
	private static final String FETCH_TANDEM_PARKING = "select ip.userid,iu.firstname,iu.lastname,iu.position,iu.contactnumber,iu.emailaddress,iu.inforaddress from tbl_inforparking ip inner join tbl_inforuser iu on ip.userid = iu.userid where ip.parkingid=:parkingid and ip.userid not in(select userid from tbl_inforparking where userid=:userid)";
	
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
		List<InforParking> transaction = customSelectQuery(FETCH_PARKING)
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
		List<InforTransaction> transaction = customSelectQuery(FETCH_TRANSACTION.concat(" where userid=:userid and timeout='-'"))
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
				.setParameter("userid", inforTransaction.getUserid())
				.list();	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<InforUser> getTandemParkingDetails(InforTransaction inforTransaction) {
		// TODO Auto-generated method stub
		List<InforUser> iu = new ArrayList<InforUser>();
		List<Object[]> plainObj = customNativeSelectQuery(FETCH_TANDEM_PARKING)
				.setParameter("userid", inforTransaction.getUserid())
				.setParameter("parkingid", inforTransaction.getParkingid())
				.list();	
		for(Object[] obj: plainObj){
			InforUser inforUser = new InforUser();
			inforUser.setUserid((Integer)obj[0]);
			inforUser.setFirstname((String)obj[1]);
			inforUser.setLastname((String)obj[2]);
			inforUser.setPosition((String)obj[3]);
			inforUser.setContactnumber((String)obj[4]);
			inforUser.setEmailaddress((String)obj[5]);
			inforUser.setInforaddress((String)obj[6]);
			iu.add(inforUser);
		}	
		return iu;
	}

}
