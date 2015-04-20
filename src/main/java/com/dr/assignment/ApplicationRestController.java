
package com.dr.assignment;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ApplicationRestController
{
    @Autowired
    private MessageRepository repo;

    private static Comparator<Message> ALPHABETICAL_ORDER = new Comparator<Message>()
    {
        @Override
        public int compare (Message str1, Message str2)
        {
            int res = String.CASE_INSENSITIVE_ORDER.compare(str1.getText(),
                                                            str2.getText());
            if (res == 0) {
                res = str1.getText().compareTo(str2.getText());
            }
            return res;
        }
    };

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getAll ()
    {
        List<Message> messages = repo.findAll();
        Collections.sort(messages, ALPHABETICAL_ORDER);

        ModelAndView modelAndView = new ModelAndView("content");
        modelAndView.addObject("message-entity", new Message());
        modelAndView.addObject("messages", messages);
        return modelAndView;
    }

    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
    public Message get (@PathVariable String id, HttpServletResponse response)
        throws IOException
    {
        Message message = repo.findOne(id);

        if (null != message) {
            return message;
        }
        else {
            response.sendError(400, "Invalid message ID!");
            return null;
        }
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public Message create (@Valid @ModelAttribute @RequestBody Message message,
                           HttpServletResponse response)
        throws IOException
    {
        if (null == message || null == message.getText()
                || message.getText().isEmpty()) {
            response.sendError(400, "Invalid POST parameters!");
            return null;
        }

        response.setStatus(302);
        response.sendRedirect("/");

        return repo.save(message);
    }

    @RequestMapping(value = "/message/{id}", method = RequestMethod.DELETE)
    public void delete (@PathVariable String id)
    {
        repo.delete(id);
    }

    @RequestMapping(value = "/message/{id}", method = RequestMethod.PUT)
    public Message update (@PathVariable String id, @RequestBody Message message)
    {
        Message update = repo.findOne(id);
        update.setText(message.getText());

        return repo.save(update);
    }
}
