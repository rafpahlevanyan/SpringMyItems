package com.example.springmyitems.conroller;


import com.example.springmyitems.entity.Item;
import com.example.springmyitems.entity.User;
import com.example.springmyitems.repository.CategoryRepository;
import com.example.springmyitems.repository.ItemRepository;
import com.example.springmyitems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${myitems.upload.path}")
    private String imgPath;

    @GetMapping("/items")
    public String itemsPage(ModelMap map) {

        List<Item> items = (List<Item>) itemRepository.findAll();
        map.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/items/byUser/{id}")
    public String itemsByUserPage(ModelMap map, @PathVariable("id") int id) {
        User user = userRepository.getById(id);
        List<Item> items = (List<Item>) itemRepository.findAllByUser(user);
        map.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/items/add")
    public String addItemPage(ModelMap map) {
        map.addAttribute("categories", categoryRepository.findAll());
        map.addAttribute("users", userRepository.findAll());
        return "saveItem";
    }

    @PostMapping("/items/add")
    public String addItem(@ModelAttribute Item item,
                          @RequestParam("picture") MultipartFile uploadedFile) throws IOException {
        if (!uploadedFile.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + uploadedFile.getOriginalFilename();
            File newFile = new File(imgPath + fileName);
            uploadedFile.transferTo(newFile);
            item.setPicUrl(fileName);
        }
        itemRepository.save(item);
        return "redirect:/items";
    }

}
