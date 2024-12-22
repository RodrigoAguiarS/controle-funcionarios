package com.rodrigo.api.exception;

public enum MensagensError {
    // Geral
    ENTIDADE_NAO_ENCONTRADO("Entidade não encontrada"),

    // Usuario
    USUARIO_NAO_ENCONTRADO_POR_LOGIN("O Usuário com o Login: %s não foi encontrado."),
    PERFIL_NAO_ENCONTRADO_POR_ID("O Perfil com o id: %D não foi encontrado."),
    PERFIL_VINCULADO_USUARIO("O Perfil %s está vinculado a um usuário."),
    USUARIO_NAO_ENCONTRADO_POR_ID("O Usuário com o Id: %d não foi encontrado."),
    ERRO_CPF_JA_CADASTRADO_SISTEMA("Erro CPF %s já cadastrado no sistema."),
    ERRO_LOGIN_JA_CADASTRADO_SISTEMA("Erro Login/E-mail %s já cadastrado no sistema."),

    // Funcionario
    FUNCIONARIO_NAO_ENCONTRADO_POR_ID("O Funcionário com o ID: %d não foi encontrado."),

    // Jornado
    JORNADA_NAO_ENCONTRADO_POR_ID("A Jornada com o ID: %d não foi encontrado."),
    JORNADA_NAO_ENCONTRADO("Jornada não encontrada para o funcionário na data especificada."),

    // Cargo
    CARGO_POSSUI_FUNCIONARIO("Não é possível excluir o cargo. Existem funcionários associados a ele."),

    // Ponto
    PONTO_NAO_ENCONTRADO("O Ponto não foi encontrado."),

    PERFIL_POSSUI_USUARIO("Não é possível excluir o Perfil. Existem Usuários associados a ele"),
    TIPO_CONTRADO_POSSUI_FUNCIONARIO("Não é possível excluir o Tipo de Contrato. Existem funcionários associados a ele");

    private final String message;

    MensagensError(String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
