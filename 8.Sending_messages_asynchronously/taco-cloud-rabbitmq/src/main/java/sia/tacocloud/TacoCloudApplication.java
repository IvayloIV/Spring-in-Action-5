package sia.tacocloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import sia.tacocloud.receivers.RabbitOrderReceiver;

@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TacoCloudApplication.class, args);
		RabbitOrderReceiver rabbitOrderReceiver = context.getBean(RabbitOrderReceiver.class);
		rabbitOrderReceiver.receiveOrderWithMessageHeader();
	}
}
