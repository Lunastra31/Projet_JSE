/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Chloe
 */
public final class MariaDbConnection {

    private static Connection instance;
    //Paramètres utiles à la connexion 
    private static final String host = "wp.ldnr.fr";
    private static final String db = "cda202301_jse_g1";
    private static String user = "cda202301_jse_g1";
    private static String password = "Licorne";
    private static final String port = "3306";

    private MariaDbConnection() {
    }

    public static Connection getInstance() {
        if (instance == null) { 
            try {
            //construction de l'objet la première fois qu'on l'utilise
            instance = getMySqlConnection();
            } catch (ClassNotFoundException |SQLException ex) {
                throw new RuntimeException("Problème de connexion au serveur");
            }
        }
        return instance; //return du singleton pour ne plus avoir à le reconstruire 
    }
    
    private static Connection getMySqlConnection() throws ClassNotFoundException, SQLException {
        Connection connection;
        //Forcer Java à trouver le driver dans les dépendances
        //Normalement inutile depuis Java 6 mais obligatoire en JEE
        Class.forName("org.mariadb.jdbc.Driver"); //driver = permet de dire d'aller chercher un truc qui s'appelle comme ça à cet endroit 
        connection = DriverManager.getConnection(
                "jdbc:mariadb://" + host + ":" + port + "/" + db, //la première partie est à récupérer sur Internet
                user, // le login pour se connecter à la BDD
                password); //Le mot de passe, idem
        return connection;
    }

}
