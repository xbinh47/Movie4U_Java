package finalproject.application.controller;

import finalproject.application.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TicketController {
    @Autowired
    private TicketService ticketService;



}
