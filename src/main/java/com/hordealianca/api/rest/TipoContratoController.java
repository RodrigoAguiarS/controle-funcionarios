package com.hordealianca.api.rest;


import com.hordealianca.api.model.form.TipoContratoForm;
import com.hordealianca.api.model.response.TipoContratoResponse;
import com.hordealianca.api.services.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tiposContrato")
public class TipoContratoController extends CrudControllerImpl<TipoContratoForm, TipoContratoResponse> {

    public TipoContratoController(CrudService<TipoContratoForm, TipoContratoResponse> service) {
        super(service);
    }
}
