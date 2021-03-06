package com.jeya.rest.messenger.resources;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.jeya.rest.messenger.model.Message;
import com.jeya.rest.messenger.resources.beans.MessageFilterBean;
import com.jeya.rest.messenger.service.MessageService;

// http://localhost:8080/messenger/webapi/messages/
@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	// to avoid having more param in method header, create separate class and use that
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean)
	{
		if(filterBean.getYear() > 0){
			return messageService.getMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart() == 0 && filterBean.getSize() > 0)
		{
			return messageService.getMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_XML)
	public Message getMessage(@PathParam("messageId") long id)
	{
		return messageService.getMessage(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Message addMessage(Message message)
	{
		return messageService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Message updateMessage(@PathParam("messageId") long id, Message message)
	{
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id)
	{
		messageService.removeMessage(id);
	}
	
	// in this point jersey will know that I need to look up another class
	// since the return type is instance of another class
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource()
	{
		return new CommentResource();
	}
	
/*	@GET
	@Path("/{[name*]}") // wrong: need to check availability of regular expression here
	@Produces(MediaType.APPLICATION_XML)
	public List<Message> getAllMessages()
	{
		return messageService.getAllMessages();
	}*/
}
