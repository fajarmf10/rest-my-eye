package com.fajarmf;

import com.fajarmf.RestMyEyes.RestMyEyes;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Runnable timerAppRunnable = () -> {
            RestMyEyes app = new RestMyEyes();
            app.setVisible(true);
        };
        SwingUtilities.invokeLater(timerAppRunnable);
    }
}