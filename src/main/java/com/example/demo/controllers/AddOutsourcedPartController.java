package com.example.demo.controllers;

import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;
import com.example.demo.service.OutsourcedPartService;
import com.example.demo.service.OutsourcedPartServiceImpl;
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
public class AddOutsourcedPartController {
    @Autowired
    private ApplicationContext context;

    @GetMapping("/showFormAddOutPart")
    public String showFormAddOutsourcedPart(Model theModel){
        Part part=new OutsourcedPart();
        theModel.addAttribute("outsourcedpart",part);
        return "OutsourcedPartForm";
    }

    @PostMapping("/showFormAddOutPart")
    public String submitForm(@Valid @ModelAttribute("outsourcedpart") OutsourcedPart part, BindingResult bindingResult, Model theModel){
        theModel.addAttribute("outsourcedpart",part);

        // Validate and format date
        if (part.getDate() == null || part.getDate().isEmpty()) {
            part.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        }

        // Parse and validate date
        LocalDate parsedDate;
        try {
            parsedDate = LocalDate.parse(part.getDate(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
            if (parsedDate.isAfter(LocalDate.now())) {
                bindingResult.rejectValue("date", "date.invalid", "Date cannot be in the future");
                return "OutsourcedPartForm";
            }
        } catch (DateTimeParseException e) {
            bindingResult.rejectValue("date", "date.invalid", "Invalid date format. Please use MM/dd/yyyy");
            return "OutsourcedPartForm";
        }

        if(bindingResult.hasErrors()){
            return "OutsourcedPartForm";
        }
        else{
            if (!part.isInventoryValid()) {
                if(part.getInv() < part.getMinInventory())
                {
                    bindingResult.rejectValue("inv", "inventory.invalid", "Inventory is too low, it must be more than " + part.getMinInventory());
                } else if(part.getInv() > part.getMaxInventory())
                {
                    bindingResult.rejectValue("inv", "inventory.invalid", "Inventory is too high, it must be less than " + part.getMaxInventory());
                }
                return "OutsourcedPartForm";
            }
            OutsourcedPartService repo=context.getBean(OutsourcedPartServiceImpl.class);
            OutsourcedPart op=repo.findById((int)part.getId());
            if(op!=null)part.setProducts(op.getProducts());
            repo.save(part);
            return "confirmationaddpart";
        }
    }




}
