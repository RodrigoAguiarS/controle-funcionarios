package com.rodrigo.api.rest;


import com.rodrigo.api.model.form.CargoForm;
import com.rodrigo.api.model.response.CargoResponse;
import com.rodrigo.api.services.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cargos")
public class CargoController extends CrudControllerImpl<CargoForm, CargoResponse> {

    public CargoController(CrudService<CargoForm, CargoResponse> service) {
        super(service);
    }
}
