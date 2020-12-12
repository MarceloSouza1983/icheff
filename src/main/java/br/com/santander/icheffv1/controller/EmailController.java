package br.com.santander.icheffv1.controller;

import java.io.File;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

	@RequestMapping(value = "/send", method = RequestMethod.POST)
	@ResponseBody
	public void login(@RequestBody String login) throws MessagingException, EmailException {

		// Pega o endereço de e-mail do do json enviado ao controller salvo na chave, icheff-email-user do localstorage
		login = login.substring(12, login.length() -2);

		try {
			HtmlEmail mensagem = conectaEmail();
			mensagem.addTo(login); 
			mensagem.setSubject("Obrigado por comprar no iCheff! Confira os detalhes do seu pedido");
			
			String id = mensagem.embed(new File("frontend/img/logotipo-icheff-original.png"));
            
		    String imagem = "</br><img src=\"cid:" + id + "\"></br><h3>A satisfação em comer bem!</h3>";
			
			mensagem.setHtmlMsg("<html><h3>Olá, </h3>" + "</br></br><h3>Segue a lista de compras:</h3>" + imagem + "</br></br><h3>Obrigado por comprar no iCheff</h3></html>");
			mensagem.buildMimeMessage();
			Message m = mensagem.getMimeMessage();
			Transport transport = mensagem.getMailSession().getTransport("smtp");
			transport.connect(mensagem.getHostName(), 587, null, null);
			transport.sendMessage(m, m.getAllRecipients());
			
			//System.out.println("\nE-mail enviado para o cliente " + login + "\n");
			
		} catch (MessagingException ex) {
			System.out.println(ex);
		} catch (EmailException ex) {
			System.out.println(ex);
		}

	}
	
	public static HtmlEmail conectaEmail() throws EmailException, NoSuchProviderException, MessagingException {
		String hostname = "smtp.gmail.com";
		String username = "-@gmail.com";
		String password = "suasenha";
		String emailOrigem = "-@gmail.com";
		String nomeOrigem = "Loja iCheff";
		
		HtmlEmail email = new HtmlEmail();
		email.setHostName(hostname);
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator(username, password));
		email.setFrom(emailOrigem, nomeOrigem);
		email.setDebug(false);
		
		Properties props = new Properties();
		props.setProperty(hostname, "smtp");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.port", "" + 587);
		props.setProperty("mail.smtp.starttls.enable", "true");
		Session mailSession = Session.getInstance(props, new DefaultAuthenticator(username, password));
		email.setMailSession(mailSession);
		return email;
	}

}