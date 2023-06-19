package chany.sample;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.OutputCaptureRule;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerWebMvcTest {

    @Rule
    public OutputCaptureRule outputCaptureRule = new OutputCaptureRule();


    @MockBean
    SampleService mockSampleService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hell() throws Exception {
        when(mockSampleService.getName()).thenReturn("CHANY");

        mockMvc.perform(get("/hello"))
                .andExpect(content().string("hello CHANY"));

        assertThat(outputCaptureRule.toString())
                .contains("yeol")
                .contains("yeol2");
    }
}
