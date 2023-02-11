package org.example.web;

import org.example.book.Book;
import org.example.book.BookClient;
import org.example.domain.Order;
import org.example.domain.OrderService;
import org.example.domain.OrderStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping
    public Flux<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    public static Order buildAcceptedOrder(Book book,int quantity){
        return Order.of(book.isbn(),book.title() + " - " +
                book.author(),book.price(),quantity, OrderStatus.ACCEPTED);
    }

    @PostMapping
    public Mono<Order> submitOrder(@RequestBody @Valid OrderRequest orderRequest){
        return orderService.submitOrder(orderRequest.isbn(),
                orderRequest.quantity());
    }
}
