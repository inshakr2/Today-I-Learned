package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class DiscountPolicyConfig {

    @Bean
    public DiscountPolicy fixDiscountPolicy() {
        return new FixDiscountPolicy();
    }

    @Bean
    @MainDiscountPolicy
    DiscountPolicy rateDiscountPolicy() {
        return new RateDiscountPolicy();
    }
}
