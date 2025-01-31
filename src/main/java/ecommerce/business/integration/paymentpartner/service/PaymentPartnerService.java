package ecommerce.business.integration.paymentpartner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mercadopago.resources.payment.Payment;

import ecommerce.business.integration.paymentpartner.entity.PaymentValidation;
import ecommerce.business.integration.paymentpartner.proxy.PaymentIntegrationService;
import ecommerce.utils.IException;

@Service
public class PaymentPartnerService {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentPartnerService.class);
    private final PaymentIntegrationService paymentIntegration;

    public PaymentPartnerService(PaymentIntegrationService paymentIntegration) {
        this.paymentIntegration = paymentIntegration;
    }

    public void createDebitCreditPayment(Payment payment) {
        LOG.info("criando pedido de pagamento credito/debito {}", payment.getPayer().getEmail());

        // var alreadyExists = this.

        // if (alreadyExists(payment.getPayer().getId())) {
        //     LOG.info("", payment);
        // }

        try {
            this.paymentIntegration.createCreditDebitPayment(payment);
        } catch (Exception e) {
            LOG.error("Ocorreu um erro ao tentar realizar o pagamento {}", e.getMessage());
            throw IException.ofError("CREATE_PAYMENT_ERROR", "Ocorreu um erro ao tentar realizar o pagamento");
        }
    }
}