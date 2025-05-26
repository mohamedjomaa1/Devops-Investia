package tn.esprit.investia.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stripe.model.Coupon;
import tn.esprit.investia.services.StripeService;
import tn.esprit.investia.utils.Response;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${stripe.key.public}")
    private String API_PUBLIC_KEY;

    private StripeService stripeService;

    public PaymentController(StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @GetMapping("/homepage")
    public ResponseEntity<Response> homepage() {
        return ResponseEntity.ok(new Response(true, "Welcome to the payment API"));
    }

    @GetMapping("/public-key")
    public ResponseEntity<Response> getPublicKey() {
        return ResponseEntity.ok(new Response(true, API_PUBLIC_KEY));
    }

    @PostMapping("/create-subscription")
    public ResponseEntity<Response> createSubscription(@RequestParam String email, @RequestParam String token, 
                                                       @RequestParam String plan, @RequestParam(required = false) String coupon) {
        if (token == null || plan.isEmpty()) {
            return ResponseEntity.badRequest().body(new Response(false, "Stripe payment token is missing. Please try again later."));
        }

        String customerId = stripeService.createCustomer(email, token);
        if (customerId == null) {
            return ResponseEntity.badRequest().body(new Response(false, "An error occurred while trying to create customer"));
        }

        String subscriptionId = stripeService.createSubscription(customerId, plan, coupon != null ? coupon : "");
        if (subscriptionId == null) {
            return ResponseEntity.badRequest().body(new Response(false, "An error occurred while trying to create subscription"));
        }

        return ResponseEntity.ok(new Response(true, "Success! Your subscription ID is " + subscriptionId));
    }

    @PostMapping("/cancel-subscription")
    public ResponseEntity<Response> cancelSubscription(@RequestParam String subscriptionId) {
        boolean subscriptionStatus = stripeService.cancelSubscription(subscriptionId);
        if (!subscriptionStatus) {
            return ResponseEntity.badRequest().body(new Response(false, "Failed to cancel subscription. Please try again later"));
        }
        return ResponseEntity.ok(new Response(true, "Subscription cancelled successfully"));
    }

   @PostMapping("/coupon-validator")
	public ResponseEntity<Response> couponValidator(@RequestParam String code) {
		Coupon coupon = stripeService.retriveCoupon(code);
		if (coupon != null && coupon.getValid()) {
			String details = (coupon.getPercentOff() == null ? "$" + (coupon.getAmountOff() / 100)
					: coupon.getPercentOff() + "%") + " OFF " + coupon.getDuration();
			return ResponseEntity.ok(new Response(true, details));
		}
		return ResponseEntity.badRequest().body(new Response(false, "This coupon code is not available. It may have expired or already been applied to your account."));
}
    @PostMapping("/create-charge")
    public ResponseEntity<Response> createCharge(@RequestParam String email, @RequestParam String token) {
        if (token == null) {
            return ResponseEntity.badRequest().body(new Response(false, "Stripe payment token is missing. Please try again later."));
        }

        String chargeId = stripeService.createCharge(email, token, 999); // 9.99 USD
        if (chargeId == null) {
            return ResponseEntity.badRequest().body(new Response(false, "An error occurred while trying to charge."));
        }

        return ResponseEntity.ok(new Response(true, "Success! Your charge ID is " + chargeId));
    }
}