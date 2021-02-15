package chany;

import chany.account.Account;
import chany.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedisRunner implements ApplicationRunner {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    AccountRepository accountRepository;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set("cy","chany");
        values.set("spring", "2.0");
        values.set("hello", "redis");

        Account account = new Account();

        account.setUsername("chany");
        account.setEmail("chany@@");

        accountRepository.save(account);
        Optional<Account> findAccount = accountRepository.findById(account.getId());
        System.out.println(findAccount.get().getUsername());
        System.out.println(findAccount.get().getEmail());
    }
}
