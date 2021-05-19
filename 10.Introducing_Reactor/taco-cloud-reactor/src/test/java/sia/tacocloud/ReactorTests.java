package sia.tacocloud;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;
import sia.tacocloud.domain.Scientist;

@SpringBootTest
public class ReactorTests {
    
    @Test
    public void testJustMethod() {
        Flux<String> fruitFlux = Flux.just("banana", "orange", "cherry");
        fruitFlux.subscribe(System.out::println);

        StepVerifier.create(fruitFlux)
            .expectNext("banana")
            .expectNext("orange")
            .expectNext("cherry")
            .verifyComplete();
    }

    @Test
    public void testFromArrayMethod() {
        String[] fruitArray = {"banana", "orange", "cherry"};
        Flux<String> fruitFlux = Flux.fromArray(fruitArray);

        StepVerifier.create(fruitFlux)
            .expectNext("banana")
            .expectNext("orange")
            .expectNext("cherry")
            .verifyComplete();
    }

    @Test
    public void testFromIterableMethod() {
        List<String> fruitList = Arrays.asList("banana", "orange", "cherry");
        Flux<String> fruitFlux = Flux.fromIterable(fruitList);

        StepVerifier.create(fruitFlux)
            .expectNext("banana")
            .expectNext("orange")
            .expectNext("cherry")
            .verifyComplete();
    }

    @Test
    public void testFromStreamMethod() {
        Stream<String> fruitStream = Stream.of("banana", "orange", "cherry");
        Flux<String> fruitFlux = Flux.fromStream(fruitStream);

        StepVerifier.create(fruitFlux)
            .expectNext("banana")
            .expectNext("orange")
            .expectNext("cherry")
            .verifyComplete();
    }

    @Test
    public void testRangeMethod() {
        Flux<Integer> numberFlux = Flux.range(1, 5);

        StepVerifier.create(numberFlux)
            .expectNext(1, 2, 3, 4, 5)
            .verifyComplete();
    }

    @Test
    public void testIntervalMethod() {
        Flux<Long> numberFlux = Flux
            .interval(Duration.ofSeconds(1))
            .take(5);

        StepVerifier.create(numberFlux)
            .expectNext(0L, 1L, 2L, 3L, 4L)
            .verifyComplete();
    }

    @Test
    public void testMergeWithMethod() {
        Flux<String> animalFlux = Flux
            .just("monkey", "cat", "tiger")
            .delayElements(Duration.ofSeconds(1));

        Flux<String> foodFlux = Flux
            .just("banana", "mouse", "human")
            .delaySequence(Duration.ofMillis(500))
            .delayElements(Duration.ofSeconds(1));

        Flux<String> animalFoodFlux = animalFlux.mergeWith(foodFlux);

        StepVerifier.create(animalFoodFlux)
            .expectNext("monkey", "banana")
            .expectNext("cat", "mouse")
            .expectNext("tiger", "human")
            .verifyComplete();
    }

    @Test
    public void testZipMethod() {
        Flux<String> animalFlux = Flux.just("monkey", "cat", "tiger");
        Flux<String> foodFlux = Flux.just("banana", "mouse", "human");

        Flux<Tuple2<String, String>> animalFoodFlux = Flux.zip(animalFlux, foodFlux);

        StepVerifier.create(animalFoodFlux)
            .expectNextMatches(af -> af.getT1().equals("monkey") 
                && af.getT2().equals("banana"))
            .expectNextMatches(af -> af.getT1().equals("cat") 
                && af.getT2().equals("mouse"))
            .expectNextMatches(af -> af.getT1().equals("tiger") 
                && af.getT2().equals("human"))
            .verifyComplete();
    }

    @Test
    public void testZipMethodWithBiFunction() {
        Flux<String> animalFlux = Flux.just("monkey", "cat", "tiger");
        Flux<String> foodFlux = Flux.just("banana", "mouse", "human");

        Flux<String> animalFoodFlux = Flux
            .zip(animalFlux, foodFlux, (a, f) -> String.format("%s eats %s", a, f));

        StepVerifier.create(animalFoodFlux)
            .expectNext("monkey eats banana")
            .expectNext("cat eats mouse")
            .expectNext("tiger eats human")
            .verifyComplete();
    }

