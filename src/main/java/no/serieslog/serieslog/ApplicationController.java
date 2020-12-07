package no.serieslog.serieslog;

import no.serieslog.serieslog.data.Series;
import no.serieslog.serieslog.data.User;
import no.serieslog.serieslog.data.UserSeriesList;
import no.serieslog.serieslog.repositories.SeriesRepository;
import no.serieslog.serieslog.repositories.UserRepository;
import no.serieslog.serieslog.repositories.UserSeriesListRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.IconUIResource;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class ApplicationController {

    UserRepository userRepository;
    SeriesRepository seriesRepository;
    UserSeriesListRepository userSeriesListRepository;
    PasswordEncoder encoder;
    APIHandler handler;

    public ApplicationController(
            UserRepository userRepository, SeriesRepository seriesRepository,
            UserSeriesListRepository userSeriesListRepository, PasswordEncoder encoder,
            APIHandler handler
    ) {
        this.userRepository = userRepository;
        this.seriesRepository = seriesRepository;
        this.userSeriesListRepository = userSeriesListRepository;
        this.encoder = encoder;
        this.handler = handler;
    }

    @GetMapping("/")
    public String index(Model model, Principal principal) {
        checkedIfLoggedIn(model, principal);
        User user = userRepository.findByName(principal.getName());
        List<UserSeriesList> favorites = new ArrayList<>();
        List<UserSeriesList> ranked = userSeriesListRepository.findByUser(user);
        for (UserSeriesList usl : ranked) {
            if (usl.isFavorite()) {
                favorites.add(usl);
            }
        }
        if (favorites.size() > 0) {
            model.addAttribute("favorites", favorites);
        }
        if (ranked.size() > 0) {
            model.addAttribute("ranked", ranked);
        }
        return "index";
    }

    @PostMapping("/tvshow/rating/delete/{seriesId}")
    public String deleteRating(
            Model model, Principal principal,
            @PathVariable Integer seriesId) {
        User user = userRepository.findByName(principal.getName());
        Series series = seriesRepository.findById(seriesId).get();
        UserSeriesList usl = userSeriesListRepository.findByUserAndSeries(user, series);
        userSeriesListRepository.delete(usl);
        if(userSeriesListRepository.findBySeries(series).size() < 1){
            System.out.println("test");
            seriesRepository.delete(series);
        } else {
            calculateRating(series);
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model, Principal principal) {
        if (checkedIfLoggedIn(model, principal)) {
            return "redirect:/";
        }
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
    public String searchTVShows(Model model, Principal principal) {
        checkedIfLoggedIn(model, principal);
        return "searchtvshows";
    }

    @PostMapping("/tvshows/search")
    public String searchResultsTvShows(@RequestParam String query, Model model, Principal principal) {
        checkedIfLoggedIn(model, principal);
        List<Series> listOfShows = handler.findMazeSeriesByQuery(query);
        listOfShows.sort(Comparator.comparing(Series::getSeriesName));
        model.addAttribute("listOfShows", listOfShows);
        return "searchtvshows";
    }

    @GetMapping("/tvshow/{mazeId}")
    public String rateShow(Model model, Principal principal, @PathVariable Integer mazeId) {
        checkedIfLoggedIn(model, principal);
        User user = userRepository.findByName(principal.getName());
        Series series = seriesRepository.findByMazeId(mazeId);
        UserSeriesList usl = null;
        if (series == null) {
            series = handler.findMazeSeriesById(mazeId);
        } else {
            usl = userSeriesListRepository.findByUserAndSeries(user, series);
        }
        model.addAttribute("series", series);
        model.addAttribute("usl", usl);
        return "rateShow";
    }

    @PostMapping("/tvshow/{mazeId}")
    public String postRateShow(
            Model model, Principal principal,
            @RequestParam Integer userScore, @RequestParam boolean favorite,
            @PathVariable Integer mazeId
    ) {
        checkedIfLoggedIn(model, principal);
        User user = userRepository.findByName(principal.getName());
        Series series = seriesRepository.findByMazeId(mazeId);
        if (series == null) {
            series = handler.findMazeSeriesById(mazeId);
            seriesRepository.save(series);
        }
        UserSeriesList usl = userSeriesListRepository.findByUserAndSeries(user, series);
        if (usl == null) {
            usl = new UserSeriesList(user.getId(), series.getId());
        }
        usl.setUserScore(userScore);
        usl.setFavorite(favorite);
        userSeriesListRepository.save(usl);
        calculateRating(series);
        return "redirect:/";
    }

    private void calculateRating(Series series) {
        List<UserSeriesList> listOfRatings = userSeriesListRepository.findBySeries(series);
        Double average = 0.0;
        int counter = 0;
        for (UserSeriesList usl : listOfRatings) {
            if (usl.getUserScore() != null && usl.getUserScore() != -1) {
                average += usl.getUserScore();
                counter++;
            }
        }
        if (counter != 0) {
            series.setAverageScore(average / counter);
        } else {
            series.setAverageScore(null);
        }
        seriesRepository.save(series);
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
