/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

/**
 *
 * @author Chloe
 */
public class DaoUser extends User {

    protected Connection connection = MariaDbConnection.getInstance(); //Etablir la connexion vers la BDD

    //Création utilisateur dans la BDD
    public void create(User obj) {
        String sql = "INSERT INTO user (login, password) VALUES (?, ?)";

        try {
            PreparedStatement pstmt = connection.prepareStatement(
                    sql, // La requête exécutée
                    PreparedStatement.RETURN_GENERATED_KEYS // Récupérer les clés générées par la requête SQL pour les fournir
            //à Java pour qu'on puisse récupérer les données côté Java
            );
            //Insère dans la BDD le login et le mot de passe
            pstmt.setString(1, obj.getLogin()); //permet d'associer les ? d'une requete SQL préparée à une valeur grâce à un mappage
            //avec les index de la BDD
            pstmt.setString(2, obj.getPassword());
            pstmt.executeUpdate(); //MAJ de la BDD
            ResultSet rs = pstmt.getGeneratedKeys(); //rs est un booléen, il récupère ici les clés générées par la requête SQL
            if (rs.first()) {
                obj.setId_user(rs.getInt(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Lire un utilisateur de la BDD
    public User read(Integer id_user) {
        User obj = null;
        String sql = "SELECT * FROM user WHERE id_user=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id_user); // on indique l'ID de l'utilisateur qu'on souhaite consulter
            ResultSet rs = pstmt.executeQuery(); //exécution de la requête
            if (rs.first()) {
                //Si rs renvoie true sur la première ligne correspondant à l'ID user, il renvoie les données suivantes
                obj = new User();
                obj.setId_user(rs.getInt("id_user"));
                obj.setLogin(rs.getString("login"));
                obj.setPassword(rs.getString("password"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(obj);
        return obj;
    }

    //Modification d'un utilisateur de la DAO
    public void update(User obj) {
        String sql = "UPDATE user SET login=?, password=? WHERE id_user=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            //Insertion des modifications sur les champs
            pstmt.setString(1, obj.getLogin()); //permet de récupérer les ? d'une requete SQL préparée 
            //grâce à un mappage avec les index 
            pstmt.setString(1, obj.getPassword());
            pstmt.setInt(2, obj.getId_user());
            //"Enregistrement" des mises à jour données 
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //Suppression d'un utilisateur de la DAO
    public void delete(Integer id_user) throws SQLException {
        String sql = "DELETE FROM user WHERE id_user=?";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
        } catch (SQLException ex) {
            Logger.getLogger(DaoUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        pstmt.setInt(1, id_user); //On efface les champs contenus dans l'id_user
        pstmt.executeUpdate();
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM user";
        return 0;
    }

    public void persist(User obj) { //=hydrater
        if (obj.getId_user() == null) {
            this.create(obj);// ici Java hydrate la BDD
        } else {
            this.update(obj);//ici la BDD hydrate Java
        }
    }

}
