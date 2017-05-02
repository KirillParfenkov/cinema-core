package by.cinema.client;

import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Kiryl_Parfiankou on 5/2/2017.
 */
public class Runner {

    public static void main(String... args) throws IOException {

        System.out.println("Test Client.");

        Scanner scanner = new Scanner(System.in);
        String audBookId = scanner.nextLine();


        Map<String, String> vars = new HashMap<>();
        vars.put("audBookId",audBookId);


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_PDF));

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Resource> resource = restTemplate.exchange(
                "http://localhost:8080/cinema-core-1.0-SNAPSHOT/booking/showAllTickets?audBookId={audBookId}",
                HttpMethod.GET,
                httpEntity,
                Resource.class,
                vars);

        InputStream input = resource.getBody().getInputStream();
        File file = new File("out.pdf");
        FileOutputStream out = new FileOutputStream(file);

        int data;
        while ( (data = input.read()) != -1) {
            out.write(data);
        }
    }
}
