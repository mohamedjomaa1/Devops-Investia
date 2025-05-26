package tn.esprit.investia.services;

import com.stripe.model.*;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StripeServiceTest {

    private StripeService stripeService;

    @BeforeEach
    void setUp() {
        stripeService = new StripeService();
        // You can inject secret key via reflection if needed
        // FieldUtils.writeField(stripeService, "API_SECET_KEY", "sk_test_123", true);
    }

    @Test
    void testCreateCustomer() throws Exception {
        try (MockedStatic<Customer> mockedStatic = mockStatic(Customer.class)) {
            Customer customer = mock(Customer.class);
            when(customer.getId()).thenReturn("cus_test_123");
            mockedStatic.when(() -> Customer.create((Map<String, Object>) any())).thenReturn(customer);

            String id = stripeService.createCustomer("test@example.com", "tok_test");

            assertEquals("cus_test_123", id);
        }
    }

    @Test
    void testCreateSubscription() throws Exception {
        try (MockedStatic<Subscription> mockedStatic = mockStatic(Subscription.class)) {
            Subscription subscription = mock(Subscription.class);
            when(subscription.getId()).thenReturn("sub_test_123");
            mockedStatic.when(() -> Subscription.create((Map<String, Object>) any())).thenReturn(subscription);

            String id = stripeService.createSubscription("cus_test", "plan_test", "");

            assertEquals("sub_test_123", id);
        }
    }

    @Test
    void testCancelSubscription() throws Exception {
        try (MockedStatic<Subscription> mockedStatic = mockStatic(Subscription.class)) {
            Subscription subscription = mock(Subscription.class);

            // Correctly mock the return of cancel()
            when(subscription.cancel()).thenReturn(subscription);

            mockedStatic.when(() -> Subscription.retrieve("sub_test_123")).thenReturn(subscription);

            boolean result = stripeService.cancelSubscription("sub_test_123");

            assertTrue(result);
        }
    }


    @Test
    void testRetrieveCoupon() throws Exception {
        try (MockedStatic<Coupon> mockedStatic = mockStatic(Coupon.class)) {
            Coupon coupon = mock(Coupon.class);
            mockedStatic.when(() -> Coupon.retrieve("coupon_test")).thenReturn(coupon);

            Coupon result = stripeService.retriveCoupon("coupon_test");

            assertNotNull(result);
        }
    }

    @Test
    void testCreateCharge() throws Exception {
        try (MockedStatic<Charge> mockedStatic = mockStatic(Charge.class)) {
            Charge charge = mock(Charge.class);
            when(charge.getId()).thenReturn("ch_test_123");
            mockedStatic.when(() -> Charge.create((Map<String, Object>) any())).thenReturn(charge);

            String id = stripeService.createCharge("test@example.com", "tok_test", 1000);

            assertEquals("ch_test_123", id);
        }
    }
}
