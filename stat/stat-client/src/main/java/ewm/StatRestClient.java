package ewm;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatRestClient {
    final RestTemplate restTemplate;

    public static void main(String[] args) {

    }
}
