package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import org.springframework.context.annotation.Bean;

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
