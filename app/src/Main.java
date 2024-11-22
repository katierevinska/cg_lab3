import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Main {
    int gridWidth = 25;
    long startTime = 0;
    long endTime = 0;
    long SbSTime = -1;
    long BrtTime = -1;
    long DDATime = -1;
    long BrtCircleTime = -1;
    MyPanel japan;
    BufferedImage imag;

    int width, height;

    public Main() {

        JFrame f = new MyFrame("Графический редактор");
        f.setSize(1400, 800);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        JMenuBar menuBar = new JMenuBar();
        f.setJMenuBar(menuBar);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        controlPanel.add(new JLabel("line X1:"));
        JTextField x1Field = new JTextField(5);
        controlPanel.add(x1Field);

        controlPanel.add(new JLabel("line Y1:"));
        JTextField y1Field = new JTextField(5);
        controlPanel.add(y1Field);

        controlPanel.add(new JLabel("line X2:"));
        JTextField x2Field = new JTextField(5);
        controlPanel.add(x2Field);

        controlPanel.add(new JLabel("line Y2:"));
        JTextField y2Field = new JTextField(5);
        controlPanel.add(y2Field);

        controlPanel.add(new JLabel("point x:"));
        JTextField xField = new JTextField(5);
        controlPanel.add(xField);

        controlPanel.add(new JLabel("point y"));
        JTextField yField = new JTextField(5);
        controlPanel.add(yField);

        controlPanel.add(new JLabel("radius"));
        JTextField radius = new JTextField(5);
        controlPanel.add(radius);


        f.add(controlPanel, BorderLayout.NORTH);

        f.setVisible(true);

        JButton gridButton = new JButton("Ширина сетки");
        menuBar.add(gridButton);

        Action gridAction = new AbstractAction("Ширина сетки") {
            public void actionPerformed(ActionEvent event) {
                try {
                    gridWidth = Integer.parseInt(JOptionPane.showInputDialog("Введите значение:"));
                    Graphics2D g2 = (Graphics2D) imag.getGraphics();
                    g2.setColor(Color.white);
                    g2.fillRect(0, 0, japan.getWidth(), japan.getHeight());
                    drawGrid(g2);
                    japan.repaint();
                } catch (Exception ignored) {
                }
            }
        };

        gridButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                gridAction.actionPerformed(event);
            }
        });
        JButton timeButton = new JButton("Посмотреть время");
        menuBar.add(timeButton);
        Action timeAction = new AbstractAction("Время") {
            public void actionPerformed(ActionEvent event) {
                try {
                    String message = "";
                    message += SbSTime != -1 ? "Пошаговый алгоритм: " + SbSTime + " ns\n" : "";
                    message += BrtTime != -1 ? "Алгоритм Брезенхема для построения отрезков: " + BrtTime + " ns\n" : "";
                    message += DDATime != -1 ? "ЦДА: " + DDATime + " ns\n" : "";
                    message += BrtCircleTime != -1 ? "Алгоритм Брезенхема для построения окружностей: " + BrtCircleTime + " ns\n" : "";

                    JOptionPane.showMessageDialog(null, message);
                } catch (Exception ignored) {
                }
            }
        };
        timeButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                timeAction.actionPerformed(event);
            }
        });

        JButton stepByStepButton = new JButton("Пошаговый алгоритм");
        menuBar.add(stepByStepButton);
        Action SbSAction = new AbstractAction("Пошаговая") {
            public void actionPerformed(ActionEvent event) {
                try {
                    int x1 = Integer.parseInt(x1Field.getText());
                    int y1 = Integer.parseInt(y1Field.getText());
                    int x2 = Integer.parseInt(x2Field.getText());
                    int y2 = Integer.parseInt(y2Field.getText());

                    startTime = System.nanoTime();
                    Algorithms.drawLineSbS((Graphics2D) imag.getGraphics(), x1, y1,
                            x2, y2, gridWidth);
                    endTime = System.nanoTime();
                    SbSTime = endTime - startTime;
                    japan.repaint();

                } catch (Exception ignored) {
                }
            }
        };
        stepByStepButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                SbSAction.actionPerformed(event);
            }
        });

        JButton DDAButton = new JButton("ЦДА");
        menuBar.add(DDAButton);
        Action DDAAction = new AbstractAction("ЦДА") {
            public void actionPerformed(ActionEvent event) {
                try {
                    int x1 = Integer.parseInt(x1Field.getText());
                    int y1 = Integer.parseInt(y1Field.getText());
                    int x2 = Integer.parseInt(x2Field.getText());
                    int y2 = Integer.parseInt(y2Field.getText());

                    startTime = System.nanoTime();
                    Algorithms.ddaAlgorithm((Graphics2D) imag.getGraphics(), x1, y1, x2, y2, gridWidth);
                    endTime = System.nanoTime();
                    DDATime = endTime - startTime;
                    japan.repaint();

                } catch (Exception ignored) {
                }
            }
        };
        DDAButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                DDAAction.actionPerformed(event);
            }
        });
        JButton Brezenhem = new JButton("Построить отрезок алгоритмом Брезенхема");
        menuBar.add(Brezenhem);
        Action BrAction = new AbstractAction("Брезенхем") {
            public void actionPerformed(ActionEvent event) {
                try {
                    int x1 = Integer.parseInt(x1Field.getText());
                    int y1 = Integer.parseInt(y1Field.getText());
                    int x2 = Integer.parseInt(x2Field.getText());
                    int y2 = Integer.parseInt(y2Field.getText());
                    if (x1 < 0 || x2 < 0 || y1 < 0 || y2 < 0) {
                        JOptionPane.showMessageDialog(null,
                                "Программа поддерживает отображение только для положительных координат," +
                                        " введите корректные числовые значения для всех координат.", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    startTime = System.nanoTime();
                    Algorithms.drawLineBr((Graphics2D) imag.getGraphics(), x1, y1,
                            x2, y2, gridWidth);
                    endTime = System.nanoTime();
                    BrtTime = endTime - startTime;
                    japan.repaint();


                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Пожалуйста, введите корректные числовые значения для всех координат.", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ignored) {
                }
            }
        };
        Brezenhem.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                BrAction.actionPerformed(event);
            }
        });
        JButton smoothLineButton = new JButton("Сглаженная линия");
        menuBar.add(smoothLineButton);

        smoothLineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    int x1 = Integer.parseInt(x1Field.getText());
                    int y1 = Integer.parseInt(y1Field.getText());
                    int x2 = Integer.parseInt(x2Field.getText());
                    int y2 = Integer.parseInt(y2Field.getText());

                    startTime = System.nanoTime();
                    Graphics2D g2 = (Graphics2D) imag.getGraphics();
                    Algorithms.drawWuLine(g2, x1, y1, x2, y2, gridWidth); // Используем метод для сглаженной линии
                    endTime = System.nanoTime();
                    BrtTime = endTime - startTime;
                    japan.repaint();

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Введите корректные числовые значения.", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Произошла ошибка: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        JButton circle = new JButton("Построить окружность алгоритмом Брезенхема");
        menuBar.add(circle);

        Action BrCirclAction = new AbstractAction("Брезенхем circle") {
            public void actionPerformed(ActionEvent event) {
                try {
                    int x = Integer.parseInt(xField.getText());
                    int y = Integer.parseInt(yField.getText());
                    int rad = Integer.parseInt(radius.getText());

                    startTime = System.nanoTime();
                    Algorithms.bresenhamCircle((Graphics2D) imag.getGraphics(), x, y, rad, gridWidth);
                    endTime = System.nanoTime();
                    BrtCircleTime = endTime - startTime;
                    japan.repaint();

                } catch (NumberFormatException ignored) {
                    JOptionPane.showMessageDialog(null, "Введите корректные числовые значения.", "Ошибка ввода", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        circle.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                BrCirclAction.actionPerformed(event);
            }
        });

        JButton cleanButton = new JButton("Очистить");
        menuBar.add(cleanButton);
        Action cleanAction = new AbstractAction("Очистить") {
            public void actionPerformed(ActionEvent event) {
                BufferedImage tempImage = new BufferedImage(japan.getWidth(), japan.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D d2 = tempImage.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, japan.getWidth(), japan.getHeight());
                width = japan.getWidth();
                height = japan.getHeight();
                d2.setColor(Color.black);
                d2.setStroke(new BasicStroke(1.0f));
                drawGrid(d2);
                imag = tempImage;
                japan.repaint();
            }
        };

        cleanButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent event) {
                cleanAction.actionPerformed(event);
            }
        });


        japan = new MyPanel();
        japan.setBounds(10, 50, 260, 260);
        japan.setBackground(Color.white);
        japan.setOpaque(true);
        f.add(japan);

        JToolBar toolbar = new JToolBar("Toolbar", JToolBar.VERTICAL);


        toolbar.setBounds(0, 0, 30, 300);
        f.add(toolbar);

        f.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                japan.setSize(f.getWidth() - 40, f.getHeight() - 80);
                BufferedImage tempImage = new BufferedImage(japan.getWidth(), japan.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D d2 = tempImage.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, japan.getWidth(), japan.getHeight());
                width = japan.getWidth();
                height = japan.getHeight();
                d2.setColor(Color.black);
                d2.setStroke(new BasicStroke(1.0f));
                drawGrid(d2);
                imag = tempImage;
                japan.repaint();
            }
        });
        f.setLayout(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }


    public void drawGrid(Graphics2D g2) {
        if (gridWidth <= 0) return;
        g2.setColor(Color.black);
        g2.setStroke(new BasicStroke(1.0f));
        for (int x = 0; x <= width; x += gridWidth) {
            g2.drawLine(x, 0, x, height);
        }

        for (int y = 0; y <= height; y += gridWidth) {
            g2.drawLine(0, y, width, y);
        }

        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(3.0f));

        g2.drawLine(0, 0, width, 0);
        g2.drawLine(width - 15, 10, width - 5, 0);

        g2.drawLine(0, 0, 0, height);
        g2.drawLine(10, height - 90, 0, height - 80);


        int x0 = gridWidth;
        int label0 = x0 / gridWidth;
        g2.drawLine(x0, 0, x0, 5);
        g2.drawString(Integer.toString(label0), x0 + gridWidth / 3, 20);

        int tickSpacingX = width / 4;
        for (int i = 1; i < 4; i++) {
            int x = (i * tickSpacingX / gridWidth) * gridWidth;
            int label = x / gridWidth;
            g2.drawLine(x, 0, x, 5);
            g2.drawString(Integer.toString(label), x + gridWidth / 3, 20);
        }

        int y0 = gridWidth;
        label0 = y0 / gridWidth;
        g2.drawLine(0, y0, 5, y0);
        g2.drawString(Integer.toString(label0), 10, y0 + gridWidth / 3);

        int tickSpacingY = height / 4;
        for (int i = 1; i < 4; i++) {
            int y = (i * tickSpacingY / gridWidth) * gridWidth;
            int label = y / gridWidth;
            g2.drawLine(0, y, 5, y);
            g2.drawString(Integer.toString(label), 10, y + gridWidth / 3);
        }

        g2.drawString("X", width - 20, 20);
        g2.drawString("Y", 10, height - 90);

        g2.setStroke(new BasicStroke(1.0f));
        g2.setColor(Color.black);
    }


    class MyFrame extends JFrame {
        public MyFrame(String title) {
            super(title);
        }

        public void paint(Graphics g) {
            super.paint(g);
        }
    }

    class MyPanel extends JPanel {
        public MyPanel() {
        }

        public void paintComponent(Graphics g) {
            if (imag == null) {
                imag = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D d2 = imag.createGraphics();
                d2.setColor(Color.white);
                d2.fillRect(0, 0, this.getWidth(), this.getHeight());
                drawGrid(d2);
            }
            super.paintComponent(g);
            g.drawImage(imag, 0, 0, this);
        }
    }
}