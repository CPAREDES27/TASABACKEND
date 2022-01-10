package com.incloud.hcp.jco.controlLogistico.dto;

import java.util.HashMap;
import java.util.List;

public class HorometroForEmailDto {
    private List<HashMap<String, Object>> horometrosAveriados;

    public List<HashMap<String, Object>> getHorometrosAveriados() {
        return horometrosAveriados;
    }

    public void setHorometrosAveriados(List<HashMap<String, Object>> horometrosAveriados) {
        this.horometrosAveriados = horometrosAveriados;
    }
}
