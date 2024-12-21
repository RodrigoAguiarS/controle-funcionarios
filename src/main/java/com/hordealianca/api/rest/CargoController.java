package com.hordealianca.api.rest;


import com.hordealianca.api.model.form.CargoForm;
import com.hordealianca.api.model.response.CargoResponse;
import com.hordealianca.api.services.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cargos")
public class CargoController extends CrudControllerImpl<CargoForm, CargoResponse> {

    public CargoController(CrudService<CargoForm, CargoResponse> service) {
        super(service);
    }
}
