package com.fajarmf.RestMyEyes;

import mdlaf.MaterialLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class RestMyEyes extends JFrame {
    private final JLabel timerLabel;
    private final JTextField hourField;
    private final JTextField minuteField;
    private final JTextField secondField;
    private final JButton startButton;
    private final JButton stopButton;
    private Timer timer;

    public RestMyEyes() {
        String title = "\"Let's Rest Up Your Eyes!\" App";
        setTitle(title);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon.ico"))).getImage());

        // Set system look and feel (Windows Aero or whatever the current theme is)
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        getContentPane().setBackground(Color.WHITE); // Set background color to white

        // Get screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        // Set the size to 25% of the screen resolution
        setSize(screenWidth / 4, screenHeight / 4);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // Center the window on the screen
        setLocationRelativeTo(null);

        timerLabel = new JLabel("00:00:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 40));
        timerLabel.setForeground(new Color(0, 0, 0)); // White
        timerLabel.setOpaque(true);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(timerLabel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add margin

        JLabel hourLabel = new JLabel("Hours:");
        hourLabel.setHorizontalAlignment(SwingConstants.LEFT);
        hourLabel.setBackground(new Color(255, 255, 255)); // White
        hourLabel.setForeground(new Color(0, 0, 0)); // Black
        inputPanel.add(hourLabel);
        hourField = new JTextField(2);
        hourField.setBackground(new Color(255, 255, 255)); // White
        hourField.setForeground(new Color(0, 0, 0)); // Black
        inputPanel.add(hourField);

        JLabel minuteLabel = new JLabel("Minutes:");
        minuteLabel.setHorizontalAlignment(SwingConstants.LEFT);
        minuteLabel.setBackground(new Color(255, 255, 255)); // White
        minuteLabel.setForeground(new Color(0, 0, 0)); // Black
        inputPanel.add(minuteLabel);
        minuteField = new JTextField(2);
        minuteField.setBackground(new Color(255, 255, 255)); // White
        minuteField.setForeground(new Color(0, 0, 0)); // Black
        inputPanel.add(minuteField);

        JLabel secondLabel = new JLabel("Seconds:");
        secondLabel.setHorizontalAlignment(SwingConstants.LEFT);
        secondLabel.setBackground(new Color(255, 255, 255)); // White
        secondLabel.setForeground(new Color(0, 0, 0)); // Black
        inputPanel.add(secondLabel);
        secondField = new JTextField(2);
        secondField.setBackground(new Color(255, 255, 255)); // White
        secondField.setForeground(new Color(0, 0, 0)); // Black
        inputPanel.add(secondField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());

        String startButtonText = "Start";
        startButton = new JButton(startButtonText);
        styleButton(startButton, startButtonText);
        startButton.addActionListener(e -> {
            String hourTextField = hourField.getText().isEmpty() ? "0" : hourField.getText();
            String minuteTextField = minuteField.getText().isEmpty() ? "0" : minuteField.getText();
            String secondTextField = secondField.getText().isEmpty() ? "0" : secondField.getText();

            int hours = Integer.parseInt(hourTextField);
            int minutes = Integer.parseInt(minuteTextField);
            int seconds = Integer.parseInt(secondTextField);
            timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
            startButton.setEnabled(false); // Disable the start button
            startTimer();
        });

        String stopButtonText = "Stop";
        stopButton = new JButton(stopButtonText);
        styleButton(stopButton, stopButtonText);
        stopButton.addActionListener(e -> stopTimer());
        stopButton.setEnabled(false); // Initially disable the stop button

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding

        buttonPanel.add(startButton, gbc);

        gbc.gridx = 1;
        buttonPanel.add(stopButton, gbc);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void startTimer() {
        String hourTextField = hourField.getText().isEmpty() ? "0" : hourField.getText();
        String minuteTextField = minuteField.getText().isEmpty() ? "0" : minuteField.getText();
        String secondTextField = secondField.getText().isEmpty() ? "0" : secondField.getText();

        int hours = Integer.parseInt(hourTextField);
        int minutes = Integer.parseInt(minuteTextField);
        int seconds = Integer.parseInt(secondTextField);

        if (seconds == 0) {
            JOptionPane.showMessageDialog(null, "Please enter a non-zero value for seconds.");
            setAlwaysOnTop(true);
            startButton.setEnabled(true); // Re-enable the start button
            return; // Stop the method if seconds is zero
        }

        int totalTime = hours * 3600 + minutes * 60 + seconds;

        timer = new Timer(1000, new ActionListener() {
            int timeLeft = totalTime;

            @Override
            public void actionPerformed(ActionEvent e) {
                int hours = timeLeft / 3600;
                int minutes = (timeLeft % 3600) / 60;
                int seconds = timeLeft % 60;
                timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds-1));
                if (timeLeft == 1) {
                    JOptionPane optionPane = new JOptionPane(
                            "Time's up! Take a break.",
                            JOptionPane.ERROR_MESSAGE,
                            JOptionPane.DEFAULT_OPTION,
                            null,
                            new Object[]{"OK"}, // Add OK button
                            null
                    );

                    JDialog dialog = optionPane.createDialog(RestMyEyes.this, "Rest Reminder");
                    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                    dialog.pack();
                    dialog.setLocationRelativeTo(RestMyEyes.this); // Center the dialog
                    dialog.setAlwaysOnTop(true); // Set dialog always on top initially
                    dialog.setVisible(true);

                    stopTimer();
                    // Reset the timer fields
                    hourField.setText("");
                    minuteField.setText("");
                    secondField.setText("");
                }
                timeLeft--;
            }
        });
        timer.start();
        stopButton.setEnabled(true); // Enable the stop button
    }

    private void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            timerLabel.setText("00:00:00"); // Reset the countdown label
            startButton.setEnabled(true); // Enable the start button
            stopButton.setEnabled(false); // Disable the stop button
        }
    }

    private void styleButton(JButton button, String buttonText) {
        button.setPreferredSize(new Dimension(150, 40));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change cursor to hand on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // Restore default cursor on exit
            }
        });
    }
}
