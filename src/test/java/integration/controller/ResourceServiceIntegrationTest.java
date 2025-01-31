//package integration.controller;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//
//import com.epam.ResourceServiceApplication;
//import com.epam.service.ResourceService;
//import jakarta.ws.rs.core.MediaType;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpStatus;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(classes = ResourceServiceApplication.class)
//@AutoConfigureMockMvc
//public class ResourceServiceIntegrationTest {
//
//    private static final String ENDPOINT = "http://localhost:8080/api/v1/resources";
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ResourceService resourceService;
//
//    @Test
//    void shouldUploadResources() throws Exception {
//
//        // Given
//        byte[] mp3Data = "mock mp3 data".getBytes();
//        Integer savedFileId = 123;
//
//        // Mocking methods
//        when(resourceService.saveFile(any(byte[].class))).thenReturn(savedFileId);
//
//        // Performing the request
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders.post(ENDPOINT + "/upload_mp3")
//                        .contentType("audio/mpeg")
//                        .content(mp3Data)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().string(savedFileId.toString()))
//                .andReturn();
//
//        // Assertions
//        assertThat(mvcResult.getResponse()).isNotNull();
//        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(savedFileId.toString());
//
//        // Verify interactions
//        verify(resourceService).saveFile(mp3Data);
//    }
//
//    @Test
//    void shouldDeleteFile() throws Exception {
//        // Given
//        String ids = "2";
//
//        // Perform the request
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders.delete(ENDPOINT + "/delete")
//                        .param("ids", ids)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        assertThat(mvcResult.getResponse()).isNotNull();
//        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
//
//        // Verify that the service method is called
//        verify(resourceService).deleteFiles(ids);
//    }
//
//    @Test
//    void shouldGetByteMp3() throws Exception {
//
//        Integer pathVariable = 1;
//        byte[] mockMp3Bytes = "mock mp3 data".getBytes();
//
//        // Mocking the service method
//        when(resourceService.getMp3File(anyInt())).thenReturn(mockMp3Bytes);
//
//        MvcResult mvcResult = mockMvc
//                .perform(MockMvcRequestBuilders.get(ENDPOINT + "/mp3/{id}", pathVariable))
//                .andExpect(status().isOk())
//                .andExpect(content().bytes(mockMp3Bytes))
//                .andReturn();
//
//        assertThat(mvcResult.getResponse()).isNotNull();
//        assertThat(mvcResult.getResponse().getContentAsByteArray()).isEqualTo(mockMp3Bytes);
//
//        verify(resourceService).getMp3File(pathVariable);
//    }
//}
