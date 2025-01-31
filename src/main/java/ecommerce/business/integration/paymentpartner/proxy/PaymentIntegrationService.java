package ecommerce.business.integration.paymentpartner.proxy;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.IdentificationRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.payment.PaymentCreateRequest;
import com.mercadopago.client.payment.PaymentPayerRequest;
import com.mercadopago.core.MPRequestOptions;
import com.mercadopago.resources.payment.Payment;

import ecommerce.utils.IException;

@Service
public class PaymentIntegrationService {
    private static final Logger LOG = LoggerFactory.getLogger(PaymentIntegrationService.class);

    private final PaymentClient paymentIntegration;

    @Value("${mercado-pago.access-token}")
    private static String access_token;

    public PaymentIntegrationService(PaymentClient paymentClient) {
        this.paymentIntegration = paymentClient;
    }

    public Payment createCreditDebitPayment(Payment payment) {
        LOG.info("criando pagamento de crédito/débito {}", payment.getPayer().getEmail());

        try {
            MercadoPagoConfig.setAccessToken(access_token);

            Map<String, String> customHeaders = new HashMap<>();
            customHeaders.put("x-idempotency-key", payment.getPayer().getId());

            MPRequestOptions requestOptions = MPRequestOptions.builder()
            .customHeaders(customHeaders)
            .build();

            var buildPayment = buildPayment(payment);
            return paymentIntegration.create(buildPayment, requestOptions);
        } catch (Exception e) {
            LOG.error("Ocorreu um erro ao tentar realizar o pagamento {}", e.getMessage());
            throw IException.ofError("CREATE_PAYMENT_ERROR", "Ocorreu um erro ao tentar realizar o pagamento");
        }
    }

    private PaymentCreateRequest buildPayment(Payment payment) {
        LOG.info("construindo pagamento {}", payment.getPayer().getEmail());
        var payer = buildPayer(payment);

        return PaymentCreateRequest.builder()
                .transactionAmount(payment.getTransactionAmount())
                .token(payment.getAuthorizationCode())
                .description(payment.getDescription())
                .installments(payment.getInstallments())
                .paymentMethodId(payment.getPaymentMethodId())
                .payer(payer)
                .build();
    }

    private PaymentPayerRequest buildPayer(Payment payment) {
        LOG.info("construindo dados do pagador {}", payment.getPayer().getEmail());
        var identification = buildIdentification(payment);

        return PaymentPayerRequest.builder()
                .email(payment.getPayer().getEmail())
                .firstName(payment.getPayer().getFirstName())
                .identification(identification)
                .build();
    }

    private IdentificationRequest buildIdentification(Payment payment) {
        LOG.info("construindo dados da identificação {}", payment.getPayer().getEmail());
        return IdentificationRequest.builder()
                .type(payment.getPayer().getIdentification().getType())
                .number(payment.getPayer().getIdentification().getNumber())
                .build();
    }
}
