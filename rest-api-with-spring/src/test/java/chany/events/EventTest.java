package chany.events;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventTest {

    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Inflearn Spring REST API")
                .description("REST API")
                .build();
        assertThat(event).isNotNull();

    }

    @Test
    public void javaBean() {
        Event event = new Event();
        String name = "Chany";
        event.setName(name);

        String description = "REST CHANY";
        event.setDescription(description);

        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }

}