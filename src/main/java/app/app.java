package app;

import app.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class app {
    public static void main(String[] args) {
        RestTemplate template = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        String url = "http://94.198.50.185:7081/api/users";
        String result = "";

        ResponseEntity<String> resp = template.getForEntity(url, String.class);
        HttpHeaders respHeaders = resp.getHeaders();
        String cookie = respHeaders.getFirst(HttpHeaders.SET_COOKIE);
        requestHeaders.add("Cookie", cookie);

        HttpEntity<User> entity = new HttpEntity<>(new User(3L, "James", "Brown", (byte) 50), requestHeaders);
        HttpEntity<String> post = template.postForEntity(url, entity, String.class);
        result += post.getBody();

        HttpEntity<User> updatedEntity = new HttpEntity<>(new User(3L, "Thomas", "Shelby", (byte) 50), requestHeaders);
        HttpEntity<String> put = template.exchange(url, HttpMethod.PUT, updatedEntity, String.class);
        result+= put.getBody();

        HttpEntity<String> delete = template.exchange(url + "/3", HttpMethod.DELETE, new HttpEntity<String>(requestHeaders), String.class);
        result+= delete.getBody();

        System.out.println(result);

    }
}
