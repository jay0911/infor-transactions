package com.infor.iservice;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
		// TODO Auto-generated method stub

		Properties props = new Properties();
		props.put("mail.smtp.host", email.getHost());
		props.put("mail.smtp.socketFactory.port", email.getSocketPort());
		props.put("mail.smtp.socketFactory.class", email.getSocketClass());
		props.put("mail.smtp.auth", email.getAuth());
		props.put("mail.smtp.port", email.getPort());

		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email.getUserName(),email.getPassword());
				}
			});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email.getSenderAddress()));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(email.getToAddress()));
			message.setSubject(email.getSubject());
			message.setText(email.getMessage());

			Transport.send(message);

			System.out.println("Done sending email");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	
	}

}
