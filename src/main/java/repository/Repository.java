/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.List;

/**
 *
 * @author Mihailo
 * @param <T>
 */
public interface Repository<T> {
    List<T> getAll() throws Exception;
}
