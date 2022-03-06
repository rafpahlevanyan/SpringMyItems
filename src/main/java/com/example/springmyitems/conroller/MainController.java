package com.example.springmyitems.conroller;

import com.example.springmyitems.entity.User;
import com.example.springmyitems.repository.UserRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @Value("${myitems.upload.path}")
    private String imgPath;

    @GetMapping("/")
    public String main(ModelMap map) {

        Iterable<User> users = userRepository.findAll();
        map.addAttribute("users", users);
        return "main";
    }

    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream = new FileInputStream(imgPath + picName);
        return IOUtils.toByteArray(inputStream);
    }


}
