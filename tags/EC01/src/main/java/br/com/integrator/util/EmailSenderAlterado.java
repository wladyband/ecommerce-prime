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

public class EmailSenderAlterado {
	public void enviarEmail(String emailDestino, String assuntoEmail, String conteudo){
		Properties p = new Properties();
		
		/*class="org.springframework.mail.javamail.JavaMailSenderImpl">
		<property name="host" value="smtp.gmail.com"/>
	    <property name="protocol" value="smtps"/>
		<property name="username" value="senac.gilson@gmail.com"/>
		<property name="password" value="[tgsist2012]"/>
		<property name="port" value="587"/>*/
		
		p.put("mail.transport.protocol", "smtp"); // define protocolo de envio como SMTP
		p.put("mail.smtp.host", "smtp.gmail.com"); // server SMTP
		p.put("mail.smtp.auth", "true"); // ativa autenticacao
		
		Authenticator auth = new SMTPAuthenticator();

		Session session = Session.getDefaultInstance(p, auth);

		MimeMessage msg = new MimeMessage(session);
		
		try {
			msg.setFrom(new InternetAddress("senac.gilson@gmail.com"));
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
			tr.connect("smtp.gmail.com", "senac.gilson@gmail.com", "[tgsist2012]");
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
			String username = "senac.gilson@gmail.com";
			String password = "[tgsist2012]";
			return new PasswordAuthentication(username, password);
		}
	}
}