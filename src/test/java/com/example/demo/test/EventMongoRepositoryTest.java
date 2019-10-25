package com.example.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Slf4j
public class EventMongoRepositoryTest {

	@Autowired
	private EventMongoRepository eventMongoRepository;

	@Autowired
	private ReactiveMongoTemplate reactiveMongoTemplate;

	@Test
	public void a_단건_삽입_테스트(){
		Welcome.Event event = new Welcome.Event(System.currentTimeMillis(), "0101234566778", "길동홍");


//		Mono<Welcome.Event> result = eventMongoRepository.save(event);
		Mono<Welcome.Event> result = reactiveMongoTemplate.save(event);

		log.info("{}", result);
	}

	@Test
	public void b_조회(){

	}
}
