package com.rodrigo.api.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ModelMapperService {

    private final ModelMapper modelMapper;

    public ModelMapperService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <D, T> D converterParaDto(T entidade, Class<D> classeDto) {
        return modelMapper.map(entidade, classeDto);
    }

    public <D, T> T converterParaEntidade(D dto, Class<T> classeEntidade) {
        return modelMapper.map(dto, classeEntidade);
    }

    public <S, D> void mapear(S origem, D destino) {
        modelMapper.map(origem, destino);
    }
}
