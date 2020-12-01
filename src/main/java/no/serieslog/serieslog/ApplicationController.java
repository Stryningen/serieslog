package no.serieslog.serieslog;

import no.serieslog.serieslog.data.User;
import no.serieslog.serieslog.repositories.SeriesRepository;
import no.serieslog.serieslog.repositories.UserRepository;
import no.serieslog.serieslog.repositories.UserSeriesListRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ApplicationController {

    UserRepository userRepository;
    SeriesRepository seriesRepository;
    UserSeriesListRepository userSeriesListRepository;
    PasswordEncoder encoder;

    public ApplicationController(
            UserRepository userRepository, SeriesRepository seriesRepository,
            UserSeriesListRepository userSeriesListRepository, PasswordEncoder encoder
    ) {
        this.userRepository = userRepository;
        this.seriesRepository = seriesRepository;
        this.userSeriesListRepository = userSeriesListRepository;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal){
        checkedIfLoggedIn(model ,principal);
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String registerUser(Model model, Principal principal) {
        if (checkedIfLoggedIn(model, principal)) {
            return "redirect:/";
        }
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute @Valid User user, BindingResult br) {
        if (!br.hasErrors() && userRepository.existsByName(user.getName())) {
            ObjectError error = new ObjectError("user_exists", "user already exists");
            br.addError(error);
        }
        if (!br.hasErrors() && userRepository.existsByEmail(user.getEmail())) {
            ObjectError error = new ObjectError("user_email_exists", "this email is already registered");
            br.addError(error);
        }
        if (br.hasErrors()) {
            return "register";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        return "redirect:/login";
    }

    @GetMapping("/tvshows/search")
    public String searchTVShows(){
        return "searchtvshows";
    }

    private boolean checkedIfLoggedIn(Model model, Principal principal) {
        model.addAttribute("loggedIn", true);
        if (principal == null) {
            model.addAttribute("loggedIn", false);
            return false;
        }
        return true;
    }


}
