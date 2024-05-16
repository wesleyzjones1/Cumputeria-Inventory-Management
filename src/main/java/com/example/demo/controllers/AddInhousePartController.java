package com.example.demo.controllers;

import com.example.demo.domain.InhousePart;
import com.example.demo.service.InhousePartService;
import com.example.demo.service.InhousePartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 *
 *
 *
 */
@Controller
public class AddInhousePartController{
    @Autowired
    private ApplicationContext context;

    @GetMapping("/showFormAddInPart")
    public String showFormAddInhousePart(Model theModel){
        InhousePart inhousepart=new InhousePart();

        theModel.addAttribute("inhousepart", inhousepart);
        return "InhousePartForm";
    }

    @PostMapping("/showFormAddInPart")
    public String submitForm(@Valid @ModelAttribute("inhousepart") InhousePart part, BindingResult theBindingResult, Model theModel){
        theModel.addAttribute("inhousepart",part);

        // Validate and format date
        if (part.getDate() == null || part.getDate().isEmpty()) {
            part.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        }

        // Parse and validate date
        LocalDate parsedDate;

        if (part.getDate().length() != 10) {
                throw new DateTimeParseException("Invalid date length", part.getDate(), 0);
            }
            parsedDate = LocalDate.parse(part.getDate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            if (parsedDate.isAfter(LocalDate.now())) {
                theBindingResult.rejectValue("date", "date.invalid", "Date cannot be in the future");
                return "InhousePartForm";
            }
            if (part.getDate().length() != 10) {
            theBindingResult.rejectValue("date", "date.invalid", "Invalid date format. Please use MM/dd/yyyy");
            return "InhousePartForm";
        }

        if(theBindingResult.hasErrors()){
            return "InhousePartForm";
        }
        else{
            if (!part.isInventoryValid()) {
                if(part.getInv() < part.getMinInventory())
                {
                    theBindingResult.rejectValue("inv", "inventory.invalid", "Inventory is too low, it must be more than " + part.getMinInventory());
                } else if(part.getInv() > part.getMaxInventory())
                {
                    theBindingResult.rejectValue("inv", "inventory.invalid", "Inventory is too high, it must be less than " + part.getMaxInventory());
                }
                return "InhousePartForm";
            }
            InhousePartService repo=context.getBean(InhousePartServiceImpl.class);
            InhousePart ip=repo.findById((int)part.getId());
            if(ip!=null)part.setProducts(ip.getProducts());
            repo.save(part);

            return "confirmationaddpart";
        }
    }


}
