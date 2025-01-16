package ecommerce.business.integration.awin.handler;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import ecommerce.business.integration.awin.processor.AwinIntegrationProcessor;

public class AwinIntegrationHandler {
    
    private final static Logger LOG = LoggerFactory.getLogger(AwinIntegrationHandler.class);
	private final AwinIntegrationProcessor awinIntegrationProcessor;

	public AwinIntegrationHandler(AwinIntegrationProcessor awinIntegrationProcessor) {
		this.awinIntegrationProcessor = awinIntegrationProcessor;
	}

	@Bean
	public Consumer<?> handleSchedulerEvent() {
		LOG.info("Recebido evento para processamento");
        
		return event -> {
			this.processEvent();
		};
	}

	private void processEvent() {
		this.awinIntegrationProcessor.process();
	}
}
