package com.incloud.hcp.jco.maestro.dto;

import java.util.List;

public class EmpresaImports {
    private String p_cdusr;
    private String p_ruc;
    private List<MaestroOptions> option;
    private List<MaestroOptionsKey> options;
    private String [] fields;
    //private List<MaestroOptionsKey> options;


    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }


    public List<MaestroOptions> getOption() {
        return option;
    }

    public void setOption(List<MaestroOptions> option) {
        this.option = option;
    }

    public List<MaestroOptionsKey> getOptions() {
        return options;
    }

    public void setOptions(List<MaestroOptionsKey> options) {
        this.options = options;
    }

    public String getP_cdusr() {
        return p_cdusr;
    }

    public void setP_cdusr(String p_cdusr) {
        this.p_cdusr = p_cdusr;
    }

    public String getP_ruc() {
        return p_ruc;
    }

    public void setP_ruc(String p_ruc) {
        this.p_ruc = p_ruc;
    }
}
