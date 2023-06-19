package chany;

import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class SampleController {

    @GetMapping("/hello")
    public EntityModel<Hello> hello() {
        Hello hello = new Hello();
        hello.setPrefix("Hey,");
        hello.setName("chany");


        EntityModel<Hello> helloEntityModel = new EntityModel<>(hello);
        helloEntityModel.add(linkTo(methodOn(SampleController.class).hello()).withSelfRel());

        return helloEntityModel;

    }
}
