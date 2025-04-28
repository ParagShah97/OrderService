# Order Service

**Port:** `8082`  
A Spring Boot microservice that handles order placement and communicates with Product and Payment services via Feign Clients and REST templates.

Docker Image: https://hub.docker.com/repository/docker/paragshah07/orderservice

---

## 🧰 Dependencies

Project initialized using Spring Initializer with the following dependencies:

1. Spring Web  
2. Spring Data JPA  
3. MySQL Driver  
4. Lombok  
5. Spring Cloud Bootstrap  
6. Spring Boot DevTools  
7. Eureka Client (Service Registry)  
8. Config Client (Centralized Configuration)  
9. Zipkin (Distributed Tracing)  
10. Resilience4J (Circuit Breaker)

---

## 🗂 Folder Structure

```
controller/
services/
repository/
entity/
model/
exceptions/
config/
external/
```

> **Note:** Rename `application.properties` to `application.yaml`

---

## 🚀 Starter Class

```java
@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
```

---

## 📦 Model Classes

- `OrderRequest` – Receives order details from API
- `OrderResponse` – Returns order status/details
- `PaymentMode` – Enum for payment options (`CASH`, `CREDIT_CARD`, etc.)
- `ErrorResponse` – Standard structure for exceptions

---

## 🧾 Repository

- `OrderRepository` – Extends `JpaRepository` for DB interaction

---

## 📄 Entity

- `Order` – Entity mapped to DB for order storage

---

## 💼 Services

- `OrderService` – Interface
- `OrderServiceImpl` – Implements business logic

---

## ⚠️ Exceptions

- `CustomException` – Extends `RuntimeException`
- `RestResponseEntityExceptionHandler` – Global error handler

---

## 🌐 External Communication

### Clients

- `ProductService` – Calls Product microservice
- `PaymentService` – Calls Payment microservice

### Decoder

- `CustomErrorDecoder` – Handles errors from Feign client API calls

### Feign Config

```java
@Configuration
public class FeignConfig {
    @Bean
    ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
```

---

## 🧩 Controller

### `OrdersController.java`

```java
@PostMapping("/placeOrder")
public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest) {
    long orderId = orderService.placeOrder(orderRequest);
    log.info("Order ID {}", orderId);
    return new ResponseEntity<>(orderId, HttpStatus.OK);
}
```

---

## ⚙️ Service Implementation

```java
@Override
public long placeOrder(OrderRequest orderRequest) {
    log.info("Placing Order request {} ", orderRequest);
    Order order = new Order(
        orderRequest.getProductId(),
        orderRequest.getQuantity(),
        Instant.now(),
        "CREATED",
        orderRequest.getTotalAmount()
    );
    order = orderRepository.save(order);
    log.info("Order Placed successfully with ID {}", order.getId());
    return order.getId();
}
```

---

## 🔁 Error Decoder

**Path:** `src/main/java/com/parag/OrderService/external/decoder/CustomErrorDecoder.java`

```java
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        // Error decoding logic here
    }
}
```

---

## 📡 Feign Client

**Path:** `src/main/java/com/parag/OrderService/external/client/PaymentService.java`

```java
@FeignClient(name = "PaymentService/payment")
public interface PaymentService {
    @PostMapping
    ResponseEntity<Long> makePayment(@RequestBody PaymentRequest paymentRequest);
}
```

---

## 🔗 REST Template Usage

**Path:** `OrderServiceApplication.java`

```java
@Bean
@LoadBalanced
public RestTemplate restTemplate() {
    return new RestTemplate();
}
```

Use in Service:

```java
ProductDetails productDetails = restTemplate.getForObject(
    "http://PRODUCTSERVICE/product/" + order.getProductId(),
    ProductDetails.class
);
```

---

## ⚠️ Error Handling Flow

```
ProductService (Exception) 
    → Feign Client 
        → CustomErrorDecoder 
            → ControllerAdvice 
                → ErrorResponse to User
```

---

## 📌 Notes

- Use `@EnableFeignClients` in the main class to enable Feign.
- Ensure services are registered with Eureka for discovery.
- Zipkin must be running for distributed tracing to work.
