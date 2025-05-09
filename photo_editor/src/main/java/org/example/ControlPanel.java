package org.example;

import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    private final JComboBox<String> operationComboBox;
    private final JSlider parameterSlider;
    private final JComboBox<String> blendModeCombo;
    private final JSlider alphaSlider;

    public ControlPanel(ImageOperations operations, ImagePanel imagePanel) {

        setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton openFirstButton = new JButton("Open Image 1");
        JButton openSecondButton = new JButton("Open Image 2");
        JButton saveFirstButton = new JButton("Save Image 1");
        JButton saveSecondButton = new JButton("Save Image 2");
        JButton blendButton = new JButton("Blend Images");
        JButton resetViewButton = new JButton("Reset View");

        String[] operationsList = {
                "Select operation...",
                "Brightening", "Darkening", "Negative",
                "Power Transform (Brightening)", "Power Transform (Darkening)",
                "Contrast", "Gaussian Blur", "Edge Detection",
                "Thresholding", "Sepia", "Blur",
                "Generate RGB Histogram", "Equalize Histogram", "Scale Histogram",
                "Low-pass Filter", "Roberts Filter", "Prewitt Filter",
                "Sobel Filter", "Laplace Filter", "Min Filter",
                "Max Filter", "Median Filter"
        };

        String[] blendModes = {
                "Normal (Alpha)", "Add", "Subtract", "Difference",
                "Multiply", "Screen", "Negation",
                "Darken", "Lighten", "Exclusion",
                "Overlay", "Hard Light", "Soft Light",
                "Color Dodge", "Color Burn", "Reflect", "Transparency"
        };

        operationComboBox = new JComboBox<>(operationsList);
        parameterSlider = new JSlider(0, 100, 50);
        parameterSlider.setVisible(false);

        blendModeCombo = new JComboBox<>(blendModes);
        alphaSlider = new JSlider(0, 100, 50);
        alphaSlider.setVisible(false);

        add(openFirstButton);
        add(openSecondButton);
        add(saveFirstButton);
        add(saveSecondButton);
        add(blendButton);
        add(new JSeparator(SwingConstants.VERTICAL));

        add(new JLabel("Operation:"));
        add(operationComboBox);
        add(new JLabel("Param:"));
        add(parameterSlider);

        add(new JSeparator(SwingConstants.VERTICAL));
        add(new JLabel("Blend Mode:"));
        add(blendModeCombo);
        add(new JLabel("Alpha:"));
        add(alphaSlider);

        add(new JSeparator(SwingConstants.VERTICAL));
        add(resetViewButton);

        JLabel selectedLabel = new JLabel("Selected: Image 1");
        imagePanel.addPropertyChangeListener("selectedImage", evt -> {
            selectedLabel.setText("Selected: Image " + imagePanel.getSelectedImageNumber());
        });
        add(selectedLabel);

        openFirstButton.addActionListener(e -> operations.openFirstImage(imagePanel));
        openSecondButton.addActionListener(e -> operations.openSecondImage(imagePanel));
        saveFirstButton.addActionListener(e -> operations.saveFirstImage(imagePanel.getFirstImage()));
        saveSecondButton.addActionListener(e -> operations.saveSecondImage(imagePanel.getSecondImage()));

        blendButton.addActionListener(e -> {
            int selectedMode = blendModeCombo.getSelectedIndex() + 1;
            double alpha = alphaSlider.getValue() / 100.0;
            operations.blendImages(imagePanel, selectedMode, alpha);
        });

        resetViewButton.addActionListener(e -> imagePanel.resetView());

        operationComboBox.addActionListener(e -> {
            String selected = (String) operationComboBox.getSelectedItem();
            if (selected != null && !selected.equals("Select operation...")) {
                parameterSlider.setVisible(needsParameter(selected));
                operations.applyOperation(selected, imagePanel, parameterSlider);
            }
        });

        parameterSlider.addChangeListener(e -> {
            String selected = (String) operationComboBox.getSelectedItem();
            if (selected != null && !selected.equals("Select operation...")) {
                operations.applyOperation(selected, imagePanel, parameterSlider);
            }
        });

        blendModeCombo.addActionListener(e -> {
            alphaSlider.setVisible(blendModeCombo.getSelectedIndex() == 0 ||
                    blendModeCombo.getSelectedIndex() == 15);
        });
    }

    private boolean needsParameter(String operation) {
        return switch (operation) {
            case "Brightening", "Darkening", "Contrast", "Thresholding",
                 "Power Transform (Brightening)", "Power Transform (Darkening)" -> true;
            default -> false;
        };
    }
}