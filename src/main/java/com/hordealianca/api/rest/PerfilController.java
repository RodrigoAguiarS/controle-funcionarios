package com.hordealianca.api.rest;

import com.hordealianca.api.model.form.PerfilForm;
import com.hordealianca.api.model.response.PerfilResponse;
import com.hordealianca.api.services.CrudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perfis")
public class PerfilController extends CrudControllerImpl<PerfilForm, PerfilResponse> {

    public PerfilController(CrudService<PerfilForm, PerfilResponse> service) {
        super(service);
    }
}
