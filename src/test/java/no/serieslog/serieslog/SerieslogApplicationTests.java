package no.serieslog.serieslog;

import no.serieslog.serieslog.data.Series;
import no.serieslog.serieslog.data.User;
import no.serieslog.serieslog.data.UserSeriesList;
import no.serieslog.serieslog.repositories.SeriesRepository;
import no.serieslog.serieslog.repositories.UserRepository;
import no.serieslog.serieslog.repositories.UserSeriesListRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SerieslogApplicationTests {

    List<User> listOfUsers = new ArrayList<>();
    List<Series> listOfSeries = new ArrayList<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private UserSeriesListRepository userSeriesListRepository;

    @Autowired
    APIHandler handler;

    @Test
    void contextLoads() {
    }

    @BeforeAll
    public void setUpTests() {

        listOfUsers.addAll(
                Arrays.asList(
                        new User("testuser12341", "emailtestting12341", "1234"),
                        new User("testuser12342", "emailtestting12342", "1234"),
                        new User("testuser12343", "emailtestting12343", "1234"),
                        new User("testuser12344", "emailtestting12344", "1234")
                )
        );

        for (User user : listOfUsers) {
            if (userRepository.findByName(user.getName()) == null) {
                userRepository.save(user);
            }
        }

        listOfSeries.addAll(
                Arrays.asList(
                        new Series("test1101"),
                        new Series("test1102"),
                        new Series("test1103"),
                        new Series("test1104"),
                        new Series("test1105"),
                        new Series("test1106")
                )
        );

        for (Series series : listOfSeries) {
            if (seriesRepository.findBySeriesName(series.getSeriesName()) == null) {
                seriesRepository.save(series);
            }
        }

        UserSeriesList usl;
        for (int i = 0; i<5; i++){
            userSeriesListRepository.save(
                    new UserSeriesList(listOfUsers.get(0).getId(), listOfSeries.get(i).getId())
            );
        }

        for (int i = 0; i<3; i++){
            userSeriesListRepository.save(
                    new UserSeriesList(listOfUsers.get(1).getId(), listOfSeries.get(i).getId())
            );
        }

        for (int i = 0; i<2; i++){
            userSeriesListRepository.save(
                    new UserSeriesList(listOfUsers.get(2).getId(), listOfSeries.get(i).getId())
            );
        }

    }

    @AfterAll
    public void tearDownAll() {

        for (User user : listOfUsers) {
            user = userRepository.findByName(user.getName());
            if (user != null) {
                for (UserSeriesList usl : userSeriesListRepository.findByUser(user)) {
                    userSeriesListRepository.delete(usl);
                }
                userRepository.delete(user);
            }
        }

        for (Series series : listOfSeries) {
            series = seriesRepository.findBySeriesName(series.getSeriesName());
            if (series != null) {
                seriesRepository.delete(series);
            }
        }

    }

    @Test
    public void testCreateUser() {
        User user = new User();
        listOfUsers.add(user);
        user.setName("testertesttesting99");
        user.setEmail("tsteieiei69");
        user.setPassword("1234");

        userRepository.save(user);
        User user2 = userRepository.findById(user.getId()).get();
        listOfUsers.add(user2);

        Assertions.assertEquals(user.getName(), user2.getName());
        Assertions.assertEquals(user.getEmail(), user2.getEmail());
        Assertions.assertEquals(user.getPassword(), user2.getPassword());
    }

    @Test
    public void testCreateSeries() {
        Series series = new Series();
        series.setSeriesName("test1");
        listOfSeries.add(series);
        seriesRepository.save(series);

        Series series2 = seriesRepository.findById(series.getId()).get();
        Assertions.assertEquals(series.getSeriesName(), series2.getSeriesName());
    }

    @Test
    public void testCreateUserSeriesList() {
        UserSeriesList usl = new UserSeriesList(listOfUsers.get(0).getId(), listOfSeries.get(0).getId());
        userSeriesListRepository.save(usl);
    }

    @Test
    public void testFindUserSeriesListByUser(){
        User user = userRepository.findByName("testuser12341");
        List<UserSeriesList> usl = userSeriesListRepository.findByUser(user);
        Assertions.assertEquals(5, usl.size());

        user = userRepository.findByName("testuser12342");
        usl = userSeriesListRepository.findByUser(user);
        Assertions.assertEquals(3, usl.size());
    }

    @Test
    public void testFindUserSeriesListBySeries(){
        Series series = seriesRepository.findBySeriesName("test1101");
        List<UserSeriesList> usl = userSeriesListRepository.findBySeries(series);
        Assertions.assertEquals(3, usl.size());

        series = seriesRepository.findBySeriesName("test1103");
        usl = userSeriesListRepository.findBySeries(series);
        Assertions.assertEquals(2, usl.size());
    }

    @Test
    public void testFindMazeSeriesById(){
        Series series = handler.findMazeSeriesById(1);
        Assertions.assertNotNull(series.getSeriesName());
        Assertions.assertNotNull(series.getMazeId());
        Assertions.assertNotNull(series.getSeriesDescription());
        Assertions.assertNotNull(series.getSeriesImageUrl());
        Assertions.assertNotNull(series.getSeriesLinkUrl());
    }

    @Test
    public void testFindMazeSeriesByQuery(){
        List<Series> list = handler.findMazeSeriesByQuery("star");
        Assertions.assertTrue(list.size() >= 10);
    }
}
