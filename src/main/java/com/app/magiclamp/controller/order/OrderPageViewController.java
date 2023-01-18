package com.app.magiclamp.controller.order;

import com.app.magiclamp.entity.AddrBook;
import com.app.magiclamp.entity.Book;
import com.app.magiclamp.entity.Mileage;
import com.app.magiclamp.model.AuthUserDTO;
import com.app.magiclamp.model.order.RequestOrderBook;
import com.app.magiclamp.service.order.OrderPageViewService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/order")
public class OrderPageViewController {

    @Autowired
    private OrderPageViewService orderPageViewService;



    @GetMapping
    public String getob(@AuthenticationPrincipal AuthUserDTO userDTO,
                        @RequestParam(value = "isbn" )String isbn,
                        RequestOrderBook orders,
                        Model model){


        log.info(" controller order >>> " + orders);
        model.addAttribute("userInfo", userDTO);

        List<String> isbns = new ArrayList<>();
        isbns.add(isbn);


        List<Book> bookList = orderPageViewService.getbooks(isbns);
        model.addAttribute("book", bookList);

        AddrBook add = orderPageViewService.getUserAddress(userDTO.getUserindex());
        model.addAttribute("userAddress", add);

        Mileage mileage = orderPageViewService.getCurrentMileage(userDTO.getUserindex());
        model.addAttribute("currMileage", mileage);

        return "/view/order/order";

    }


}