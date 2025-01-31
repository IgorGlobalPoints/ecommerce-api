package ecommerce.business.payments.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mercadopago.resources.payment.Payment;

import ecommerce.business.integration.paymentpartner.entity.PaymentValidation;
import ecommerce.business.integration.paymentpartner.service.PaymentPartnerService;

public class EcommercePaymentService {

    private static final Logger LOG = LoggerFactory.getLogger(EcommercePaymentService.class);
    
    public EcommercePaymentService() {}

    public void processPayment(Payment payment) {
        LOG.info("Processando pagamento {}", payment.getPayer().getEmail());

        // var user = this.userService.getUser(payment.getPayer().getId());


        PaymentValidation.validatePayment(payment);

    
    }
}
