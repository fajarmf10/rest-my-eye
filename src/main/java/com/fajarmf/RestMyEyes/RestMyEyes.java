package com.fajarmf.RestMyEyes;

import mdlaf.MaterialLookAndFeel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

public class RestMyEyes extends JFrame {
    private final JLabel timerLabel;
    private JTextField hourField;
    private JTextField minuteField;
    private JTextField secondField;
    private JButton startButton;
    private JButton stopButton;
    private Timer timer;

    public RestMyEyes() {
        setTitle("\"Let's Rest Up Your Eyes!\" App");
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icon.ico"))).getImage());

        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }

        getContentPane().setBackground(Color.WHITE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 4, screenSize.height / 4);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 40));
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setOpaque(true);
        add(timerLabel, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        createInputFields(inputPanel);
        add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        createButtons(buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createInputFields(JPanel panel) {
        hourField = createLabeledTextField(panel, "Hours:");
        minuteField = createLabeledTextField(panel, "Minutes:");
        secondField = createLabeledTextField(panel, "Seconds:");
    }

    private JTextField createLabeledTextField(JPanel panel, String labelText) {
        JLabel label = new JLabel(labelText, SwingConstants.LEFT);
        label.setForeground(Color.BLACK);
        panel.add(label);

        JTextField textField = new JTextField(2);
        textField.setBackground(Color.WHITE);
        textField.setForeground(Color.BLACK);
        panel.add(textField);

        return textField;
    }

    private void createButtons(JPanel panel) {
        startButton = new JButton("Start");
        styleButton(startButton);
        startButton.addActionListener(e -> startTimer());

        stopButton = new JButton("Stop");
        styleButton(stopButton);
        stopButton.addActionListener(e -> stopTimer());
        stopButton.setEnabled(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        panel.add(startButton, gbc);
        panel.add(stopButton, gbc);
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(150, 40));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void startTimer() {
        int hours = parseTimeField(hourField);
        int minutes = parseTimeField(minuteField);
        int seconds = parseTimeField(secondField);

        if (hours == 0 && minutes == 0 && seconds == 0) {
            JOptionPane.showMessageDialog(null, "Please enter at least 1 second.");
            setAlwaysOnTop(true);
            startButton.setEnabled(true);
            return;
        }

        timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));

        int totalTime = hours * 3600 + minutes * 60 + seconds;
        timer = new Timer(1000, new ActionListener() {
            int timeLeft = totalTime;

            @Override
            public void actionPerformed(ActionEvent e) {

                timeLeft--;
                int hrs = timeLeft / 3600;
                int mins = (timeLeft % 3600) / 60;
                int secs = timeLeft % 60;
                timerLabel.setText(String.format("%02d:%02d:%02d", hrs, mins, secs));

                if (timeLeft <= 0) {
                    showTimeUpDialog();
                    stopTimer();
                    resetFields();
                }
            }
        });

        timer.start();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    private int parseTimeField(JTextField field) {
        return field.getText().isEmpty() ? 0 : Integer.parseInt(field.getText());
    }

    private void showTimeUpDialog() {
        JOptionPane optionPane = new JOptionPane(
                "Time's up! Take a break.",
                JOptionPane.ERROR_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                new Object[]{"OK"},
                null
        );

        JDialog dialog = optionPane.createDialog(this, "Rest Reminder");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

    private void stopTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
            timerLabel.setText("00:00:00");
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
        }
    }

    private void resetFields() {
        hourField.setText("");
        minuteField.setText("");
        secondField.setText("");
    }
}