    @Test
    @SuppressWarnings("deprecation")
    public void testFirstMethod() {
        Flux<String> animalFlux = Flux
            .just("monkey", "cat", "tiger")
            .delaySubscription(Duration.ofMillis(100));

        Flux<String> foodFlux = Flux.just("banana", "mouse", "human");
        Flux<String> animalFoodFlux = Flux.first(animalFlux, foodFlux);

        StepVerifier.create(animalFoodFlux)
            .expectNext("banana", "mouse", "human")
            .verifyComplete();
    }

    @Test
    public void testSkipMethod() {
        Flux<String> animalFlux = Flux
            .just("monkey", "cat", "tiger", "elephant")
            .skip(2);

        StepVerifier.create(animalFlux)
            .expectNext("tiger", "elephant")
            .verifyComplete();
    }

    @Test
    public void testSkipMethodWithDuration() {
        Flux<String> animalFlux = Flux
            .just("monkey", "cat", "tiger", "elephant")
            .delayElements(Duration.ofSeconds(1))
            .skip(Duration.ofSeconds(2));

        StepVerifier.create(animalFlux)
            .expectNext("cat", "tiger", "elephant")
            .verifyComplete();
    }

    @Test
    public void testTakeMethod() {
        Flux<String> animalFlux = Flux
            .just("monkey", "cat", "tiger", "elephant")
            .take(2);

        StepVerifier.create(animalFlux)
            .expectNext("monkey", "cat")
            .verifyComplete();
    }

    @Test
    public void testTakeMethodWithDuration() {
        Flux<String> animalFlux = Flux
            .just("monkey", "cat", "tiger", "elephant")
            .delayElements(Duration.ofSeconds(1))
            .take(Duration.ofSeconds(3));

        StepVerifier.create(animalFlux)
            .expectNext("monkey", "cat")
            .verifyComplete();
    }

    @Test
    public void testFilterMethod() {
        Flux<String> itemFlux = Flux
            .just("John Johnson", "cat", "Marco Polo", "elephant")
            .filter(e -> !e.contains(" "));

        StepVerifier.create(itemFlux)
            .expectNext("cat", "elephant")
            .verifyComplete();
    }

    @Test
    public void testDistinctMethod() {
        Flux<String> animalFlux = Flux
            .just("monkey", "cat", "tiger", "monkey", "elephant", "tiger")
            .distinct();

        StepVerifier.create(animalFlux)
            .expectNext("monkey", "cat", "tiger", "elephant")
            .verifyComplete();
    }

    @Test
    public void testMapMethod() {
        Flux<Scientist> scientistFlux = Flux
            .just("Albert Einstein", "Marco Polo", "Galileo Galilei")
            .map(s -> {
                String[] names = s.split("\\s+");
                return new Scientist(names[0], names[1]);
            });

        StepVerifier.create(scientistFlux)
            .expectNext(new Scientist("Albert", "Einstein"))
            .expectNext(new Scientist("Marco", "Polo"))
            .expectNext(new Scientist("Galileo", "Galilei"))
            .verifyComplete();
    }

    @Test
    public void testFlatMapMethod() {
        Flux<Scientist> scientistFlux = Flux
            .just("Albert Einstein", "Marco Polo", "Galileo Galilei")
            .flatMap(s -> Mono
                .just(s)
                .map(sc -> {
                    String[] names = sc.split("\\s+");
                    return new Scientist(names[0], names[1]);
                })
            );

        StepVerifier.create(scientistFlux)
            .expectNext(new Scientist("Albert", "Einstein"))
            .expectNext(new Scientist("Marco", "Polo"))
            .expectNext(new Scientist("Galileo", "Galilei"))
            .verifyComplete();
    }

    @Test
    public void testFlatMapMethodInParallel() {
        Flux<Scientist> scientistFlux = Flux
            .just("Albert Einstein", "Marco Polo", "Galileo Galilei")
            .flatMap(s -> Mono
                .just(s)
                .map(sc -> {
                    String[] names = sc.split("\\s+");
                    return new Scientist(names[0], names[1]);
                })
                .subscribeOn(Schedulers.parallel())
            );

        List<Scientist> scientists = Arrays.asList(
            new Scientist("Albert", "Einstein"),
            new Scientist("Marco", "Polo"),
            new Scientist("Galileo", "Galilei"));

        StepVerifier.create(scientistFlux)
            .expectNextMatches(s -> scientists.contains(s))
            .expectNextMatches(s -> scientists.contains(s))
            .expectNextMatches(s -> scientists.contains(s))
            .verifyComplete();
    }

