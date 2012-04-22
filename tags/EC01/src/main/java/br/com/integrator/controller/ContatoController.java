package br.com.integrator.controller;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Controller;

import br.com.integrator.util.EmailSender;
import br.com.integrator.util.EmailSenderAlterado;
import br.com.integrator.util.FacesUtils;

@Controller("contatoController")
@Scope("request")
public class ContatoController {
	private String nome;
	private String email;
	private String assunto;
	private String mensagem;
	
	@Resource
	private JavaMailSender enviarEmail;
	
	
	public ContatoController(){}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAssunto() {
		return assunto;
	}
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	

	
	public String enviar(){
		
		if(nome!=null && !nome.equals("") &&
				email!=null && !email.equals("") &&	
				assunto!=null && !assunto.equals("") &&
				mensagem!=null && !mensagem.equals("")
		){
			EmailSender es = new EmailSender();
			es.enviarEmail(email, assunto, mensagem);
			/*//envia o e-mail do contato efetuado no site
	        MimeMessagePreparator preparator = new MimeMessagePreparator() {
	            
	            public void prepare(MimeMessage mimeMessage) throws Exception {

	    			MimeMessageHelper MMhelper = 
	    				new MimeMessageHelper(mimeMessage, true,
	    					"ISO-8859-1");
	    			MMhelper.setTo("senac.gilson@gmail.com");
	    			MMhelper.setFrom(email);
	    			MMhelper.setSubject(assunto);
	    			MMhelper.setText(
	                		"<html><body>" +
	                		"<b>Nome: </b>"+ nome + "<br/>" +
	                		"<b>Mensagem: </b>"+mensagem +
	                		"</body></html>", true);
	            }
	        };
	        try {
	            this.enviarEmail.send(preparator);
	            return "enviado";
	        }
	        catch (MailException ex) {
	            System.err.println(ex.getMessage()); 
	            FacesUtils.mensInfo("Problemas ao enviar o contato");
	        }*/
			
			return "enviado";
			
		}else{
			
			FacesUtils.mensInfo("Campos com * são obrigatórios");
		}
		
		return null;
		
	}
}
