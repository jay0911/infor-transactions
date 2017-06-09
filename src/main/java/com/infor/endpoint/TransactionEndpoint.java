package com.infor.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.infor.dto.TransactionDTO;
import com.infor.models.AjaxResponseBody;
import com.infor.models.Email;
import com.infor.models.InforTransaction;
import com.infor.service.TransactionService;

@RestController
public class TransactionEndpoint {
	
	@Autowired
	private TransactionService ts;
	
	@Value("${email.host}")
	private String host;
	
	@Value("${email.socketport}")
	private String socketport;
	
	@Value("${email.socketclass}")
	private String socketclass;
	
	@Value("${email.auth}")
	private String auth;
	
	@Value("${email.port}")
	private String port;
	
	@Value("${email.username}")
	private String username;
	
	@Value("${email.password}")
	private String password;
	
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
		AjaxResponseBody aj = new AjaxResponseBody();
		try{
			ts.endTransaction(it);
			aj.setCode("200");
			aj.setMsg("timeout success");
		}catch (Exception e) {
			// TODO: handle exception
			aj.setCode("400");
			aj.setMsg(e.getMessage());
		}
		return aj;
	}
	
	@PostMapping("/sendmail")
	public AjaxResponseBody sendemail(@RequestBody Email email){
		email.setHost(host);
		email.setSocketPort(socketport);
		email.setSocketClass(socketclass);
		email.setAuth(auth);
		email.setPort(port);
		email.setUserName(username);
		email.setPassword(password);
		
		AjaxResponseBody aj = new AjaxResponseBody();
		try{
			ts.sendMail(email);
			aj.setCode("200");
			aj.setMsg("email send success");
		}catch (Exception e) {
			// TODO: handle exception
			aj.setCode("400");
			aj.setMsg(e.getMessage());
		}
		return aj;
	}

}
