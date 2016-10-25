/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.garanhuns.ifpe.crow.classes;

/**
 *
 * @author Gleydson
 */
public class CrudAttribute {
    private String name;
    private String type;
    private boolean id;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the id
     */
    public boolean isId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(boolean id) {
        this.id = id;
    }
}
