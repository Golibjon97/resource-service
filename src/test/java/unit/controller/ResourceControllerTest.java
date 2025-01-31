//package unit.controller;
//
//
//import com.epam.controller.ResourceController;
//import com.epam.service.ResourceService;
//import java.io.IOException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import unit.objectmothers.ObjectMother;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class ResourceControllerTest {
//
//  @InjectMocks
//  private ResourceController resourceController;
//
//  @Mock
//  ResourceService resourceService;
//
//  @BeforeEach
//  public void initRequest() {
//    MockHttpServletRequest request = new MockHttpServletRequest();
//    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//  }
//
////  @Test
////  void testUploadResource() throws IOException {
////    byte[] mp3Data = ObjectMother.mp3Data();
////    Integer expectedResult = 1;
////
////    when(resourceService.saveFile(mp3Data)).thenReturn(expectedResult);
////
////    ResponseEntity<?> actualResponse = resourceController.uploadResource(mp3Data);
////
////    assertThat(actualResponse.getBody()).isEqualTo(expectedResult);
////    verify(resourceService).saveFile(mp3Data);
////  }
//
//  @Test
//  void testDeleteFile(){
//    String validIds = "1,2,3";
//
//    ResponseEntity<?> response = resourceController.deleteFile(validIds);
//
//    assertThat(HttpStatus.OK).isEqualTo(response.getStatusCode());
//    verify(resourceService).deleteFiles(validIds);
//  }
//
////  @Test
////  void testGetByteMp3() throws IOException {
////    byte[] expectedResult = ObjectMother.mp3Data();
////    Integer resourceId = 1;
////
////    when(resourceService.getMp3File(resourceId)).thenReturn(expectedResult);
////    ResponseEntity<?> actualResponse = resourceController.getByteMp3(resourceId);
////
////    assertThat(actualResponse.getBody()).isEqualTo(expectedResult);
////    verify(resourceService).getMp3File(resourceId);
////
////  }
//
//}
