package chany.events;

import chany.accounts.Account;
import chany.accounts.CurrentUser;
import chany.common.ErrorsResource;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

    private final EventRepository eventRepository;

    private final ModelMapper modelMapper;

    private final EventValidator eventValidator;

    public EventController(EventRepository eventRepository, ModelMapper modelMapper, EventValidator eventValidator) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
        this.eventValidator = eventValidator;

    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Valid EventDto eventDto,
                                      Errors errors,
                                      @CurrentUser Account currentUser) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorsResource(errors));
        }

        eventValidator.validate(eventDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorsResource(errors));
        }

        Event event = modelMapper.map(eventDto, Event.class);
        event.update();
        event.setManager(currentUser);
        Event newEvent = this.eventRepository.save(event);
        WebMvcLinkBuilder selfLinkBuilder = linkTo(EventController.class).slash(newEvent.getId());
        URI createdUri = selfLinkBuilder.toUri();
        EventResource eventResource = new EventResource(event);
//        EntityModel<Event> eventResource = EntityModel.of(newEvent);
        eventResource.add(linkTo(EventController.class).withRel("query-events"));
        eventResource.add(selfLinkBuilder.withRel("update-event"));
        eventResource.add(new Link("/docs/index.html#resources-events-create").withRel("profile"));
        return ResponseEntity.created(createdUri).body(eventResource);
    }

    @GetMapping
    public ResponseEntity queryEvents(Pageable pageable, PagedResourcesAssembler<Event> assembler,
                                      @CurrentUser Account currentUser) {

        Page<Event> page = this.eventRepository.findAll(pageable);
        PagedModel<EntityModel<Event>> pagedModel = assembler.toModel(page, e -> new EventResource(e));
        pagedModel.add(new Link("/docs/index.html#resources-events-list").withRel("profile"));
        if (currentUser != null) {
            pagedModel.add(linkTo(EventController.class).withRel("create-event"));
        }
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    private ResponseEntity getEvent(@PathVariable Integer id,
                                    @CurrentUser Account currentUser) {
        Optional<Event> optionalEvent = this.eventRepository.findById(id);

        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {

            Event event = optionalEvent.get();
            EventResource eventResource = new EventResource(event);
            eventResource.add(new Link("docs/index.html#resources-events-get").withRel("profile"));
            if (event.getManager().equals(currentUser)) {
                eventResource.add(linkTo(EventController.class).slash(event.getId()).withRel("update-event"));
            }
            return ResponseEntity.ok(eventResource);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity updateEvent(@PathVariable Integer id,
                                      @RequestBody @Valid EventDto eventDto,
                                      Errors errors,
                                      @CurrentUser Account currentUser) {

        Optional<Event> optionalEvent = this.eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorsResource(errors));
        }

        this.eventValidator.validate(eventDto, errors);

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorsResource(errors));
        }

        Event existingEvent = optionalEvent.get();

        if (!existingEvent.getManager().equals(currentUser)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        this.modelMapper.map(eventDto, existingEvent);
        Event savedEvent = this.eventRepository.save(existingEvent);
        EventResource eventResource = new EventResource(savedEvent);
        eventResource.add(new Link("/docs/index.html#resources-events-update").withRel("profile"));

        return ResponseEntity.ok(eventResource);
    }
}
