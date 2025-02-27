package id.ac.ui.cs.advprog.eshop.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

@ActiveProfiles("non-test")
@TestPropertySource(properties = {
    "spring.thymeleaf.enabled=false",
    "spring.thymeleaf.prefix=classpath:/templates/",  // Set template prefix
    "spring.thymeleaf.suffix=.html"                  // Set template suffix
})
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProductService productService;
    
    @MockBean
    private CarServiceImpl carservice;

    @BeforeEach
    void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/templates/");
        viewResolver.setSuffix(".html");
        mockMvc = MockMvcBuilders.standaloneSetup(new CarController(productService, carservice))
                .setViewResolvers(viewResolver)
                .build();
    }

    @Test
    public void testCreateCarPost() throws Exception {
        Car car = new Car();
        car.setCarId("car-1");
        car.setCarName("TestCar");
        when(carservice.create(any(Car.class))).thenReturn(car);
        
        mockMvc.perform(post("/car/createCar")
                        .param("carName", "TestCar")
                        .param("carColor", "Red")
                        .param("carQuantity", "1"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("listCar")); // Remove "redirect:" prefix
    }

    @Test
    public void testCarListPage() throws Exception {
        Car car = new Car();
        car.setCarId("car-2");
        car.setCarName("ListedCar");
        when(carservice.findAll()).thenReturn(java.util.Arrays.asList(car));
        
        mockMvc.perform(get("/car/listCar"))
            .andExpect(status().isOk())
            .andExpect(view().name("carList"))
            .andExpect(model().attributeExists("cars"));
    }

    @Test
    public void testEditCarPage() throws Exception {
        Car car = new Car();
        car.setCarId("car-edit");
        car.setCarName("EditCar");
        when(carservice.findById("car-edit")).thenReturn(car);
        
        mockMvc.perform(get("/car/editCar/car-edit"))
            .andExpect(status().isOk())
            .andExpect(view().name("editCar"))
            .andExpect(model().attributeExists("car"));
    }

    @Test
    public void testEditCarPost() throws Exception {
        mockMvc.perform(post("/car/editCar")
                        .param("carId", "car-edit")
                        .param("carName", "EditedCar")
                        .param("carColor", "Blue")
                        .param("carQuantity", "5"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("listCar")); // Remove "redirect:" prefix
    }

    @Test
    public void testDeleteCar() throws Exception {
        doNothing().when(carservice).deleteCarById("car-delete");
        mockMvc.perform(post("/car/deleteCar").param("carId", "car-delete"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("listCar")); // Remove "redirect:" prefix
    }
}
