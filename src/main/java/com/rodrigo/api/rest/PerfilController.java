package com.rodrigo.api.rest;

import com.rodrigo.api.model.form.PerfilForm;
import com.rodrigo.api.model.response.PerfilResponse;
import com.rodrigo.api.services.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perfis")
public class PerfilController extends CrudControllerImpl<PerfilForm, PerfilResponse> {

    public PerfilController(CrudService<PerfilForm, PerfilResponse> service) {
        super(service);
    }
}
