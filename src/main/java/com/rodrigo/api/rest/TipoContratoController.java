package com.rodrigo.api.rest;


import com.rodrigo.api.model.form.TipoContratoForm;
import com.rodrigo.api.model.response.TipoContratoResponse;
import com.rodrigo.api.services.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tiposContrato")
public class TipoContratoController extends CrudControllerImpl<TipoContratoForm, TipoContratoResponse> {

    public TipoContratoController(CrudService<TipoContratoForm, TipoContratoResponse> service) {
        super(service);
    }
}
