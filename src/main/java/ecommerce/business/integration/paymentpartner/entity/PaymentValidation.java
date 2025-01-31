package ecommerce.business.integration.paymentpartner.entity;

import java.math.BigDecimal;

import com.mercadopago.resources.payment.Payment;

import ecommerce.utils.IException;

public class PaymentValidation {
    

    public PaymentValidation() {}

    public static void validatePayment(Payment payment) {

        if (payment == null) {
            throw IException.ofValidation("PAYMENT_INFO_INVALID", "Informações do pagamento inválidas");
        }

        if (payment.getPayer() == null) {
            throw IException.ofValidation("PAYER_INFO_INVALID", "Informações do pagador inválidas");
        }

        if (payment.getPayer().getIdentification() == null) {
            throw IException.ofValidation("IDENTIFICATION_INVALID", "Identificação inválida");
        }

        if (payment.getTransactionAmount().compareTo(BigDecimal.ZERO) < 0 ) {
            throw IException.ofValidation("AMOUNT_INVALID", "Valor inválido");
        }

        if (payment.getInstallments() < 0) {
            throw IException.ofValidation("INSTALLMENTS_INVALID", "Parcelas inválidas");
        }
     
        if (payment.getPayer().getEmail().isEmpty()) {
            throw IException.ofValidation("EMAIL_INVALID", "Email inválido");
        }

        if (payment.getPayer().getFirstName().isEmpty()) {
            throw IException.ofValidation("NAME_INVALID", "Nome inválido");
        }

        if (payment.getPayer().getLastName().isEmpty()) {
            throw IException.ofValidation("LAST_NAME_INVALID", "Sobrenome inválido");
        }
    }
}
