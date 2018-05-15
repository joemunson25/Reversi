/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import model.Reversi;

/**
 * Web application lifecycle listener.
 *
 * @author josephmunson
 */
public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("reversi", new Reversi());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    }
}
