package app.dev.pack.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/register")
public class RegistrationController {

    private final RegistrationService service;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return service.register(request);
    }

    @GetMapping(path = "/confirm")
    public ModelAndView confirm(@RequestParam("token") String token,ModelAndView modelAndView){
        service.confirmToken(token);

        modelAndView.addObject("token");
        modelAndView.setViewName("index-template/confirmToken.html");

        return modelAndView;
    }
}
