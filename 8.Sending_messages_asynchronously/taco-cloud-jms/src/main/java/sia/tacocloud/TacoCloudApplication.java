package sia.tacocloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import sia.tacocloud.receivers.OrderReceiver;

@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TacoCloudApplication.class, args);
		OrderReceiver orderReceiver = context.getBean(OrderReceiver.class);
		orderReceiver.receiveOrderPullWithPlainDestinationConverter();
	}
}
