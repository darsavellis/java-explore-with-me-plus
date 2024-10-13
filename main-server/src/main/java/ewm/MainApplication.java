package ewm;

import ewm.client.StatRestClient;
import ewm.dto.EndpointHitDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);

        Thread.sleep(5000);

        StatRestClient client = context.getBean(StatRestClient.class);
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        EndpointHitDto hitDto1 = new EndpointHitDto("app1", "/client/home", "192.1.168.1",
            now.format(dateTimeFormatter));
        EndpointHitDto hitDto2 = new EndpointHitDto("app2", "/client/home", "192.1.168.2",
            now.format(dateTimeFormatter));
        EndpointHitDto hitDto3 = new EndpointHitDto("app3", "/admin/home", "192.1.168.1",
            now.format(dateTimeFormatter));
        EndpointHitDto hitDto4 = new EndpointHitDto("app4", "/admin/home", "192.1.168.2",
            now.format(dateTimeFormatter));
        EndpointHitDto hitDto5 = new EndpointHitDto("app5", "/guest/home", "192.1.168.1",
            now.format(dateTimeFormatter));
        EndpointHitDto hitDto6 = new EndpointHitDto("app6", "/guest/home", "192.1.168.2",
            now.format(dateTimeFormatter));

        client.addHit(hitDto1).subscribe();
        client.addHit(hitDto2).subscribe();
        client.addHit(hitDto3).subscribe();
        client.addHit(hitDto4).subscribe();
        client.addHit(hitDto5).subscribe();
        client.addHit(hitDto6).subscribe();

        Thread.sleep(1000);

        client.stats(now.minusDays(5), now.plusDays(5), List.of("/guest/home", "/admin/home", "/admin/home"), false)
            .subscribe(viewStatsDtos ->
                viewStatsDtos.forEach(System.out::println));
    }
}