    @Test
    public void testImmediateFlatMapMethod() {
        Flux<Scientist> scientistFlux = Flux
            .just("Albert Einstein", "Marco Polo", "Galileo Galilei")
            .flatMap(s -> Mono
                .just(s)
                .map(sc -> {
                    String[] names = sc.split("\\s+");
                    return new Scientist(names[0], names[1]);
                })
                .subscribeOn(Schedulers.immediate())
            );

        StepVerifier.create(scientistFlux)
            .expectNext(new Scientist("Albert", "Einstein"))
            .expectNext(new Scientist("Marco", "Polo"))
            .expectNext(new Scientist("Galileo", "Galilei"))
            .verifyComplete();
    }

    @Test
    public void testFlatMapMethodWithSingleThread() {
        Flux<Scientist> scientistFlux = Flux
            .just("Albert Einstein", "Marco Polo", "Galileo Galilei")
            .flatMap(s -> Mono
                .just(s)
                .map(sc -> {
                    String[] names = sc.split("\\s+");
                    return new Scientist(names[0], names[1]);
                })
                .subscribeOn(Schedulers.single())
            );

        StepVerifier.create(scientistFlux)
            .expectNext(new Scientist("Albert", "Einstein"))
            .expectNext(new Scientist("Marco", "Polo"))
            .expectNext(new Scientist("Galileo", "Galilei"))
            .verifyComplete();
    }

    @Test
    public void testBufferMethod() {
        Flux<List<String>> animalFlux = Flux
            .just("monkey", "cat", "tiger", "elephant")
            .buffer(3);

        StepVerifier.create(animalFlux)
            .expectNext(Arrays.asList("monkey", "cat", "tiger"))
            .expectNext(Arrays.asList("elephant"))
            .verifyComplete();
    }

    @Test
    public void testParallelBufferMethod() {
        Flux.just("monkey", "cat", "tiger", "elephant", "dog", "panda", "bear")
            .buffer(2)
            .flatMap(a -> Flux
                .fromIterable(a)
                .map(e -> e.toUpperCase())
                .subscribeOn(Schedulers.parallel())
                .log()
            ).subscribe();
    }

    @Test
    public void testEmptyBufferMethod() {
        Flux<List<String>> animalFlux = Flux
            .just("monkey", "cat", "tiger", "elephant")
            .buffer();
        
        StepVerifier.create(animalFlux)
            .expectNext(Arrays.asList("monkey", "cat", "tiger", "elephant"))
            .verifyComplete();
    }

    @Test
    public void testCollectListMethod() {
        Mono<List<String>> animalMono = Flux
            .just("monkey", "cat", "tiger", "elephant")
            .collectList();
        
        StepVerifier.create(animalMono)
            .expectNext(Arrays.asList("monkey", "cat", "tiger", "elephant"))
            .verifyComplete();
    }

    @Test
    public void testCollectMapMethod() {
        Mono<Map<Character, String>> animalMono = Flux
            .just("monkey", "cat", "tiger", "elephant", "tarantula", "coala")
            .collectMap(a -> a.charAt(0));
        
        StepVerifier.create(animalMono)
            .expectNextMatches(m -> m.size() == 4 &&
                m.get('m').equals("monkey") &&
                m.get('c').equals("coala") &&
                m.get('t').equals("tarantula") &&
                m.get('e').equals("elephant"))
            .verifyComplete();
    }

    @Test
    public void testAllMethodWithTrue() {
        Mono<Boolean> animalMono = Flux
            .just("monkey", "tiger", "elephant")
            .all(a -> a.contains("e"));
        
        StepVerifier.create(animalMono)
            .expectNext(true)
            .verifyComplete();
    }

    @Test
    public void testAllMethodWithFalse() {
        Mono<Boolean> animalMono = Flux
            .just("monkey", "tiger", "elephant")
            .all(a -> a.contains("a"));
        
        StepVerifier.create(animalMono)
            .expectNext(false)
            .verifyComplete();
    }

    @Test
    public void testAnyMethodWithTrue() {
        Mono<Boolean> animalMono = Flux
            .just("monkey", "tiger", "elephant")
            .any(a -> a.contains("i"));
        
        StepVerifier.create(animalMono)
            .expectNext(true)
            .verifyComplete();
    }

    @Test
    public void testAnyMethodWithFalse() {
        Mono<Boolean> animalMono = Flux
            .just("monkey", "tiger", "elephant")
            .any(a -> a.contains("z"));
        
        StepVerifier.create(animalMono)
            .expectNext(false)
            .verifyComplete();
    }
}
