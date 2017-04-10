package by.cinema.configuration;

import by.cinema.bean.Auditorium;
import by.cinema.bean.AuditoriumCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kiryl_Parfiankou on 11/10/2015.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "by.cinema")
@PropertySource({"classpath:auditoriums.properties","classpath:booking.properties"})
public class ApplicationConfiguration {

    @Autowired
    Environment env;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }


    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.DERBY)
                .addScript("db-schema.sql")
                .addScript("db-test-data.sql")
                .build();
    }

    @Bean
    public List<Auditorium> auditoriumList() {
        List<Auditorium> list = new ArrayList<Auditorium>();
        list.add(new Auditorium(
                AuditoriumCode.RED_ROOM.toString(),
                env.getProperty("red.name"),
                Integer.valueOf(env.getProperty("red.seats.number")),
                env.getProperty("red.seats.vip")
        ));
        list.add(new Auditorium(
                AuditoriumCode.BLACK_ROOM.toString(),
                env.getProperty("black.name"),
                Integer.valueOf(env.getProperty("black.seats.number")),
                env.getProperty("black.seats.vip")
        ));
        list.add(new Auditorium(
                AuditoriumCode.GREEN_ROOM.toString(),
                env.getProperty("green.name"),
                Integer.valueOf(env.getProperty("green.seats.number")),
                env.getProperty("green.seats.vip")
        ));

        list.add(new Auditorium(
                AuditoriumCode.YELLOW_ROOM.toString(),
                env.getProperty("yellow.name"),
                Integer.valueOf(env.getProperty("yellow.seats.number")),
                env.getProperty("yellow.seats.vip")
        ));

        return list;
    }
}
