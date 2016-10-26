/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.garanhuns.ifpe.crow.classes;

/**
 *
 * @author casa01
 */
public class StringUtil {
    
    public static String upperCaseFirst(String value) {

	// Convert String to char array.
	char[] array = value.toCharArray();
	// Modify first element in array.
	array[0] = Character.toUpperCase(array[0]);
	// Return string.
	return new String(array);
    }
}
