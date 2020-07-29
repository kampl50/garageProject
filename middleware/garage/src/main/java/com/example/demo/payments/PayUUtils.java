package com.example.demo.payments;

import com.example.demo.models.Order;
import com.example.demo.models.Payment;
import com.example.demo.models.Product;
import com.example.demo.utils.WebUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PayUUtils {

    public static String getToken() throws JsonProcessingException {

        RestTemplate client = new RestTemplate();
        ResponseEntity<String> response = client.getForEntity(PayUConsts.TOKEN_URL, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        return root.get("access_token").asText();
    }

    public static ResponseEntity getUrlToPay(Payment payment, HttpServletRequest request) throws JsonProcessingException, JSONException {
        Product product = Payment.toProductConverter(payment);
        List productList = Arrays.asList(product);

        Order order = new Order(PayUConsts.CONTINUE_URL, payment.getNotificationID(), WebUtils.getIpAddress(request), PayUConsts.POS_ID, payment.getDescription(), PayUConsts.CURRENCY, payment.getPrice(), productList);

        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(PayUUtils.getToken());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(order);
        HttpEntity<?> entity = new HttpEntity<>(json, headers);
        String orderUrl = "https://secure.snd.payu.com/api/v2_1/orders/";
        ResponseEntity<String> response1 = client.exchange(orderUrl, HttpMethod.POST, entity, String.class);
        JsonNode root1 = mapper.readTree(response1.getBody());


        JSONObject jsonpObject = new JSONObject();

        jsonpObject.put("redirectUri", root1.get("redirectUri").asText());


        HttpHeaders headers2 = new HttpHeaders();
        headers2.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        return ResponseEntity.ok()
                .headers(headers2)
                .body(jsonpObject.toString());
    }
}
