package models;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;

import java.util.HashMap;

public class Pay {
    public static void main(String[] args) {
        // Set your secret key here
        Stripe.apiKey = "";

        try {
            // Retrieve your account information
            Account account = Account.retrieve();
            System.out.println("Account ID: " + account.getId());

            // Check if metadata is null and initialize it
            if (account.getMetadata() == null) {
                account.setMetadata(new HashMap<>());
            }

            // Set name in metadata
            account.getMetadata().put("name", "John Doe");

            // Print other account information as needed
            System.out.println("Account Name: " + account.getMetadata().get("name"));
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }
}
