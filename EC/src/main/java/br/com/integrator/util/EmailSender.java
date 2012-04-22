package br.com.integrator.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
	public void enviarEmail(String emailDestino, String assuntoEmail, String conteudo){
		Properties p = new Properties();
		
		p.put("mail.transport.protocol", "smtp"); // define protocolo de envio como SMTP
		p.put("mail.smtp.host", "mail.javacia.com.br"); // server SMTP
		p.put("mail.smtp.auth", "true"); // ativa autenticacao
		
		Authenticator auth = new SMTPAuthenticator();

		Session session = Session.getDefaultInstance(p, auth);

		MimeMessage msg = new MimeMessage(session);
		
		try {
			msg.setFrom(new InternetAddress("email_tgi@javacia.com.br"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailDestino));
			msg.setSentDate(new Date());

			msg.setSubject(assuntoEmail);
			msg.setContent(conteudo, "text/html");
		} catch (Exception e) {
			System.out.println("Erro ao enviar um email." + e);
		}

		// Objeto encarregado de enviar os dados para o email
		Transport tr;
		try {
			tr = session.getTransport("smtp"); // define smtp para transporte
			tr.connect("mail.javacia.com.br", "email_tgi@javacia.com.br", "tgi12345");
			msg.saveChanges(); // don't forget this
			// envio da mensagem
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();
		} catch (Exception e) {
			System.out.println("Erro ao enviar um email. " + e);
		}
	}
	
	private class SMTPAuthenticator extends Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = "email_tgi@javacia.com.br";
			String password = "tgi12345";
			return new PasswordAuthentication(username, password);
		}
	}
}