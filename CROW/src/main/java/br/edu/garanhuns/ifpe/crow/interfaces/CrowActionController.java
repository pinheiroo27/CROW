/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.garanhuns.ifpe.crow.interfaces;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Gleydson
 */
public interface CrowActionController<Bean, ID extends Serializable> {

    public void create(Bean bean);

    public Collection<Bean> read();

    public Bean update(Bean entity);

    public void delete(Bean bean);

    public Bean getById(ID id);
    
    public List<Bean> list();

}
