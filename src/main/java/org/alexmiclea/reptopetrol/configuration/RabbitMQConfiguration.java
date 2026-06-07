package org.alexmiclea.reptopetrol.configuration;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    public Queue priceChangeQueue() {
        return new Queue("priceChangeQueue");
    }

    @Bean
    public Queue purchaseQueue() {
        return new Queue("purchaseQueue");
    }

    @Bean
    public Exchange exchange() {
        return new TopicExchange("reptopetrolExchange");
    }

    @Bean
    public Binding priceChangeBinding(Queue priceChangeQueue, Exchange exchange) {
        return BindingBuilder.bind(priceChangeQueue).to(exchange).with("priceChange").noargs();
    }

    @Bean
    public Binding purchaseBinding(Queue purchaseQueue, Exchange exchange) {
        return BindingBuilder.bind(purchaseQueue).to(exchange).with("purchase").noargs();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder()
                // set so we can receive Instant via message
                .addModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .build();
    }

}
